package `in`.ev.cakelist.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


@BindingAdapter("imageUrl", "placeholderData", requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?, placeholderIcon: Drawable) {
    val context = imageView.context
    imageView.visibility = View.VISIBLE
        Glide.with(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .placeholder(placeholderIcon)
            .into(imageView)
}

