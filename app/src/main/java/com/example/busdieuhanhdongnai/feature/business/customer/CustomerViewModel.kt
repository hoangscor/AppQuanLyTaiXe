package com.example.busdieuhanhdongnai.feature.business.customer // đặt ViewModel trong phần khách hàng doanh nghiệp

import android.app.Application // lấy Application để truy cập database
import androidx.lifecycle.AndroidViewModel // tạo ViewModel sử dụng Application
import androidx.lifecycle.viewModelScope // chạy tác vụ theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // truy cập Room database
import com.example.busdieuhanhdongnai.data.local.CustomerEntity // dữ liệu thống kê của một ngày
import com.example.busdieuhanhdongnai.data.local.CustomerStatisticsSummary // dữ liệu tổng hợp theo khoảng ngày
import com.example.busdieuhanhdongnai.data.repository.CustomerRepository // lớp Repository quản lý dữ liệu khách hàng
import kotlinx.coroutines.ExperimentalCoroutinesApi // cho phép sử dụng flatMapLatest
import kotlinx.coroutines.flow.Flow // luồng dữ liệu Room
import kotlinx.coroutines.flow.MutableStateFlow // tạo trạng thái có thể thay đổi
import kotlinx.coroutines.flow.StateFlow // cung cấp trạng thái chỉ đọc cho giao diện
import kotlinx.coroutines.flow.asStateFlow // chuyển MutableStateFlow thành StateFlow
import kotlinx.coroutines.flow.combine // kết hợp ngày bắt đầu và ngày kết thúc
import kotlinx.coroutines.flow.flatMapLatest // đổi truy vấn khi ngày lọc thay đổi
import kotlinx.coroutines.launch // chạy thao tác lưu trong coroutine
import java.time.LocalDate // xử lý ngày tháng

enum class CustomerPeriodFilter { // định nghĩa các loại bộ lọc thời gian
    TODAY, // dữ liệu hôm nay
    LAST_7_DAYS, // dữ liệu 7 ngày gần đây
    LAST_30_DAYS, // dữ liệu 30 ngày gần đây
    LAST_1_YEAR, // dữ liệu 1 năm gần đây
    CUSTOM // khoảng ngày người dùng tự chọn
}

@OptIn(ExperimentalCoroutinesApi::class) // cho phép sử dụng flatMapLatest
class CustomerViewModel(
    application: Application // nhận Application từ Android
) : AndroidViewModel(application) {

    private val repository = CustomerRepository( // tạo Repository quản lý dữ liệu khách hàng
        customerDao = AppDatabase
            .getDatabase(application) // lấy database duy nhất của ứng dụng
            .customerDao() // lấy CustomerDao từ database
    )

    private val todayDate = LocalDate.now() // lấy ngày hiện tại của thiết bị

    private val _selectedFilter = MutableStateFlow( // lưu loại bộ lọc đang được chọn
        CustomerPeriodFilter.TODAY // mặc định hiển thị dữ liệu hôm nay
    )

    val selectedFilter: StateFlow<CustomerPeriodFilter> = // cho giao diện đọc bộ lọc hiện tại
        _selectedFilter.asStateFlow()

    private val _startDate = MutableStateFlow( // lưu ngày bắt đầu của khoảng lọc
        todayDate.toString() // mặc định bắt đầu từ hôm nay
    )

    val startDate: StateFlow<String> = // cho giao diện đọc ngày bắt đầu
        _startDate.asStateFlow()

    private val _endDate = MutableStateFlow( // lưu ngày kết thúc của khoảng lọc
        todayDate.toString() // mặc định kết thúc ở hôm nay
    )

    val endDate: StateFlow<String> = // cho giao diện đọc ngày kết thúc
        _endDate.asStateFlow()

    val customerStatistics: Flow<CustomerEntity?> = // giữ tương thích với giao diện hiện tại
        _endDate.flatMapLatest { selectedDate -> // truy vấn lại khi ngày kết thúc thay đổi
            repository.getCustomerStatisticsByDate( // lấy dữ liệu của một ngày
                recordDate = selectedDate // dùng ngày đang được chọn
            )
        }

    val customerStatisticsSummary: Flow<CustomerStatisticsSummary> = // cung cấp dữ liệu tổng hợp theo khoảng ngày
        combine(
            _startDate, // theo dõi ngày bắt đầu
            _endDate // theo dõi ngày kết thúc
        ) { startDateValue, endDateValue ->
            startDateValue to endDateValue // ghép hai ngày thành một cặp
        }.flatMapLatest { selectedRange ->
            repository.getCustomerStatisticsByRange( // truy vấn tổng dữ liệu trong khoảng ngày
                startDate = selectedRange.first, // truyền ngày bắt đầu
                endDate = selectedRange.second // truyền ngày kết thúc
            )
        }

    fun selectToday() { // chọn bộ lọc hôm nay
        val today = LocalDate.now() // lấy lại ngày hiện tại mới nhất

        _selectedFilter.value = CustomerPeriodFilter.TODAY // đánh dấu bộ lọc hôm nay
        _startDate.value = today.toString() // ngày bắt đầu là hôm nay
        _endDate.value = today.toString() // ngày kết thúc là hôm nay
    }

    fun selectLast7Days() { // chọn 7 ngày gần đây
        val today = LocalDate.now() // lấy ngày hiện tại

        _selectedFilter.value = CustomerPeriodFilter.LAST_7_DAYS // đánh dấu bộ lọc 7 ngày
        _startDate.value = today.minusDays(6).toString() // tính đủ 7 ngày gồm cả hôm nay
        _endDate.value = today.toString() // kết thúc ở hôm nay
    }

    fun selectLast30Days() { // chọn 30 ngày gần đây
        val today = LocalDate.now() // lấy ngày hiện tại

        _selectedFilter.value = CustomerPeriodFilter.LAST_30_DAYS // đánh dấu bộ lọc 30 ngày
        _startDate.value = today.minusDays(29).toString() // tính đủ 30 ngày gồm cả hôm nay
        _endDate.value = today.toString() // kết thúc ở hôm nay
    }

    fun selectLast1Year() { // chọn dữ liệu 1 năm gần đây
        val today = LocalDate.now() // lấy ngày hiện tại

        _selectedFilter.value = CustomerPeriodFilter.LAST_1_YEAR // đánh dấu bộ lọc một năm
        _startDate.value = today.minusYears(1).toString() // lấy ngày cùng kỳ năm trước
        _endDate.value = today.toString() // kết thúc ở hôm nay
    }

    fun selectCustomRange(
        startDate: String, // ngày bắt đầu do người dùng chọn
        endDate: String // ngày kết thúc do người dùng chọn
    ) {
        _selectedFilter.value = CustomerPeriodFilter.CUSTOM // đánh dấu khoảng ngày tự chọn
        _startDate.value = startDate // cập nhật ngày bắt đầu
        _endDate.value = endDate // cập nhật ngày kết thúc
    }

    fun saveCustomerStatistics(
        singleTicketCount: Int, // số hành khách sử dụng vé lượt
        monthlyTicketCount: Int, // số hành khách sử dụng vé tháng
        freeTicketCount: Int, // số hành khách sử dụng vé miễn phí
        workerSurveyCount: Int, // số liệu khảo sát công nhân
        studentSurveyCount: Int, // số liệu khảo sát học sinh và sinh viên
        recordDate: String = LocalDate.now().toString() // mặc định lưu cho ngày hiện tại
    ) {
        viewModelScope.launch { // chạy thao tác lưu ngoài luồng giao diện
            repository.saveCustomerStatistics(
                customer = CustomerEntity(
                    id = 0, // để Room tự tạo mã bản ghi
                    recordDate = recordDate, // lưu ngày thống kê theo yyyy-MM-dd
                    singleTicketCount = singleTicketCount, // lưu vé lượt
                    monthlyTicketCount = monthlyTicketCount, // lưu vé tháng
                    freeTicketCount = freeTicketCount, // lưu vé miễn phí
                    workerSurveyCount = workerSurveyCount, // lưu khảo sát công nhân
                    studentSurveyCount = studentSurveyCount // lưu khảo sát học sinh và sinh viên
                )
            )
        }
    }
}