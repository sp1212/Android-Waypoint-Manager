package edu.virginia.cs4720.finalproject.scp4exq

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import kotlin.collections.ArrayList

const val DATABASE_NAME = "AppDatabase"
const val TABLE_NAME = "Waypoints"
const val COL_ID = "id"
const val COL_TITLE = "title"
const val COL_DATE = "date"
const val COL_NOTES = "notes"
const val COL_LAT = "lat"
const val COL_LONG = "long"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " VARCHAR(256)," +
                COL_DATE + " VARCHAR(256)," +
                COL_NOTES + " VARCHAR(256)," +
                COL_LAT + " VARCHAR(256)," +
                COL_LONG + " VARCHAR(256))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(item : Waypoint) {
        val db = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL_TITLE, item.title)
        cv.put(COL_DATE, item.date)
        cv.put(COL_NOTES, item.notes)
        cv.put(COL_LAT, item.lat)
        cv.put(COL_LONG, item.long)

        var result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Item creation failure.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Item created successfully.", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() : MutableList<Waypoint> {
        var list : MutableList<Waypoint> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var item = Waypoint()
                item.id = result.getString(0).toInt()
                item.title = result.getString(1)
                item.date = result.getString(2)
                item.notes = result.getString(3)
                item.lat = result.getString(4)
                item.long = result.getString(5)
                list.add(item)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun deleteItem(id : Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    private fun formatLeadingZero(input : Int) : String {
        return if (input < 10) {
            "0$input"
        } else {
            input.toString()
        }
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: Android Tutorial (Kotlin) - 30 - SQLite Database Creation and Insertion
 *  Author: CodeAndroid
 *  Date: Dec 11, 2017
 *  URL: https://www.youtube.com/watch?v=OxHNcCXnxnE
 *
 *  Title: Android Tutorial (Kotlin) - 31 - Read Delete and update SQlite Database Records
 *  Author: CodeAndroid
 *  Date: Dec 18, 2017
 *  URL: https://www.youtube.com/watch?v=vov_rsFWkmM
 ***************************************************************************************/