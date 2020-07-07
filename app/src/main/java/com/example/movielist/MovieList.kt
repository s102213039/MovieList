package com.example.movielist

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class MovieList(
    val page: String,
    val results: List<Result>,
    val total_pages: String,
    val total_results: String
) {
    class Deserializer : ResponseDeserializable<MovieList> {
        override fun deserialize(content: String): MovieList? =
            Gson().fromJson(content, MovieList::class.java)
    }

    override fun toString(): String {
        return "MovieList(page='$page', results=$results, total_pages='$total_pages', total_results='$total_results')"
    }
}

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<String>,
    val id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: String
) {
    override fun toString(): String {
        return "Result(adult=$adult, backdrop_path='$backdrop_path', genre_ids=$genre_ids, id='$id', original_language='$original_language', original_title='$original_title', popularity=$popularity, poster_path='$poster_path', release_date='$release_date', title='$title', video=$video, vote_average=$vote_average, vote_count='$vote_count')"
    }
}