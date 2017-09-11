package info.kotako.Taaker.View

import android.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.view.Gravity
import android.widget.Toast
import info.kotako.Taaker.Event.*
import info.kotako.Taaker.R
import info.kotako.Taaker.View.Dialog.TaskActionDialog
import info.kotako.Taaker.View.Listener.NavigationListener
import info.kotako.Taaker.View.Dialog.TaskDescriptionDialog
import info.kotako.Taaker.View.Dialog.TaskCreateDialog
import info.kotako.Taaker.View.Fragment.CategoryFragment
import info.kotako.Taaker.View.Fragment.DoneTasksFragment
import info.kotako.Taaker.View.Fragment.TasksFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      setting Toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "Recently"
        toolbar.setNavigationIcon(R.mipmap.menu_white)
        toolbar.setNavigationOnClickListener { (findViewById(R.id.drawer) as DrawerLayout).openDrawer(GravityCompat.START) }

//      setting floating action button
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val ft = fragmentManager.beginTransaction()
            val prevFragment = fragmentManager.findFragmentByTag("dialog_create")
            if (prevFragment != null) ft.remove(prevFragment)
            ft.addToBackStack(null)

            val fragment: DialogFragment = TaskCreateDialog.newInstance()
            fragment.show(ft, "dialog_create")
        }

//      setting NavigationView
        val navigation = findViewById(R.id.navigation) as NavigationView
        navigation.setNavigationItemSelectedListener(NavigationListener())
        navigation.setCheckedItem(R.id.menu_recently)

//      setting fragment
        fragmentManager.beginTransaction()
                .replace(R.id.container, TasksFragment.newInstance())
                .commit()
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNavigationChanged(e: NavigationEvent) {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.show()

        when (e.itemId) {
            R.id.menu_recently -> {
                toolbar.title = "Recently"
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TasksFragment.newInstance())
                        .commit()
            }
            R.id.menu_done -> {
                toolbar.title = "Done"
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DoneTasksFragment.newInstance())
                        .commit()
            }
            R.id.menu_category -> {
                toolbar.title = "Category"
                fab.hide()
                val fragment = CategoryFragment.newInstance()
                fragment.enterTransition = Slide(Gravity.END)
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()
            }
            R.id.menu_setting -> {
                toolbar.title = "Setting"
                fab.hide()
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SettingFragment())
                        .commit()
            }
            R.id.menu_sync -> {
                toolbar.title = "Sync with Google"
                fab.hide()
            }
        }
        (findViewById(R.id.navigation) as NavigationView).setCheckedItem(e.itemId)
        (findViewById(R.id.drawer) as DrawerLayout).closeDrawer(GravityCompat.START)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun showToast(e: ToastEvent) {
        runOnUiThread { Toast.makeText(applicationContext, e.msg, Toast.LENGTH_SHORT).show() }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showTasks(e: CategorySelectedEvent) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, TasksFragment.newInstance(e.category))
                .addToBackStack(null)
                .commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showTaskDescription(e: TaskShowEvent) {
        val ft = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag("dialog_show")
        if (prevFragment != null) ft.remove(prevFragment)
        ft.addToBackStack(null)

        val fragment: DialogFragment = TaskDescriptionDialog.newInstance(e.taskId)
        fragment.show(ft, "dialog_show")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showTaskAction(e: TaskActionEvent) {
        val ft = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag("dialog_action")
        if (prevFragment != null) ft.remove(prevFragment)
        ft.addToBackStack(null)

        val fragment: TaskActionDialog = TaskActionDialog.newInstance(e.id)
        fragment.show(ft, "dialog_action")
    }
}
