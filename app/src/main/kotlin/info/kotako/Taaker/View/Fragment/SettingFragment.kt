package info.kotako.Taaker.View.Fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import info.kotako.Taaker.R

class SettingFragment : PreferenceFragment() {

    val KEY_PREF_NOTIFICATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}
