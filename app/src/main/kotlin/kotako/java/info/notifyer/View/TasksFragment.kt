package kotako.java.info.notifyer.View

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotako.java.info.notifyer.Model.Task
import kotako.java.info.notifyer.R
import kotako.java.info.notifyer.View.Adapter.TaskRecyclerViewAdapter
import org.greenrobot.eventbus.EventBus
import java.util.*

class TasksFragment : Fragment() {

    var applicationContext: Context? = null
    var recyclerView: RecyclerView? = null

    fun newInstance(): TasksFragment {
        return TasksFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        applicationContext = context!!.applicationContext
        //EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = view.findViewById(R.id.fragment_tasks) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext!!)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list: ArrayList<Task> = ArrayList()
        list.add(Task("task1", "Genre1", 2017, 7, 18))
        list.add(Task("task2", "Genre2", 2017, 7, 17))
        list.add(Task("task3", "Genre3", 2017, 7, 16))

        val adapter = TaskRecyclerViewAdapter(list)
        recyclerView!!.adapter = adapter
    }

    override fun onPause() {
        //EventBus.getDefault().unregister(this)
        super.onPause()
    }
}
