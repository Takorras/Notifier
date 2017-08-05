package kotako.java.info.notifyer

import android.app.Application
import io.realm.Realm

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}