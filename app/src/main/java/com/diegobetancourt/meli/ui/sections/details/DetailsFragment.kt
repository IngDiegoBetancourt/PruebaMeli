package com.diegobetancourt.meli.ui.sections.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.diegobetancourt.meli.R
import com.diegobetancourt.meli.data.repository.products.IProductsRepo
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Product
import com.diegobetancourt.meli.databinding.FragmentDetailsBinding
import com.diegobetancourt.meli.ui.sections.results.ResultsFragment
import com.glide.slider.library.slidertypes.TextSliderView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass for show details from selected product.
 * Use the [Fragment.findNavController] to
 * create an instance of this fragment.
 *
 * use safe args
 * @param product : [Product] -> product to get details
 */
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()
    lateinit var binding : FragmentDetailsBinding

    //safe args
    val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = requireActivity()

        setupView(args.product)
        handleObservers()

        //prevents http_requests on screen orientation change
        if(viewModel.description.value == null){
            viewModel.get_details(args.product.id)
        }else{
            viewModel.load_from_memory()
        }

        return binding.root
    }

    fun setupView(product: Product){
        //prevents auto change slider
        binding.slider.apply {
            stopAutoCycle()
        }

        //configure list features
        binding.listFeatures.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = FeatureRecyclerViewAdapter(attrs = product.attributes)
        }

        //binding ui details data from product to layout
        binding.dt = UIDetailsData(product)


    }

    fun handleObservers() {
        //observes details info changes to show
        viewModel.details.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let {
                it.pictures.forEach {
                    binding.slider.addSlider(TextSliderView(context).image(it.secure_url))
                }
            }
        })

        //observes description info changes to show
        viewModel.description.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let {
                binding.descripcion.text = it.plain_text
            }
        })

        //observes error info changes to alert
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandledOrReturnNull()?.let{
                Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
            }
        })

    }



    }