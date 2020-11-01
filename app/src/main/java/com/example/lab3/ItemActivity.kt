package com.example.lab3

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.net.URI
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ItemActivity : AppCompatActivity() {
    companion object {
        const val ITEM = "ITEM"
    }

    private lateinit var titleTextView: TextView
    private lateinit var pubDateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var linkTextView: TextView
    private lateinit var item: Item

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        item = intent.getSerializableExtra(ITEM) as Item

        titleTextView = findViewById(R.id.itemTitle)
        pubDateTextView = findViewById(R.id.itemPubDate)
        descriptionTextView = findViewById(R.id.itemDescription)
        linkTextView = findViewById(R.id.itemLink)

        titleTextView.text = item.title
        pubDateTextView.text = item.pubDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        descriptionTextView.text = item.description
        linkTextView.text = item.link

        linkTextView.paintFlags = linkTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    fun onLinkClick(view: View?) {
        Intent(Intent.ACTION_VIEW, Uri.parse(item.link)).also {
            startActivity(it)
        }
    }
}