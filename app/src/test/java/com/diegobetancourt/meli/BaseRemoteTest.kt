package com.diegobetancourt.meli

import com.diegobetancourt.meli.data.repository.products.remote.IRemoteProductEntryPoint
import com.diegobetancourt.meli.di.networkModule
import com.diegobetancourt.meli.di.productsModule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.get
import org.koin.test.KoinTest

class BaseRemoteTest : KoinTest {

    @Before
    fun getToken() {
        startKoin {
            modules(listOf(networkModule, productsModule))
        }
    }

    @Test
    fun get_results(){
        runBlocking {
            val productsRepo : IRemoteProductEntryPoint = get()
            val response =  productsRepo.getProducts("play",0,3)
            print(response.body())
            Assert.assertEquals(200,response.code())
        }
    }

    @Test
    fun get_suggestions(){
        runBlocking {
            val productsRepo : IRemoteProductEntryPoint = get()
            val response =  productsRepo.getSuggestions("play",3)
            print(response.body()?.data?.size)
            Assert.assertEquals(200,response.code())
        }
    }

    @Test
    fun get_details(){
        runBlocking {
            val productsRepo : IRemoteProductEntryPoint = get()
            val response =  productsRepo.getDetails("MCO449914956")
            print(response.body()?.pictures?.size)
            Assert.assertEquals(200,response.code())
        }
    }

    @Test
    fun get_description(){
        runBlocking {
            val productsRepo : IRemoteProductEntryPoint = get()
            val response =  productsRepo.getDescription("MCO449914956")
            print(response.body()?.get(0)?.plain_text)
            Assert.assertEquals(200,response.code())
        }
    }

    @After
    fun end() {
        stopKoin()
    }
}