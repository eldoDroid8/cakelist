package `in`.ev.cakelist.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cake(
    val desc: String,
    val image: String,
    val title: String
) : Parcelable
