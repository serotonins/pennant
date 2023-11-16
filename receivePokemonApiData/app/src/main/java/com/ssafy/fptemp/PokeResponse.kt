package com.ssafy.fptemp

import com.google.gson.annotations.SerializedName

data class NamesResponse (
    val count: Int,
    val results: List<Result>
)

data class Result(
    val name: String,
    val url: String
)


// -------------------------------------------
data class SpeciesResponse (
    val is_legendary : Boolean,
    val is_mythical : Boolean,

    val names : List<NameNational>
)

data class NameNational (
    val language : NameLanguage,
    val name : String
)

data class NameLanguage (
    @SerializedName("name") val nationName : String
)

// -------------------------------------------
data class ImageTypeResponse (
    val sprites : Sprites,
    val types : List<Types>
)


data class Sprites(
    val other: Other,
)

data class Other (
    @SerializedName("official-artwork") val officialArtwork : OfficialArtwork,
    val home : Home
)

data class OfficialArtwork(
    val front_default: String,
)

data class Home(
    val front_default: String,
)


// -------------------------------------------
data class Types (
    val type : TypeName
)

data class TypeName (
    @SerializedName("name") val typeName: String
)