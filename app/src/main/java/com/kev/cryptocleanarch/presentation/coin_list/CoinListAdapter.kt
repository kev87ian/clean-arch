package com.kev.cryptocleanarch.presentation.coin_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.kev.cryptocleanarch.databinding.CoinsLayoutFileBinding
import com.kev.cryptocleanarch.domain.model.Coin

class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinsViewHolder>() {
	class CoinsViewHolder(val binding: CoinsLayoutFileBinding) : RecyclerView.ViewHolder(binding.root){

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
		val binding = CoinsLayoutFileBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)

		return CoinsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

		with(holder){
			with(differ.currentList[position]){
				binding.coinName.text = this.name
				if (this.isActive){
					binding.coinStatus.text = "active"

				} else{
					binding.coinStatus.text = "inactive"
				}
			}
		}

		holder.itemView.setOnClickListener {
			val currentCoin = differ.currentList[position]
			val direction = CoinsFragmentDirections.actionCoinsFragmentToCoinDetailsFragment(currentCoin.id)
			it.findNavController().navigate(direction)

		}

	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<Coin>(){
		override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
			return oldItem.name == newItem.name
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)
}