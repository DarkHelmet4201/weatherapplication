package test.weatherapplication.Models

import java.io.Serializable

/**
 * Created by MyLaptop on 5/16/18.
 */


class BaseObject : Serializable
{
    lateinit var weather : Array<WeatherObject>

    lateinit var wind : WindObject

    lateinit var clouds : CloudsObject

    lateinit var main : MainObject

    lateinit var sys : SystemObject

    var dt : Int? = 0

    var name : String? = ""

    var cod : Int? = 0
}