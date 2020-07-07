package com.example.movielist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val secureBaseUrl = "https://image.tmdb.org/t/p/w92"
    var title = itemView.title
    var poster = itemView.poster
    var releaseDate = itemView.releaseDate
    var voteAverage = itemView.voteAverage
    var ratedR = itemView.ratedR
}