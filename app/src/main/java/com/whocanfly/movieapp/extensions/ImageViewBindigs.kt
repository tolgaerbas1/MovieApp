package com.whocanfly.movieapp.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.whocanfly.movieapp.MovieApp
import com.whocanfly.movieapp.api.Movie

@BindingAdapter(value = ["imageUrlOrId", "imagePlaceholder"], requireAll = false)
fun ImageView.bindImage(imageUrl: String?, imagePlaceholder: Drawable? = null) {
    if (!imageUrl.isNullOrBlank()) {
        try {
            setImageResource(imageUrl.toInt())
        } catch (e: NumberFormatException) {
            val glideUrl = GlideUrl(
                imageUrl,
                LazyHeaders.Builder().build()
            )
            var glideBuilder = Glide.with(MovieApp.getInstance()!!.applicationContext)
                .load(glideUrl)
            if (imagePlaceholder != null) {
                glideBuilder = glideBuilder.placeholder(imagePlaceholder)
                    .error(imagePlaceholder)
            }
            glideBuilder.into(this)
        }
    }
}
@BindingAdapter("profileImage")
fun loadImage(view: ImageView, profileImage: String) {
    Glide.with(view.context)
        .load(profileImage)
        .into(view)
}

fun String.minToHour() : String{

    val minInt : Int? = this.toIntOrNull()
    val hourStr : String = (minInt?.div(60) ).toString()
    val reminderMin : String = minInt?.rem(60).toString()

    return "${hourStr}hr ${reminderMin}m"
}