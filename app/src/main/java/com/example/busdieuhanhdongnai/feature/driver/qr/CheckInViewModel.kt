package com.example.busdieuhanhdongnai.feature.driver.qr

import android.app.Application // dùng Application để lấy Context toàn ứng dụng
import androidx.lifecycle.AndroidViewModel // ViewModel có thể nhận Application
import androidx.lifecycle.viewModelScope // scope chạy coroutine theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // lấy Room database
import com.example.busdieuhanhdongnai.data.local.CheckInEntity // model dữ liệu check-in
import com.example.busdieuhanhdongnai.data.repository.CheckInRepository // repository thao tác check-in
import kotlinx.coroutines.flow.Flow // dùng Flow theo dõi dữ liệu Room
import kotlinx.coroutines.launch // chạy lưu dữ liệu bất đồng bộ

class CheckInViewModel(
    application: Application // nhận Application của ứng dụng
) : AndroidViewModel(application) {

    private val repository = CheckInRepository( // tạo repository cho chức năng check-in
        AppDatabase.getDatabase(application).checkInDao() // lấy CheckInDao từ Room
    )

    fun saveCheckIn( // lưu một lượt hành khách lên xe
        passengerCode: String, // mã QR hoặc mã thẻ điện tử
        date: String, // ngày check-in
        time: String, // giờ check-in
        route: String, // tuyến xe
        vehiclePlate: String, // biển số xe
        scheduledTime: String, // khung giờ dự kiến của chuyến
        onResult: (Boolean) -> Unit // trả kết quả lưu thành công hoặc bị trùng
    ) {
        viewModelScope.launch { // chạy thao tác Room trong coroutine
            val isSaved = repository.saveCheckIn(
                CheckInEntity(
                    passengerCode = passengerCode.trim(), // lưu mã hành khách đã loại khoảng trắng
                    date = date, // lưu ngày check-in
                    time = time, // lưu giờ check-in
                    route = route, // lưu tuyến xe
                    vehiclePlate = vehiclePlate, // lưu biển số xe
                    scheduledTime = scheduledTime // lưu khung giờ chuyến
                )
            )

            onResult(isSaved) // trả true nếu lưu thành công, false nếu mã bị trùng
        }
    }

    fun getCheckInCountByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ của chuyến
    ): Flow<Int> {
        return repository.getCheckInCountByTrip(
            date = date, // truyền ngày xuống Repository
            scheduledTime = scheduledTime // truyền giờ dự kiến xuống Repository
        )
    }

    fun getCheckInsByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ của chuyến
    ): Flow<List<CheckInEntity>> {
        return repository.getCheckInsByTrip(
            date = date, // truyền ngày xuống Repository
            scheduledTime = scheduledTime // truyền giờ dự kiến xuống Repository
        )
    }
}