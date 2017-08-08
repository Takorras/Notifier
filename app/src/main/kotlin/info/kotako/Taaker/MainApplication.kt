package info.kotako.Taaker

import android.app.Application
import io.realm.Realm

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}