package kotako.java.info.notifyer.View.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import io.realm.Realm
import kotako.java.info.notifyer.Model.Task
import kotako.java.info.notifyer.R

class TaskDescriptionDialog : DialogFragment() {

    var task: Task? = null

    companion object {
        fun newInstance(taskId: String): TaskDescriptionDialog {
            val fragment: TaskDescriptionDialog = TaskDescriptionDialog()
            val args: Bundle = Bundle()
            args.putString("taskId", taskId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      argumentsにtaskのIDが入ってたらそれに対応するIDを読み込む
        if (arguments == null) return
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { task = realm.where(Task::class.java).equalTo("id", arguments.getString("taskId")).findFirst() }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflate: View = activity.layoutInflater.inflate(R.layout.dialog_task_description, null, false)
        if (task != null) {
            (inflate.findViewById(R.id.text_title_dialog_task_description) as TextView).text = task!!.content
            (inflate.findViewById(R.id.text_genre_dialog_task_description) as TextView).text = task!!.genre
            (inflate.findViewById(R.id.spinner_dialog_task_description) as TextView).text =
                    android.text.format.DateFormat.getLongDateFormat(activity).format(task!!.milestone)
            (inflate.findViewById(R.id.switch_notification_dialog_description) as Switch).isChecked = task!!.isNotify
        }

        builder.setView(inflate)
                .setPositiveButton("OK", { _, _ -> dismiss() })

        return builder.create()
    }

}
