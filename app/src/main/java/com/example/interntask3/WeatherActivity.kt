package com.example.interntask3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONException

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //
    }

    @SuppressLint("SetTextI18n")
    fun WeatherAPI(view: View) {
        val city = cityName.text.toString()
        val key = "c9bb5f0c393049709a595242211408"
        //Toast.makeText(this, "City Name $city", Toast.LENGTH_SHORT).show()

        val url = "https://api.weatherapi.com/v1/current.json?key=$key&q=$city&aqi=no"
        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val mTemp_c = response.getJSONObject("current").getString("temp_c")
                    val mtemp_f = response.getJSONObject("current").getString("temp_f")

                    textView5.setText(mTemp_c).toString()
                    textView6.setText(mtemp_f).toString()

                    val mLat = response.getJSONObject("location").getString("lat")
                    val mLon = response.getJSONObject("location").getString("lon")

                    textView7.setText(mLat).toString()
                    textView8.setText(mLon).toString()

                } catch (e: JSONException) {
                    Toast.makeText(this, "Enter Proper CityName${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            },
            { error ->
                // TODO: Handle error
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.app_bar_search -> {
            user()
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        else -> {
            Toast.makeText(applicationContext, "Error....", Toast.LENGTH_SHORT).show()
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun user() {
        //Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
    }
}


