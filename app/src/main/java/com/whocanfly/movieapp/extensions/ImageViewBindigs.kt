package com.whocanfly.movieapp.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.whocanfly.movieapp.MovieApp

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