package com.example.busdieuhanhdongnai.feature.driver.incident

import android.app.Application // dùng Application để lấy Context toàn app
import androidx.lifecycle.AndroidViewModel // ViewModel có thể nhận Application
import androidx.lifecycle.viewModelScope // scope chạy coroutine trong ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // lấy Room database
import com.example.busdieuhanhdongnai.data.local.IncidentEntity // model báo cáo sự cố
import com.example.busdieuhanhdongnai.data.repository.IncidentRepository // repository thao tác sự cố
import kotlinx.coroutines.launch // chạy lưu dữ liệu bất đồng bộ

class IncidentViewModel(application: Application) : AndroidViewModel(application) { // quản lý dữ liệu sự cố cho giao diện

    private val repository = IncidentRepository( // tạo repository thao tác bảng incidents
        AppDatabase.getDatabase(application).incidentDao() // lấy IncidentDao từ Room
    )

    val allIncidents = repository.allIncidents // cung cấp danh sách sự cố để màn hình theo dõi

    fun saveIncident( // hàm để màn hình gọi khi tài xế gửi báo cáo
        date: String, // ngày gửi báo cáo
        time: String, // giờ gửi báo cáo
        route: String, // tuyến xe xảy ra sự cố
        vehiclePlate: String, // biển số xe
        incidentType: String, // loại sự cố
        description: String // mô tả sự cố
    ) {
        viewModelScope.launch { // chạy lưu dữ liệu trong coroutine của ViewModel
            repository.saveIncident( // gọi Repository lưu báo cáo vào Room
                IncidentEntity( // tạo dữ liệu báo cáo sự cố
                    date = date, // gán ngày gửi
                    time = time, // gán giờ gửi
                    route = route, // gán tuyến xe
                    vehiclePlate = vehiclePlate, // gán biển số xe
                    incidentType = incidentType, // gán loại sự cố
                    description = description // gán nội dung mô tả
                )
            )
        }
    }
}