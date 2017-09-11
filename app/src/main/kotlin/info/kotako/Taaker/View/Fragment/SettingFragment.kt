package info.kotako.Taaker.View

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotako.Taaker.Event.ToolbarSetTitleEvent
import info.kotako.Taaker.R
import org.greenrobot.eventbus.EventBus

class SettingFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        EventBus.getDefault().post(ToolbarSetTitleEvent("Settings"))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}
