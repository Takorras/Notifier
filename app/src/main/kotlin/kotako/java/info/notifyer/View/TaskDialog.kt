package kotako.java.info.notifyer.View

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotako.java.info.notifyer.Event.DateSetEvent
import kotako.java.info.notifyer.Event.ToastEvent
import kotako.java.info.notifyer.R
import kotako.java.info.notifyer.View.Listener.DateSetListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Calendar
import java.util.GregorianCalendar

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
        dialogView = activity.layoutInflater.inflate(R.layout.dialog_task, null, false)

//      DatePickerの作成と、ボタンへのセット
        val calendar = GregorianCalendar()
        val datePickerDialog = DatePickerDialog(activity, DateSetListener(), calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE])
        (dialogView!!.findViewById(R.id.button_milestone_dialog_task) as Button).setOnClickListener { datePickerDialog.show() }

//      SaveボタンCancelボタンの作成とイベントのセット
        builder.setView(dialogView)
                .setPositiveButton("Save", { _, _ -> getInputData() })
                .setNegativeButton("Cancel", { _, _ -> dismiss() })

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    fun getInputData() {
        val name = (dialogView!!.findViewById(R.id.text_title_dialog_task) as TextView).text.toString()
        val milestone = (dialogView!!.findViewById(R.id.text_milestone_dialog_task) as TextView).text.toString()
        val genre = (dialogView!!.findViewById(R.id.text_genre_dialog_task) as TextView).text.toString()
        EventBus.getDefault().post(ToastEvent(name + milestone + genre))
    }

    //  DatePickerDialogのコールバックリスナから返ってくる、日付の値を処理
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDateSet(e: DateSetEvent) {
        (dialogView!!.findViewById(R.id.text_milestone_dialog_task) as TextView).text = "${e.y}/${e.m}/${e.d}"
    }
}
