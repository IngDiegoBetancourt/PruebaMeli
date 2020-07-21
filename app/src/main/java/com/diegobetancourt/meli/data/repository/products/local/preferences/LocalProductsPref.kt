package com.diegobetancourt.meli.data.repository.products.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.diegobetancourt.meli.data.repository.products.local.ILocalProductEntryPoint

/**
 * The remote entry point instance for [ILocalProductEntryPoint].
 * instanced via injection (see [com.diegobetancourt.meli.di.ProductsModule]).
 */
class LocalProductsPref(private val context: Context) : ILocalProductEntryPoint {

    private val prefsName = "com.diegobetancourt.meli.PREFS"
    private val historyPref = "history"
    private val sharedPref : SharedPreferences

    init{
        sharedPref = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }


    override  fun get_history() : Set<String>{
        return sharedPref.getStringSet(historyPref,HashSet()) as Set<String>
    }

    override  fun save_seach(query: String) {

        val set: MutableSet<String> = HashSet()
        get_history().let {
            set.addAll(it)
            set.add(query)
        }
        with(sharedPref.edit()) {
            putStringSet(historyPref, set)
            commit()
        }
    }


}