package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.NotificationDao // dùng DAO thao tác bảng notifications
import com.example.busdieuhanhdongnai.data.local.NotificationEntity // dùng model dữ liệu thông báo

class NotificationRepository(
    private val notificationDao: NotificationDao // nhận NotificationDao từ Room
) {

    val allNotifications = notificationDao.getAllNotifications() // theo dõi toàn bộ danh sách thông báo

    val unreadNotificationCount =
        notificationDao.getUnreadNotificationCount() // theo dõi số lượng thông báo chưa đọc

    suspend fun saveNotification(
        notification: NotificationEntity // nhận thông báo cần lưu
    ) {
        notificationDao.insertNotification(notification) // thêm thông báo vào Room
    }

    suspend fun markNotificationAsRead(
        notificationId: Int // nhận id thông báo đã mở
    ) {
        notificationDao.markNotificationAsRead(
            notificationId = notificationId // cập nhật thông báo thành đã đọc
        )
    }

    suspend fun markAllNotificationsAsRead() { // đánh dấu toàn bộ thông báo đã đọc
        notificationDao.markAllNotificationsAsRead() // gọi DAO cập nhật dữ liệu Room
    }
}