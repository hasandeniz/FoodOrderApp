package com.hasandeniz.bitirmeprojesi.retrofit


class ApiUtils {
    companion object{
        var BASE_URL = "http://kasimadalan.pe.hu/"

        fun getFoodDao() : FoodDao{
            return RetrofitClient.getClient(BASE_URL).create(FoodDao::class.java)
        }
    }
}