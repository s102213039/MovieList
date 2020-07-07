package com.example.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter(context: Context) : SimpleAdapter<Result>(context) {

    override fun getYourItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    override fun getYourItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return MovieListHolder(view)
    }

    override fun bindYourViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val mHolder = holder as MovieListHolder
        val entity = getItem(position)
        Glide.with(mHolder.itemView.context)
            .load(mHolder.secureBaseUrl + entity.poster_path)
            .into(mHolder.poster)
        mHolder.title.text = entity.title
        mHolder.releaseDate.text = "上映日期: ${entity.release_date}"
        mHolder.voteAverage.text = "評分: ${entity.vote_average.toString()}"
        mHolder.ratedR.visibility = if (entity.adult) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
