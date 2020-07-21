package com.diegobetancourt.meli.di

import android.content.Context
import com.diegobetancourt.meli.data.repository.products.IProductsRepo
import com.diegobetancourt.meli.data.repository.products.ProductsRepo
import com.diegobetancourt.meli.data.repository.products.local.ILocalProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.local.preferences.LocalProductsPref
import com.diegobetancourt.meli.ui.sections.search.ProductsViewModel
import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.ProductApiContract
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.RetrofitProductsService
import com.diegobetancourt.meli.ui.sections.details.DetailsViewModel
import com.diegobetancourt.meli.ui.sections.results.ResultsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * File to centralize all Koin modules associated with Products Repo
 * Module should be get through the productsModule field.
 */

val productsModule = module {
    //Provides entrypoints
    factory { provideProductApi(get())}
    factory { provideEntryPoint(get()) }
    factory { provideLocalEntryPoint(androidContext()) }

    //Provides repository as a singlenton
    single { provideRepo(get(),get()) }

    //Provides viewmodels
    viewModel { ProductsViewModel(get()) }
    viewModel { ResultsViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

}

private fun provideProductApi(retrofit: Retrofit): ProductApiContract = retrofit.create(ProductApiContract::class.java)
private fun provideEntryPoint(contract: ProductApiContract): IRemoteProductEntryPoint = RetrofitProductsService(contract)
private fun provideLocalEntryPoint(context: Context): ILocalProductEntryPoint = LocalProductsPref(context)
private fun provideRepo(remote: IRemoteProductEntryPoint, local: ILocalProductEntryPoint): IProductsRepo = ProductsRepo(remote,local)