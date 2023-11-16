package com.ssafy.fptemp

data class PokeData (
    val id : Int,
    val name : String,
    val nameKorean: String,
    val imageOfficial: String,
    val image3D: String,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val percentage: Double,
    val type: List<String>


//    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
//    @ColumnInfo(name = "name")val name: String,
//    @ColumnInfo(name = "image")val image: String,
//    @ColumnInfo(name = "isLegendary")val isLegendary: Boolean,
//    @ColumnInfo(name = "isMythical")val isMythical: Boolean,
//    @ColumnInfo(name = "percentage")val percentage: Double,
//    @ColumnInfo(name = "type")val type: String
)