package com.example.busdieuhanhdongnai.data.repository // đặt Repository trong tầng dữ liệu

import com.example.busdieuhanhdongnai.data.local.CustomerDao // sử dụng DAO của bảng khách hàng
import com.example.busdieuhanhdongnai.data.local.CustomerEntity // sử dụng dữ liệu thống kê theo ngày
import com.example.busdieuhanhdongnai.data.local.CustomerStatisticsSummary // sử dụng dữ liệu tổng hợp theo khoảng ngày
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu Room thay đổi theo thời gian thực
import java.time.LocalDate // lấy ngày hiện tại của thiết bị

class CustomerRepository( // tạo Repository trung gian giữa DAO và ViewModel
    private val customerDao: CustomerDao // nhận CustomerDao từ AppDatabase
) {

    val customerStatistics: Flow<CustomerEntity?> = // tạm giữ tương thích với CustomerViewModel hiện tại
        customerDao.getCustomerStatisticsByDate( // lấy thống kê của ngày hiện tại
            recordDate = LocalDate.now().toString() // chuyển ngày hiện tại thành yyyy-MM-dd
        )

    fun getCustomerStatisticsByDate( // lấy thống kê của một ngày cụ thể
        recordDate: String // ngày cần lấy theo định dạng yyyy-MM-dd
    ): Flow<CustomerEntity?> { // trả dữ liệu ngày hoặc null khi chưa có
        return customerDao.getCustomerStatisticsByDate( // gọi hàm truy vấn theo ngày trong DAO
            recordDate = recordDate // truyền ngày cần truy vấn
        )
    }

    fun getCustomerStatisticsByRange( // lấy tổng thống kê trong một khoảng thời gian
        startDate: String, // ngày bắt đầu theo định dạng yyyy-MM-dd
        endDate: String // ngày kết thúc theo định dạng yyyy-MM-dd
    ): Flow<CustomerStatisticsSummary> { // trả dữ liệu đã cộng tổng
        return customerDao.getCustomerStatisticsByRange( // gọi truy vấn khoảng ngày trong DAO
            startDate = startDate, // truyền ngày bắt đầu
            endDate = endDate // truyền ngày kết thúc
        )
    }

    suspend fun saveCustomerStatistics( // lưu hoặc cập nhật dữ liệu thống kê một ngày
        customer: CustomerEntity // nhận bản ghi cần lưu từ ViewModel
    ) {
        customerDao.saveCustomerStatistics( // yêu cầu DAO lưu dữ liệu vào Room
            customer = customer // truyền bản ghi thống kê khách hàng
        )
    }
}