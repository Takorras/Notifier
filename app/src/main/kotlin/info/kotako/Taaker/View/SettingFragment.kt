package info.kotako.Taaker.View

import android.os.Bundle
import android.preference.PreferenceFragment
import info.kotako.Taaker.R

class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}
