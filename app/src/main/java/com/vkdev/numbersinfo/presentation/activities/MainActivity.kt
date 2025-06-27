package com.vkdev.numbersinfo.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.vkdev.numbersinfo.R
import com.vkdev.numbersinfo.databinding.ActivityMainBinding
import com.vkdev.numbersinfo.databinding.ActivityNumberBinding
import com.vkdev.numbersinfo.presentation.adapters.HistoryAdapter
import com.vkdev.numbersinfo.presentation.vm.MainActivityVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val vm: MainActivityVM by viewModels()

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        val adapter = HistoryAdapter { id ->
            val intent = Intent(this, NumberActivity::class.java)
            intent.putExtra("number_id", id)
            startActivity(intent)
        }

        binding.rvHistory.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)
        binding.rvHistory.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                binding.pbApi.isVisible = state.isLoading

                    state.message?.let {
                        showToast(it)
                        vm.clearMessage()
                    }
                }
            }
        }

        vm.observeAdapterState(adapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.isPagingLoading.collect { isLoading ->
                    binding.pbRv.isVisible = isLoading
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.pagedNumbers.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        binding.btnNumber.setOnClickListener {
            val number: Int? = binding.etNumber.text.toString().toIntOrNull()
            number?.let {
                vm.getNumberInfo(number)
            }
        }

        binding.btnRandom.setOnClickListener {
            vm.getRandom()
        }
    }
}