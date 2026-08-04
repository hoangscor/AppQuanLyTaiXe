package com.example.busdieuhanhdongnai.feature.business.vehicle // đặt ViewModel trong chức năng quản lý phương tiện

import android.app.Application // lấy Application để truy cập Room database
import androidx.lifecycle.AndroidViewModel // tạo ViewModel có thể nhận Application
import androidx.lifecycle.viewModelScope // chạy thao tác database theo vòng đời ViewModel
import com.example.busdieuhanhdongnai.data.local.AppDatabase // truy cập database chính của ứng dụng
import com.example.busdieuhanhdongnai.data.local.BusinessVehicleEntity // sử dụng dữ liệu phương tiện trong Room
import com.example.busdieuhanhdongnai.data.repository.BusinessVehicleRepository // sử dụng Repository phương tiện
import kotlinx.coroutines.flow.MutableStateFlow // tạo state lưu từ khóa tìm kiếm
import kotlinx.coroutines.flow.SharingStarted // kiểm soát thời điểm Flow bắt đầu hoạt động
import kotlinx.coroutines.flow.StateFlow // cung cấp dữ liệu chỉ đọc cho giao diện
import kotlinx.coroutines.flow.asStateFlow // chuyển MutableStateFlow thành StateFlow chỉ đọc
import kotlinx.coroutines.flow.flatMapLatest // thay đổi truy vấn khi từ khóa tìm kiếm thay đổi
import kotlinx.coroutines.flow.stateIn // chuyển Flow thành StateFlow cho Compose
import kotlinx.coroutines.flow.first // lấy danh sách phương tiện đầu tiên từ Room để kiểm tra database trống
import kotlinx.coroutines.launch // chạy thao tác lưu và xóa trong coroutine

class BusinessVehicleViewModel( // tạo ViewModel quản lý dữ liệu phương tiện
    application: Application // nhận Application từ hệ thống Android
) : AndroidViewModel(application) { // kế thừa AndroidViewModel để sử dụng Application

    private val repository = BusinessVehicleRepository( // tạo Repository quản lý phương tiện
        businessVehicleDao = AppDatabase // bắt đầu truy cập database chính
            .getDatabase(application) // lấy một bản database duy nhất của ứng dụng
            .businessVehicleDao() // lấy DAO của bảng business_vehicles
    )

    private val _searchQuery = MutableStateFlow("") // lưu từ khóa tìm kiếm đang nhập

    val searchQuery: StateFlow<String> = // cung cấp từ khóa tìm kiếm cho giao diện
        _searchQuery.asStateFlow() // không cho giao diện tự sửa state trực tiếp

    val allVehicles: StateFlow<List<BusinessVehicleEntity>> = // cung cấp toàn bộ phương tiện cho phần tổng quan
        repository.allVehicles.stateIn( // chuyển danh sách xe từ Room thành StateFlow
            scope = viewModelScope, // giữ Flow hoạt động theo vòng đời ViewModel
            started = SharingStarted.WhileSubscribed(5_000), // dừng sau 5 giây khi không còn màn hình theo dõi
            initialValue = emptyList() // dùng danh sách trống khi Room chưa trả dữ liệu
        )

    val filteredVehicles: StateFlow<List<BusinessVehicleEntity>> = // cung cấp danh sách xe sau khi tìm kiếm
        _searchQuery.flatMapLatest { keyword -> // chạy lại truy vấn khi từ khóa thay đổi
            if (keyword.isBlank()) { // kiểm tra ô tìm kiếm đang trống
                repository.allVehicles // trả toàn bộ phương tiện khi chưa nhập từ khóa
            } else { // xử lý khi người dùng đã nhập từ khóa
                repository.searchVehicles( // tìm phương tiện trong Room
                    keyword = keyword // truyền từ khóa biển số hoặc loại xe
                )
            }
        }.stateIn( // chuyển kết quả tìm kiếm thành StateFlow
            scope = viewModelScope, // giữ kết quả theo vòng đời ViewModel
            started = SharingStarted.WhileSubscribed(5_000), // dừng sau 5 giây khi không còn màn hình sử dụng
            initialValue = emptyList() // dùng danh sách trống trước khi Room trả kết quả
        )
    init { // chạy một lần ngay khi BusinessVehicleViewModel được tạo
        seedSampleVehiclesIfNeeded() // kiểm tra và thêm dữ liệu mẫu khi bảng phương tiện còn trống
    }

    private fun seedSampleVehiclesIfNeeded() { // tạo dữ liệu phương tiện ban đầu cho ứng dụng
        viewModelScope.launch { // thực hiện kiểm tra và lưu dữ liệu ngoài luồng giao diện
            val currentVehicles = repository.allVehicles.first() // lấy danh sách phương tiện hiện có trong Room

            if (currentVehicles.isEmpty()) { // chỉ thêm dữ liệu mẫu khi database chưa có phương tiện
                val sampleVehicles = listOf( // tạo danh sách bốn phương tiện mẫu để lưu vào Room
                    BusinessVehicleEntity( // tạo phương tiện mẫu thứ nhất
                        id = 0, // để Room tự động tạo mã phương tiện
                        plateNumber = "51B-123.45", // lưu biển số phương tiện thứ nhất
                        vehicleType = "Thaco Town 60 chỗ", // lưu loại xe phương tiện thứ nhất
                        driverName = "Nguyễn Văn An", // lưu tài xế phụ trách phương tiện thứ nhất
                        maintenanceDate = "2026-07-25", // lưu ngày bảo trì gần nhất của phương tiện thứ nhất
                        status = "Hoạt động" // lưu trạng thái phương tiện thứ nhất
                    ),
                    BusinessVehicleEntity( // tạo phương tiện mẫu thứ hai
                        id = 0, // để Room tự động tạo mã phương tiện
                        plateNumber = "51B-234.56", // lưu biển số phương tiện thứ hai
                        vehicleType = "Samco 40 chỗ", // lưu loại xe phương tiện thứ hai
                        driverName = "Trần Minh Tuấn", // lưu tài xế phụ trách phương tiện thứ hai
                        maintenanceDate = "2026-07-20", // lưu ngày bảo trì gần nhất của phương tiện thứ hai
                        status = "Hoạt động" // lưu trạng thái phương tiện thứ hai
                    ),
                    BusinessVehicleEntity( // tạo phương tiện mẫu thứ ba
                        id = 0, // để Room tự động tạo mã phương tiện
                        plateNumber = "60B-345.67", // lưu biển số phương tiện thứ ba
                        vehicleType = "Thaco Garden 79 chỗ", // lưu loại xe phương tiện thứ ba
                        driverName = "Lê Quốc Hùng", // lưu tài xế phụ trách phương tiện thứ ba
                        maintenanceDate = "2026-08-01", // lưu ngày bảo trì gần nhất của phương tiện thứ ba
                        status = "Bảo trì" // lưu trạng thái phương tiện thứ ba
                    ),
                    BusinessVehicleEntity( // tạo phương tiện mẫu thứ tư
                        id = 0, // để Room tự động tạo mã phương tiện
                        plateNumber = "60B-456.78", // lưu biển số phương tiện thứ tư
                        vehicleType = "Hyundai County 29 chỗ", // lưu loại xe phương tiện thứ tư
                        driverName = "Phạm Hoàng Nam", // lưu tài xế phụ trách phương tiện thứ tư
                        maintenanceDate = "2026-07-18", // lưu ngày bảo trì gần nhất của phương tiện thứ tư
                        status = "Tạm dừng" // lưu trạng thái phương tiện thứ tư
                    )
                )

                sampleVehicles.forEach { vehicle -> // lần lượt duyệt qua từng phương tiện mẫu
                    repository.saveVehicle( // yêu cầu Repository lưu phương tiện vào Room
                        vehicle = vehicle // truyền phương tiện hiện tại cần lưu
                    )
                }
            }
        }
    }
    fun updateSearchQuery( // cập nhật từ khóa tìm kiếm từ giao diện
        newValue: String // nhận nội dung mới người dùng vừa nhập
    ) {
        _searchQuery.value = newValue // lưu từ khóa mới vào StateFlow
    }
    fun saveVehicleIfPlateNumberAvailable( // kiểm tra biển số trước khi thêm mới hoặc chỉnh sửa phương tiện
        vehicle: BusinessVehicleEntity, // nhận dữ liệu phương tiện cần lưu
        editingVehicleId: Int? = null, // null là thêm mới, có id là đang chỉnh sửa phương tiện
        onResult: (Boolean) -> Unit // trả true khi lưu thành công và false khi biển số bị trùng
    ) {
        viewModelScope.launch { // chạy kiểm tra và lưu dữ liệu trong coroutine
            val plateNumberExists = if (editingVehicleId == null) { // kiểm tra đây có phải thao tác thêm mới hay không
                repository.isPlateNumberExists( // kiểm tra biển số trên toàn bộ bảng khi thêm mới
                    plateNumber = vehicle.plateNumber // truyền biển số người dùng vừa nhập
                )
            } else { // xử lý khi người dùng đang chỉnh sửa phương tiện
                repository.isPlateNumberUsedByAnotherVehicle( // kiểm tra biển số nhưng bỏ qua chính xe đang sửa
                    plateNumber = vehicle.plateNumber, // truyền biển số hiện tại trong form chỉnh sửa
                    excludedVehicleId = editingVehicleId // loại id của phương tiện đang sửa khỏi kết quả kiểm tra
                )
            }

            if (plateNumberExists) { // xử lý khi một phương tiện khác đã sử dụng biển số
                onResult(false) // thông báo cho giao diện rằng không thể lưu
            } else { // xử lý khi biển số hợp lệ và chưa thuộc phương tiện khác
                repository.saveVehicle( // lưu phương tiện mới hoặc cập nhật phương tiện hiện tại
                    vehicle = vehicle // truyền đầy đủ dữ liệu phương tiện vào Room
                )

                onResult(true) // thông báo cho giao diện rằng đã lưu thành công
            }
        }
    }
    fun saveVehicle( // thêm mới hoặc cập nhật một phương tiện
        vehicle: BusinessVehicleEntity // nhận dữ liệu phương tiện cần lưu
    ) {
        viewModelScope.launch { // chạy thao tác lưu ngoài luồng giao diện
            repository.saveVehicle( // yêu cầu Repository lưu dữ liệu
                vehicle = vehicle // truyền phương tiện cần lưu vào Room
            )
        }
    }

    fun deleteVehicle( // xóa một phương tiện khỏi database
        vehicle: BusinessVehicleEntity // nhận phương tiện cần xóa
    ) {
        viewModelScope.launch { // chạy thao tác xóa ngoài luồng giao diện
            repository.deleteVehicle( // yêu cầu Repository xóa dữ liệu
                vehicle = vehicle // truyền đúng phương tiện cần xóa
            )
        }
    }
}