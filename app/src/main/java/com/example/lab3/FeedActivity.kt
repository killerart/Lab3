package com.example.lab3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.ktrssreader.generated.RssChannelReader

class FeedActivity : AppCompatActivity() {
    companion object {
        const val SELECTED_CHANNELS = "SELECTED_CHANNELS"
    }

    private lateinit var selectedChannels: Array<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemsAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        itemsAdapter = ItemsAdapter(this)

        val viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.itemsRecyclerView).apply {
            layoutManager = viewManager
            addItemDecoration(
                DividerItemDecoration(
                    this@FeedActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = itemsAdapter
        }

        val tempChannels = intent.getStringArrayExtra(SELECTED_CHANNELS)
        if (tempChannels is Array<String>) {
            selectedChannels = tempChannels
            lifecycleScope.launch(Dispatchers.IO) {
                for (channel in selectedChannels) {
                    val result = RssChannelReader.coRead(channel)
                    lifecycleScope.launch(Dispatchers.Main) {
                        itemsAdapter.itemSortedList.addAll(result.items.map { item -> Item(item) })
                    }
                }
            }
        }
    }
}