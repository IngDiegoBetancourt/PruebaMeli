package com.diegobetancourt.meli.ui.sections.results

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.diegobetancourt.meli.R
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductsResponseModel
import com.diegobetancourt.meli.databinding.FragmentResultsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass for fast product search.
 * Use the [Fragment.findNavController] to
 * create an instance of this fragment.
 *
 * use safe args
 * @param query :String -> keyword to search
 */
class ResultsFragment : Fragment() {

    lateinit var binding: FragmentResultsBinding
    private val viewModel: ResultsViewModel by viewModel()

    //bind safe args
    val args: ResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultsBinding.inflate(inflater, container, false)

        setupView()
        handleObservers()

        //prevents http request on change orientation screen
        if(viewModel.request.value == null){
            viewModel.load_products(args.query)
        }else{
            viewModel.request.value?.peekContent()?.data?.let {
                updateQueryText(it)
            }
        }
        return binding.root
    }

    fun setupView(){

        // use for refresh data
        binding.srlResults.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.load_products(args.query)
            }
        }

        // show results on recycler view
        binding.resultsList.apply {
            layoutManager = StaggeredGridLayoutManager(
                getColumnsNumber(),
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = ResultsViewAdapter(results = viewModel.products.value!!,listener = viewModel)

            // notify on scrolled for load aditional data
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!canScrollVertically(RecyclerView.LAYOUT_DIRECTION_RTL)){
                        Log.d("ResultsFragment","onScrolled")
                        viewModel.load_products(args.query,adapter?.itemCount?:0)
                    }
                }
            })
        }


    }

    fun handleObservers(){

        //observes request state
        viewModel.request.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let { result ->
                Log.d("ResultsFragment","new data")
                if(result.error == null){
                    result.data?.let {
                        val adapter = (binding.resultsList.adapter as? ResultsViewAdapter)
                        if(it.paging.offset == 0){
                             adapter?.restartItems(it.results)
                        }else{
                            adapter?.addItems(it.results)
                        }
                        updateQueryText(it)
                    }

                }else{
                    Toast.makeText(context, result.error.message, Toast.LENGTH_SHORT).show()
                    Log.w("RESULTS:","ERROR: "+result.error)
                }
            }
        })

        //observes selected product for redirect to Details screen
        viewModel.selected_product.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let {
                findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToDetailsFragment(it))
            }

        })
    }

    //update text view from request
    fun updateQueryText(response: ProductsResponseModel){
        binding.queryText.text = getString(R.string.query_result_text,response.paging.total,response.query)
    }

    //define columns number per orientation screen
    fun getColumnsNumber(): Int{
        return when(resources.configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
    }


}