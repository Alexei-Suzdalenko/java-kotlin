package factura.factura.assets
class Part {
    constructor ()

    var id_part     = ""
    var id_client   = ""
    var name_client = ""
    var fecha       = ""
    var start_part  = ""
    var finish_part = ""
    var work_part   = ""
    var auto_part   = ""
    var puth_image  = ""
    var price_part  = ""

    constructor   (
                  puth_image : String,
                  id_part    : String,
                  id_client  : String,
                  name_client: String,
                  fecha      : String,
                  start_part : String,
                  finish_part: String,
                  work_part  : String,
                  auto_part  : String,
                  price_part : String
                  ){
        this.puth_image  = puth_image
        this.id_part     = id_part
        this.id_client   = id_client
        this.name_client = name_client
        this.fecha       = fecha
        this.start_part  = start_part
        this.finish_part = finish_part
        this.work_part   = work_part
        this.auto_part   = auto_part
        this.price_part  = price_part
    }
}