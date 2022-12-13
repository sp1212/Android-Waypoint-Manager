package edu.virginia.cs4720.finalproject.scp4exq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter(this)
        recyclerView.adapter = adapter

        supportActionBar?.title = "Waypoint Journal"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addTb -> {
                val intent = Intent(this, ActivityNew::class.java)
                startActivity(intent)
            }
            R.id.mapTb -> {
                val intent = Intent(this, ActivityMap::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: RecyclerView in Android Studio [Kotlin 2020]
 *  Author: CodeWithMazn
 *  Date: September 10, 2020
 *  URL: https://www.youtube.com/watch?v=UCddGYMQJCo
 *
 *  Title: How to open a new activity with a button click - Android Kotlin Example
 *  Author: Simple Schwarz
 *  Date: October 6, 2021
 *  URL: https://medium.com/@simple.schwarz/how-to-open-a-new-activity-with-a-button-click-android-kotlin-example-bd2107946bbe
 ***************************************************************************************/