package factura.factura.assets
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import factura.factura.R

class DataBase (private val c : Context) : SQLiteOpenHelper(c, databaseName, null, dataBaseVersion) {
    companion object {
        private val databaseName = "putin_juilo_suk"
        private val dataBaseVersion = 1

        private val table_name = "person"
        private val col_id = "id"
        private val col_name = "name"
        private val col_tlf = "tlf"

        private val table_name_part = "part"
        private val puth_image = "puth_image"
        private val id_part = "id_part"
        private val id_client = "id_client"
        private val name_client = "name_client"
        private val fecha = "fecha"
        private val start_part = "start_part"
        private val finish_part = "finish_part"
        private val work_part = "work_part"
        private val auto_part = "auto_part"
        private val price_part = "price_part"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        var query =
            ("CREATE TABLE $table_name ($col_id INTEGER PRIMARY KEY AUTOINCREMENT, $col_name TEXT, $col_tlf TEXT)")
        sqLiteDatabase?.execSQL(query)
        query =
            ("CREATE TABLE $table_name_part ($id_part INTEGER PRIMARY KEY AUTOINCREMENT, $puth_image TEXT, $id_client TEXT, $name_client TEXT, $fecha TEXT, $start_part TEXT, $finish_part TEXT, $work_part TEXT, $auto_part TEXT, $price_part TEXT )")
        sqLiteDatabase?.execSQL(query)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase!!.execSQL("DROP TABLE IF EXISTS $table_name")
        onCreate(sqLiteDatabase)
    }

    @SuppressLint("Recycle")
    fun readData(): List<DataPerson> {
        val listPersons = ArrayList<DataPerson>()
        val selectQuery = "SELECT * FROM $table_name"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val person = DataPerson()
                person.id = cursor.getString(cursor.getColumnIndex(col_id))
                person.name = cursor.getString(cursor.getColumnIndex(col_name))
                person.tlf = cursor.getString(cursor.getColumnIndex(col_tlf))
                listPersons.add(person)
            } while (cursor.moveToNext())
        }
        db.close()
        return listPersons
    }

    fun addPerson(dataPerson: DataPerson) {
        val sqLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(col_name, dataPerson.name)
        values.put(col_tlf, dataPerson.tlf)
        val result = sqLiteDatabase.insert(table_name, null, values)
        val clientSaved = dataPerson.name + " " + c.resources.getString(R.string.clientSaved)
        val error = c.resources.getString(R.string.error) + " " + dataPerson.name
        if (result == (-1).toLong()) Toast.makeText(c, error, Toast.LENGTH_LONG).show()
        else Toast.makeText(c, clientSaved, Toast.LENGTH_LONG).show()
        sqLiteDatabase.close()
    }

    fun updatePerson(dataPerson: DataPerson) {
        val sqLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(col_name, dataPerson.name)
        values.put(col_tlf, dataPerson.tlf)
        val result = sqLiteDatabase.update(table_name, values, "$col_id=?", arrayOf(dataPerson.id))
        val clientModified =  c.resources.getString(R.string.client_modified)
        if (result == 1) Toast.makeText(c, clientModified, Toast.LENGTH_LONG).show()
        else Toast.makeText(c, "¡ Error !", Toast.LENGTH_LONG).show()
    }

    fun updateParteUriImage(id: String) {
        val sqLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put("puth_image", "0")
        sqLiteDatabase.update(table_name_part, cv, "$id_part=?", arrayOf(id))
    }

    fun deletePerson(dataPerson: DataPerson) {
        val sqLiteDatabase = this.writableDatabase
        val result = sqLiteDatabase.delete(table_name, "$col_id=?", arrayOf(dataPerson.id))
        val clientDeleted = c.resources.getString(R.string.client_deleted)
        if( result == 1) Toast.makeText(c, clientDeleted, Toast.LENGTH_LONG).show()
        else Toast.makeText(c, "Error", Toast.LENGTH_LONG).show()
        sqLiteDatabase.close()
    }

    fun deletePart(id: String) {
        val sqLiteDatabase = this.writableDatabase
        val result = sqLiteDatabase.delete(table_name_part, "$id_part=?", arrayOf(id))
        val partDeleted = c.resources.getString(R.string.part_deleted)
        if (result == 1 )  Toast.makeText(c, partDeleted, Toast.LENGTH_LONG).show()
        else Toast.makeText(c, "Error", Toast.LENGTH_LONG).show()
        sqLiteDatabase.close()
    }

     fun addPart(p: Part){
         val sqLiteDatabase = this.writableDatabase
         val values         = ContentValues()
             values.put(puth_image, p.puth_image)
             values.put(id_client,  p.id_client)
             values.put(name_client, p.name_client)
             values.put(fecha, p.fecha)
             values.put(start_part, p.start_part)
             values.put(finish_part, p.finish_part)
             values.put(work_part, p.work_part)
             values.put(auto_part, p.auto_part)
             values.put(price_part, p.price_part)
         val result = sqLiteDatabase.insert(table_name_part, null, values)
         val partSave = c.resources.getString(R.string.partSave)
         val partSaveError = c.resources.getString(R.string.partSaveError)
         if (result == (-1).toLong()) Toast.makeText(c, partSaveError, Toast.LENGTH_LONG).show()
         else Toast.makeText(c, partSave, Toast.LENGTH_LONG).show()
         sqLiteDatabase.close()
     }

     @SuppressLint("Recycle")
     fun readDataPart() : List<Part>{
         val listPart = ArrayList<Part>()
         val query = "SELECT * FROM $table_name_part"
         val sqLiteDatabase = this.readableDatabase
         val cursor = sqLiteDatabase.rawQuery(query, null)
         if (cursor.moveToFirst()){
             do{
                 val p = Part()
                 p.puth_image = cursor.getString(cursor.getColumnIndex(puth_image))
                 p.id_part    = cursor.getString(cursor.getColumnIndex(id_part))
                 p.id_client  = cursor.getString(cursor.getColumnIndex(id_client))
                 p.name_client= cursor.getString(cursor.getColumnIndex(name_client))
                 p.fecha      = cursor.getString(cursor.getColumnIndex(fecha))
                 p.start_part = cursor.getString(cursor.getColumnIndex(start_part))
                 p.finish_part= cursor.getString(cursor.getColumnIndex(finish_part))
                 p.work_part  = cursor.getString(cursor.getColumnIndex(work_part))
                 p.auto_part  = cursor.getString(cursor.getColumnIndex(auto_part))
                 p.price_part = cursor.getString(cursor.getColumnIndex(price_part))
                 listPart.add(p)
             } while (cursor.moveToNext())
         }
         sqLiteDatabase.close()
         return listPart
     }

    fun updatePart(p: Part){
        val sqLiteDatabase = this.writableDatabase
        val values         = ContentValues()
        values.put(puth_image, p.puth_image)
        values.put(id_client,  p.id_client)
        values.put(name_client, p.name_client)
        values.put(fecha, p.fecha)
        values.put(start_part, p.start_part)
        values.put(finish_part, p.finish_part)
        values.put(work_part, p.work_part)
        values.put(auto_part, p.auto_part)
        values.put(price_part, p.price_part)
        val result = sqLiteDatabase.update(table_name_part, values, "$id_part=?", arrayOf(p.id_part))
        val partSave = c.resources.getString(R.string.partSave)
        val partSaveError = c.resources.getString(R.string.partSaveError)
        if (result == 1) Toast.makeText(c, partSave, Toast.LENGTH_LONG).show()
        else Toast.makeText(c, partSaveError, Toast.LENGTH_LONG).show()
    }

    fun getImageSrc(id: String): String? {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $table_name_part WHERE $id_part = ?"
        db.rawQuery(selectQuery, arrayOf(id)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Part()
                result.puth_image  = it.getString(it.getColumnIndex(puth_image))
                return result.puth_image
            }
        }
        return null
    }
}

// sqLiteDatabase.update(table_name_part, cv, "id_part=$id_part", null)



















































































