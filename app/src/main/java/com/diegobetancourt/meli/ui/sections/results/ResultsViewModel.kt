package com.diegobetancourt.meli.ui.sections.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegobetancourt.meli.data.common.CatchedError
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Product
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductsResponseModel
import kotlinx.coroutines.launch
import com.diegobetancourt.meli.data.common.Event
import com.diegobetancourt.meli.data.repository.products.IProductsRepo
import com.diegobetancourt.meli.ui.sections.search.SearchFragment

/**
 * A  [ViewModel] subclass for manage [ResultsFragment] events and get products from [IProductsRepo.getProducts].
 * Use the dependency injections to
 * create an instance of this viewmodel.
 */
class ResultsViewModel(val repo: IProductsRepo) : ViewModel(), ResultsViewAdapter.OnResultClickListener {

    //current products data list
    val products : LiveData<ArrayList<Product>> = MutableLiveData(ArrayList())

    //manage request event
    private val _request = MutableLiveData<Event<RequestDataState>>()
    val request: LiveData<Event<RequestDataState>> = _request

    //notify selected product
    private val _selected_product = MutableLiveData<Event<Product>>()
    val selected_product: LiveData<Event<Product>> = _selected_product

    private val RESULTS_PER_REQUEST = 10

    //get search results from repo
    fun load_products(query : String, offset: Int = 0){
        viewModelScope.launch {
            runCatching {
                repo.getProducts(query,offset,RESULTS_PER_REQUEST)
            }.onSuccess {
                if(it.success){
                    _request.value = Event(RequestDataState( data =  it.body()))
                }else{
                    _request.value = Event(RequestDataState( error =  it.error))
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    //observe result item action for show @product in a specifc screen
    override fun openDetails(product: Product) {
        _selected_product.value =
            Event(product)
    }
}

//wrapper class for manage the search request
data class RequestDataState(
    val data : ProductsResponseModel? = null,
    val error : CatchedError? = null
)