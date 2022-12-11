package edu.virginia.cs4720.finalproject.scp4exq

class Waypoint {
    var id: Int = 0
    var title : String = ""
    var date : String = ""
    var notes : String = ""
    var lat : String = ""
    var long : String = ""

    constructor(title:String, date:String, notes:String, lat:String, long:String) {
        this.id = 0
        this.title = title
        this.date = date
        this.notes = notes
        this.lat = lat
        this.long = long
    }

    constructor(id: Int, title:String, date:String, notes:String, lat:String, long:String) {
        this.id = id
        this.title = title
        this.date = date
        this.notes = notes
        this.lat = lat
        this.long = long
    }

    constructor() {

    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: Android Tutorial (Kotlin) - 30 - SQLite Database Creation and Insertion
 *  Author: CodeAndroid
 *  Date: Dec 11, 2017
 *  URL: https://www.youtube.com/watch?v=OxHNcCXnxnE
 ***************************************************************************************/