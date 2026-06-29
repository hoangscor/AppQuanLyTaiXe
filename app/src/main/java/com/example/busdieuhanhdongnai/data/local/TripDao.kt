package com.example.busdieuhanhdongnai.data.local

import androidx.room.Dao // đánh dấu đây là nơi Room thao tác dữ liệu
import androidx.room.Insert // dùng để thêm một chuyến xe vào database
import androidx.room.OnConflictStrategy // quy định cách xử lý khi dữ liệu trùng
import androidx.room.Query // dùng để đọc dữ liệu từ database
import kotlinx.coroutines.flow.Flow // giúp giao diện tự cập nhật khi dữ liệu thay đổi

@Dao // khai báo đây là Data Access Object của Room
interface TripDao { // tạo các hàm thao tác bảng trips

    @Insert(onConflict = OnConflictStrategy.REPLACE) // thêm chuyến mới hoặc thay thế nếu trùng id
    suspend fun insertTrip(trip: TripEntity) // lưu một chuyến xe vào Room

    @Query("SELECT * FROM trips ORDER BY id DESC") // lấy danh sách chuyến mới nhất trước
    fun getAllTrips(): Flow<List<TripEntity>> // trả về danh sách và tự cập nhật khi có dữ liệu mới

    @Query("DELETE FROM trips") // xóa toàn bộ lịch sử chuyến xe
    suspend fun deleteAllTrips() // dùng khi cần làm trống dữ liệu
}