package com.diegobetancourt.meli.ui.sections.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegobetancourt.meli.data.common.CatchedError
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Suggestion
import kotlinx.coroutines.launch
import com.diegobetancourt.meli.data.common.Event
import com.diegobetancourt.meli.data.repository.products.IProductsRepo

/**
 * A  [ViewModel] subclass for manage [SearchFragment] events and get suggestions from [IProductsRepo.getSuggestions].
 * Use the dependency injections to
 * create an instance of this viewmodel.
 */
class ProductsViewModel(val repo: IProductsRepo) : ViewModel(), SearchRecyclerViewAdapter.ISearchActions {

    //current suggestions data list
    val suggestions : LiveData<ArrayList<Suggestion>> = MutableLiveData(ArrayList(repo.get_history().map { Suggestion(text = it) }))

    //manage request event
    private val _request = MutableLiveData<Event<SearchRequestState>>()
    val request: LiveData<Event<SearchRequestState>> = _request

    //manage searchview state
    private val _textSearch = MutableLiveData<Event<SearchViewState>>()
    val textSearch: LiveData<Event<SearchViewState>>  = _textSearch

    //get suggestions from repo
    fun getSuggetions(query : String){

        _textSearch.postValue(
            Event(SearchViewState(query = query))
        )

        if(query.trim().equals("")){
            load_local_suggestions()
            return
        }

        viewModelScope.launch {
            runCatching {
                repo.getSuggestions(query,10)
            }.onSuccess {
                if(textSearch.value!!.peekContent().query.equals(query)){
                    if(it.success){
                        suggestions.value?.let{ results ->
                            results.clear()
                            results.addAll(it.body().data)
                        }
                        _request.value = Event(SearchRequestState())
                    }else{
                        _request.value = Event(SearchRequestState(error = it.error ))
                    }
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    //load suggetions from preferences
    fun load_local_suggestions(){
        suggestions.value?.apply{
            clear()
            addAll(repo.get_history().map { Suggestion(text = it) })
        }

        _request.value = Event(SearchRequestState())
    }

    //save seach on preferences
    fun save_search(query: String){
        repo.save_seach(query)
    }

    //observe seggestions item action for put @query in the searchview
    override fun useText(query: String) {
        _textSearch.value =
            Event(SearchViewState(query = query))
    }

    //observe seggestions item action for use @query directly
    override fun search(query: String) {
        _textSearch.value = Event(
            SearchViewState(
                query = query,
                search = true
            )
        )
    }
}

//wrapper class for manage the search view
data class SearchViewState(
    val query : String = "",
    val search : Boolean = false
)

//wrapper class for manage the suggestion request
data class SearchRequestState(
    val error : CatchedError? = null
)