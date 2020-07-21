package com.diegobetancourt.meli.ui.sections.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diegobetancourt.meli.databinding.FragmentSplashBinding
import com.diegobetancourt.meli.ui.MainActivity

/**
 * A simple [Fragment] subclasa for provides splash screen.
 * its a start destination of [R.navigation.nav_graph.xml]
 * Use the [Fragment.findNavController] to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment(){

    lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        setupView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSearchFragment())
            (requireActivity() as MainActivity).supportActionBar?.show()
        }, 3000)
    }

    fun setupView(){
        (requireActivity() as MainActivity).supportActionBar?.hide()
    }



}