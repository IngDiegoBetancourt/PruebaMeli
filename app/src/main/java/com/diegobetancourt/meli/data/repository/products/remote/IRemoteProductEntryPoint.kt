package com.diegobetancourt.meli.data.repository.products.remote

import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DescriptionResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DetailsResonposeModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductsResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.SuggestionsResponseModel
import retrofit2.Response

/**
 * The entry point for the products remote.
 * Classes that need to consume the products API (like a repo) should have an implementation of this interface
 * preferably via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
interface IRemoteProductEntryPoint {
    suspend fun getProducts(query: String, offset: Int, limit: Int): Response<ProductsResponseModel>
    suspend fun getSuggestions(query: String, limit: Int): Response<SuggestionsResponseModel>
    suspend fun getDetails(id: String): Response<DetailsResonposeModel>
    suspend fun getDescription(id: String): Response<List<DescriptionResponseModel>>

}