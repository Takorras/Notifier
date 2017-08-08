package info.kotako.Taaker.View

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.Sort
import info.kotako.Taaker.Event.TaskCreatedEvent
import info.kotako.Taaker.Event.TaskDestroyEvent
import info.kotako.Taaker.Event.TaskDoneEvent
import info.kotako.Taaker.Event.ToastEvent
import info.kotako.Taaker.Model.Task
import info.kotako.Taaker.R
import info.kotako.Taaker.View.Adapter.TaskRecyclerViewAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.collections.ArrayList

class TasksFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var list: ArrayList<Task> = ArrayList()
    var realm: Realm? = null

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        //(view.findViewById(R.id.text_title_list) as TextView).text = "直近のイベント"
        recyclerView = view.findViewById(R.id.fragment_tasks) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity.applicationContext)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//      RealmでTaskを全て持ってくる
        realm = Realm.getDefaultInstance()
        list.addAll(realm!!.where(Task::class.java).equalTo("isDone", false).findAllSorted("milestone", Sort.ASCENDING))

        recyclerView!!.adapter = TaskRecyclerViewAdapter(list)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        realm!!.close()
        super.onDestroy()
    }

    // realmの保存とカードの作成
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTaskCreated(e: TaskCreatedEvent) {
        if (e.task.content == "") {
            EventBus.getDefault().post(ToastEvent("タスクのタイトルは必ず入力して下さい"))
            return
        }
        realm!!.executeTransaction { realm -> realm.copyToRealmOrUpdate(e.task) }
        list.add(e.task)
        recyclerView!!.adapter.notifyItemInserted(list.size - 1)
    }

    //  realmからの削除とカードの削除
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTaskDestroyed(e: TaskDestroyEvent) {
        val result = realm!!.where(Task::class.java).equalTo("id", list.removeAt(e.id).id).findAllSorted("milestone", Sort.DESCENDING)
        realm!!.executeTransaction { result.deleteAllFromRealm() }

        recyclerView!!.adapter.notifyItemRemoved(e.id)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTaskDone(e: TaskDoneEvent) {
        val result = realm!!.where(Task::class.java).equalTo("id", list.removeAt(e.id).id).findAll().first()
        realm!!.executeTransaction { result.isDone = true }

        recyclerView!!.adapter.notifyItemRemoved(e.id)
    }
}
