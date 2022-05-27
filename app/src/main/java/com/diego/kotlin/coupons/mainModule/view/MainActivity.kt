package com.diego.kotlin.coupons.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.diego.kotlin.coupons.R
import com.diego.kotlin.coupons.common.entities.CouponEntity
import com.diego.kotlin.coupons.common.utils.hideKeyboard
import com.diego.kotlin.coupons.databinding.ActivityMainBinding
import com.diego.kotlin.coupons.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupObservers()
        setupButtons()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupObservers() {
        /*mainViewModel.getResult().observe(this) { coupon ->
            if (coupon == null) {
                binding.tilDescription.hint =  getString(R.string.main_hint_description)
                binding.tilDescription.isEnabled = true
                binding.btnCreate.visibility = View.VISIBLE
            } else {
                binding.etDescription.setText(coupon.description)
                val status = getString(if (coupon.isActive) R.string.main_hint_active else R.string.main_hint_description)
                binding.tilDescription.hint = status
                binding.tilDescription.isEnabled = false
                binding.btnCreate.visibility = if (coupon.isActive) View.GONE else View.VISIBLE
            }
        }*/

        mainViewModel.getSnackbarMsg().observe(this) { msg ->
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupButtons() {
        binding.btnConsult.setOnClickListener {
            //mainViewModel.consultCouponByCode(binding.etCoupon.text.toString())
            hideKeyboard(this, binding.root)
        }

        binding.btnCreate.setOnClickListener {
            val coupon = CouponEntity(
                code = binding.etCoupon.text.toString(),
                description = binding.etDescription.text.toString())
            //mainViewModel.saveCoupon(coupon)
            hideKeyboard(this, binding.root)
        }
    }
}