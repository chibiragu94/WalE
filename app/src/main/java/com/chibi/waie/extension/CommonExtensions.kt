package com.chibi.waie.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Make the view Visible
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * Make the view GONE
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Make the view INVISIBLE
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Show Toast message for Long time
 */
fun Context.toastLong(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 *
 * Show Toast message for Short time
 */
fun Context.toastShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Shows the Snackbar inside an Activity or Fragment
 *
 * @param messageRes Text to be shown inside the Snackbar
 * @param length Duration of the Snackbar
 * @param f Action of the Snackbar
 */
fun View.showSnackbar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.f()
    snackBar.show()
}

/**
 * Adds action to the Snackbar
 *
 * @param actionRes Action text to be shown inside the Snackbar
 * @param color Color of the action text
 * @param listener Onclick listener for the action
 */

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    setAction(actionRes, listener)
    color?.let { setActionTextColor(color) }
}