package com.example.lab3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.ktrssreader.generated.RssChannelReader

class FeedActivity : AppCompatActivity() {
    companion object {
        const val SELECTED_CHANNELS = "SELECTED_CHANNELS"
    }

    private lateinit var selectedChannels: Array<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var spinner: SpinKitView

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
        spinner = findViewById(R.id.spin_kit)

        val tempChannels = intent.getStringArrayExtra(SELECTED_CHANNELS)
        if (tempChannels is Array<String>) {
            selectedChannels = tempChannels
//            delay(5000)
            val items = mutableListOf<Item>()
            var count = 0
            for (channel in selectedChannels) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = RssChannelReader.coRead(channel)
                    lifecycleScope.launch(Dispatchers.Default) {
                        items.addAll(result.items.map { item -> Item(item) })
                        count++
                    }
                }
            }

            lifecycleScope.launch(Dispatchers.Default) {
                while (count != selectedChannels.size) {
                    delay(10)
                }
                lifecycleScope.launch(Dispatchers.Main) {
                    itemsAdapter.itemSortedList.addAll(items)
                    spinner.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}