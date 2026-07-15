package com.example.busdieuhanhdongnai.data.local

import androidx.room.Entity // khai báo bảng Room
import androidx.room.Index // tạo ràng buộc chống check-in trùng
import androidx.room.PrimaryKey // khai báo khóa chính

@Entity(
    tableName = "check_ins", // tên bảng lưu lượt hành khách lên xe
    indices = [
        Index(
            value = ["passengerCode", "date", "scheduledTime"], // xác định một lượt check-in trong chuyến
            unique = true // không cho cùng mã check-in hai lần trong cùng chuyến và cùng ngày
        )
    ]
)
data class CheckInEntity(
    @PrimaryKey(autoGenerate = true) // Room tự tăng id
    val id: Int = 0, // mã định danh lượt check-in

    val passengerCode: String, // mã QR hoặc mã thẻ điện tử của hành khách
    val date: String, // ngày hành khách check-in
    val time: String, // giờ hành khách check-in
    val route: String, // tuyến xe hành khách đi
    val vehiclePlate: String, // biển số xe thực hiện chuyến
    val scheduledTime: String, // khung giờ dự kiến của chuyến
    val status: String = "Đã check-in" // trạng thái xác nhận hành khách
)