package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.news.api.api.model.ApiServices
import com.example.newsapp.news.api.api.model.SourceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ApiServices.getWebServices().loadSources()
            .enqueue(object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse?>,
                    response: Response<SourceResponse?>
                ) {
                    Log.e("onResponse", response.body().toString())
                    if(response.isSuccessful){
                        TODO("Show Tab Bar")
                    }else{
                        TODO("Show Error")
                    }
                }

                override fun onFailure(
                    call: Call<SourceResponse?>,
                    t: Throwable
                ) {
                    Log.e("onFailure", t.message.toString())
                    TODO("Show Error")
                }

            })

    }
}