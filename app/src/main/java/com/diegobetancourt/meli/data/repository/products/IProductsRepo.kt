package com.diegobetancourt.meli.data.repository.products


import com.diegobetancourt.meli.data.common.WrapperResponse
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DescriptionResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DetailsResonposeModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductsResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.SuggestionsResponseModel

/**
 * The repository for provides products services.
 * Classes that need to consume products services should have an implementation of this interface
 * preferably via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
interface IProductsRepo {
    suspend fun getProducts(
        query: String,
        offset: Int,
        limit: Int
    ): WrapperResponse<ProductsResponseModel>

     suspend fun getSuggestions(
        query: String,
        limit: Int
    ): WrapperResponse<SuggestionsResponseModel>

    suspend fun getDetails(id: String): WrapperResponse<DetailsResonposeModel>

    suspend fun getDescription(id: String): WrapperResponse<List<DescriptionResponseModel>>

    fun get_history(): Set<String>

    fun save_seach(query: String)

}