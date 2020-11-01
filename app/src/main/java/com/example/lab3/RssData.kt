package com.example.lab3

import android.os.Build
import androidx.annotation.RequiresApi
import tw.ktrssreader.annotation.RssTag
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RssTag(name = "channel")
data class RssChannel(
    var title: String?,
    @RssTag(name = "item")
    var items: List<RssItem>
) : Serializable

@RssTag(name = "item")
data class RssItem(
    var title: String?,
    var link: String?,
    var description: String?,
    var pubDate: String?
) : Serializable

@RequiresApi(Build.VERSION_CODES.O)
class Item(rssItem: RssItem) : Serializable {
    var title: String = rssItem.title.orEmpty()
    var link: String = rssItem.link.orEmpty()
    var description: String = rssItem.description.orEmpty()
    var pubDate: LocalDateTime = LocalDateTime.parse(rssItem.pubDate.orEmpty(), DateTimeFormatter.RFC_1123_DATE_TIME)
}