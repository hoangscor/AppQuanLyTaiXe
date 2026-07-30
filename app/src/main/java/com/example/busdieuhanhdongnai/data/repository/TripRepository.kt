package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.TripDao // dùng DAO thao tác Room
import com.example.busdieuhanhdongnai.data.local.TripEntity // dùng dữ liệu chuyến xe
import kotlinx.coroutines.flow.Flow // giúp danh sách tự cập nhật

class TripRepository( // lớp trung gian giữa giao diện và Room
    private val tripDao: TripDao // DAO được truyền vào Repository
) {

    val allTrips: Flow<List<TripEntity>> = tripDao.getAllTrips() // lấy toàn bộ lịch sử chuyến
    fun getActiveTrip(
        date: String, // ngày của chuyến cần tìm
        route: String, // tuyến xe cần tìm
        vehiclePlate: String, // biển số xe cần tìm
        scheduledTime: String // khung giờ dự kiến cần tìm
    ): Flow<TripEntity?> {
        return tripDao.getActiveTrip(
            date = date, // truyền ngày xuống DAO
            route = route, // truyền tuyến xuống DAO
            vehiclePlate = vehiclePlate, // truyền biển số xuống DAO
            scheduledTime = scheduledTime // truyền khung giờ xuống DAO
        )
    }
    suspend fun saveTrip(trip: TripEntity) { // lưu một chuyến xe mới
        tripDao.insertTrip(trip) // gọi DAO thêm chuyến vào Room
    }
    suspend fun updateTrip(
        trip: TripEntity // nhận chuyến xe đã thay đổi trạng thái
    ) {
        tripDao.updateTrip(
            trip = trip // yêu cầu DAO cập nhật bản ghi hiện có
        )
    }
    suspend fun markOldActiveTripsAsIncomplete(
        currentDate: String // nhận ngày hiện tại từ ViewModel
    ) {
        tripDao.markOldActiveTripsAsIncomplete(
            currentDate = currentDate // yêu cầu Room xử lý các chuyến cũ bị treo
        )
    }
    suspend fun clearTripHistory() { // xóa toàn bộ lịch sử chuyến
        tripDao.deleteAllTrips() // gọi DAO xóa dữ liệu Room
    }
}