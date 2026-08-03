package com.example.busdieuhanhdongnai.data.local // đặt DAO trong khu vực dữ liệu Room local

import androidx.room.Dao // khai báo lớp thao tác dữ liệu Room
import androidx.room.Insert // dùng để thêm hoặc thay thế bản ghi
import androidx.room.OnConflictStrategy // quy định cách xử lý khi trùng ngày
import androidx.room.Query // sử dụng câu lệnh truy vấn SQL
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu thay đổi theo thời gian thực

data class CustomerStatisticsSummary( // dữ liệu tổng hợp khách hàng trong một khoảng ngày
    val singleTicketCount: Int = 0, // tổng số hành khách sử dụng vé lượt
    val monthlyTicketCount: Int = 0, // tổng số hành khách sử dụng vé tháng
    val freeTicketCount: Int = 0, // tổng số hành khách sử dụng vé miễn phí
    val workerSurveyCount: Int = 0, // tổng số công nhân được khảo sát
    val studentSurveyCount: Int = 0 // tổng số học sinh và sinh viên được khảo sát
)

@Dao // đánh dấu đây là DAO của Room
interface CustomerDao { // cung cấp các thao tác với bảng customer_statistics

    @Query("SELECT * FROM customer_statistics WHERE recordDate = :recordDate LIMIT 1") // lấy thống kê đúng một ngày
    fun getCustomerStatisticsByDate( // hàm theo dõi thống kê của một ngày
        recordDate: String // ngày cần lấy theo định dạng yyyy-MM-dd
    ): Flow<CustomerEntity?> // trả null khi ngày đó chưa có dữ liệu

    @Query( // truy vấn cộng dữ liệu trong khoảng ngày được chọn
        """
        SELECT
            COALESCE(SUM(singleTicketCount), 0) AS singleTicketCount,
            COALESCE(SUM(monthlyTicketCount), 0) AS monthlyTicketCount,
            COALESCE(SUM(freeTicketCount), 0) AS freeTicketCount,
            COALESCE(SUM(workerSurveyCount), 0) AS workerSurveyCount,
            COALESCE(SUM(studentSurveyCount), 0) AS studentSurveyCount
        FROM customer_statistics
        WHERE recordDate BETWEEN :startDate AND :endDate
        """
    )
    fun getCustomerStatisticsByRange( // lấy tổng dữ liệu từ ngày bắt đầu đến ngày kết thúc
        startDate: String, // ngày bắt đầu theo định dạng yyyy-MM-dd
        endDate: String // ngày kết thúc theo định dạng yyyy-MM-dd
    ): Flow<CustomerStatisticsSummary> // trả dữ liệu tổng hợp cho màn hình

    @Insert(onConflict = OnConflictStrategy.REPLACE) // thay thế dữ liệu khi ngày đó đã tồn tại
    suspend fun saveCustomerStatistics( // lưu thống kê khách hàng của một ngày
        customer: CustomerEntity // bản ghi khách hàng cần lưu vào Room
    )
}