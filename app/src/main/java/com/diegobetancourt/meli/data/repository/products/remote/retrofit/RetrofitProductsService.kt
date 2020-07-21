package com.diegobetancourt.meli.data.repository.products.remote.retrofit
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import retrofit2.Response
/**
 * The remote entry point instance for [IRemoteProductEntryPoint] using [ProductApiContract].
 * instanced via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
class RetrofitProductsService(val productApi: ProductApiContract) : IRemoteProductEntryPoint {

    override suspend fun getProducts(query: String, offset: Int, limit: Int): Response<ProductsResponseModel> = productApi.getProducts(query,offset,limit)
    override suspend fun getSuggestions(query: String,  limit: Int): Response<SuggestionsResponseModel> =  productApi.getSuggestions(query,limit)
    override suspend fun getDetails(id: String): Response<DetailsResonposeModel> = productApi.getDetails(id)
    override suspend fun getDescription(id: String): Response<List<DescriptionResponseModel>> = productApi.getDescription(id)


}