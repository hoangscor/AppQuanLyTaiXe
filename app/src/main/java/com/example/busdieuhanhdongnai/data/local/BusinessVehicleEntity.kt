package com.example.busdieuhanhdongnai.data.local // đặt dữ liệu phương tiện trong khu vực Room local

import androidx.room.Entity // khai báo một bảng dữ liệu trong Room
import androidx.room.Index // tạo chỉ mục và ngăn biển số xe bị trùng
import androidx.room.PrimaryKey // khai báo khóa chính của bảng

@Entity( // bắt đầu cấu hình bảng phương tiện doanh nghiệp
    tableName = "business_vehicles", // đặt tên bảng lưu phương tiện
    indices = [ // khai báo danh sách chỉ mục của bảng
        Index( // tạo chỉ mục cho biển số xe
            value = ["plateNumber"], // sử dụng cột biển số làm chỉ mục
            unique = true // không cho phép hai xe có cùng biển số
        ) // kết thúc cấu hình chỉ mục
    ] // kết thúc danh sách chỉ mục
) // kết thúc cấu hình Entity
data class BusinessVehicleEntity( // đại diện cho một phương tiện trong Room

    @PrimaryKey(autoGenerate = true) // để Room tự động tạo mã phương tiện
    val id: Int = 0, // mã duy nhất của phương tiện

    val plateNumber: String, // biển số phương tiện

    val vehicleType: String, // loại xe hoặc dòng xe

    val driverName: String, // tên tài xế đang phụ trách

    val maintenanceDate: String, // ngày bảo trì gần nhất theo yyyy-MM-dd

    val status: String // trạng thái Hoạt động, Bảo trì hoặc Tạm dừng
) // kết thúc dữ liệu phương tiện