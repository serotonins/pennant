package com.ssafy.fptemp

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {

    val Base_Url = "https://pokeapi.co/api/v2/"
//    val Species_Base_Url = "https://pokeapi.co/api/v2/pokemon-species/"
//    val Image_Base_Url = "https://pokeapi.co/api/v2/pokemon/"

    companion object {

//        받아올 친구들
//        val id: Int,
//        val name: String,
//        val image: String,
//        val isLegendary: Boolean,
//        val isMythical: Boolean,
//        val percentage: Double,
//        val type: String


        lateinit var baseRetrofit: Retrofit
//    어쩌구 // id 151번까지 이름 받아오기
//        lateinit var speciesRetrofit: Retrofit // 받아온 이름으로 isMythical, isLegendary
//        lateinit var imageRetrofit: Retrofit // 받아온 이름으로 이미지, types 받아오기


    }

    override fun onCreate() {
        super.onCreate()

        baseRetrofit = Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        speciesRetrofit = Retrofit.Builder()
//            .baseUrl(Species_Base_Url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        imageRetrofit = Retrofit.Builder()
//            .baseUrl(Image_Base_Url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()


    }
}