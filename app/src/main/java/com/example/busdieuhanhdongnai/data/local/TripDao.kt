package com.example.busdieuhanhdongnai.data.local

import androidx.room.Dao // đánh dấu đây là nơi Room thao tác dữ liệu
import androidx.room.Insert // dùng để thêm một chuyến xe vào database
import androidx.room.OnConflictStrategy // quy định cách xử lý khi dữ liệu trùng
import androidx.room.Query // dùng để đọc dữ liệu từ database
import androidx.room.Update // dùng để cập nhật chuyến xe đã tồn tại
import kotlinx.coroutines.flow.Flow // giúp giao diện tự cập nhật khi dữ liệu thay đổi

@Dao // khai báo đây là Data Access Object của Room
interface TripDao { // tạo các hàm thao tác bảng trips

    @Insert(onConflict = OnConflictStrategy.REPLACE) // thêm chuyến mới hoặc thay thế nếu trùng id
    suspend fun insertTrip(trip: TripEntity) // lưu một chuyến xe vào Room
    @Query(
        """
    SELECT * FROM trips
    WHERE date = :date
    AND route = :route
    AND vehiclePlate = :vehiclePlate
    AND scheduledTime = :scheduledTime
    AND status = 'Đang thực hiện'
    ORDER BY id DESC
    LIMIT 1
    """
    ) // tìm chuyến đang chạy đúng ngày, tuyến, xe và khung giờ
    fun getActiveTrip(
        date: String, // ngày của chuyến cần tìm
        route: String, // tuyến của chuyến cần tìm
        vehiclePlate: String, // biển số xe của chuyến cần tìm
        scheduledTime: String // khung giờ dự kiến của chuyến
    ): Flow<TripEntity?> // trả null khi chưa có chuyến đang chạy

    @Update // cập nhật đúng bản ghi dựa theo khóa chính id
    suspend fun updateTrip(
        trip: TripEntity // dữ liệu chuyến sau khi thay đổi trạng thái
    )
    @Query("SELECT * FROM trips ORDER BY id DESC") // lấy danh sách chuyến mới nhất trước
    fun getAllTrips(): Flow<List<TripEntity>> // trả về danh sách và tự cập nhật khi có dữ liệu mới
    @Query( // cập nhật các chuyến cũ vẫn còn trạng thái đang thực hiện
        """
    UPDATE trips
    SET status = 'Chưa hoàn thành'
    WHERE status = 'Đang thực hiện'
    AND date != :currentDate
    """
    )
    suspend fun markOldActiveTripsAsIncomplete(
        currentDate: String // ngày hiện tại theo định dạng dd/MM/yyyy
    )
    @Query("DELETE FROM trips") // xóa toàn bộ lịch sử chuyến xe
    suspend fun deleteAllTrips() // dùng khi cần làm trống dữ liệu
}