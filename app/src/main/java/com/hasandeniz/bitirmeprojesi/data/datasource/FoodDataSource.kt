package com.hasandeniz.bitirmeprojesi.data.datasource

import android.util.Log
import com.hasandeniz.bitirmeprojesi.data.entity.CartFood
import com.hasandeniz.bitirmeprojesi.data.entity.Food
import com.hasandeniz.bitirmeprojesi.retrofit.FoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.EOFException

class FoodDataSource(var fdao:FoodDao) {

    suspend fun loadFoods() : List<Food> =
        withContext(Dispatchers.IO){
            fdao.loadFoods().yemekler
        }


    suspend fun addToCart(foodName:String,foodImageName:String,foodPrice:Int, foodOrderNumber:Int, username:String) =
        fdao.addToCart(foodName,foodImageName,foodPrice, foodOrderNumber, username)

    suspend fun loadCart(username: String) : List<CartFood> =
        withContext(Dispatchers.IO){
            try {
                fdao.loadCart(username).sepet_yemekler
            }catch (e:EOFException){
                Log.d("FoodDataSource","Cart is empty and throws exception: \n $e")
                emptyList()
            }

        }

    suspend fun removeFromCart(cartFoodId:Int,username: String) =
        fdao.removeFromCart(cartFoodId,username)
}