package kotako.java.info.notifyer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer = findViewById(R.id.drawer) as DrawerLayout

//      setting Toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "Notifier"
        toolbar.setNavigationIcon(R.mipmap.menu_white)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
    }
}
