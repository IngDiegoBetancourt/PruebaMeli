package com.diegobetancourt.meli.ui.sections.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.diegobetancourt.meli.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass for fast product search.
 * its in [R.navigation.nav_graph.xml]
 * Use the [Fragment.findNavController] to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {


    lateinit var binding : FragmentSearchBinding
    private val viewModel: ProductsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment via autobinding
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        setupView()
        handleObservers()

        return binding.root
    }

    fun setupView(){

        //prevents back action
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        //add searchview listeners
        binding.searchView.setOnQueryTextListener(this)

        //initialices the recyclerView
        binding.suggestionList.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = SearchRecyclerViewAdapter(viewModel.suggestions.value!!,viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        showKeyboard()
    }

    fun showKeyboard(){
        requireActivity().run {
            val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }

    fun handleObservers(){

        //observes the search view state
        viewModel.textSearch.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let {
                binding.searchView.setQuery(it.query,it.search)
            }
        })

        //observe the suggestions request
        viewModel.request.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let{result ->
                Log.d("SearchFragment:", "New suggestions")
                if(result.error == null){
                    binding.suggestionList.adapter?.notifyDataSetChanged()
                } else{
                    Toast.makeText(context,result.error.message,Toast.LENGTH_SHORT).show()
                    Log.d("SEARCH_RESULT:", "ERROR")
                }
            }

        })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.d("SEARCH_TEXT_SUBMIT",query)
        binding.searchView.clearFocus()
        viewModel.save_search(query)
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToResultsFragment(query))
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        Log.d("SEARCH_TEXT_CHANGE",query)
        if(!(viewModel.textSearch.value !=null && query.equals(viewModel.textSearch.value!!.peekContent().query)))
            viewModel.getSuggetions(query)

        return true
    }
}