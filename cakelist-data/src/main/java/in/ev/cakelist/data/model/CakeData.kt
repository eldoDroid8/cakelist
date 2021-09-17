package `in`.ev.cakelist.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class CakeItem(
    @Json(name = "desc")
    val desc: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "title")
    val title: String
)