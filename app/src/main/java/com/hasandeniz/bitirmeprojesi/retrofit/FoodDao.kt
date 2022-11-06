package com.hasandeniz.bitirmeprojesi.retrofit

import com.hasandeniz.bitirmeprojesi.data.entity.CRUDResponse
import com.hasandeniz.bitirmeprojesi.data.entity.CartFoodResponse
import com.hasandeniz.bitirmeprojesi.data.entity.FoodResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadFoods() : FoodResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@Field("yemek_adi") foodName:String,
                          @Field("yemek_resim_adi") foodImageName:String,
                          @Field("yemek_fiyat") foodPrice:Int,
                          @Field("yemek_siparis_adet") foodOrderNumber:Int,
                          @Field("kullanici_adi") username:String) : CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun loadCart(@Field("kullanici_adi") username: String) : CartFoodResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun removeFromCart(@Field("sepet_yemek_id") cartFoodId: Int, @Field("kullanici_adi") username: String) : CRUDResponse
}