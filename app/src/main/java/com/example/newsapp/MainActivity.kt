package com.example.newsapp

import android.os.Bundle
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
                    TODO("Show Error")
                }

            })

    }
}