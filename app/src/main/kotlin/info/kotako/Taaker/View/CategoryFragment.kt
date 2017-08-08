package info.kotako.Taaker.View

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotako.Taaker.Model.Task
import info.kotako.Taaker.R
import info.kotako.Taaker.View.Adapter.CategoryRecyclerViewAdapter
import io.realm.Realm

class CategoryFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var list: ArrayList<String> = arrayListOf("None")

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // RealmでTaskのカテゴリ名を全て持ってくる、重複はない幹事
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                list.addAll(realm.where(Task::class.java).distinct("genre").map { value -> value.genre }.toTypedArray())
                list.remove("")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = view.findViewById(R.id.fragment_tasks) as RecyclerView
        recyclerView!!.addItemDecoration(DividerItemDecoration(recyclerView!!.context, LinearLayoutManager(activity).orientation))
        recyclerView!!.layoutManager = LinearLayoutManager(activity.applicationContext)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView!!.adapter = CategoryRecyclerViewAdapter(list)
    }
}