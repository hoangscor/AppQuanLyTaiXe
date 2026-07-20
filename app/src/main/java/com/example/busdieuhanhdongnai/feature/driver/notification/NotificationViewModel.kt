package com.example.busdieuhanhdongnai.feature.driver.notification

import android.app.Application // dùng Application để lấy Context toàn ứng dụng
import androidx.lifecycle.AndroidViewModel // tạo ViewModel có thể nhận Application
import androidx.lifecycle.viewModelScope // scope chạy coroutine theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // lấy Room database
import com.example.busdieuhanhdongnai.data.local.NotificationEntity // model dữ liệu thông báo
import com.example.busdieuhanhdongnai.data.repository.NotificationRepository // repository thao tác thông báo
import kotlinx.coroutines.launch // chạy tác vụ lưu và cập nhật bất đồng bộ

class NotificationViewModel(
    application: Application // nhận Application của toàn ứng dụng
) : AndroidViewModel(application) {

    private val repository = NotificationRepository( // tạo repository quản lý thông báo
        AppDatabase.getDatabase(application).notificationDao() // lấy NotificationDao từ Room
    )

    val allNotifications = repository.allNotifications // cung cấp danh sách thông báo cho giao diện

    val unreadNotificationCount =
        repository.unreadNotificationCount // cung cấp số lượng thông báo chưa đọc

    fun saveNotification(
        title: String, // tiêu đề thông báo
        message: String, // nội dung chi tiết
        date: String, // ngày tạo thông báo
        time: String, // giờ tạo thông báo
        type: String = "Thông thường" // loại thông báo
    ) {
        viewModelScope.launch { // chạy lưu thông báo trong coroutine
            repository.saveNotification(
                NotificationEntity(
                    title = title, // lưu tiêu đề
                    message = message, // lưu nội dung
                    date = date, // lưu ngày
                    time = time, // lưu giờ
                    type = type, // lưu loại thông báo
                    isRead = false // thông báo mới mặc định chưa đọc
                )
            )
        }
    }

    fun markNotificationAsRead(
        notificationId: Int // nhận id thông báo người dùng vừa mở
    ) {
        viewModelScope.launch { // chạy cập nhật trạng thái trong coroutine
            repository.markNotificationAsRead(
                notificationId = notificationId // chuyển thông báo thành đã đọc
            )
        }
    }

    fun markAllNotificationsAsRead() { // đánh dấu tất cả thông báo đã đọc
        viewModelScope.launch { // chạy cập nhật Room trong coroutine
            repository.markAllNotificationsAsRead() // gọi repository cập nhật toàn bộ
        }
    }
}