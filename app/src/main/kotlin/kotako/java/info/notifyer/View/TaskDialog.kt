package kotako.java.info.notifyer.View

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotako.java.info.notifyer.Event.ToastEvent
import kotako.java.info.notifyer.R
import org.greenrobot.eventbus.EventBus

class TaskDialog : DialogFragment() {

    var dialogView: View? = null

    companion object {
        fun newInstance(): TaskDialog {
            val fragment: TaskDialog = TaskDialog()
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        dialogView = inflater.inflate(R.layout.dialog_task, null, false)

        builder.setView(dialogView)
                .setPositiveButton("Save", { _, _ -> inputData() })
                .setNegativeButton("Cancel", { _, _ -> dismiss() })
        return builder.create()
    }

    fun inputData() {
        val name = (dialogView!!.findViewById(R.id.text_title_dialog_task) as TextView).text.toString()
        val milestone = (dialogView!!.findViewById(R.id.text_milestone_dialog_task) as TextView).text.toString()
        val genre = (dialogView!!.findViewById(R.id.text_genre_dialog_task) as TextView).text.toString()
        EventBus.getDefault().post(ToastEvent(name + milestone + genre))
    }
}
