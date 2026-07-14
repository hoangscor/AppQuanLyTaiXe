package com.example.busdieuhanhdongnai.data.local

import androidx.room.Entity // dùng annotation tạo bảng Room
import androidx.room.PrimaryKey // dùng annotation tạo khóa chính

@Entity(tableName = "incidents") // tạo bảng lưu báo cáo sự cố
data class IncidentEntity( // mô tả một báo cáo sự cố
    @PrimaryKey(autoGenerate = true) // Room tự tăng mã báo cáo
    val id: Int = 0, // mã định danh của báo cáo sự cố

    val date: String, // ngày tài xế gửi báo cáo
    val time: String, // thời gian tài xế gửi báo cáo
    val route: String, // tuyến xe đang gặp sự cố
    val vehiclePlate: String, // biển số xe gặp sự cố
    val incidentType: String, // loại sự cố, ví dụ hỏng xe hoặc tai nạn
    val description: String, // nội dung mô tả chi tiết sự cố
    val status: String = "Chưa xử lý" // trạng thái xử lý ban đầu của báo cáo
)