package com.hasandeniz.bitirmeprojesi.data.repo

import com.hasandeniz.bitirmeprojesi.data.datasource.FoodDataSource
import com.hasandeniz.bitirmeprojesi.data.entity.CartFood
import com.hasandeniz.bitirmeprojesi.data.entity.Food

class FoodRepository(var fds:FoodDataSource) {

    suspend fun loadFoods() : List<Food> = fds.loadFoods()

    suspend fun addToCart(foodName:String,foodImageName:String,foodPrice:Int, foodOrderNumber:Int, username:String) =
        fds.addToCart(foodName,foodImageName,foodPrice, foodOrderNumber, username)

    suspend fun loadCart(username: String) : List<CartFood> = fds.loadCart(username)

    suspend fun removeFromCart(cartFoodId: Int,username: String) = fds.removeFromCart(cartFoodId, username)
}