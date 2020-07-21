package com.diegobetancourt.meli.ui.sections.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Attribute
import com.diegobetancourt.meli.databinding.ItemFeatureBinding

/**
 * A  [RecyclerView.Adapter] subclass for show features.
 * feature items does't have any action
 */
class FeatureRecyclerViewAdapter(var attrs: List<Attribute>) :
    RecyclerView.Adapter<FeatureRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = ItemFeatureBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
       return attrs.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.configure(attrs.get(position))
    }

    inner class ViewHolder(val binding : ItemFeatureBinding): RecyclerView.ViewHolder(binding.root){
        fun configure(obj: Attribute) {
            binding.featName.text = obj.name
            binding.featValue.text = obj.value_name
        }
    }

}