package com.example.busdieuhanhdongnai.data.local

import androidx.room.Entity // khai báo bảng dữ liệu Room
import androidx.room.PrimaryKey // khai báo khóa chính của bảng

@Entity(tableName = "notifications") // tạo bảng notifications trong Room
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) // Room tự động tăng id
    val id: Int = 0, // mã định danh của thông báo

    val title: String, // tiêu đề thông báo
    val message: String, // nội dung chi tiết của thông báo
    val date: String, // ngày tạo thông báo
    val time: String, // giờ tạo thông báo
    val type: String = "Thông thường", // loại thông báo
    val isRead: Boolean = false // trạng thái đã đọc hay chưa
)