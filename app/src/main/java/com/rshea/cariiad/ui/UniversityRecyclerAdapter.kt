package com.rshea.cariiad.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rshea.cariiad.databinding.LayoutUniversityListItemBinding
import com.rshea.cariiad.models.University

class UniversityRecyclerAdapter(
    universityList: List<University>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = universityList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val countryListItemBinding: LayoutUniversityListItemBinding =
            LayoutUniversityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(countryListItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: University = items[position]
        when(holder) {
            is UniversityViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class UniversityViewHolder constructor(
        private val itemBinding: LayoutUniversityListItemBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(university: University) {
            itemBinding.universityName.text = university.name
            itemBinding.country.text = university.country
        }

    }

}