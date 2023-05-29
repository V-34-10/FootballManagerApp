package com.digijed.goplayer.ui.main.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class BaseDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context).setTitle("Опис гравця:")
            .setMessage(arguments?.getString(MESSAGE).orEmpty())
            .setPositiveButton("OK") { _, _ ->
                (activity as? Listener)?.onClick()
            }
            .create()
    }

    companion object {
        private const val MESSAGE = "message"

        fun newInstance(message: String, fragmentManager: FragmentManager) = BaseDialog().apply {
            arguments = bundleOf(MESSAGE to message)
        }.also { dialog ->
            dialog.show(fragmentManager, "base_dialog")
        }
    }

    interface Listener {
        fun onClick()
    }
}