package com.rshea.cariiad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rshea.cariiad.R
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

    private var currentHolderView: View? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: University = items[position]
        when(holder) {
            is UniversityViewHolder -> {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    currentHolderView?.setBackgroundResource(R.color.background_grey)
                    currentHolderView = holder.itemView
                    holder.itemView.setBackgroundResource(R.color.purple_200)
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    findNavController(holder.itemView).navigate(
                        R.id.action_UniversityListFragment_to_UniversityDetailFragment,
                        bundle
                    )
                }
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