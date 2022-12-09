package edu.virginia.cs4720.finalproject.scp4exq

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new.*
import java.util.*

class ActivityNew : AppCompatActivity() {

    lateinit var chooseDate: Button
    lateinit var chosenDate: TextView
    lateinit var notesField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        chooseDate = findViewById(R.id.button2)
        chosenDate = findViewById(R.id.chooseDate)
        notesField = findViewById(R.id.new_notes)

        chooseDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val dpg = DatePickerDialog(this,
                {
                        _, year, monthOfYear, dayOfMonth -> chosenDate.text =
                    (year.toString() + "-" + formatLeadingZero(monthOfYear + 1) + "-" + formatLeadingZero(dayOfMonth))
                }, y, m, d)
            dpg.show()
        }

        val context = this

        submitButton.setOnClickListener {
            if (itemTitle.text.toString().isNotEmpty() && chosenDate.text.toString().isNotEmpty()
                && notesField.text.toString().length < 256) {
                var item = Waypoint(itemTitle.text.toString(), chosenDate.text.toString(),
                    notesField.text.toString(), "Lat", "Long")
                var db = DatabaseHandler(context)
                db.insertData(item)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Input validation error.", Toast.LENGTH_SHORT).show()
            }
        }
        supportActionBar?.title = "Waypoint Journal"
    }

    private fun formatLeadingZero(input : Int) : String {
        return if (input < 10) {
            "0$input";
        } else {
            input.toString();
        }
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: How to open a new activity with a button click - Android Kotlin Example
 *  Author: Simple Schwarz
 *  Date: October 6, 2021
 *  URL: https://medium.com/@simple.schwarz/how-to-open-a-new-activity-with-a-button-click-android-kotlin-example-bd2107946bbe
 *
 *  Title: DatePicker in Android
 *  Author: chaitanyamunje
 *  Date: 17 Jul, 2022
 *  URL: https://www.geeksforgeeks.org/datepicker-in-android/
 *
 *  Title: Android Tutorial (Kotlin) - 30 - SQLite Database Creation and Insertion
 *  Author: CodeAndroid
 *  Date: Dec 11, 2017
 *  URL: https://www.youtube.com/watch?v=OxHNcCXnxnE
 ***************************************************************************************/