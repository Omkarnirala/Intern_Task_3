package com.example.interntask3

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        check.isClickable = false
        check.isEnabled = false

        pinCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim { it <= ' ' }.isEmpty()) {

                } else {
                    check.isClickable = true
                    check.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        val spinner: Spinner = findViewById(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Gender,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    when(selectedItem){
                        "Male" -> {
                            val sni = spinner.selectedItem.toString()
                        }
                        "Female" -> {
                            val sni = spinner.selectedItem.toString()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                   // Toast.makeText(this@MainActivity, "not selected", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun datePick(view: View) {
        var date: Calendar = Calendar.getInstance()
        var thisAYear = date.get(Calendar.YEAR).toInt()
        var thisAMonth = date.get(Calendar.MONTH).toInt()
        var thisADay = date.get(Calendar.DAY_OF_MONTH).toInt()

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { dpd, thisYear, thisMonth, thisDay ->
                // Display Selected date in Edit Text
                thisAMonth = thisMonth + 1
                thisADay = thisDay
                thisAYear = thisYear

                dob.setText("DOB:-  $thisDay/$thisAMonth/$thisYear")
                val newDate: Calendar = Calendar.getInstance()
                newDate.set(thisYear, thisMonth, thisDay)

                var age = date.get(Calendar.YEAR) - thisAYear

                if (date.get(Calendar.MONTH) == thisAMonth) {
                    if (date.get(Calendar.DAY_OF_MONTH) < thisADay) {
                        age--
                    } else if (date.get(Calendar.MONTH) < thisAMonth) {
                        age--
                    }
                }
                autoAge.setText("Age: $age")
            },
            thisAYear,
            thisAMonth,
            thisADay)
        dpd.show()
    }


    @SuppressLint("SetTextI18n")
    fun pinCodeChecker(view: View) {
        district.setText("District (Autofill)")
        state.setText("State (Autofill)")

        val pin = pinCode.text.toString()

        val url = "https://api.postalpincode.in/pincode/$pin"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonObject = response.getJSONObject(0)
                    if (jsonObject != null) {
                        var postOffice = jsonObject.getJSONArray("PostOffice")
                        for (i in 0 until postOffice.length()) {
                            val mDist = postOffice.getJSONObject(i).getString("District")
                            val mState = postOffice.getJSONObject(i).getString("State")

                            district.setText("District: $mDist")
                            state.setText("State: $mState")
                        }
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Enter Proper PinCode", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                // TODO: Handle error
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    fun register(view: View) {

        val i = Intent(this, WeatherActivity::class.java)
        startActivity(i)
    }

}





