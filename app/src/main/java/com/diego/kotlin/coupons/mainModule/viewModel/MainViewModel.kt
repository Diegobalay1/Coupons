package com.diego.kotlin.coupons.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.kotlin.coupons.R
import com.diego.kotlin.coupons.common.entities.CouponEntity
import com.diego.kotlin.coupons.common.utils.getMsgErrorByCode
import com.diego.kotlin.coupons.mainModule.model.MainRepository
import kotlinx.coroutines.launch

/**
 * Se encarga de comunicar a la vista
 * con el repositorio y
 * su correcta administración de datos para livedata
 */
class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    val coupon = MutableLiveData(CouponEntity())

    private val hideKeyboard = MutableLiveData<Boolean>()
    fun isHideKeyboard() = hideKeyboard

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg


    fun consultCouponByCode() {
        coupon.value?.code?.let { code ->
            viewModelScope.launch {
                hideKeyboard.value = true
                coupon.value = repository.consultCouponByCode(code) ?: CouponEntity(code = code, isActive = false)
            }
        }
    }


    fun saveCoupon(){
        coupon.value?.let { couponEntity ->
            viewModelScope.launch {
                hideKeyboard.value = true
                try {
                    couponEntity.isActive = true
                    repository.saveCoupon(couponEntity)
                    consultCouponByCode()
                    snackbarMsg.value = R.string.main_save_success
                } catch (e: Exception) {
                    snackbarMsg.value = getMsgErrorByCode(e.message)
                }
            }
        }
    }
}