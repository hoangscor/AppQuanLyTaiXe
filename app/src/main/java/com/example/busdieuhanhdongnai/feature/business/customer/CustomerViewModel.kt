package com.example.busdieuhanhdongnai.feature.business.customer

import android.app.Application // lấy Application để khởi tạo Room database
import androidx.lifecycle.AndroidViewModel // tạo ViewModel có thể sử dụng Application
import androidx.lifecycle.viewModelScope // chạy tác vụ lưu dữ liệu theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // truy cập database chính của ứng dụng
import com.example.busdieuhanhdongnai.data.local.CustomerEntity // sử dụng dữ liệu thống kê khách hàng
import com.example.busdieuhanhdongnai.data.repository.CustomerRepository // gọi lớp Repository khách hàng
import kotlinx.coroutines.flow.Flow // cung cấp dữ liệu Room cho giao diện
import kotlinx.coroutines.launch // chạy thao tác lưu trong coroutine

class CustomerViewModel(
    application: Application // nhận Application từ hệ thống Android
) : AndroidViewModel(application) {

    private val repository = CustomerRepository(
        customerDao = AppDatabase
            .getDatabase(application)
            .customerDao() // lấy CustomerDao từ Room database
    )

    val customerStatistics: Flow<CustomerEntity?> =
        repository.customerStatistics // cung cấp dữ liệu khách hàng cho giao diện theo dõi

    fun saveCustomerStatistics(
        singleTicketCount: Int, // số hành khách sử dụng vé lượt
        monthlyTicketCount: Int, // số hành khách sử dụng vé tháng
        freeTicketCount: Int, // số hành khách sử dụng vé miễn phí
        workerSurveyCount: Int, // số liệu khảo sát công nhân
        studentSurveyCount: Int // số liệu khảo sát học sinh và sinh viên
    ) {
        viewModelScope.launch { // chạy thao tác lưu dữ liệu trong coroutine
            repository.saveCustomerStatistics(
                customer = CustomerEntity(
                    id = 1, // luôn cập nhật một bản ghi thống kê chung của doanh nghiệp
                    singleTicketCount = singleTicketCount, // lưu số vé lượt
                    monthlyTicketCount = monthlyTicketCount, // lưu số vé tháng
                    freeTicketCount = freeTicketCount, // lưu số vé miễn phí
                    workerSurveyCount = workerSurveyCount, // lưu khảo sát công nhân
                    studentSurveyCount = studentSurveyCount // lưu khảo sát học sinh và sinh viên
                )
            )
        }
    }
}