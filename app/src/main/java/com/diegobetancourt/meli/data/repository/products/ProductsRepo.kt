package com.diegobetancourt.meli.data.repository.products

import android.util.Log
import com.diegobetancourt.meli.data.common.ErrorTemplate
import com.diegobetancourt.meli.data.common.WrapperFromException
import com.diegobetancourt.meli.data.common.WrapperFromRequest
import com.diegobetancourt.meli.data.common.WrapperResponse
import com.diegobetancourt.meli.data.repository.products.local.ILocalProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DescriptionResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DetailsResonposeModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductsResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.SuggestionsResponseModel


/**
 * The repository for provides products services.
 * instance of [IProductsRepo]
 * instanced via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
class ProductsRepo(val remote: IRemoteProductEntryPoint,val local : ILocalProductEntryPoint ) :
    IProductsRepo {

    override suspend fun getProducts  (
        query: String,
        offset: Int,
        limit: Int
    ): WrapperResponse<ProductsResponseModel>  {
        var response = WrapperResponse<ProductsResponseModel>()
        kotlin.runCatching {
            remote.getProducts(query,offset,limit)
        }.onSuccess {
            response = WrapperFromRequest(it)
        }.onFailure {
            response = WrapperFromException(it)
        }
        return response
    }

    override suspend fun getSuggestions(
        query: String,
        limit: Int
    ): WrapperResponse<SuggestionsResponseModel> {

        var response = WrapperResponse<SuggestionsResponseModel>()
        kotlin.runCatching {
            remote.getSuggestions(query,limit)
        }.onSuccess {
            response = WrapperFromRequest(it)
        }.onFailure {
            response = WrapperFromException(it)
        }
        return response

    }

    override suspend fun getDetails(id: String): WrapperResponse<DetailsResonposeModel> {

        var response = WrapperResponse<DetailsResonposeModel>()
        kotlin.runCatching {
            remote.getDetails(id)
        }.onSuccess {
            response = WrapperFromRequest(it)
        }.onFailure {
            response = WrapperFromException(it)
        }

        return response
    }

    override suspend fun getDescription(id: String): WrapperResponse<List<DescriptionResponseModel>> {
        var response = WrapperResponse<List<DescriptionResponseModel>>()
        kotlin.runCatching {
            remote.getDescription(id)
        }.onSuccess {
            response = WrapperFromRequest(it)
        }.onFailure {
            response = WrapperFromException(it)
        }

        return response
    }

    override fun get_history(): Set<String> {
        return local.get_history()
    }

    override fun save_seach(query: String){
         local.save_seach(query)
    }


}