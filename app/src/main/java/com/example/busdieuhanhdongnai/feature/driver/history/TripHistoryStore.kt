package com.example.busdieuhanhdongnai.feature.driver.history

import androidx.compose.runtime.mutableStateListOf // danh sách tự cập nhật giao diện
import androidx.compose.ui.graphics.Color // dùng màu trạng thái

object TripHistoryStore { // nơi lưu chung danh sách chuyến xe

    val tripList = mutableStateListOf( // danh sách chuyến xe đang hiển thị
        TripHistoryItem(
            date = "23/06/2026",
            route = "Tuyến 01: Bến xe A → Bến xe B",
            time = "07:00 - 08:00",
            passengers = "35 khách",
            status = "Đã hoàn thành",
            statusColor = Color(0xFF1A9B54)
        ),
        TripHistoryItem(
            date = "22/06/2026",
            route = "Tuyến 01: Bến xe A → Bến xe B",
            time = "10:00 - 11:00",
            passengers = "28 khách",
            status = "Đã hoàn thành",
            statusColor = Color(0xFF1A9B54)
        ),
        TripHistoryItem(
            date = "21/06/2026",
            route = "Tuyến 02: Bến xe B → Bến xe C",
            time = "14:00 - 15:00",
            passengers = "Chưa cập nhật",
            status = "Chậm chuyến",
            statusColor = Color(0xFFFF8A00)
        )
    )

    fun addCompletedTrip( // thêm chuyến vừa hoàn thành vào đầu danh sách
        passengers: String,
        note: String
    ) {
        tripList.add( // thêm chuyến mới vào vị trí đầu tiên
            0,
            TripHistoryItem(
                date = "24/06/2026", // ngày mẫu hiện tại
                route = "Tuyến 01: Bến xe A → Bến xe B",
                time = "07:00 - 08:00",
                passengers = "$passengers khách",
                status = "Đã hoàn thành",
                statusColor = Color(0xFF1A9B54)
            )
        )
    }
}