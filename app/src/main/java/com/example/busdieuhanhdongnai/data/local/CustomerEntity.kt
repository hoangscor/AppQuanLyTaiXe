package com.example.busdieuhanhdongnai.data.local // đặt dữ liệu trong khu vực Room local

import androidx.room.Entity // khai báo một bảng trong Room
import androidx.room.Index // tạo chỉ mục và ngăn trùng ngày thống kê
import androidx.room.PrimaryKey // khai báo khóa chính của bảng

@Entity( // khai báo cấu hình bảng thống kê khách hàng
    tableName = "customer_statistics", // giữ nguyên tên bảng đang sử dụng
    indices = [Index(value = ["recordDate"], unique = true)] // mỗi ngày chỉ có một bản thống kê
) // kết thúc phần cấu hình Entity
data class CustomerEntity( // đại diện cho thống kê khách hàng của một ngày
    @PrimaryKey(autoGenerate = true) // để Room tự tạo mã bản ghi
    val id: Int = 0, // mã duy nhất của bản thống kê

    val recordDate: String = "", // ngày thống kê theo định dạng yyyy-MM-dd

    val singleTicketCount: Int = 0, // số hành khách sử dụng vé lượt
    val monthlyTicketCount: Int = 0, // số hành khách sử dụng vé tháng
    val freeTicketCount: Int = 0, // số hành khách sử dụng vé miễn phí
    val workerSurveyCount: Int = 0, // số công nhân trong dữ liệu khảo sát
    val studentSurveyCount: Int = 0 // số học sinh và sinh viên trong khảo sát
) // kết thúc dữ liệu thống kê khách hàng