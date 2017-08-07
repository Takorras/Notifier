package kotako.java.info.notifyer.View.Dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Switch
import android.widget.TextView
import io.realm.Realm
import kotako.java.info.notifyer.Event.DateSetEvent
import kotako.java.info.notifyer.Event.TaskCreatedEvent
import kotako.java.info.notifyer.Model.Task
import kotako.java.info.notifyer.R
import kotako.java.info.notifyer.View.Listener.DateSetListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class TaskCreateDialog : DialogFragment() {

    var dialogView: View? = null
    var date: Date = Date()

    companion object {
        fun newInstance(): TaskCreateDialog {
            return TaskCreateDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        dialogView = activity.layoutInflater.inflate(R.layout.dialog_task_create, null, false)

//      DatePickerの作成と、ボタンへのセット
        val calendar = GregorianCalendar()
        val datePickerDialog = DatePickerDialog(activity, DateSetListener(), calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE])
        (dialogView!!.findViewById(R.id.spinner_dialog_task) as TextView).setOnClickListener { datePickerDialog.show() }
        var genreList: Array<String> = arrayOf()
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                genreList = realm.where(Task::class.java).distinct("genre").map { value -> value.genre }.toTypedArray()
            }
        }
        (dialogView!!.findViewById(R.id.text_genre_dialog_task) as AutoCompleteTextView).setAdapter(
                ArrayAdapter(activity, android.R.layout.simple_list_item_1, genreList))

//      SaveボタンCancelボタンの作成とイベントのセット
        builder.setView(dialogView)
                .setPositiveButton("Save", { _, _ -> EventBus.getDefault().post(TaskCreatedEvent(getInputData())) })
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

    fun getInputData(): Task {
        return Task(
                (dialogView!!.findViewById(R.id.text_title_dialog_task) as TextView).text.toString(),
                (dialogView!!.findViewById(R.id.text_genre_dialog_task) as TextView).text.toString(),
                date,
                (dialogView!!.findViewById(R.id.switch_notification_dialog) as Switch).isChecked)
    }

    //  DatePickerDialogのコールバックリスナから返ってくる、日付の値を処理
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDateSet(e: DateSetEvent) {
        date = e.date
        (dialogView!!.findViewById(R.id.spinner_dialog_task) as TextView).text =
                android.text.format.DateFormat.getLongDateFormat(activity).format(date)
    }
}
