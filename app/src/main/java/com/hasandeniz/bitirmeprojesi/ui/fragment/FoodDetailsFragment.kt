package com.hasandeniz.bitirmeprojesi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.databinding.FragmentFoodDetailsBinding
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodDetailsFragment : Fragment() {
    private lateinit var binding : FragmentFoodDetailsBinding
    private val viewModel : FoodDetailsViewModel by viewModels()
    var orderNumber = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_details, container, false)
        val bundle:FoodDetailsFragmentArgs by navArgs()
        val selectedFood = bundle.selectedFood
        binding.foodObject = selectedFood
        binding.fragmentObject = this
        binding.number = orderNumber

        showFoodImage(selectedFood.yemek_resim_adi)
        return binding.root
    }

    private fun showFoodImage(foodImageName:String){
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$foodImageName"
        Glide.with(this).load(url).override(150,150).into(binding.imageView)
    }

    fun addToCartButtonClick(foodName:String,foodImageName:String,foodPrice:Int, foodOrderNumber:Int){
        viewModel.addToCart(foodName,foodImageName,foodPrice, foodOrderNumber)
        backButtonClick()
    }

    fun backButtonClick(){
        findNavController().popBackStack()
    }

    fun incrementNumber(){
        orderNumber += 1
        binding.number = orderNumber
    }

    fun decrementNumber(){
        if (orderNumber-1 > 0){
            orderNumber -= 1
            binding.number = orderNumber
        }

    }
}