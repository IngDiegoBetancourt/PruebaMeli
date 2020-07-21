package com.diegobetancourt.meli.ui.sections.details

import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Product
import com.diegobetancourt.meli.util.toCurrencyString


/**
 * Map [Product] data to show in [DetailsFragment]
 * */
class UIDetailsData (var product: Product){

    var state: String = ""
    var price: String = ""
    var title: String = ""
    var availability: String =""

    init {
        price = product.price.toCurrencyString()
        state = "${mapCondition(product.condition)} ${mapSoldQuantity(product.soldQuantity)}"
        title = product.title
        availability = mapAvailability(product.availableQuantity)
    }

    fun mapCondition(condition: String): String{
        if(condition.equals("new")){
            return "Nuevo"
        }else{
            return "Usado"
        }
    }

    fun mapSoldQuantity(soldQuantity: Int): String{
        if(soldQuantity==1){
            return "- ${soldQuantity} vendido"
        }else if (soldQuantity > 1){
            return "- ${soldQuantity} vendidos"
        }else{
            return ""
        }
    }

    fun mapAvailability(availableQuantity: Int): String{
        if (availableQuantity >= 1){
            return "En Stock"
        }else{
            return "Agotado"
        }
    }
}

