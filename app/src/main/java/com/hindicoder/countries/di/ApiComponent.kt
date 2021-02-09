package com.hindicoder.countries.di

import com.hindicoder.countries.model.CountriesService
import com.hindicoder.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)
    fun inject(viewModel: ListViewModel)
}