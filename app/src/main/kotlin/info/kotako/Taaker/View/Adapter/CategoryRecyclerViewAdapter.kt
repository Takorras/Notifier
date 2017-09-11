package info.kotako.Taaker.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotako.Taaker.Event.CategorySelectedEvent
import info.kotako.Taaker.R
import org.greenrobot.eventbus.EventBus

class CategoryRecyclerViewAdapter(val list: List<String>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.cellview_category, parent, false)
        return CategoryViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.genreView.text = list[position]
        holder.itemView.setOnClickListener { EventBus.getDefault().post(CategorySelectedEvent(list[position])) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}