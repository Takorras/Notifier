package kotako.java.info.notifyer.Model

import java.util.*

class Task(val content: String, val genre: String, val year: Int, val month: Int, val date: Int) {
    val milestone: Calendar = GregorianCalendar()
    init {
        milestone.set(year, month, date)
    }
}
