package com.dixa.dixagitpreview.util

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dixa.dixagitpreview.extension.roundBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt


object BindingAdapterUtil {

    @JvmStatic
    @BindingAdapter(value = ["loadFromUrl", "radius"], requireAll = false)
    fun setImageFromUrl(imageView: ImageView, url: String?, radius: Float?) {
        CoroutineScope(Dispatchers.Default).launch {
            var imageURL: URL? = null
            if (url != null) {
                try {
                    imageURL = URL(url)
                    val connection: HttpURLConnection = imageURL
                        .openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val inputStream: InputStream = connection.inputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream) // Convert to bitmap
                    CoroutineScope(Dispatchers.Main).launch {
                        val finalBitmap =
                            if (radius == null || radius == 0f) bitmap else bitmap.roundBitmap(
                                radius.roundToInt()
                            )
                        imageView.setImageBitmap(finalBitmap)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                //set any default
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["startDrawable", "drawablePadding"], requireAll = false)
    fun setTextViewStartDrawable(textview: TextView, drawable: Drawable, drawablePadding: Float?) {
        val size = textview.height
        drawable.setBounds(0, 0, size, size)
        textview.setCompoundDrawables(drawable, null, null, null)
        if (drawablePadding != null && drawablePadding > 0f)
            textview.compoundDrawablePadding = drawablePadding.roundToInt()
    }
    @JvmStatic
    @BindingAdapter(value = ["stringVisibilityCheck"], requireAll = false)
    fun handleStringNullValueForVisibility(view: View, value: String?) {
        if (value.isNullOrBlank())
            view.visibility = View.GONE
        else
            view.visibility = View. VISIBLE
    }
}