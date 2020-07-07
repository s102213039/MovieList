package com.example.movielist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    var page: Int = 1
    var categoryName = "top_rated"
    var replaceData = false
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDrawerListener()
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setAdapter()
        loadMoreData()
        categoryText.text = "最高評分"

        navigation_view.apply {
            itemBackground = navigationItemBackground(context)
        }

        movieListRecycler.addOnScrollListener(object : RecyclerViewScrollListener() {
            override fun onScrollUp() {
            }

            override fun onScrollDown() {
            }

            override fun onLoadMore() {
                replaceData = false
                loadMoreData()
            }
        })
    }

    private fun setDrawerListener() {
        DrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })
    }

    private fun setAdapter() {
        movieListAdapter = MovieListAdapter(this@MainActivity)
        movieListRecycler.setHasFixedSize(false)
        movieListRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        movieListRecycler.adapter = movieListAdapter
    }

    private fun loadMoreData() {
        Fuel.get(
            "https://api.themoviedb.org/3/movie/$categoryName",
            listOf(
                "api_key" to "ebd9ebcc34a61e3b21abfad72e7f4174",
                "page" to "$page",
                "language" to "zh-TW"
            )
        )
            .responseObject(MovieList.Deserializer()) { request, response, result ->
                val (movie, err) = result
                when (result) {
                    is Result.Failure -> {
                        Toast.makeText(this@MainActivity, "連線失敗", Toast.LENGTH_SHORT).show()
                        movieListAdapter.showLoading(false)
                    }
                    is Result.Success -> {
                        result.get().results.forEach {
                            Log.e("resultresult", "loadMoreData: ${it.toString()}")
                        }
                        page++
                        if (replaceData) {
                            result.get().results.let {
                                movieListAdapter.replace(it)
                            }
                        } else {
                            result.get().results.let {
                                if (it.isNotEmpty()) {
                                    movieListAdapter.addAll(it)
                                }
                            }
                        }
                        movieListAdapter.showLoading(true)
                        movieListAdapter.notifyDataSetChanged()
                    }
                }
            }.join()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.topRated -> {
                if (categoryName != "top_rated") {
                    page = 1
                    categoryName = "top_rated"
                    replaceData = true
                    loadMoreData()
                    categoryText.text = "最高評分"
                }
                true
            }
            R.id.popular -> {
                if (categoryName != "popular") {
                    page = 1
                    categoryName = "popular"
                    replaceData = true
                    loadMoreData()
                    categoryText.text = "高人氣"
                }
                true
            }
            R.id.upComing -> {
                if (categoryName != "upcoming") {
                    page = 1
                    categoryName = "upcoming"
                    replaceData = true
                    loadMoreData()
                    categoryText.text = "即將上映"
                }
                true
            }
            R.id.nowPlaying -> {
                if (categoryName != "now_playing") {
                    page = 1
                    categoryName = "now_playing"
                    replaceData = true
                    loadMoreData()
                    categoryText.text = "現正撥出"
                }
                true
            }
//            R.id.Latest -> {
//                if (categoryName != "latest") {
//                    page = 1
//                    categoryName = "latest"
//                    loadMoreData(categoryName, true)
//                    categoryText.text = "最新熱播"
//                }
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}


