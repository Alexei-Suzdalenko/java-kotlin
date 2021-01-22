package factura.factura
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import factura.factura.util.Article
import factura.factura.util.CustomInvoiceView
import factura.factura.util.Helper
import kotlinx.android.synthetic.main.view_invoice.*
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat

class ViewInvoice : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_invoice)

      MobileAds.initialize(this) {}
      mAdView = findViewById(R.id.adView)
      val adRequest = AdRequest.Builder().build()
      mAdView.loadAd(adRequest)
      mAdView.adListener = object: AdListener() {
          override fun onAdLoaded() {
              relativeLayoutGeneral.post{
                  val heidhtAdview = mAdView.height + 7
                  val layoutParams = scrollView.layoutParams as RelativeLayout.LayoutParams
                  layoutParams.setMargins(0,7, 0,heidhtAdview)
                  scrollView.layoutParams = layoutParams
              }
          }
      }




        //                    http://tutorials.jenkov.com/java-itext/paragraph.html
        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)


      val infoBildTo = sharedPreferences.getString("bill_to_client", "").toString()
      bill_to.text = infoBildTo

      val info1 = sharedPreferences.getString("invoiceTop", "").toString()
      textView_invoice.text = info1

      val info2 = sharedPreferences.getString("invoiceData", "").toString()
      data_invoice.text = info2


      val info3  = sharedPreferences.getString("save_subtotal", "").toString()
      set_subtotal.text = info3
      val info4= sharedPreferences.getString("save_iva", "").toString()
      set_iva.text = info4
      val info5 = sharedPreferences.getString("save_total", "").toString()
      set_price_total.text = info5


      val stringListArticle = sharedPreferences.getString("items", "no_data")
      if(stringListArticle == "no_data") listViewViewInvoice.visibility = View.GONE
      else{
          listViewViewInvoice.visibility = View.VISIBLE
          val gabon = Gson()
          val listArticle  = gabon.fromJson(stringListArticle, Array<Article>::class.java).asList()
          val l = listArticle.toMutableList()
          listViewViewInvoice.adapter = CustomInvoiceView(this, l)
          Helper.getListViewSize(listViewViewInvoice)
      }

        compartirDescargar.setOnClickListener{
            showInvoice()
        }

    }



  private fun showInvoice(){
      var subTotal = 0.0
      try{
          val l = System.currentTimeMillis().toString() + ".pdf"
          val filePuth = getExternalFilesDir(null).toString()
          val fi = File(filePuth, "shareTestFileDireccion")
          if(!fi.exists()) fi.mkdir()
          val f = File(fi, l)
          //  f.createNewFile()

          val document = Document(PageSize.A4, 45f, 45f, 45f, 45f)
          PdfWriter.getInstance(document, FileOutputStream(f))

          document.open()
          val padding = 5F
          val fntSize = 30f
          val lineSpacing = 20f
          val title = resources.getString(R.string.app_name)
          var p = Paragraph(Phrase(lineSpacing, title, FontFactory.getFont(FontFactory.COURIER, fntSize)))
          var tableOne1 = PdfPTable(1)
          tableOne1.widthPercentage = 100F
          val cellO = PdfPCell(p)
          cellO.horizontalAlignment = Element.ALIGN_RIGHT
          cellO.setPadding (padding)
          cellO.border = Rectangle.NO_BORDER
          tableOne1.addCell(cellO)
          document.add(tableOne1)


          val companyName = sharedPreferences.getString("name_org", "").toString()
          val bold = Font(Font.FontFamily.TIMES_ROMAN, 14F, Font.BOLD)
          val tableOne = PdfPTable(1)
          tableOne.widthPercentage = 100F
          p = Paragraph(companyName, bold)
          val cellOne = PdfPCell(p)
          cellOne.border = Rectangle.NO_BORDER
          tableOne.addCell(cellOne)
          document.add(tableOne)


          val youName = sharedPreferences.getString("your_name", "").toString()
          val normal = Font(Font.FontFamily.TIMES_ROMAN, 13F, Font.NORMAL)
          p = Paragraph(youName, normal)
          tableOne1 = PdfPTable(1)
          tableOne1.widthPercentage = 100F
          var cellOne1 = PdfPCell(p)
          cellOne1.setPadding (padding)
          cellOne1.border = Rectangle.NO_BORDER
          tableOne1.addCell(cellOne1)
          document.add(tableOne1)


          val youAddress = sharedPreferences.getString("company_address", "").toString()
          p = Paragraph(youAddress, normal)
          tableOne1 = PdfPTable(1)
          tableOne1.widthPercentage = 100F
          cellOne1 = PdfPCell(p)
          cellOne1.setPadding (padding)
          cellOne1.border = Rectangle.NO_BORDER
          tableOne1.addCell(cellOne1)
          document.add(tableOne1)


          val yourSiteStateZip = sharedPreferences.getString("state", "").toString() + " " +  sharedPreferences.getString("city", "").toString() + " " + sharedPreferences.getString("zip", "").toString()
          p = Paragraph(yourSiteStateZip, normal)
          tableOne1 = PdfPTable(1)
          tableOne1.widthPercentage = 100F
          cellOne1 = PdfPCell(p)
          cellOne1.setPadding (padding)
          cellOne1.border = Rectangle.NO_BORDER
          tableOne1.addCell(cellOne1)
          document.add(tableOne1)


          ///////// start to client data first /////////
          val billTo =  resources.getString(R.string.bT) + " " + sharedPreferences.getString("bill_to_client", "").toString()
          val p1 = Paragraph(billTo, bold)
          val columnWidths = floatArrayOf(5f, 1f, 1f)
          val table0 = PdfPTable(3)
          table0.spacingBefore = 22F
          table0.widthPercentage = 100F
          table0.setWidths(columnWidths)
          var cell = PdfPCell(p1)
          cell.border = Rectangle.NO_BORDER
          table0.addCell(cell)
          val invoice = resources.getString(R.string.app_name_small)
          p = Paragraph(invoice, normal)
          cell = PdfPCell(p)
          cell.border = Rectangle.NO_BORDER
          table0.addCell(cell)
          val invoiceData = sharedPreferences.getString("invoiceTop", "").toString()
          p = Paragraph(invoiceData, normal)
          cell = PdfPCell(p)
          cell.horizontalAlignment = Element.ALIGN_RIGHT
          cell.border = Rectangle.NO_BORDER
          table0.addCell(cell)
          document.add(table0)



          ///////// start to client data second /////////
          val table4 = PdfPTable(3)
          table4.widthPercentage = 100F
          table4.setWidths(columnWidths)
          val clientCompany = sharedPreferences.getString("company_name_client", "").toString()
          p = Paragraph(clientCompany, normal)
          cell = PdfPCell(p)
          cell.border = Rectangle.NO_BORDER
          table4.addCell(cell)
          p = Paragraph(" ", normal)
          cell = PdfPCell(p)
          cell.border = Rectangle.NO_BORDER
          table4.addCell(cell)
          p = Paragraph(" ", normal)
          cell = PdfPCell(p)
          cell.horizontalAlignment = Element.ALIGN_RIGHT
          cell.border = Rectangle.NO_BORDER
          table4.addCell(cell)
          document.add(table4)

          ///////// start to client data three /////////

          val table3 = PdfPTable(3)
          table3.widthPercentage = 100F
          table3.setWidths(columnWidths)
          val addressClient = sharedPreferences.getString("address_client", "").toString()
          p = Paragraph(addressClient, normal)
          cell = PdfPCell(p)
          cell.border = Rectangle.NO_BORDER
          table3.addCell(cell)
          val date = resources.getString(R.string.fecha)
          p = Paragraph(date, normal)
          cell = PdfPCell(p)
          cell.border = Rectangle.NO_BORDER
          table3.addCell(cell)
          val dateData = sharedPreferences.getString("invoiceData", "").toString()
          p = Paragraph(dateData, normal)
          cell = PdfPCell(p)
          cell.horizontalAlignment = Element.ALIGN_RIGHT
          cell.border = Rectangle.NO_BORDER
          table3.addCell(cell)
          document.add(table3)


          val cityStateZip = sharedPreferences.getString("city_client", "").toString() + " " + sharedPreferences.getString("state_client", "").toString() + " " +sharedPreferences.getString("zip_client", "").toString()
          val tableZip = PdfPTable(1)
          tableZip.widthPercentage = 100F
          p = Paragraph(cityStateZip, normal)
          val cellOneZip = PdfPCell(p)
          cellOneZip.border = Rectangle.NO_BORDER
          tableZip.addCell(cellOneZip)
          document.add(tableZip)

          val clientCountry = sharedPreferences.getString("country_client", "").toString()
          val tableZip2 = PdfPTable(1)
          tableZip2.widthPercentage = 100F
          p = Paragraph(clientCountry, normal)
          val cellOneZip2 = PdfPCell(p)
          cellOneZip2.border = Rectangle.NO_BORDER
          tableZip2.addCell(cellOneZip2)
          document.add(tableZip2)


          // black table ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
          val columnBlackWidths = floatArrayOf(4f, 1f, 1f, 2f)
          var itemDescription = resources.getString(R.string.item_description)
          val tableBlack = PdfPTable(4)
          tableBlack.spacingBefore = 10f
          //  tableBlack.set
          tableBlack.widthPercentage = 100F
          //    cellBlack.border = Rectangle.NO_BORDER
          tableBlack.setWidths(columnBlackWidths)
          p = Paragraph(itemDescription, bold)
          var cellBlack = PdfPCell(p)
          cellBlack.setPadding (padding)
          tableBlack.addCell(cellBlack)

          var candida = resources.getString(R.string.cant)
          p = Paragraph(candida, bold)
          cellBlack = PdfPCell(p)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          cellBlack.setPadding (padding)
          tableBlack.addCell(cellBlack)

          var price = resources.getString(R.string.price)
          p = Paragraph(price, bold)
          cellBlack = PdfPCell(p)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          cellBlack.setPadding (padding)
          tableBlack.addCell(cellBlack)

          val amount = resources.getString(R.string.amount)
          p = Paragraph(amount, bold)
          cellBlack = PdfPCell(p)
          cellBlack.setPadding (padding)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          tableBlack.addCell(cellBlack)
          // document.add(tableBlack)
          /////////////////// list money and product /////////////////////////

          val stringListArticle = sharedPreferences.getString("items", "no_data")
          val gabon = Gson()
          val listArticle  = gabon.fromJson(stringListArticle, Array<Article>::class.java).asList()

          val rowsTable = listArticle.size - 1
          val farmstead = DecimalFormat("0.00")

          for(i in 0..rowsTable){
              itemDescription = listArticle[i].name_article
              p = Paragraph(itemDescription, normal)
              cellBlack = PdfPCell(p)
              cellBlack.border = Rectangle.NO_BORDER
              cellBlack.setPadding (padding)
              tableBlack.addCell(cellBlack)

              candida = listArticle[i].quantity.toString()
              p = Paragraph(candida, normal)
              cellBlack = PdfPCell(p)
              cellBlack.border = Rectangle.NO_BORDER
              cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
              cellBlack.setPadding (padding)
              tableBlack.addCell(cellBlack)

              price = listArticle[i].cost.toString()
              p = Paragraph(price, normal)
              cellBlack = PdfPCell(p)
              cellBlack.border = Rectangle.NO_BORDER
              cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
              cellBlack.setPadding (padding)
              tableBlack.addCell(cellBlack)

              val aMount = listArticle[i].totalItem
              subTotal += aMount
              val amountM = farmstead.format(aMount).toString()
              p = Paragraph(amountM, normal)
              cellBlack = PdfPCell(p)
              cellBlack.border = Rectangle.NO_BORDER
              cellBlack.setPadding (padding)
              cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
              tableBlack.addCell(cellBlack)
          }
          document.add(tableBlack)

          /////////////////////////////////  subtotal ///////////////////////////////////////////////////////
          val column = floatArrayOf(1f, 1f, 1f)
          val subtotalName = resources.getString(R.string.subtotalName)
          val subTotalTable = PdfPTable(3)
          subTotalTable.widthPercentage = 100F
          //    cellBlack.border = Rectangle.NO_BORDER
          subTotalTable.setWidths(column)
          p = Paragraph(" ", normal)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          subTotalTable.addCell(cellBlack)

          p = Paragraph(subtotalName, normal)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          cellBlack.setPadding (padding)
          subTotalTable.addCell(cellBlack)

          val s = farmstead.format(subTotal).toString()
          p = Paragraph(s, bold)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          subTotalTable.addCell(cellBlack)

          document.add(subTotalTable)
///////////////////////////////////////////////////////////////////////////////////////////////////////
//////////// iva /////////////////////////////////////////////////////////////////
          val cilium = floatArrayOf(1f, 1f, 1f)
          val iva = resources.getString(R.string.iva)
          val ivaTable = PdfPTable(3)
          ivaTable.widthPercentage = 100F
          //    cellBlack.border = Rectangle.NO_BORDER
          ivaTable.setWidths(cilium)
          p = Paragraph(" ", normal)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          ivaTable.addCell(cellBlack)

          p = Paragraph(iva, normal)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          cellBlack.setPadding (padding)
          ivaTable.addCell(cellBlack)

          val ivaValue = sharedPreferences.getString("save_iva", "0.0")
          p = Paragraph(ivaValue, bold)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          ivaTable.addCell(cellBlack)

          document.add(ivaTable)

////////////////////////////////////////////////////////////////////////////////////
/// total price ////////////////////////////////////////////////////////

          val total = resources.getString(R.string.total)
          val totalTable = PdfPTable(3)
          totalTable.widthPercentage = 100F
          //    cellBlack.border = Rectangle.NO_BORDER
          totalTable.setWidths(cilium)
          p = Paragraph(" ", normal)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          totalTable.addCell(cellBlack)

          p = Paragraph(total, bold)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          cellBlack.setPadding (padding)
          totalTable.addCell(cellBlack)

          val totalValue = sharedPreferences.getString("save_total", "Error PDF")
          p = Paragraph(totalValue, bold)
          cellBlack = PdfPCell(p)
          cellBlack.border = Rectangle.NO_BORDER
          cellBlack.setPadding (padding)
          cellBlack.horizontalAlignment = Element.ALIGN_RIGHT
          totalTable.addCell(cellBlack)

          document.add(totalTable)
//////////////////////// note -> term and condition's        ///////////////////////////////////////////////////////////
          val note = resources.getString(R.string.notes)
          p = Paragraph(note, normal)
          document.add(p)
          val noteText = sharedPreferences.getString("notes", "").toString()
          p = Paragraph(noteText, normal)
          document.add(p)

          val terminus = resources.getString(R.string.terms_and_conditions)
          p = Paragraph(terminus, normal)
          document.add(p)
          val terminusText = sharedPreferences.getString("termAndCondiciones", "").toString()
          p = Paragraph(terminusText, normal)
          document.add(p)



          ////////////////////////////////////////////////////////////////////////////////



          document.close()

          val path = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", f)
          Log.d("tag", "view() Method path--> $path")

          val pdfIntent = Intent(Intent.ACTION_VIEW)
          pdfIntent.setDataAndType(path, "application/pdf")
          pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
          pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
          startActivity(pdfIntent)


      } catch (e:Exception) {
          Log.d("tag", "E R R O R === " + e.message)
      }

  }
}

