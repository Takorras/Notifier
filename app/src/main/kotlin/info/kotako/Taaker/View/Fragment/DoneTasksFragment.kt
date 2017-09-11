package info.kotako.Taaker.View.Fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.Sort
import info.kotako.Taaker.Event.TaskDestroyEvent
import info.kotako.Taaker.Model.Task
import info.kotako.Taaker.R
import info.kotako.Taaker.View.Adapter.TaskRecyclerViewAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DoneTasksFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var list: ArrayList<Task> = ArrayList()
    var realm: Realm? = null

    companion object {
        fun newInstance(): DoneTasksFragment {
            return DoneTasksFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = view.findViewById(R.id.fragment_tasks) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity.applicationContext)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//      RealmでTaskを全て持ってくる
        realm = Realm.getDefaultInstance()
        list.addAll(realm!!.where(Task::class.java).equalTo("isDone", true).findAllSorted("milestone", Sort.ASCENDING))

        recyclerView?.adapter = TaskRecyclerViewAdapter(list)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }

    //  realmからの削除とカードの削除
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTaskDestroyed(e: TaskDestroyEvent) {
        realm?.let { realm ->
            val result = realm.where(Task::class.java).equalTo("id", list.removeAt(e.id).id).findAll().first()
            realm.executeTransaction { result.isDone = true }
        }
        recyclerView?.adapter?.notifyItemRemoved(e.id)
    }
}
