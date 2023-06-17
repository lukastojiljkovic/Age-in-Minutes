@file:Suppress("CanBeVal")

package com.lukastojiljkovic.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // set the layout
        var btDate = findViewById<Button>(R.id.btDate) // get the button

        btDate.setOnClickListener { view -> // when the button is clicked
            clickDatePicker(view) // call the function to pick a date
            Toast.makeText(this, "Picking date...", Toast.LENGTH_SHORT).show() // display the toast
        }

    }


    // function to pick a date, gets time from beginning and time from current date and displays the difference in minutes
    private fun clickDatePicker(view: View){

        var myCalendar = Calendar.getInstance() // get the current date
        var year = myCalendar.get(Calendar.YEAR) // get the year
        var month = myCalendar.get(Calendar.MONTH) // get the month
        var day = myCalendar.get(Calendar.DAY_OF_MONTH) // get the day
        var txtSelectedDate = findViewById<TextView>(R.id.txtSelectedDate) // display the selected date
        var txtAge = findViewById<TextView>(R.id.txtAgeInMin) // display the difference in minutes
        val dpd = DatePickerDialog(this, // context
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(this, "Picked date: $selectedDay/${selectedMonth+1}/$selectedYear", Toast.LENGTH_SHORT).show()

                var selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear" // to string for the selected date

                txtSelectedDate.text = selectedDate // display the selected date

                val sdf = SimpleDateFormat("dd/MM/yyyy") // date format
                var theDate = sdf.parse(selectedDate) // parse the date

                theDate?.let { // if the date is valid
                    var selectedDateInMinutes = theDate!!.time / 60000 // convert to minutes

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) // current date
                    currentDate?.let { // if the current date is valid
                        val currentDateToMinutes = currentDate!!.time / 60000 // convert to minutes

                        val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes // difference in minutes

                        txtAge.text = "$differenceInMinutes" // display the difference in minutes
                    }
                }

            }, // callback function
            year, // default year
            month, // default month
            day // default date
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // max date is yesterday
        dpd.show() // show the dialog
    }

}
