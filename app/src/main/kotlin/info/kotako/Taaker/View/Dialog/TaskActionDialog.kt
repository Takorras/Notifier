package info.kotako.Taaker.View.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import info.kotako.Taaker.Event.TaskDestroyEvent
import info.kotako.Taaker.R
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
        arguments?.let { arg -> taskId = arg.getInt("taskId") }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        taskId?.let { id ->
            builder.setView(activity.layoutInflater.inflate(R.layout.dialog_task_action, null, false))
                    .setPositiveButton("Yes", { _, _ -> EventBus.getDefault().post(TaskDestroyEvent(id)) })
                    .setNegativeButton("No", { _, _ -> dismiss() })
        }
        return builder.create()
    }
}