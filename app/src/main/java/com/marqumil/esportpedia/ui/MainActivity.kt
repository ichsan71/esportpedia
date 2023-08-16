package com.marqumil.esportpedia.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marqumil.esportpedia.databinding.ActivityMainBinding
import com.marqumil.esportpedia.remote.ApiConfig
import com.marqumil.esportpedia.remote.ApiService
import com.marqumil.esportpedia.remote.HeroResponseItem
import com.marqumil.esportpedia.remote.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var heroAdapter: Adapter // Declare the adapter here
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mainViewModel = MainViewModel()

        mainViewModel.getHero()

        recyclerView = binding.rvHero

        recyclerView.setHasFixedSize(true)

        // Initialize the adapter here
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        //recyclerView.adapter = Adapter(hero as List<HeroResponseItem>)

        // make coroutines scope

//        activityScope.launch {
//            val response = ApiConfig.getApiService().getHeroes()
//            val hero = response[0].heroResponse
//            Log.d("MainActivity", "onCreate: $hero")
//
//            recyclerView = binding.rvHero
//
//            recyclerView.setHasFixedSize(true)
//
//            // Initialize the adapter here
//            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//
//            recyclerView.adapter = Adapter(hero as List<HeroResponseItem>)
//        }



        mainViewModel.HeroResponse.observe(this) {
            when (it) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val heroList = it
                    heroAdapter = Adapter(heroList.value as List<HeroResponseItem>)
                    recyclerView.adapter = heroAdapter
                }
                is ResultState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    //Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.VISIBLE
                }

                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}