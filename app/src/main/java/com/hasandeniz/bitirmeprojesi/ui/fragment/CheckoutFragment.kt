package com.hasandeniz.bitirmeprojesi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.databinding.FragmentCheckoutBinding
import com.hasandeniz.bitirmeprojesi.ui.BottomNavVisibilityManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {
    private lateinit var binding : FragmentCheckoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_checkout,container,false)
        binding.checkoutFragmentObject = this
        return binding.root
    }

    fun backToOrderScreen(){
        val act = activity as BottomNavVisibilityManager
        act.makeVisible()
        val action = CheckoutFragmentDirections.actionCheckoutFragmentToOrderFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}