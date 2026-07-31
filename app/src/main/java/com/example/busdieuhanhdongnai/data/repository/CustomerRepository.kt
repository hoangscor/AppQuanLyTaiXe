package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.CustomerDao // sử dụng DAO của bảng thống kê khách hàng
import com.example.busdieuhanhdongnai.data.local.CustomerEntity // sử dụng dữ liệu cơ cấu vé và khảo sát
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu Room thay đổi theo thời gian thực

class CustomerRepository(
    private val customerDao: CustomerDao // nhận CustomerDao từ AppDatabase
) {

    val customerStatistics: Flow<CustomerEntity?> =
        customerDao.getCustomerStatistics() // cung cấp dữ liệu thống kê khách hàng cho ViewModel

    suspend fun saveCustomerStatistics(
        customer: CustomerEntity // nhận dữ liệu thống kê cần lưu hoặc cập nhật
    ) {
        customerDao.saveCustomerStatistics(
            customer = customer // yêu cầu DAO lưu dữ liệu vào Room
        )
    }
}