package com.example.busdieuhanhdongnai.data.local

import androidx.room.Dao // khai báo DAO thao tác với bảng notifications
import androidx.room.Insert // dùng để thêm thông báo vào Room
import androidx.room.OnConflictStrategy // quy định cách xử lý dữ liệu bị trùng
import androidx.room.Query // dùng để viết câu lệnh truy vấn Room
import kotlinx.coroutines.flow.Flow // giúp giao diện tự cập nhật khi dữ liệu thay đổi

@Dao // đánh dấu đây là Data Access Object của bảng notifications
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // thêm hoặc thay thế thông báo
    suspend fun insertNotification(
        notification: NotificationEntity // thông báo cần lưu vào Room
    )

    @Query("SELECT * FROM notifications ORDER BY id DESC") // lấy thông báo mới nhất lên đầu
    fun getAllNotifications(): Flow<List<NotificationEntity>> // trả danh sách thông báo theo thời gian thực

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0") // đếm thông báo chưa đọc
    fun getUnreadNotificationCount(): Flow<Int> // trả số lượng chưa đọc theo thời gian thực

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId") // đánh dấu một thông báo đã đọc
    suspend fun markNotificationAsRead(
        notificationId: Int // id của thông báo được mở
    )

    @Query("UPDATE notifications SET isRead = 1") // đánh dấu toàn bộ thông báo đã đọc
    suspend fun markAllNotificationsAsRead()
}