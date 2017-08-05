package kotako.java.info.notifyer.View

import android.os.Bundle
import android.preference.PreferenceFragment
import kotako.java.info.notifyer.R

class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}
