package com.example.news

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
        showNetworkStatus()
    }

    private fun showNetworkStatus() {
        val a = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = a.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {
            if (networkInfo.type == ConnectivityManager.TYPE_MOBILE)
            {
                Toast.makeText(applicationContext, "Mobile is connected", Toast.LENGTH_SHORT).show()
            }
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI)
            {
                Toast.makeText(applicationContext, "Wifi is connected", Toast.LENGTH_SHORT).show()}
            else
            {
                var toast = Toast.makeText(applicationContext, "You are offline", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }

        }
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",1)
        news.enqueue(object : Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                Log.d("success","news")
                val news:News?=response.body()
                if (news != null)
                {
                    Log.d("Main",news.toString())
                    adapter= NewsAdapter(this@MainActivity,news.articles)
                    newsList.adapter=adapter
                    newsList.layoutManager= LinearLayoutManager(this@MainActivity)


                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Error","Error in Fetching News",t)
            }
        })

    }
}