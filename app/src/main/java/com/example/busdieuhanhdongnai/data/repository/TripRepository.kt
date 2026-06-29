package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.TripDao // dùng DAO thao tác Room
import com.example.busdieuhanhdongnai.data.local.TripEntity // dùng dữ liệu chuyến xe
import kotlinx.coroutines.flow.Flow // giúp danh sách tự cập nhật

class TripRepository( // lớp trung gian giữa giao diện và Room
    private val tripDao: TripDao // DAO được truyền vào Repository
) {

    val allTrips: Flow<List<TripEntity>> = tripDao.getAllTrips() // lấy toàn bộ lịch sử chuyến

    suspend fun saveTrip(trip: TripEntity) { // lưu một chuyến xe mới
        tripDao.insertTrip(trip) // gọi DAO thêm chuyến vào Room
    }

    suspend fun clearTripHistory() { // xóa toàn bộ lịch sử chuyến
        tripDao.deleteAllTrips() // gọi DAO xóa dữ liệu Room
    }
}