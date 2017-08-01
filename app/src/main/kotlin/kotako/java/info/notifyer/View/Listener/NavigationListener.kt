package kotako.java.info.notifyer.View.Listener

import android.support.design.widget.NavigationView
import android.view.MenuItem
import kotako.java.info.notifyer.Event.NavigationEvent
import org.greenrobot.eventbus.EventBus

class NavigationListener : NavigationView.OnNavigationItemSelectedListener{
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        EventBus.getDefault().post(NavigationEvent(item.itemId))
        return false
    }
}
