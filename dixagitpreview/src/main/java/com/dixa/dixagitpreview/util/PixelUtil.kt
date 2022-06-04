package com.dixa.dixagitpreview.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.round

@Suppress("unused", "MemberVisibilityCanBePrivate")
class PixelUtil(val app: Context) {

    private inline val windowManager: WindowManager get() = app.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    fun dpToPx(dp: Float) = round(dp * pixelScaleFactor).toInt()
    fun pxToDp(px: Int) = round(px / pixelScaleFactor).toInt()

    inline val pixelScaleFactor get() = app.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

    val configWidthPx get() = dpToPx(configWidthDp.toFloat())
    val configWidthDp : Int get() {
        var ans = app.resources.configuration.screenWidthDp
        if (ans == Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
            ans = widthDp
        }
        return ans
    }

    val widthPx get() = app.resources.displayMetrics.widthPixels
    val widthDp get() = pxToDp(widthPx)

    val heightPx get() = app.resources.displayMetrics.heightPixels
    val heightDp get() = pxToDp(heightPx)

    fun getNavigationBarSize(): Point {
        val appUsableSize = getAppUsableScreenSize()
        val realScreenSize = getRealScreenSize()
        // navigation bar on the side
        if (appUsableSize.x < realScreenSize.x) {
            return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }
        // navigation bar at the bottom
        return if (appUsableSize.y < realScreenSize.y) {
            Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        } else Point()
        // navigation bar is not present
    }

    fun getAppUsableScreenSize(): Point {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    fun getAppUsableScreenHeight() = getAppUsableScreenSize().y

    fun getRealScreenSize(): Point {
        val display = windowManager.defaultDisplay
        val size = Point()
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        }/* else if (Build.VERSION.SDK_INT >= 16) {
            try {
                size.x = (Display::class.java.getMethod("getRawWidth").invoke(display) as Int)
                size.y = (Display::class.java.getMethod("getRawHeight").invoke(display) as Int)
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            } catch (e: NoSuchMethodException) {
            }
        }*/
        return size
    }
}