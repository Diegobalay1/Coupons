package com.diego.kotlin.coupons.mainModule.model

import android.database.sqlite.SQLiteConstraintException
import com.diego.kotlin.coupons.CouponsApplication
import com.diego.kotlin.coupons.common.dataAccess.CouponDao
import com.diego.kotlin.coupons.common.entities.CouponEntity
import com.diego.kotlin.coupons.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Base de datos
 */
class RoomDatabase {
    // Extraer acceso a dao
    private val dao: CouponDao by lazy { CouponsApplication.database.couponDao() }

    suspend fun consultCouponByCode(code: String) = dao.consultCouponByCode(code)

    suspend fun saveCoupon(couponEntity: CouponEntity) = withContext(Dispatchers.IO){
        try {
            dao.addCoupon(couponEntity)
        } catch (e: Exception) {
            (e as? SQLiteConstraintException)?.let { throw Exception(Constants.ERROR_EXIST) }
            throw Exception(e.message ?: Constants.ERROR_UNKNOWN)
        }
    }
}