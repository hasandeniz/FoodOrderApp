package com.hasandeniz.bitirmeprojesi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hasandeniz.bitirmeprojesi.data.entity.Food
import com.hasandeniz.bitirmeprojesi.data.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(var frepo: FoodRepository, var app: Application) : AndroidViewModel(app) {

    val foodList : LiveData<State<List<Food>>> get() = _foodList
    private val _foodList = MutableLiveData<State<List<Food>>>()

    init {
        loadFoods()
    }

    fun loadFoods(){
        _foodList.value = State.loading()
        viewModelScope.launch {
            val state = withContext(Dispatchers.Main){
                try {
                    val foodList = frepo.loadFoods()
                    State.success(foodList)
                }catch (exception: Exception){
                    State.error(exception.message)
                }
            }
            _foodList.value = state
        }

    }

}