package test.weatherapplication.Models

import java.io.Serializable

/**
 * Created by MyLaptop on 5/16/18.
 */


class MainObject : Serializable
{
    var temp : Double? = 0.0

    var pressure : Int? = 0

    var humidity : Int? = 0

    var temp_min : Double? = 0.0

    var temp_max : Double? = 0.0


}