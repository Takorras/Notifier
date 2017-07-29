package kotako.java.info.notifyer.View

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotako.java.info.notifyer.R
import org.greenrobot.eventbus.EventBus

class TasksFragment : Fragment() {

    var applicationContext :Context? = null

    fun TasksFragment() {}

    fun newInstance() {
        return TasksFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        applicationContext = context!!.applicationContext
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        val fragment = view.findViewById(R.id.fragment_tasks) as RecyclerView

        fragment.layoutManager = LinearLayoutManager(applicationContext!!)
        return view
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

}
