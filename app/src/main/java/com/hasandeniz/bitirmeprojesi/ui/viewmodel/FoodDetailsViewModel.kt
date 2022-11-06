package com.hasandeniz.bitirmeprojesi.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.hasandeniz.bitirmeprojesi.data.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(var frepo: FoodRepository, var app: Application) : AndroidViewModel(app) {


    fun addToCart(foodName:String,foodImageName:String,foodPrice:Int, foodOrderNumber:Int){
        if (hasInternetConnection()){
            CoroutineScope(Dispatchers.Main).launch {
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser
                val username = user!!.uid
                frepo.addToCart(foodName,foodImageName,foodPrice, foodOrderNumber, username)
                Toast.makeText(app.applicationContext,"$foodOrderNumber $foodName successfully added to cart!",
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(app.applicationContext,"No internet, could not be added to cart!",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}