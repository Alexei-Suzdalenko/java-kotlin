package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.simpleactivity.serv.ApiService
import com.example.simpleactivity.serv.Post
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_get_post_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetPostRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_post_request)
        Log.d("none", "===> START REQUEST" )
        Log.i("none", "===> START REQUEST" )
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ApiService>(ApiService::class.java)

        service.getAllPosts().enqueue(object : Callback<MutableList<Post>>{
            override fun onResponse(call: Call<MutableList<Post>>, response: Response<MutableList<Post>>) {
                val posts = response.body()
          //      textView4.text = posts?.get(0)?.title.toString()
                Log.d("none", "===>" + Gson().toJson(posts) )
            }
            override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


        service.getPostById(1).enqueue(object: Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val post = response.body()
               // textView4.text = post.toString()
                Log.d("none", "===>" + Gson().toJson(post) )
               // textView4.text = Gson().toJson(post).toString()
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


        val post : Post = Post(2,2,"hello quetqw", "body")
        service.editPostById(1, post).enqueue(object: Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val post1 = response.body()
                 textView4.text = post1.toString()
                Log.d("none", "===>" + Gson().toJson(post1) )
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }
}