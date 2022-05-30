package com.diego.kotlin.coupons.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.diego.kotlin.coupons.BR
import com.diego.kotlin.coupons.R
import com.diego.kotlin.coupons.common.entities.CouponEntity
import com.diego.kotlin.coupons.common.utils.hideKeyboard
import com.diego.kotlin.coupons.databinding.ActivityMainBinding
import com.diego.kotlin.coupons.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
        //setupButtons()
    }

    private fun setupViewModel() {
        //mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let {
            it.coupon.observe(this@MainActivity) { coupon ->
                binding.isActive = coupon != null && coupon.isActive
            }
            it.getSnackbarMsg().observe(this@MainActivity) { msg ->
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
            it.isHideKeyboard().observe(this@MainActivity) { isHide ->
                if (isHide) hideKeyboard(this@MainActivity, binding.root)
            }
        }
    }

    /*private fun setupButtons() {
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
    }*/
}