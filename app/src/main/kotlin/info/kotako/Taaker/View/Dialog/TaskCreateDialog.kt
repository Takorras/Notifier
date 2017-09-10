package info.kotako.Taaker.View.Dialog

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
import info.kotako.Taaker.Event.DateSetEvent
import info.kotako.Taaker.Event.TaskCreatedEvent
import info.kotako.Taaker.Model.Task
import info.kotako.Taaker.View.Listener.DateSetListener
import info.kotako.Taaker.R
import info.kotako.Taaker.View.Animation.FabTransform
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.DateFormat
import java.util.*

class TaskCreateDialog : DialogFragment() {

    var dialogView: View? = null
    var date: Date = Date()

    companion object {
        fun newInstance(): TaskCreateDialog {
            return TaskCreateDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        dialogView = activity.layoutInflater.inflate(R.layout.dialog_task_create, null, false)

//      DatePickerの作成と、ボタンへのセット
        val calendar = GregorianCalendar(TimeZone.getDefault())
        val datePickerDialog = DatePickerDialog(activity, DateSetListener(), calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE])
        (dialogView!!.findViewById(R.id.spinner_dialog_task) as TextView).setOnClickListener { datePickerDialog.show() }

//      ジャンルの予測変換リストを作成
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
                DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date)
    }
}

