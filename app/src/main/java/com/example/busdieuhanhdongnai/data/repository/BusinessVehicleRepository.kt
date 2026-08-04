package com.example.busdieuhanhdongnai.data.repository // đặt Repository trong tầng dữ liệu trung gian

import com.example.busdieuhanhdongnai.data.local.BusinessVehicleDao // sử dụng DAO phương tiện của Room
import com.example.busdieuhanhdongnai.data.local.BusinessVehicleEntity // sử dụng dữ liệu phương tiện trong Room
import kotlinx.coroutines.flow.Flow // theo dõi dữ liệu thay đổi theo thời gian thực

class BusinessVehicleRepository( // tạo Repository trung gian giữa DAO và ViewModel
    private val businessVehicleDao: BusinessVehicleDao // nhận BusinessVehicleDao từ AppDatabase
) {

    val allVehicles: Flow<List<BusinessVehicleEntity>> = // cung cấp toàn bộ danh sách phương tiện
        businessVehicleDao.getAllVehicles() // gọi DAO để theo dõi toàn bộ xe trong Room

    fun searchVehicles( // tạo hàm tìm kiếm phương tiện
        keyword: String // nhận từ khóa biển số hoặc loại xe
    ): Flow<List<BusinessVehicleEntity>> { // trả danh sách phương tiện phù hợp
        return businessVehicleDao.searchVehicles( // gọi câu truy vấn tìm kiếm trong DAO
            keyword = keyword.trim() // loại bỏ khoảng trắng thừa trước và sau từ khóa
        )
    }

    fun getVehicleById( // tạo hàm lấy chi tiết một phương tiện
        vehicleId: Int // nhận mã id của phương tiện
    ): Flow<BusinessVehicleEntity?> { // trả phương tiện hoặc null khi không tìm thấy
        return businessVehicleDao.getVehicleById( // gọi hàm lấy xe theo id trong DAO
            vehicleId = vehicleId // truyền mã phương tiện cần lấy
        )
    }
    suspend fun isPlateNumberExists( // kiểm tra biển số phương tiện đã tồn tại hay chưa
        plateNumber: String // nhận biển số phương tiện cần kiểm tra
    ): Boolean { // trả true khi biển số đã tồn tại và false khi chưa tồn tại
        val duplicateVehicleCount = businessVehicleDao.countVehiclesByPlateNumber( // gọi DAO để đếm biển số trùng
            plateNumber = plateNumber.trim() // loại bỏ khoảng trắng thừa trước và sau biển số
        )

        return duplicateVehicleCount > 0 // có ít nhất một bản ghi nghĩa là biển số đã tồn tại
    }
    suspend fun saveVehicle( // tạo hàm lưu hoặc cập nhật phương tiện
        vehicle: BusinessVehicleEntity // nhận dữ liệu phương tiện cần lưu
    ) {
        businessVehicleDao.saveVehicle( // yêu cầu DAO lưu dữ liệu vào Room
            vehicle = vehicle // truyền phương tiện cần lưu
        )
    }

    suspend fun deleteVehicle( // tạo hàm xóa phương tiện
        vehicle: BusinessVehicleEntity // nhận phương tiện cần xóa
    ) {
        businessVehicleDao.deleteVehicle( // yêu cầu DAO xóa dữ liệu khỏi Room
            vehicle = vehicle // truyền đúng bản ghi cần xóa
        )
    }
}