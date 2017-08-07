package kotako.java.info.notifyer.View.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import kotako.java.info.notifyer.Event.TaskDestroyEvent
import kotako.java.info.notifyer.R
import org.greenrobot.eventbus.EventBus

class TaskActionDialog : DialogFragment() {

    var taskId: Int? = null

    companion object {
        fun newInstance(taskId: Int): TaskActionDialog {
            val args: Bundle = Bundle()
            val fragment: TaskActionDialog = TaskActionDialog()
            args.putInt("taskId", taskId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null) return
        taskId = arguments.getInt("taskId")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setView(activity.layoutInflater.inflate(R.layout.dialog_task_action, null, false))
                .setPositiveButton("Yes", { _, _ -> EventBus.getDefault().post(TaskDestroyEvent(taskId!!)) })
                .setNegativeButton("No", { _, _ -> dismiss() })
        return builder.create()
    }
}