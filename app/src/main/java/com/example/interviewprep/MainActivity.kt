package com.example.interviewprep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.util.Log

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.interviewprep.databinding.ActivityMainBinding
import com.example.interviewprep.models.ComicsData
import com.example.interviewprep.network.DataRepository
import com.example.interviewprep.network.RetrofitService
import com.example.interviewprep.view.DataAdapter
import com.example.interviewprep.viewModel.DataViewModel
import com.example.interviewprep.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: DataViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = DataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using ViewModelProvider.Factory
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(DataRepository(retrofitService))
        ).get(
                DataViewModel::class.java
            )

        //set adapter in recyclerview
        binding.recyclerview.adapter = adapter

        binding.btnShow.setOnClickListener {
            viewModel.getAllCharacters()
            binding.btnShow.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
            println("hellooooooooooo")
            // binding.btnShowMovies.visibility = View.GONE

        }
        //the observer will only receive events if the owner(activity) is in active state
        //invoked when movieList data changes
        viewModel.dataList.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, "movieList: $it")
                adapter.setDataList(it)
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })
    }

    override fun onDestroy() {
        //don't send events  once the activity is destroyed
        viewModel.disposable.dispose()
        super.onDestroy()
    }
}