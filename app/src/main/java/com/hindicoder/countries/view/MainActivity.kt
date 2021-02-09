package com.hindicoder.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hindicoder.countries.R
import com.hindicoder.countries.model.Country
import com.hindicoder.countries.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.hindicoder.countries.CountriesListAdapter as CountriesListAdapter

class MainActivity : AppCompatActivity() {
    lateinit var viewModel:ListViewModel
    private val countriesAdapter = CountriesListAdapter(ArrayList<Country>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        countriesListRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer {
            it?.let {
                countriesAdapter.updateCountries(it)
                countriesListRV.visibility = View.VISIBLE
            }
        })

        viewModel.countryLoadError.observe(this, Observer {
            it?.let { errorTextTV.visibility = if (it) View.VISIBLE else View.GONE }
            if (it){
                progressBar.visibility = View.GONE
                countriesListRV.visibility = View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let { progressBar.visibility = if (it) View.VISIBLE else View.GONE }

            if (it){
                errorTextTV.visibility = View.GONE
                countriesListRV.visibility = View.GONE
            }
        })
    }
}