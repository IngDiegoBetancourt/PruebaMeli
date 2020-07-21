package com.diegobetancourt.meli.data.repository.products.remote.retrofit
import android.os.Parcelable
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * This file contains the DTO'S for  [ProductApiContract] responses
 */

data class ErrorResponseModel(
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "error")
    val error: String
)

data class SuggestionsResponseModel(
    @field:Json(name = "suggested_queries")
    val data: List<Suggestion> = ArrayList()
)
data class Suggestion(
    @field:Json(name = "q")
    val text: String = "",

    @field:Json(name = "match_start")
    val match_start: Int = 0,

    @field:Json(name = "match_end")
    val match_end: Int = 0
)

data class ProductsResponseModel(
    @field:Json(name = "site_id")
    val site_id: String = "",

    @field:Json(name = "query")
    val query: String = "",

    @field:Json(name = "results")
    val results: List<Product> = ArrayList(),

    @field:Json(name = "paging")
    val paging: Paging = Paging()
)
data class Paging (
    val total : Int = 0,
    val offset : Int = 0,
    val limit : Int = 0,
    val primary_results : Int = 0
)

@Parcelize
data class Product (
    @field:Json(name ="id")
    val id: String,

    @field:Json(name ="title")
    val title: String,

    @field:Json(name ="thumbnail")
    val thumbnail: String,

    @field:Json(name ="price")
    val price: Int,

    @field:Json(name ="condition")
    val condition: String,

    @field:Json(name ="sold_quantity")
    val soldQuantity: Int = 0,

    @field:Json(name ="available_quantity")
    val availableQuantity:Int = 0,

    @field:Json(name ="shipping")
    val shipping: Shipping,

    @field:Json(name ="attributes")
    var attributes: List<Attribute>
) : Parcelable

@Parcelize
data class Attribute (
    @field:Json(name ="name")
    val name: String,

    @field:Json(name ="value_name")
    val value_name: String

): Parcelable

@Parcelize
data class Shipping (
    @field:Json(name ="free_shipping")
    val freeShipping: Boolean
): Parcelable

data class DetailsResonposeModel(
    @field:Json(name = "pictures")
    val pictures: List<Picture> = ArrayList()
)
data class Picture(
    @field:Json(name = "secure_url")
    val secure_url: String
)

data class DescriptionResponseModel(
    @field:Json(name = "plain_text")
    val plain_text: String
)
