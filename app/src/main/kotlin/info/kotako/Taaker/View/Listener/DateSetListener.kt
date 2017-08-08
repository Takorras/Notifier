package info.kotako.Taaker.View.Listener

import android.app.DatePickerDialog
import android.widget.DatePicker
import info.kotako.Taaker.Event.DateSetEvent
import org.greenrobot.eventbus.EventBus
import java.util.Calendar

class DateSetListener : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        EventBus.getDefault().post(DateSetEvent(calendar.time))
    }
}
