package com.example.currencyexchangeapp.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.currencyexchangeapp.R

class AppDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(arguments?.getString(TITLE))
            .setMessage(arguments?.getString(MESSAGE))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .create()


    companion object {
        const val TAG = "AppDialog"
        private const val MESSAGE = "dialog_message"
        private const val TITLE = "dialog_title"

        fun newInstance(title: String, message: String): AppDialogFragment =
            AppDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(TITLE, title)
                        putString(MESSAGE, message)
                    }
            }
    }
}