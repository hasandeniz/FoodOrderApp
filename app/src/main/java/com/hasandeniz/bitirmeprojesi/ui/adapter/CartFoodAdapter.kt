package com.hasandeniz.bitirmeprojesi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.data.entity.CartFood
import com.hasandeniz.bitirmeprojesi.databinding.CartCardDesignBinding
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.CartViewModel

class CartFoodAdapter(var mContext: Context, var cartFoodList:List<CartFood>, var viewModel: CartViewModel)
    : RecyclerView.Adapter<CartFoodAdapter.CardViewHolder>() {

    inner class CardViewHolder(binding: CartCardDesignBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: CartCardDesignBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: CartCardDesignBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.cart_card_design ,parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val food = cartFoodList[position]
        val binding = holder.binding
        binding.foodObject = food

        showFoodImage(food.yemek_resim_adi,binding)

        binding.removeFromCart.setOnClickListener {
            viewModel.removeFromCart(food.sepet_yemek_id)
        }

        binding.ibIncreaseCartOrderNumber.setOnClickListener {
            binding.ibIncreaseCartOrderNumber.isEnabled = false
            viewModel.increaseCartOrderNumber(food)
        }

        binding.ibDecreaseCartOrderNumber.setOnClickListener {
            binding.ibDecreaseCartOrderNumber.isEnabled = false
            viewModel.decreaseCartOrderNumber(food)
        }

    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }

    private fun showFoodImage(foodImageName:String, binding: CartCardDesignBinding){
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$foodImageName"
        Glide.with(mContext).load(url).override(75,75).into(binding.cartFoodImage)
    }
}