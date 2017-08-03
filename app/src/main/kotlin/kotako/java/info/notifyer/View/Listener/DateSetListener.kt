package kotako.java.info.notifyer.View.Listener

import android.app.DatePickerDialog
import android.widget.DatePicker
import kotako.java.info.notifyer.Event.DateSetEvent
import org.greenrobot.eventbus.EventBus
import java.util.Calendar

class DateSetListener : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        EventBus.getDefault().post(DateSetEvent(calendar.time))
    }
}
