package com.diegobetancourt.meli.ui.sections.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.diegobetancourt.meli.R
import com.diegobetancourt.meli.data.repository.products.remote.retrofit.Suggestion
import com.diegobetancourt.meli.databinding.ItemSuggestBinding
import kotlinx.android.synthetic.main.item_suggest.view.*

/**
 * A simple [RecyclerView.Adapter] subclass for show suggestions.
 * suggestions items can be used for help to search
 */
class SearchRecyclerViewAdapter(var suggestions: List<Suggestion>, val search_actions: ISearchActions) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    //creates view via autobinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuggestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return suggestions.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.configure(suggestions.get(position))
    }

    //update view & actions
    inner class ViewHolder(val binding: ItemSuggestBinding): RecyclerView.ViewHolder(binding.root){
        fun configure(obj: Suggestion) {
            binding.suggestedText.text = obj.text
            binding.root.setOnClickListener { search_actions.search(obj.text) }

            if(obj.match_end == 0 && obj.match_start == 0)
                binding.suggestedType.setImageResource(R.drawable.ic_clock)
            else
                binding.suggestedType.setImageResource(R.drawable.ic_buscar)

            binding.suggestedAction.setOnClickListener { search_actions.useText(obj.text) }
        }
    }

    //interface for notify suggest item actions
    interface ISearchActions{
        fun useText(query: String)
        fun search(query: String)
    }



}