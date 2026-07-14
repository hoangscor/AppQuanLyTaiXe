package com.example.busdieuhanhdongnai.data.local

import androidx.room.Dao // khai báo interface thao tác với Room
import androidx.room.Insert // dùng để thêm dữ liệu vào bảng
import androidx.room.OnConflictStrategy // quy định cách xử lý khi trùng dữ liệu
import androidx.room.Query // dùng để viết câu lệnh truy vấn Room
import kotlinx.coroutines.flow.Flow // dùng Flow để giao diện tự cập nhật khi dữ liệu đổi

@Dao // đánh dấu đây là Data Access Object của bảng incidents
interface IncidentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // thêm báo cáo sự cố vào Room
    suspend fun insertIncident(incident: IncidentEntity) // lưu một báo cáo sự cố

    @Query("SELECT * FROM incidents ORDER BY id DESC") // lấy báo cáo mới nhất lên đầu
    fun getAllIncidents(): Flow<List<IncidentEntity>> // trả danh sách báo cáo để giao diện theo dõi
}