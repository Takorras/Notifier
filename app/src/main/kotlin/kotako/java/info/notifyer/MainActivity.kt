package kotako.java.info.notifyer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotako.java.info.notifyer.Event.NavigationEvent
import kotako.java.info.notifyer.Event.ToastEvent
import kotako.java.info.notifyer.View.NavigationListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        val drawer = findViewById(R.id.drawer) as DrawerLayout

//      setting Toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "Notifier"
        toolbar.setNavigationIcon(R.mipmap.menu_white)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setOnMenuItemClickListener {
            Toast.makeText(applicationContext, "add", Toast.LENGTH_SHORT).show()
            true
        }

//      setting NavigationView
        val navigation = findViewById(R.id.navigation) as NavigationView
        navigation.setNavigationItemSelectedListener(NavigationListener())
        navigation.setCheckedItem(R.id.recently_menu)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNavigationChanged(e: NavigationEvent) {
        val drawer = findViewById(R.id.drawer) as DrawerLayout
        val navigation = findViewById(R.id.navigation) as NavigationView

        navigation.setCheckedItem(e.itemId)
        drawer.closeDrawer(GravityCompat.START)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun showToast(e: ToastEvent) {
        runOnUiThread { Toast.makeText(applicationContext, e.msg, Toast.LENGTH_SHORT).show() }
    }
}


