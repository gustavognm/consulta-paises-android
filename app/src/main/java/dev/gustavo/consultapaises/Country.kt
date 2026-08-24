package dev.gustavo.consultapaises

import com.google.gson.annotations.SerializedName

data class CountrySearchResponse(
    val data: CountryData
)

data class CountryData(
    val objects: List<Country>
)

data class Country(
    val names: CountryNames,
    val capitals: List<Capital>?,
    val region: String?,
    val population: Long?,
    val flag: CountryFlag
)

data class CountryNames(
    val common: String,
    val official: String?
)

data class Capital(
    val name: String
)

data class CountryFlag(
    @SerializedName("url_png") val urlPng: String?,
    @SerializedName("url_svg") val urlSvg: String?
)
