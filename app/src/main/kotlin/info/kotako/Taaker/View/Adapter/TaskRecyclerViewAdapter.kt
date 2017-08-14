package info.kotako.Taaker.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import info.kotako.Taaker.Event.TaskActionEvent
import info.kotako.Taaker.Event.TaskDoneEvent
import info.kotako.Taaker.Event.TaskShowEvent
import info.kotako.Taaker.Model.Task
import info.kotako.Taaker.R
import org.greenrobot.eventbus.EventBus
import java.util.*

class TaskRecyclerViewAdapter(val list: List<Task>) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.cardview_task, parent, false)
        return TaskViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder!!.contentView.text = list[position].content
        holder.milestoneView.text = diff(list[position].milestone)

        // 完了したタスクはチェック非表示
        if (list[holder.adapterPosition].isDone) (holder.itemView.findViewById(R.id.button_done) as ImageButton).visibility = ImageButton.INVISIBLE

        // カードにクリックイベントを実装
        holder.itemView.findViewById(R.id.button_done).setOnClickListener { EventBus.getDefault().post(TaskDoneEvent(holder.adapterPosition)) }

        holder.itemView.setOnClickListener { EventBus.getDefault().post(TaskShowEvent(list[holder.adapterPosition].id)) }
        holder.itemView.setOnLongClickListener {
            EventBus.getDefault().post(TaskActionEvent(holder.adapterPosition))
            true }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun diff(date: Date): String {
        val dayDiff = (date.time - System.currentTimeMillis()) / 86400000
        val hourDiff = ((date.time - System.currentTimeMillis()) / 3600000) % 24
        return "${dayDiff}日 ${hourDiff}時間"
    }
}