package test.weatherapplication.Models

/**
 * Created by MyLaptop on 5/16/18.
 */


class UrlUtil
{
    val baseURL : String = "api.openweathermap.org/data/2.5/weather"

    val TIMEOUT_CONNECTION : Int = 180 * 1000

    val TIMEOUT_READ : Int = 180 * 1000

    fun getUrlString(url : String, vars : Map<String, String>)
    {
        var removeWhiteSpace = url.replace(" ", "+")

    }
}