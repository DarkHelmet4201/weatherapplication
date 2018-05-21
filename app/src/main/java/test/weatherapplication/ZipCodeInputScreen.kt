package test.weatherapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import org.jetbrains.anko.doAsync
import test.weatherapplication.Models.BaseObject
import test.weatherapplication.utilities.Util
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import java.net.URLConnection
import java.lang.reflect.Type
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.uiThread

class ZipCodeInputScreen : AppCompatActivity() {

    companion object {
        //Trying to use this in a unit test to check if entered zip code is valid
        fun testZipCode(zip : String) : Boolean
        {
            var regex = Regex("^\\d{5}$")
            return regex.matches(zip)

        }
    }

    val url: String = "http://api.openweathermap.org/data/2.5/weather?"

    //api.openweathermap.org/data/2.5/weather?zip=18974,us&appid=d25afced3ef29a5ee0aa95d4b0ac2145

    val appIDKey: String = "d25afced3ef29a5ee0aa95d4b0ac2145" //Need to apply for one of these

    val zipKey: String = "zip"

    val idKey: String = "appid"

    val unitsKey : String = "units=imperial"

    lateinit var zipCodeTextField: EditText

    lateinit var collectDataButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zip_code_input_screen)




        zipCodeTextField = this.findViewById(R.id.zip_code_field)
        zipCodeTextField.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId:Int, event: KeyEvent?):Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    Util.hideSoftKeyboard(this@ZipCodeInputScreen)
                    return true
                }
                return false
            }
        })

        collectDataButton = this.findViewById(R.id.collect_data_button)
        collectDataButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                runDataCollection()
            }
        })
    }

    fun runDataCollection() {
        if (zipCodeTextField.text.isNotEmpty()) {

            /*
             ^\d{5}(?:[-\s]\d{4})?$

             ^       = Start of String
             \d{5}   = Match for 5 digits
             (?:...) = Look for grouping
             [-\s]   = test for space or hyphen
             \d{4}   = Match for 4 digits
             ? after = pattern before is optional
             $       = end of the string
             */

            //This checks to see of the user entered a valid 5 digit zip code in the text view
            var regex = Regex("^\\d{5}$")
            var isValidZip = regex.matches(zipCodeTextField.text.toString())
            if(isValidZip)
            {

                val zipcode: String = zipCodeTextField.text.toString()
                val urlString = "$url$zipKey=$zipcode,us&$idKey=$appIDKey&$unitsKey"
                lateinit var stream: InputStream
                doAsync {
                    try {
                        val url: URL = URL(urlString)
                        var connection: URLConnection = url.openConnection()
                        connection.connectTimeout = 180000
                        connection.readTimeout = 180000


                        stream = connection.getInputStream()


                    } catch (e: Exception) {
                        uiThread {
                            //val dataErrorToast = Toast.makeText(this@ZipCodeInputScreen, "There was an error getting your weather data. Please try again later.", Toast.LENGTH_LONG)
                            //dataErrorToast.show()
                            createPopup("There was an error getting your weather data. Please try again later.")
                        }
                    } finally {
                        val jsonString = getJson(stream)
                        parseData(jsonString)
                    }
                }
            }
            else
            {
                //val invalidZipToast = Toast.makeText(this@ZipCodeInputScreen, "An invalid Zip Code was entered. Please enter a valid Zip Code to continue.", Toast.LENGTH_LONG)
                //invalidZipToast.show()
                createPopup("An invalid Zip Code was entered. Please enter a valid Zip Code to continue.")
            }
        } else {
            createPopup("Please enter a valid Zip Code to continue")
        }
    }

    fun getJson(stream: InputStream) : String
    {
        val reader: InputStreamReader = InputStreamReader(stream)

        val jsonString : String = readAllFromReader(reader)

        return jsonString
    }


    fun readAllFromReader(reader : InputStreamReader) : String
    {
        val sb : StringBuilder = StringBuilder()
        var cp : Int = 0

        //Creates a json string from read buffer 1 character at a time until all characters exhausted
        try{
            while ({cp = reader.read(); cp}() != -1)
            {
                sb.append(cp.toChar())
            }
        }catch(e : IOException)
        {
            createPopup("There was a problem. Please try again")
        }

        return sb.toString()
    }

    fun parseData(data : String)
    {
        //parses the json string into an object
        val gson = Gson()
        var weatherData: BaseObject? = null
        try {


            weatherData = gson.fromJson(data, BaseObject::class.java)

        }catch (e : Exception){
            createPopup("There was a problem. Please try again")
        }
        finally {
            runOnUiThread {
                val weatherInfoScreen = Intent(this, WeatherInfoScreen::class.java)
                weatherInfoScreen.putExtra(WeatherInfoScreen().WEATHER_DATA, weatherData!!)
                startActivity(weatherInfoScreen)
            }
        }
        //http://openweathermap.org/img/w/10d.png  need to replace 10d.png with needed icon
        //print("")

    }

    fun createPopup(message : String)
    {
        val errorPopup = AlertDialog.Builder(this)
        errorPopup.setTitle("Attention!")
        errorPopup.setMessage(message)
        errorPopup.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->

        })
        errorPopup.show()
    }
}
