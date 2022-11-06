package com.hasandeniz.bitirmeprojesi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hasandeniz.bitirmeprojesi.data.entity.CartFood
import com.hasandeniz.bitirmeprojesi.data.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(var frepo: FoodRepository, var app: Application) : AndroidViewModel(app) {

    val cartFoodList : LiveData<State<List<CartFood>>> get() = _cartFoodList
    private val _cartFoodList = MutableLiveData<State<List<CartFood>>>()
    private lateinit var username: String

    init {
        _cartFoodList.value = State.loading()
        viewModelScope.launch {
            val state = withContext(Dispatchers.Main){
                try {
                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser
                    username = user!!.uid
                    val cartFoodList = frepo.loadCart(username)
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }

    }

    fun loadCart() {
       _cartFoodList.value = State.loading()
        viewModelScope.launch {
            val state = withContext(Dispatchers.Main){
                try {
                    val cartFoodList = frepo.loadCart(username)
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }
    }

    fun removeFromCart(cartFoodId: Int){
        viewModelScope.launch{
           val state = withContext(Dispatchers.Main){
                try {
                    frepo.removeFromCart(cartFoodId, username)
                    val cartFoodList = frepo.loadCart(username)
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }

    }

    fun increaseCartOrderNumber(cartFood: CartFood){

        val currentOrderNumber = cartFood.yemek_siparis_adet
        val newOrderNumber = currentOrderNumber + 1
        viewModelScope.launch{
           val state = withContext(Dispatchers.Main){
                try {
                    frepo.removeFromCart(cartFood.sepet_yemek_id, username)
                    frepo.addToCart(cartFood.yemek_adi,cartFood.yemek_resim_adi,cartFood.yemek_fiyat,newOrderNumber,username)
                    val cartFoodList = frepo.loadCart(username)
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }


    }

    fun decreaseCartOrderNumber(cartFood: CartFood){

        val currentOrderNumber = cartFood.yemek_siparis_adet
        val newOrderNumber = currentOrderNumber - 1
        viewModelScope.launch{
            val state = withContext(Dispatchers.Main){
                try {
                    frepo.removeFromCart(cartFood.sepet_yemek_id, username)
                    if (newOrderNumber > 0)
                        frepo.addToCart(cartFood.yemek_adi,cartFood.yemek_resim_adi,cartFood.yemek_fiyat,newOrderNumber,username)
                    val cartFoodList = frepo.loadCart(username)
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }
      }

    fun clearCart() {
        viewModelScope.launch {
            val state = withContext(Dispatchers.Main){
                try {
                    _cartFoodList.value?.data?.forEach { cartFood->
                        frepo.removeFromCart(cartFood.sepet_yemek_id,username)
                    }
                    val cartFoodList = emptyList<CartFood>()
                    State.success(cartFoodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _cartFoodList.value = state
        }

    }

}