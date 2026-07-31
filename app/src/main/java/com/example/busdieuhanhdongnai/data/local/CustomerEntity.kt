package com.example.busdieuhanhdongnai.data.local // đặt dữ liệu trong phần Room local

import androidx.room.Entity // khai báo một bảng trong Room
import androidx.room.PrimaryKey // khai báo khóa chính của bảng

@Entity(tableName = "customer_statistics") // tạo bảng lưu thống kê hành khách và vé
data class CustomerEntity( // đại diện cho một bản ghi thống kê khách hàng

    @PrimaryKey
    val id: Int = 1, // chỉ sử dụng một bản ghi tổng hợp cho doanh nghiệp hiện tại

    val singleTicketCount: Int = 0, // số hành khách sử dụng vé lượt

    val monthlyTicketCount: Int = 0, // số hành khách sử dụng vé tháng

    val freeTicketCount: Int = 0, // số hành khách sử dụng vé miễn phí

    val workerSurveyCount: Int = 0, // số công nhân trong dữ liệu khảo sát

    val studentSurveyCount: Int = 0 // số học sinh và sinh viên trong dữ liệu khảo sát
)