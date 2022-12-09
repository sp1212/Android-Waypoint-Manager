package edu.virginia.cs4720.finalproject.scp4exq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(var context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var db = DatabaseHandler(context)
    var data = db.readData().sortedBy { it.date }

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
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var id: TextView
        var title: TextView
        var date: TextView
        var notes: TextView
        var latlong: TextView

        init {
            id = itemView.findViewById(R.id.itemId)
            title = itemView.findViewById(R.id.item_title)
            date = itemView.findViewById(R.id.item_date)
            notes = itemView.findViewById(R.id.notes_text)
            latlong = itemView.findViewById(R.id.text_latlong)
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
 ***************************************************************************************/