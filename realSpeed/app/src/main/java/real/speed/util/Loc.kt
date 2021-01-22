package real.speed.util

import android.location.Location

class Loc(l: Location?, private var bUseMetricsUnits: Boolean) : Location(l) {

    private fun getUseMetricUnits() : Boolean{
        return bUseMetricsUnits
    }

    fun setUserMetricUnits(bUseMetricsUnits: Boolean){
        this.bUseMetricsUnits = bUseMetricsUnits
    }

    override fun distanceTo(dest: Location?): Float {
        var nDistance: Float = super.distanceTo(dest)
        if(!this.getUseMetricUnits()){
            // convert meter to feet
            nDistance *= 3.28083989501312F
        }
        return nDistance
    }

    override fun getAltitude(): Double {
        var nAltitude: Double = super.getAltitude()
        if(!this.getUseMetricUnits()){
            // convert meter to feet
            nAltitude *= 3.28083989501312
        }
        return nAltitude
    }

    override fun getSpeed(): Float {
        var nSpeed = super.getSpeed() * 3.6f
        if(!this.getUseMetricUnits()){
            // convert meter/secons to miles/hour
            nSpeed = super.getSpeed() * 2.23693629f
        }
        return nSpeed
    }

    override fun getAccuracy(): Float {
        var nAccuracy: Float = super.getAccuracy()
        if(!this.getUseMetricUnits()){
            // convert meter to feet
            nAccuracy *= 3.28083989501312f
        }
        return nAccuracy
    }
}