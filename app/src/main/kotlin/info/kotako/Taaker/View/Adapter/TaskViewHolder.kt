package info.kotako.Taaker.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import info.kotako.Taaker.R

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contentView = view.findViewById(R.id.text_card_task_name) as TextView
    val milestoneView = view.findViewById(R.id.text_card_milestone) as TextView
}
