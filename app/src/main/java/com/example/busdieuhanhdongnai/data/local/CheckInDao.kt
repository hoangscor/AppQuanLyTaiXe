package com.example.busdieuhanhdongnai.data.local

import androidx.room.Dao // khai báo DAO thao tác với Room
import androidx.room.Insert // thêm lượt check-in vào bảng
import androidx.room.OnConflictStrategy // quy định xử lý khi mã bị trùng
import androidx.room.Query // viết truy vấn dữ liệu Room
import kotlinx.coroutines.flow.Flow // giúp giao diện tự cập nhật khi dữ liệu thay đổi

@Dao
interface CheckInDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // bỏ qua nếu hành khách đã check-in trong chuyến
    suspend fun insertCheckIn(checkIn: CheckInEntity): Long
    // trả về id mới nếu lưu thành công, trả về -1 nếu dữ liệu bị trùng

    @Query(
        """
        SELECT COUNT(*) FROM check_ins
        WHERE date = :date
        AND scheduledTime = :scheduledTime
        """
    )
    fun getCheckInCountByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ dự kiến của chuyến
    ): Flow<Int> // tự cập nhật tổng số hành khách đã check-in

    @Query(
        """
        SELECT * FROM check_ins
        WHERE date = :date
        AND scheduledTime = :scheduledTime
        ORDER BY id DESC
        """
    )
    fun getCheckInsByTrip(
        date: String, // ngày thực hiện chuyến
        scheduledTime: String // khung giờ dự kiến của chuyến
    ): Flow<List<CheckInEntity>> // danh sách hành khách của chuyến
}