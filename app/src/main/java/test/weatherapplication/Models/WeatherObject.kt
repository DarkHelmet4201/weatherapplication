package test.weatherapplication.Models

import java.io.Serializable

/**
 * Created by MyLaptop on 5/16/18.
 */



class WeatherObject : Serializable
{
    var id : Int? = 0

    var main : String? = ""

    var description : String? = ""

    var icon : String? = ""
}