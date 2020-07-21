package com.diegobetancourt.meli.ui.sections.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegobetancourt.meli.data.common.CatchedError
import com.diegobetancourt.meli.data.common.Event
import com.diegobetancourt.meli.data.repository.products.IProductsRepo
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DescriptionResponseModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.DetailsResonposeModel
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Product
import kotlinx.coroutines.launch

/**
 * A  [ViewModel] subclass for manage [DetailsFragment] events and get detail info from [IProductsRepo.getDetails] and a description from [IProductsRepo.getDescription].
 * Use the dependency injections to
 * create an instance of this viewmodel.
 */
class DetailsViewModel(val repo: IProductsRepo) : ViewModel() {

    //Notify details loaded
    private val _details = MutableLiveData<Event<DetailsResonposeModel>>()
    val details: LiveData<Event<DetailsResonposeModel>> = _details

    //Notify description loaded
    private val _description = MutableLiveData<Event<DescriptionResponseModel>>()
    val description: LiveData<Event<DescriptionResponseModel>> = _description

    //Notify error
    private val _error = MutableLiveData<Event<CatchedError>>()
    val error: LiveData<Event<CatchedError>> = _error

    //get details and description from repo
    fun get_details(id_product : String){
        viewModelScope.launch {
            runCatching {
                val rq = repo.getDetails(id_product)
                if(rq.success){
                    _details.value = Event(rq.body())
                    val rq2 = repo.getDescription(id_product)
                    if(rq2.success){
                        _description.value = Event(rq2.body().get(0))
                    }else{
                        _error.value = Event(rq2.error!!)
                    }
                }else{
                    _error.value = Event(rq.error!!)
                }
                repo.getDescription(id_product)
            }.onFailure{
                it.printStackTrace()
            }
        }
    }

    //load results from memory
    fun load_from_memory() {
        _details.value = Event(_details.value!!.peekContent())
        _description.value = Event(description.value!!.peekContent())
    }


}