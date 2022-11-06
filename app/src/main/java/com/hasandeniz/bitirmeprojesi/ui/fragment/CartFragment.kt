package com.hasandeniz.bitirmeprojesi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.data.entity.CartFood
import com.hasandeniz.bitirmeprojesi.databinding.FragmentCartBinding
import com.hasandeniz.bitirmeprojesi.ui.BottomNavVisibilityManager
import com.hasandeniz.bitirmeprojesi.ui.adapter.CartFoodAdapter
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.CartViewModel
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private val viewModel : CartViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.fragmentObject = this

        viewModel.cartFoodList.observe(viewLifecycleOwner){ state->
            when (state.status){
                Status.SUCCESS -> {
                    showCart(state.data as List<CartFood>)
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    showError(state.message)
                }
            }
        }

        return binding.root
    }

    private fun showError(message: String?) {
        binding.errorMessage = message
        binding.rvCart.visibility = View.GONE
        binding.cvCost.visibility = View.GONE
        binding.btnCheckout.visibility = View.GONE
        binding.ivEmptyCart.visibility = View.GONE
        binding.tvEmptyCart.visibility = View.GONE
        binding.circularProgressCart.visibility = View.GONE
        binding.tvErrorCart.visibility = View.VISIBLE
        binding.ivErrorCart.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.rvCart.visibility = View.GONE
        binding.cvCost.visibility = View.GONE
        binding.btnCheckout.visibility = View.GONE
        binding.ivEmptyCart.visibility = View.GONE
        binding.tvEmptyCart.visibility = View.GONE
        binding.tvErrorCart.visibility = View.GONE
        binding.ivErrorCart.visibility = View.GONE
        binding.circularProgressCart.visibility = View.VISIBLE
    }

    private fun showCart(data: List<CartFood>) {
        binding.tvErrorCart.visibility = View.GONE
        binding.ivErrorCart.visibility = View.GONE
        binding.circularProgressCart.visibility = View.GONE

        if (data.isNotEmpty()){
            binding.rvCart.visibility = View.VISIBLE
            binding.cvCost.visibility = View.VISIBLE
            binding.btnCheckout.visibility = View.VISIBLE
            binding.ivEmptyCart.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.GONE
        }
        else {
            binding.rvCart.visibility = View.GONE
            binding.cvCost.visibility = View.GONE
            binding.btnCheckout.visibility = View.GONE
            binding.ivEmptyCart.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.VISIBLE
        }

        val adapter = CartFoodAdapter(requireContext(),data.sortedBy { it.yemek_adi },viewModel)
        binding.adapter = adapter
        var totalItemCost = 0
        data.forEach { cartFood ->
            totalItemCost += cartFood.yemek_fiyat*cartFood.yemek_siparis_adet
        }
        binding.totalItemCost = totalItemCost


    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCart()
    }

    fun checkoutButton(){
        if ((viewModel.cartFoodList.value?.data?.size ?: 0) > 0){
            viewModel.clearCart()
            val act = activity as BottomNavVisibilityManager
            act.makeGone()
            val action = CartFragmentDirections.actionCartFragmentToCheckoutFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

    }
}