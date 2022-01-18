package com.whocanfly.movieapp.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import androidx.databinding.InverseMethod
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.whocanfly.movieapp.R
import java.text.SimpleDateFormat

inline fun <T> delay(delay: Long, crossinline block: () -> T): Cancellable {
    return Handler().let { handler ->
        with(Runnable { block() }) {
            handler.postDelayed(this, delay)
            Cancellable {
                handler.removeCallbacks(this)
            }
        }
    }
}

class Cancellable(private val cancelFunction: () -> Unit) {

    fun cancel() {
        cancelFunction()
    }
}

fun NavController.safeNavigate(directions: NavDirections) {
    try {
        navigate(directions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.safeNavigate(directions: NavDirections, id: Int) {
    try {
        navigate(
            directions, NavOptions.Builder().setPopUpTo(id, true).build()
        )
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun Fragment.navigationRun(navDirections: NavDirections, id: Int = 0) {
    findNavController().run {
        this.currentDestination?.getAction(navDirections.actionId)?.let {
            if (id == 0) {
                safeNavigate(navDirections)
            } else {
                safeNavigate(navDirections, id)
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun String?.dateFromIso(): String {
    return this?.let {
        SimpleDateFormat("dd.MM.yyyy").format(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(
                this
            )
        )
    } ?: run { "" }
}


fun convertStringToInt(value: String): Int {
    return try {
        value.toInt()
    } catch (e: NumberFormatException) {
        -1
    }
}

@InverseMethod("convertStringToInt")
fun convertIntToString(value: Int): String? {
    return value.toString()
}


fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}


fun Activity.hideSoftKeyboard(flags: Int = 0) {
    currentFocus?.also {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, flags)
    }
}
fun Activity.setNavigationListener(listener: NavController.OnDestinationChangedListener) {
    findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(listener)
}

fun Any.className() = this::class.simpleName
