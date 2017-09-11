package info.kotako.Taaker.View.Listener

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import info.kotako.Taaker.Event.DateSetEvent
import org.greenrobot.eventbus.EventBus
import java.util.*

class DateSetListener : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = GregorianCalendar(TimeZone.getDefault())
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        // Log.d("dev", calendar.time.toString())

        EventBus.getDefault().post(DateSetEvent(calendar.time))
    }
}
