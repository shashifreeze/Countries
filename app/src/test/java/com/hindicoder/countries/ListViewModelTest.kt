package com.hindicoder.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hindicoder.countries.model.CountriesService
import com.hindicoder.countries.model.Country
import com.hindicoder.countries.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()
    @Mock
    lateinit var countryService:CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess()
    {
        val country = Country("name","capital","url")
        val countryList = arrayListOf<Country>(country)
        testSingle = Single.just(countryList)

        Mockito.`when`(countryService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.countries.value?.size)
        Assert.assertEquals(false,listViewModel.countryLoadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }

    @Test
    fun getCountriesFailure()
    {

        testSingle = Single.error(Throwable("Error aa gya"))

        Mockito.`when`(countryService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(true,listViewModel.countryLoadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }
    private var testSingle:Single<List<Country>>? = null
    @Before
    fun setUpRxSchedulers()
    {
        val immediate= object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}