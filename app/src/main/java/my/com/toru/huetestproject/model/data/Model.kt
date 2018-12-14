package my.com.toru.huetestproject.model.data


//{"on":true, "sat":254, "bri":254,"hue":10000}

data class Model(private val on:Boolean,
                 private val stat:Int?,
                 private val bri:Int?,
                 private val hue:Int = 10000)
