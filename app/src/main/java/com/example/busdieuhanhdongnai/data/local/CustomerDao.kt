package com.example.busdieuhanhdongnai.data.local // đặt DAO cùng khu vực Room local

import androidx.room.Dao // khai báo lớp thao tác dữ liệu Room
import androidx.room.Query // sử dụng câu lệnh truy vấn dữ liệu
import androidx.room.Upsert // thêm mới hoặc cập nhật bản ghi hiện có
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu thay đổi theo thời gian thực

@Dao // đánh dấu đây là DAO của Room
interface CustomerDao { // cung cấp các thao tác với bảng thống kê khách hàng

    @Query("SELECT * FROM customer_statistics WHERE id = 1 LIMIT 1")
    fun getCustomerStatistics(): Flow<CustomerEntity?> // theo dõi bản ghi thống kê khách hàng

    @Upsert
    suspend fun saveCustomerStatistics(
        customer: CustomerEntity // nhận dữ liệu khách hàng cần lưu hoặc cập nhật
    )
}