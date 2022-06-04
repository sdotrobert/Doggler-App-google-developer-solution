package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.databinding.ItemListItemBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */
//val viewModel: InventoryViewModel,
class ItemListAdapter(
    val viewModel: InventoryViewModel,
    private val onItemClicked: (Item) -> Unit
) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current, viewModel)
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, viewModel: InventoryViewModel) {
            binding.apply {
                vocEnglish.text = item.vocEnglish
                vocChinese.text = item.vocChinese
                if (item.vocFavorite==true){
                    vocFavorite.setBackgroundResource(R.drawable.ic_star)
                }else{
                    vocFavorite.setBackgroundResource(R.drawable.ic_star_border)
                }
                vocFavorite.setOnClickListener {
                    viewModel.updateItem(
                        item.id,
                        item.vocEnglish,
                        item.vocChinese,
                        !item.vocFavorite
                    )
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.vocEnglish == newItem.vocEnglish
            }
        }
    }
}