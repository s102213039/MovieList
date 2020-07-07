package com.example.movielist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loader_item_layout.view.*

class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mProgressBar = itemView.progressbar
}