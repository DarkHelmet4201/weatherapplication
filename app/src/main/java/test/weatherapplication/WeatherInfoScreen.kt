package test.weatherapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import org.w3c.dom.Text
import test.weatherapplication.Models.BaseObject
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context
import android.widget.*

/**
 * Created by MyLaptop on 5/17/18.
 */


class WeatherInfoScreen : AppCompatActivity()
{

    val WEATHER_DATA : String = "weather_data"
    var weatherObject : BaseObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.weather_result_screen)
        val intent = this.intent

        lateinit var view : View
        lateinit var toolbar : android.support.v7.widget.Toolbar


        val menuTitles : Array<String> = arrayOf("One", "Two", "Three", "Four", "Five", "Six")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menuTitles)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toolbar = this.findViewById(R.id.toolbar)


        val spinner = this.findViewById<Spinner>(R.id.spinner_menu)
        spinner.adapter = arrayAdapter
        spinner.visibility = View.GONE

        this.setSupportActionBar(toolbar)
        val bar : ActionBar = this.supportActionBar!!
        bar.setHomeButtonEnabled(false)
        bar.setDisplayShowTitleEnabled(false)
        bar.setDisplayShowCustomEnabled(true)

        val inflator : LayoutInflater = this.layoutInflater
        val menuInflator : MenuInflater = this.menuInflater
        view = inflator.inflate(R.layout.toolbar_layout, null)
        toolbar.addView(view, Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT))




        val hamburgerButton : Button = view.findViewById(R.id.hamburger_menu_button)
        hamburgerButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                //This unfortunately needs 2 clicks when the spinner is visible to fire and hide the spinner
                if(spinner.visibility == View.GONE)
                {
                    spinner.visibility = View.VISIBLE
                    spinner.performClick()

                }
                else
                {
                    spinner.visibility = View.GONE
                }
            }
        })

        weatherObject = intent.getSerializableExtra(WEATHER_DATA) as BaseObject

        if(weatherObject != null) {

            val weatherTypeTextField: TextView = this.findViewById(R.id.weather_type_field)

            if (weatherObject!!.weather != null) {
                if (weatherObject!!.weather[0].main != null) {
                    val weather = weatherObject!!.weather!![0].main
                    weatherTypeTextField.text = weather
                } else {
                    weatherTypeTextField.text = this.getText(R.string.not_available)
                }
            } else {
                weatherTypeTextField.text = this.getText(R.string.not_available)
            }

            val weatherInfoTextField: TextView = this.findViewById(R.id.weather_info_field)

            if (weatherObject!!.weather != null) {
                var tempString: String = ""
                if (weatherObject!!.main!!.temp_min != null) {
                    tempString += "Low " + weatherObject!!.main!!.temp_min!!.toInt() + "F. "
                }
                if (weatherObject!!.main!!.temp_max != null) {
                    tempString += "High " + weatherObject!!.main!!.temp_max!!.toInt() + "F."
                }
                var weatherString: String = ""
                if (weatherObject!!.weather[0].description != null) {
                    weatherString += weatherObject!!.weather[0].description + "\n" + tempString
                }
                weatherInfoTextField.text = weatherString
            } else {
                weatherInfoTextField.text = this.getText(R.string.not_available)
            }

            val windTextField: TextView = this.findViewById(R.id.wind_speed_field)

            if (weatherObject!!.wind != null && weatherObject!!.wind!!.speed != null) {
                windTextField.text = weatherObject!!.wind.speed.toString() + " m/h"
            } else {
                windTextField.text = this.getString(R.string.not_available)
            }

            val cloudTextField: TextView = this.findViewById(R.id.cloud_type_field)

            if (weatherObject!!.clouds != null && weatherObject!!.clouds!!.all != null) {
                cloudTextField.text = weatherObject!!.clouds.all.toString() + " %"
            } else {
                cloudTextField.text = this.getString(R.string.not_available)
            }

            val pressureTextField: TextView = this.findViewById(R.id.pressure_value_field)

            if (weatherObject!!.main != null && weatherObject!!.main!!.pressure != null) {
                pressureTextField.text = weatherObject!!.main!!.pressure.toString() + " hpa"
            } else {
                pressureTextField.text = this.getString(R.string.not_available)
            }

            val humidityTextField: TextView = this.findViewById(R.id.humidity_value_field)

            if (weatherObject!!.main != null && weatherObject!!.main!!.humidity != null) {
                humidityTextField.text = weatherObject!!.main!!.humidity.toString() + " %"
            } else {
                humidityTextField.text = this.getString(R.string.not_available)
            }

            val sunriseTextField: TextView = this.findViewById(R.id.sunrise_value_field)

            if (weatherObject!!.sys != null && weatherObject!!.sys!!.sunrise != null) {
                val date: Date = Date(weatherObject!!.sys!!.sunrise!! * 1000L)

                val dateFormat: SimpleDateFormat = SimpleDateFormat("h:mm a")
                dateFormat.timeZone = java.util.TimeZone.getTimeZone("GMT-4")
                val formattedTime = dateFormat.format(date)
                sunriseTextField.text = formattedTime
            } else {
                sunriseTextField.text = this.getString(R.string.not_available)
            }

            val currentTempTextField: TextView = this.findViewById(R.id.temp_value_field)

            if (weatherObject!!.main != null && weatherObject!!.main!!.temp != null) {
                val temp = weatherObject!!.main!!.temp!!.toInt().toString()
                val combinedString: String = temp + "\u00b0" + "F"
                val ss: SpannableString = SpannableString(combinedString)
                ss.setSpan(RelativeSizeSpan(0.5f), temp.length + 1, temp.length + 2, 0)
                currentTempTextField.text = ss
            } else {
                currentTempTextField.text = this.getString(R.string.not_available)
            }

            val locationField: TextView = this.findViewById(R.id.location_name_field)

            if (weatherObject!!.name != null) {
                locationField.text = "Measured: " + weatherObject!!.name
            } else {
                locationField.text = this.getString(R.string.not_available)
            }
        }
    }


}