package com.kev.cryptocleanarch.presentation.coin_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kev.cryptocleanarch.R
import com.kev.cryptocleanarch.common.Resource
import com.kev.cryptocleanarch.databinding.FragmentCoinDetailsBinding
import com.kev.cryptocleanarch.databinding.FragmentCoinsBinding
import com.kev.cryptocleanarch.domain.model.CoinDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailsFragment : Fragment(R.layout.fragment_coin_details){

	private var _binding:FragmentCoinDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel:CoinDetailViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		setupObserver()
		super.onViewCreated(view, savedInstanceState)
	}

	private fun setupObserver() {
		viewModel.coinState.observe(viewLifecycleOwner){state->

			if (state.isLoading){
				binding.views.visibility = View.GONE
				binding.coinDetailsProgressbar.visibility = View.VISIBLE
			}

			else if(state.error.isNotEmpty()){
				binding.coinDetailsErrorTextview.visibility = View.VISIBLE
				binding.coinDetailsProgressbar.visibility = View.GONE
				binding.coinDetailsErrorTextview.text = state.error
			}

			else{
				binding.coinDetailsProgressbar.visibility = View.GONE
				binding.coinDetailsErrorTextview.visibility = View.GONE
				bindViews(state.coin)
			}

		}
	}

	private fun bindViews(coin: CoinDetail?) {
		binding.coinDetailsNameTextview.text = coin?.name
		binding.coinDetailsDescriptionTextview.text = coin?.description
		binding.rankTextView.text = coin?.rank.toString()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCoinDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}