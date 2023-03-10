package edu.virginia.cs4720.finalproject.scp4exq

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerAdapter(var context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var db = DatabaseHandler(context)
    private var data = db.readData().sortedBy { it.date }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.id.text = data[position].id.toString()
        holder.title.text = data[position].title
        holder.date.text = data[position].date
        holder.notes.text = data[position].notes
        holder.latlong.text = data[position].lat + " " + data[position].long

        if (data[position].imageUrl.compareTo("") != 0) {
            Picasso.with(context).load(data[position].imageUrl).into(holder.weatherIcon)
        }

        if (data[position].temp.compareTo("") != 0) {
            holder.temp.text = data[position].temp
        }

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Delete waypoint:  " + holder.title.text + "?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    if (holder.id.text.toString().toInt() >= 0) {
                        val db = DatabaseHandler(context)
                        db.deleteItem(holder.id.text.toString().toInt())

                        val intent = Intent(it.context, MainActivity::class.java)
                        it.context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Unable to delete item.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        holder.shareButton.setOnClickListener {
            val intent = Intent(ACTION_SEND)
            intent.putExtra(EXTRA_SUBJECT, "Waypoint: \"" + holder.title.text + "\"")
            intent.putExtra(
                EXTRA_TEXT, "Waypoint\n" +
                    "Title:  " + holder.title.text + "\n" +
                    "Date:  " + holder.date.text + "\n" +
                    "Coordinates:  " + holder.latlong.text + "\n" +
                    "Notes:  " + holder.notes.text + "\n")
            intent.type = "text/plain"

            context.startActivity(createChooser(intent, "Share your waypoint:"))
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var id: TextView
        var title: TextView
        var date: TextView
        var notes: TextView
        var latlong: TextView
        var deleteButton: ImageButton
        var shareButton: ImageButton
        var temp: TextView
        var weatherIcon: ImageView

        init {
            id = itemView.findViewById(R.id.itemId)
            title = itemView.findViewById(R.id.item_title)
            date = itemView.findViewById(R.id.item_date)
            notes = itemView.findViewById(R.id.notes_text)
            latlong = itemView.findViewById(R.id.text_latlong)
            deleteButton = itemView.findViewById(R.id.delete_button)
            shareButton = itemView.findViewById(R.id.share_button)
            temp = itemView.findViewById(R.id.weather_temp)
            weatherIcon = itemView.findViewById(R.id.weather_image)
        }
    }

}

/***************************************************************************************
 *  REFERENCES
 *  Title: RecyclerView in Android Studio [Kotlin 2020]
 *  Author: CodeWithMazn
 *  Date: September 10, 2020
 *  URL: https://www.youtube.com/watch?v=UCddGYMQJCo
 *
 *  Title: How to open a different activity on recyclerView item onclick
 *  Author: Milad Moosavi
 *  Date: Nov 18, 2017
 *  URL: https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
 *
 *  Title: Send Email using Intent - Android Studio - Kotlin
 *  Author: Atif Pervaiz
 *  Date: November 19, 2018
 *  URL: https://devofandroid.blogspot.com/2018/11/send-email-using-intent-android-studio.html
 *
 *  Title: How to Set confirm delete AlertDialogue box in kotlin
 *  Author: Son Truong
 *  Date: Dec 16, 2019
 *  URL: https://stackoverflow.com/questions/59340099/how-to-set-confirm-delete-alertdialogue-box-in-kotlin
 ***************************************************************************************/