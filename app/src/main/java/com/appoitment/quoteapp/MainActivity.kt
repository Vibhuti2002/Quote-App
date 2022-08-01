package com.appoitment.quoteapp

import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.appoitment.quoteapp.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var quotesAdapter: QuotesAdapter
    lateinit var quoteList : ArrayList<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getQuotes()
            }
            catch (e:IOException){
                Log.e(TAG, "IOException, you might not have good network connection" )
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            catch (e: HttpException){
                Log.e(TAG, "HTTPException, unexpected response" )
                binding.progressBar.isVisible = false
                return@launchWhenCreated

            }

            if(response.isSuccessful && response.body()!=null){

                val body = response.body()!!
                var qList : ArrayList<Result> = arrayListOf()
                for (i in 0..19){
                    qList.add(body.results[i])
                }
                quotesAdapter.quotes = qList

            }
            else{
                Log.e(TAG, "Response failed")
            }
            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvQuotes.apply {
        quotesAdapter= QuotesAdapter()
        adapter = quotesAdapter
        layoutManager= LinearLayoutManager(this@MainActivity)
    }
}