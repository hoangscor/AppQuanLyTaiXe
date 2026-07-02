package com.example.busdieuhanhdongnai.feature.driver.trip

import android.app.Application // lấy Application để tạo Room database
import androidx.lifecycle.AndroidViewModel // ViewModel có thể dùng Application
import androidx.lifecycle.viewModelScope // scope coroutine theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // gọi Room database
import com.example.busdieuhanhdongnai.data.local.TripEntity // dùng dữ liệu một chuyến xe
import com.example.busdieuhanhdongnai.data.repository.TripRepository // gọi Repository
import kotlinx.coroutines.launch // chạy tác vụ lưu dữ liệu

class TripViewModel(application: Application) : AndroidViewModel(application) { // ViewModel quản lý dữ liệu chuyến xe

    private val repository = TripRepository( // tạo Repository để thao tác dữ liệu
        AppDatabase.getDatabase(application).tripDao() // lấy TripDao từ Room database
    )

    val allTrips = repository.allTrips // cung cấp danh sách lịch sử chuyến xe cho giao diện

    fun saveTrip( // hàm được giao diện gọi khi hoàn thành chuyến xe
        date: String, // ngày thực hiện chuyến xe
        route: String, // tên tuyến xe
        vehiclePlate: String, // biển số xe của chuyến được chọn
        time: String, // thời gian chạy chuyến xe
        passengers: String, // số lượt khách
        status: String, // trạng thái chuyến xe
        note: String // ghi chú hoặc sự cố
    ) {
        viewModelScope.launch { // chạy lưu dữ liệu trong coroutine của ViewModel
            repository.saveTrip( // gọi Repository lưu vào Room
                TripEntity( // tạo dữ liệu một chuyến xe trước khi lưu
                    date = date, // gán ngày thực hiện
                    route = route, // gán tuyến xe
                    vehiclePlate = vehiclePlate, // lưu biển số xe vào Room
                    time = time, // gán thời gian
                    passengers = passengers, // gán số khách
                    status = status, // gán trạng thái
                    note = note // gán ghi chú
                )
            )
        }
    }
}