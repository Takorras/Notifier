package kotako.java.info.notifyer.View

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotako.java.info.notifyer.Event.TaskCreatedEvent
import kotako.java.info.notifyer.Model.Task
import kotako.java.info.notifyer.R
import kotako.java.info.notifyer.View.Adapter.TaskRecyclerViewAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.collections.ArrayList

class TasksFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var list: ArrayList<Task> = ArrayList()

    fun newInstance(): TasksFragment {
        return TasksFragment()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = view.findViewById(R.id.fragment_tasks) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity.applicationContext)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list.add(Task("task1", "Genre1", 2017, 7, 18))
        list.add(Task("task2", "Genre2", 2017, 7, 17))
        list.add(Task("task3", "Genre3", 2017, 7, 16))

        recyclerView!!.adapter = TaskRecyclerViewAdapter(list)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTaskCreated(e: TaskCreatedEvent) {
        list.add(e.task)
    }
}
