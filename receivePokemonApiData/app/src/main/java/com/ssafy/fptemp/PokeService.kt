package com.ssafy.fptemp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    // 151번까지 이름 받아오기
    @GET("pokemon/")
    suspend fun getNames(@Query(value = "limit") limit: Int = 151) : Response<NamesResponse>

    // 받아온 이름으로 isMythical, isLegendary
    @GET("pokemon-species/{name}/")
    suspend fun getMythicalLegendary(@Path(value = "name") name : String) : Response<SpeciesResponse>

    // 받아온 이름으로 Image, type
    @GET("pokemon/{name}/")
    suspend fun getImageType(@Path(value = "name") name : String) : Response<ImageTypeResponse>
}