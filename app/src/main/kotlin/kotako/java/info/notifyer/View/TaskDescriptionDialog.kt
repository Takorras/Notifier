package kotako.java.info.notifyer.View

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import kotako.java.info.notifyer.R

class TaskDescriptionDialog : DialogFragment(){

    companion object {
        fun newInstance(): TaskDescriptionDialog {
            val fragment: TaskDescriptionDialog = TaskDescriptionDialog()
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflate :View = activity.layoutInflater.inflate(R.layout.dialog_task_description, null, false)

        builder.setView(inflate)
                .setPositiveButton("OK", { _, _ -> dismiss()})

        return builder.create()
    }


}
