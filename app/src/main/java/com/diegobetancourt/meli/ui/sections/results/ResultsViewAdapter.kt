package com.diegobetancourt.meli.ui.sections.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diegobetancourt.meli.R
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Product
import com.diegobetancourt.meli.util.toCurrencyString
import kotlinx.android.synthetic.main.item_result.view.*

/**
 * A  [RecyclerView.Adapter] subclass for show products.
 * results items can be used for see details of specific product
 */
class ResultsViewAdapter(val results: ArrayList<Product>,val listener : OnResultClickListener) : RecyclerView.Adapter<ResultsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return results.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.configure(results.get(position))
    }

    //Add items from request data
    fun addItems(products: List<Product>){
        results.addAll(products)
        notifyDataSetChanged()
    }

    //clear all existing data and show request data
    fun restartItems(products: List<Product>){
        results.clear()
        notifyDataSetChanged()

        results.addAll(products)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun configure(prod: Product){
            //update data
            view.apply {
                titleResult.text = prod.title
                priceResult.text = prod.price.toCurrencyString()
                conditionResult.text = if(prod.condition.equals("new"))"Nuevo" else "Usado"
                shippingResult.visibility = if(prod.shipping.freeShipping) View.VISIBLE else View.GONE
            }

            //load image
            Glide.with(view)
                .load(prod.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view.imageResult)

            //add action
            view.content.setOnClickListener { listener.openDetails(prod) }
        }

    }

    //interface for notify a selected product
    interface OnResultClickListener{
        fun openDetails(product: Product)
    }
}