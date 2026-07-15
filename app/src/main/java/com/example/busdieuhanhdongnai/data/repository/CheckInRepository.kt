package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.CheckInDao // dùng DAO thao tác bảng check_ins
import com.example.busdieuhanhdongnai.data.local.CheckInEntity // dùng model dữ liệu check-in
import kotlinx.coroutines.flow.Flow // dùng Flow để giao diện tự cập nhật

class CheckInRepository(
    private val checkInDao: CheckInDao // nhận DAO check-in từ Room
) {

    suspend fun saveCheckIn(checkIn: CheckInEntity): Boolean { // lưu một lượt hành khách check-in
        val insertedId = checkInDao.insertCheckIn(checkIn) // Room trả id mới hoặc -1 nếu bị trùng

        return insertedId != -1L // true nếu lưu thành công, false nếu hành khách đã check-in
    }

    fun getCheckInCountByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ dự kiến của chuyến
    ): Flow<Int> {
        return checkInDao.getCheckInCountByTrip(
            date = date, // truyền ngày cho DAO
            scheduledTime = scheduledTime // truyền khung giờ cho DAO
        )
    }

    fun getCheckInsByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ dự kiến của chuyến
    ): Flow<List<CheckInEntity>> {
        return checkInDao.getCheckInsByTrip(
            date = date, // truyền ngày cho DAO
            scheduledTime = scheduledTime // truyền khung giờ cho DAO
        )
    }
}