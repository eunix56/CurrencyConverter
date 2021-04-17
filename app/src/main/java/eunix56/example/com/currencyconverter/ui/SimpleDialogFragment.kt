package eunix56.example.com.currencyconverter.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


private const val ARG_DIALOG_TITLE = "param1"
private const val ARG_DIALOG_TEXT= "param2"



class SimpleDialogFragment() : DialogFragment() {

    private var dialogTitle: String? = null
    private var dialogBody: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            dialogTitle = it.getString(ARG_DIALOG_TITLE)
            dialogBody = it.getString(ARG_DIALOG_TEXT)
        }

        val dialog = AlertDialog.Builder(context!!)
        dialog.setTitle(dialogTitle)
        dialog.setMessage(dialogBody)

        dialog.setPositiveButton("OK") { dialog, which ->
            dialog?.dismiss()
        }

        return dialog.create()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SimpleDialogFragment.
         */
        @JvmStatic
        fun newInstance(dialogTitle: String, dialogBody: String) =
            SimpleDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DIALOG_TITLE, dialogTitle)
                    putString(ARG_DIALOG_TEXT, dialogBody)
                }
            }
    }
}
