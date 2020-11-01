package com.example.lab3

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ItemsAdapter(private val context: Context) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    var itemSortedList: SortedList<Item>

    init {
        itemSortedList =
            SortedList(Item::class.java, object : SortedListAdapterCallback<Item>(this) {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun compare(o1: Item?, o2: Item?): Int {
                    if (o1 != null && o2 != null)
                        return o2.pubDate.compareTo(o1.pubDate)
                    if (o1 == null)
                        return -1
                    return 1
                }

                override fun areContentsTheSame(oldItem: Item?, newItem: Item?): Boolean {
                    return oldItem?.title == newItem?.title
                }

                override fun areItemsTheSame(item1: Item?, item2: Item?): Boolean {
                    return item1?.title == item2?.title
                }

            })
    }

    class ItemViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val itemName: TextView = view.findViewById(R.id.itemTitle)
        private lateinit var item: Item

        override fun onClick(v: View?) {
            Intent(context, ItemActivity::class.java).apply {
                putExtra(ItemActivity.ITEM, item)
            }.also {
                context.startActivity(it)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setItem(item: Item) {
            this.item = item
            itemName.text = item.title
        }
    }


    override fun getItemCount(): Int {
        return itemSortedList.size()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_view_holder, parent, false)
            .let {
                return ItemViewHolder(it, context).apply { itemView.setOnClickListener(this) }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        itemSortedList[position]?.also { item ->
            holder.setItem(item)
        }
    }
}