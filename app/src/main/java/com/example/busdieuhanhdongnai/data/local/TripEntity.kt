package com.example.busdieuhanhdongnai.data.local


import androidx.room.Entity // dùng annotation tạo bảng Room
import androidx.room.PrimaryKey // dùng annotation tạo khóa chính

@Entity(tableName = "trips") // khai báo bảng lưu lịch sử chuyến xe
data class TripEntity( // tạo cấu trúc dữ liệu một chuyến xe
    @PrimaryKey(autoGenerate = true) // Room tự tạo mã id cho mỗi chuyến
    val id: Int = 0, // mã định danh của chuyến xe
    val date: String, // ngày thực hiện chuyến xe
    val route: String, // tên tuyến xe
    val vehiclePlate: String = "", // biển số xe thực hiện chuyến
    val scheduledTime: String = "", // khung giờ dự kiến của chuyến được chọn từ lịch trình
    val time: String, // thời gian chạy chuyến xe
    val passengers: String, // số lượng khách của chuyến xe
    val status: String, // trạng thái như Đã hoàn thành hoặc Chậm chuyến
    val note: String = "" // ghi chú hoặc sự cố của chuyến xe
)