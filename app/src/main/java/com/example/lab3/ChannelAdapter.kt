package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback

class ChannelAdapter(private val mainActivity: MainActivity) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    val channelSortedList: SortedList<String>

    init {
        channelSortedList = SortedList(String::class.java, object :
            SortedListAdapterCallback<String>(this) {
            override fun compare(o1: String?, o2: String?): Int {
                if (o1 != null && o2 != null)
                    return o1.compareTo(o2)
                if (o1 == null)
                    return -1
                return 1
            }

            override fun areContentsTheSame(oldItem: String?, newItem: String?): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: String?, item2: String?): Boolean {
                return item1.equals(item2)
            }
        })
    }

    class ChannelViewHolder(view: View, private val mainActivity: MainActivity) :
        RecyclerView.ViewHolder(view) {
        private val channelName: TextView = view.findViewById(R.id.channelName)
        private val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        init {
            checkBox.setOnClickListener { view ->
                if (view is CheckBox) {
                    if (view.isChecked)
                        mainActivity.selectedChannels.add(channelName.text.toString())
                    else
                        mainActivity.selectedChannels.remove(channelName.text.toString())
                }
            }
        }

        fun setChannel(name: String) {
            channelName.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.channel_view_holder, parent, false)
            .let {
                return ChannelViewHolder(it, mainActivity)
            }
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        channelSortedList[position]?.also { channelName ->
            holder.setChannel(channelName)
        }
    }

    override fun getItemCount(): Int {
        return channelSortedList.size()
    }
}