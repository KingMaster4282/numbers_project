package com.vkdev.numbersinfo.presentation.activities

import android.os.Bundle
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
import com.vkdev.numbersinfo.R
import com.vkdev.numbersinfo.databinding.ActivityMainBinding
import com.vkdev.numbersinfo.databinding.ActivityNumberBinding
import com.vkdev.numbersinfo.presentation.vm.MainActivityVM
import com.vkdev.numbersinfo.presentation.vm.NumberActivityVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NumberActivity : BaseActivity<ActivityNumberBinding>() {
    private val vm: NumberActivityVM by viewModels()

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityNumberBinding {
        return ActivityNumberBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        val numberId = intent.getIntExtra("number_id", -1)

        vm.setNumberId(numberId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    binding.pbNumber.isVisible = state.isLoading

                    state.numberInfoModel?.let {
                        binding.tvNumber.text="${it.number}"
                        binding.tvText.text="${it.text}"
                    }

                    state.message?.let {
                        showToast(it)
                        vm.clearMessage()
                    }
                }
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}