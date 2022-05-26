package com.diego.kotlin.coupons.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.kotlin.coupons.R
import com.diego.kotlin.coupons.common.entities.CouponEntity
import com.diego.kotlin.coupons.common.utils.getMsgErrorByCode
import com.diego.kotlin.coupons.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val result = MutableLiveData<CouponEntity>()
    fun getResult() = result

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    fun consultCouponByCode(code: String) {
        viewModelScope.launch {
            result.value = repository.consultCouponByCode(code)
        }
    }

    fun saveCoupon(couponEntity: CouponEntity){
        viewModelScope.launch {
            try {
                repository.saveCoupon(couponEntity)
                consultCouponByCode(couponEntity.code)
                snackbarMsg.value = R.string.main_save_success
            } catch (e: Exception) {
                snackbarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }
}