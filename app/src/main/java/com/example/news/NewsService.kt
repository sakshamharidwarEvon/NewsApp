package com.example.news

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://newsapi.org/v2/everything?q=apple&from=2020-09-16&to=2020-09-16&sortBy=popularity&apiKey=c3a7436a3d7b444191c6789ff40a384f
//http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=c3a7436a3d7b444191c6789ff40a384f

const val BASE_URL="https://newsapi.org/"
const val API_KEY="c3a7436a3d7b444191c6789ff40a384f"

interface NewsInterface{

    @GET(value = "v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country:String,@Query("page") page:Int) :Call<News>

    //https://newsapi.org/v2/top-headlines?apiKey=c3a7436a3d7b444191c6789ff40a384f&country=in&page=1
}

object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}