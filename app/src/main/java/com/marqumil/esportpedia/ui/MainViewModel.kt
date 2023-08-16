package com.marqumil.esportpedia.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marqumil.esportpedia.remote.ApiConfig
import com.marqumil.esportpedia.remote.ResultState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import androidx.lifecycle.viewModelScope
import com.marqumil.esportpedia.remote.HeroResponseItem


class MainViewModel: ViewModel() {
    private val _HeroResponse = MutableLiveData<ResultState<List<HeroResponseItem>>>()
    val HeroResponse: MutableLiveData<ResultState<List<HeroResponseItem>>> = _HeroResponse

    private val _forceLogout = MutableLiveData(false)
    val forceLogout : LiveData<Boolean> = _forceLogout


    // Fungsi buat dapetin data dari API
//    fun getHero() {
//        _HeroResponse.value = ResultState.Loading
//         viewModelScope.launch {
//             try {
//                 // Panggil API dari file ApiConfig.kt
//                 val response = ApiConfig.getApiService().getHeroes()
//                 val hero = response[0].heroResponse
//                 Log.d("konto", "getHero: ${hero.size}")
//
//                 // kirim ke result state
//                 _HeroResponse.postValue(ResultState.Success((hero)))
//                 Log.d("konto", "getHero: ${_HeroResponse.value}")
//             }
//             catch (e: HttpException) {
//                 if (e.code() == 401) {
//                     _forceLogout.postValue(true)
//                 }
//             }
//             catch (e : Exception) {
//                 _HeroResponse.postValue(ResultState.Failure(e))
//                 Log.d("konto", "getHero: ${e.message}")
//             }
//         }
//    }

    fun getHero() {
        _HeroResponse.value = ResultState.Loading
        viewModelScope.launch {
            try {
                // Panggil API dari file ApiConfig.kt
                val response = ApiConfig.getApiService().getHeroes()
                val heroes = response
                Log.d("test", "getHeroes: ${heroes.size}")

                // Kirim ke result state
                _HeroResponse.postValue(ResultState.Success(heroes))
                Log.d("test", "getHeroes: ${_HeroResponse.value}")
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    _forceLogout.postValue(true)
                }
            } catch (e: Exception) {
                _HeroResponse.postValue(ResultState.Failure(e))
                Log.d("test", "getHeroes: ${e.message}")
            }
        }
    }
}

