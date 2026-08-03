package com.example.busdieuhanhdongnai.data.local // đặt DAO phương tiện trong khu vực Room local

import androidx.room.Dao // khai báo đây là một DAO của Room
import androidx.room.Delete // cung cấp lệnh xóa bản ghi
import androidx.room.Query // cho phép viết câu truy vấn SQL
import androidx.room.Upsert // thêm mới hoặc cập nhật bản ghi đã tồn tại
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu thay đổi theo thời gian thực

@Dao // đánh dấu interface này là DAO của Room
interface BusinessVehicleDao { // cung cấp các thao tác với bảng business_vehicles

    @Query( // lấy toàn bộ phương tiện trong database
        """
        SELECT *
        FROM business_vehicles
        ORDER BY plateNumber ASC
        """
    )
    fun getAllVehicles(): Flow<List<BusinessVehicleEntity>> // trả danh sách xe và tự cập nhật khi dữ liệu đổi

    @Query( // tìm phương tiện theo biển số hoặc loại xe
        """
        SELECT *
        FROM business_vehicles
        WHERE plateNumber LIKE '%' || :keyword || '%'
           OR vehicleType LIKE '%' || :keyword || '%'
        ORDER BY plateNumber ASC
        """
    )
    fun searchVehicles( // khai báo hàm tìm kiếm phương tiện
        keyword: String // nhận từ khóa biển số hoặc loại xe
    ): Flow<List<BusinessVehicleEntity>> // trả danh sách xe phù hợp theo thời gian thực

    @Query( // lấy một phương tiện dựa vào mã id
        """
        SELECT *
        FROM business_vehicles
        WHERE id = :vehicleId
        LIMIT 1
        """
    )
    fun getVehicleById( // khai báo hàm lấy chi tiết một xe
        vehicleId: Int // nhận mã phương tiện cần tìm
    ): Flow<BusinessVehicleEntity?> // trả null khi không tìm thấy phương tiện

    @Upsert // thêm xe mới hoặc cập nhật xe đã có
    suspend fun saveVehicle( // khai báo hàm lưu phương tiện
        vehicle: BusinessVehicleEntity // nhận dữ liệu phương tiện cần lưu
    )

    @Delete // xóa đúng bản ghi phương tiện được truyền vào
    suspend fun deleteVehicle( // khai báo hàm xóa phương tiện
        vehicle: BusinessVehicleEntity // nhận phương tiện cần xóa
    )
}