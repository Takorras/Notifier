package kotako.java.info.notifyer.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotako.java.info.notifyer.Event.TaskDestoyEvent
import kotako.java.info.notifyer.Event.ToastEvent
import kotako.java.info.notifyer.Model.Task
import kotako.java.info.notifyer.R
import kotako.java.info.notifyer.View.TaskViewHolder
import org.greenrobot.eventbus.EventBus
import java.util.*

class TaskRecyclerViewAdapter(val list: List<Task>) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.cardview_task, parent, false)
        return TaskViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder!!.contentView.text = list[position].content
        holder.milestoneView.text = "あと${daysDiff(list[position].milestone)}日"

        holder.itemView.setOnClickListener { EventBus.getDefault().post(ToastEvent("selected")) }
        holder.itemView.setOnLongClickListener {
            EventBus.getDefault().post(TaskDestoyEvent(position))
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun daysDiff(date: Date): Long {
        return (date.time - System.currentTimeMillis()) / 86400000
    }
}