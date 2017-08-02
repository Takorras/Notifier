package kotako.java.info.notifyer.View.Listener

import android.app.DatePickerDialog
import android.widget.DatePicker
import kotako.java.info.notifyer.Event.DateSetEvent
import org.greenrobot.eventbus.EventBus

class DateSetListener : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        EventBus.getDefault().post(DateSetEvent(year, month, dayOfMonth))
    }
}