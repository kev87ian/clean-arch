package com.kev.cryptocleanarch.presentation.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kev.cryptocleanarch.R
import com.kev.cryptocleanarch.databinding.FragmentCoinsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : Fragment(R.layout.fragment_coins) {
	private lateinit var coinsAdapter: CoinListAdapter

	private var _binding: FragmentCoinsBinding? = null
	private val binding get() = _binding!!
	private val viewModel: CoinListViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


		super.onViewCreated(view, savedInstanceState)
		setUpObserver()
		initAdapter()
	}

	private fun initAdapter() {
		coinsAdapter = CoinListAdapter()
		binding.coinsRecyclerview.apply {
			adapter = coinsAdapter
			layoutManager = LinearLayoutManager(requireContext())
		}
	}

	private fun setUpObserver() {


		viewModel.coinState.observe(viewLifecycleOwner) { state ->

			if (state.isLoading) {
				binding.coinsProgressBar.visibility = View.VISIBLE
				binding.errorTextView.visibility = View.GONE
			} else if (state.error.isNotBlank()) {
				binding.coinsProgressBar.visibility = View.GONE
				binding.errorTextView.visibility = View.VISIBLE
				binding.errorTextView.text = state.error
			} else {
				binding.errorTextView.visibility = View.GONE
				binding.coinsProgressBar.visibility = View.GONE
				state.coins.let {
					coinsAdapter.differ.submitList(it)
				}

			}

		}


	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCoinsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}