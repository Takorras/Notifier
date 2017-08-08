package info.kotako.Taaker.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import info.kotako.Taaker.R

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val genreView = view.findViewById(R.id.text_genre_cellview) as TextView
}