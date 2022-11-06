package com.hasandeniz.bitirmeprojesi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.data.entity.Food
import com.hasandeniz.bitirmeprojesi.databinding.CardDesignBinding
import com.hasandeniz.bitirmeprojesi.ui.fragment.OrderFragmentDirections
import com.hasandeniz.bitirmeprojesi.ui.viewmodel.OrderViewModel

class FoodAdapter(var mContext: Context, var foodList: List<Food>, var viewModel: OrderViewModel)
    : RecyclerView.Adapter<FoodAdapter.CardViewHolder>() {

    inner class CardViewHolder(binding: CardDesignBinding) : RecyclerView.ViewHolder(binding.root){
        var binding:CardDesignBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding:CardDesignBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
            R.layout.card_design ,parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val food = foodList[position]
        val binding = holder.binding
        binding.foodObject = food

        showFoodImage(food.yemek_resim_adi,binding)

        binding.foodRowCard.setOnClickListener {
            val action = OrderFragmentDirections.actionOrderFragmentToFoodDetailsFragment(food)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    private fun showFoodImage(foodImageName:String, binding: CardDesignBinding){
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$foodImageName"
        Glide.with(mContext).load(url).override(75,75).into(binding.foodImage)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilter(foodList:List<Food>){
        this.foodList = foodList
        notifyDataSetChanged()
    }


}