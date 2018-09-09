package com.eduardodev.cars.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import org.jetbrains.anko.support.v4.browse


private const val ARG_MESSAGE = "message"
private const val ARG_POSITIVE_BUTTON_MESSAGE = "positiveButtonMessage"
private const val ARG_LINK = "link"

class InformationDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(message: String, positiveButtonMessage: String, link: String) =
                InformationDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_MESSAGE, message)
                        putString(ARG_POSITIVE_BUTTON_MESSAGE, positiveButtonMessage)
                        putString(ARG_LINK, link)
                    }
                }
    }

    private val message by lazy { arguments!!.getString(ARG_MESSAGE) }
    private val positiveButtonMessage by lazy { arguments!!.getString(ARG_POSITIVE_BUTTON_MESSAGE) }
    private val link by lazy { arguments!!.getString(ARG_LINK) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(context!!)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonMessage) { _, _ -> browse(link) }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .create()
}