package info.kotako.Taaker.View.Listener

import android.support.design.widget.NavigationView
import android.view.MenuItem
import info.kotako.Taaker.Event.NavigationEvent
import org.greenrobot.eventbus.EventBus

class NavigationListener : NavigationView.OnNavigationItemSelectedListener{
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        EventBus.getDefault().post(NavigationEvent(item.itemId))
        return false
    }
}
