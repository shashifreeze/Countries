package com.hindicoder.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hindicoder.countries.di.DaggerApiComponent
import com.hindicoder.countries.model.CountriesService
import com.hindicoder.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {
    @Inject
    lateinit var countriesService:CountriesService
    val disposable = CompositeDisposable()
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerApiComponent.create().inject(this)
    }
    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {

        loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        loading.value = false
                        countryLoadError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = false
                        countryLoadError.value=true
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}