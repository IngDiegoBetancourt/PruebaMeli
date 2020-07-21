package com.diegobetancourt.meli.data.repository.products.local

/**
 * The entry point for the products local.
 * Classes that need to consume the stored product data should have an implementation of this interface
 * preferably via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
interface ILocalProductEntryPoint{
     fun get_history():Set<String>
     fun save_seach(query: String)
}