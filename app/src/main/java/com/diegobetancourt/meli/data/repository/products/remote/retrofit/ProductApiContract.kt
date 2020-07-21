package com.diegobetancourt.meli.data.repository.products.remote.retrofit

import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Provides guidelines to consume MERCADOL LIBRE API product services.
 * instanced via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
interface ProductApiContract {

    /**
     * Search products in a specific site on Mercado Libre
     * Note: the site MCO (Colombia) is fixed for this request
     *
     * @param q      -> keyword to loking for
     * @param limit  -> elements limit per request
     * @param offset -> lower limit for a relative search.
     *
     */
    @GET("sites/MCO/search")
    suspend fun getProducts(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ProductsResponseModel>

    /**
     * Provides keywords suggestions in specific site on Mercado Libre
     * Note: the site MCO (Colombia) is fixed for this request
     *
     * @param q      -> keyword to loking for
     * @param limit  -> elements limit per request
     *
     */
    @GET("sites/MCO/autosuggest")
    suspend fun getSuggestions(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): Response<SuggestionsResponseModel>

    /**
     * Provides detail of a specific product on Mercado Libre
     *
     * @param id     -> product identifier to loking for
     *
     */
    @GET("items/{id}")
    suspend fun getDetails(
        @Path("id") id :String
    ): Response<DetailsResonposeModel>

    /**
     * Provides full description of a specific product on Mercado Libre
     *
     * @param id     -> product identifier to loking for
     *
     */
    @GET("items/{id}/descriptions")
    suspend fun getDescription(
        @Path("id") id :String
    ): Response<List<DescriptionResponseModel>>

}