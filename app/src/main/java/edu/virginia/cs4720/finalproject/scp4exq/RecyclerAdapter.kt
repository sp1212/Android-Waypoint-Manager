package edu.virginia.cs4720.finalproject.scp4exq

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
        holder.itemTitle.text = data[position].title
        holder.itemDate.text = data[position].date
        holder.itemId.text = data[position].id.toString()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        var itemDate: TextView
        var itemId: TextView
        var itemCheck: CheckBox

        init {
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDate = itemView.findViewById(R.id.item_date)
            itemId = itemView.findViewById(R.id.itemId)
            itemCheck = itemView.findViewById(R.id.checkBox)
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