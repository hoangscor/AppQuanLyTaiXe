package com.example.busdieuhanhdongnai.feature.business.vehicle // đặt màn hình trong phân hệ phương tiện doanh nghiệp

import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.layout.Arrangement // sắp xếp khoảng cách các thành phần
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh cần áp dụng vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ dùng cạnh vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng camera
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.getValue // đọc giá trị state bằng từ khóa by
import androidx.compose.runtime.mutableStateOf // tạo state lưu nội dung tìm kiếm
import androidx.compose.runtime.saveable.rememberSaveable // giữ nội dung tìm kiếm khi xoay màn hình
import androidx.compose.runtime.setValue // cập nhật giá trị state bằng từ khóa by
import androidx.compose.material3.OutlinedTextField // tạo ô tìm kiếm phương tiện
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ
import androidx.compose.foundation.layout.Row // xếp hai thẻ thống kê theo chiều ngang
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn của màn hình
import androidx.compose.foundation.verticalScroll // cho phép nội dung màn hình cuộn dọc
import androidx.compose.material3.Card // tạo thẻ thống kê phương tiện
import androidx.compose.material3.CardDefaults // thiết lập màu nền cho thẻ
import androidx.compose.ui.text.style.TextAlign // căn giữa nội dung chữ
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc các thẻ thống kê

private val VehicleBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val VehicleBackground = Color(0xFFF6F8FC) // màu nền xám trắng
private data class BusinessVehicleItem( // tạo kiểu dữ liệu đại diện cho một phương tiện
    val plateNumber: String, // lưu biển số của phương tiện
    val vehicleType: String, // lưu loại hoặc dòng xe
    val driverName: String, // lưu tên tài xế đang phụ trách
    val maintenanceDate: String, // lưu ngày bảo trì gần nhất
    val status: String // lưu trạng thái hoạt động của phương tiện
) // kết thúc kiểu dữ liệu phương tiện

private val sampleBusinessVehicles = listOf( // tạo danh sách phương tiện mẫu để kiểm tra giao diện
    BusinessVehicleItem( // tạo phương tiện mẫu thứ nhất
        plateNumber = "51B-123.45", // đặt biển số xe thứ nhất
        vehicleType = "Thaco Town 60 chỗ", // đặt loại xe thứ nhất
        driverName = "Nguyễn Văn An", // đặt tài xế phụ trách
        maintenanceDate = "2026-07-25", // đặt ngày bảo trì gần nhất
        status = "Hoạt động" // đặt trạng thái xe
    ), // kết thúc phương tiện thứ nhất
    BusinessVehicleItem( // tạo phương tiện mẫu thứ hai
        plateNumber = "51B-234.56", // đặt biển số xe thứ hai
        vehicleType = "Samco 40 chỗ", // đặt loại xe thứ hai
        driverName = "Trần Minh Tuấn", // đặt tài xế phụ trách
        maintenanceDate = "2026-07-20", // đặt ngày bảo trì gần nhất
        status = "Hoạt động" // đặt trạng thái xe
    ), // kết thúc phương tiện thứ hai
    BusinessVehicleItem( // tạo phương tiện mẫu thứ ba
        plateNumber = "60B-345.67", // đặt biển số xe thứ ba
        vehicleType = "Thaco Garden 79 chỗ", // đặt loại xe thứ ba
        driverName = "Lê Quốc Hùng", // đặt tài xế phụ trách
        maintenanceDate = "2026-08-01", // đặt ngày bảo trì gần nhất
        status = "Bảo trì" // đặt trạng thái xe
    ), // kết thúc phương tiện thứ ba
    BusinessVehicleItem( // tạo phương tiện mẫu thứ tư
        plateNumber = "60B-456.78", // đặt biển số xe thứ tư
        vehicleType = "Hyundai County 29 chỗ", // đặt loại xe thứ tư
        driverName = "Phạm Hoàng Nam", // đặt tài xế phụ trách
        maintenanceDate = "2026-07-18", // đặt ngày bảo trì gần nhất
        status = "Tạm dừng" // đặt trạng thái xe
    ) // kết thúc phương tiện thứ tư
) // kết thúc danh sách phương tiện mẫu
@Composable
fun VehicleManagementScreen(
    onBack: () -> Unit = {} // nhận hành động quay về trang chủ doanh nghiệp
) {
    var vehicleSearchQuery by rememberSaveable { // tạo state lưu nội dung doanh nghiệp nhập
        mutableStateOf("") // mặc định ô tìm kiếm để trống
    }
    val cleanedVehicleSearchQuery = vehicleSearchQuery.trim() // loại bỏ khoảng trắng thừa trong từ khóa

    val filteredBusinessVehicles = sampleBusinessVehicles.filter { vehicle -> // lọc danh sách theo từ khóa
        cleanedVehicleSearchQuery.isBlank() || // hiển thị toàn bộ xe khi ô tìm kiếm đang trống
                vehicle.plateNumber.contains( // kiểm tra từ khóa có nằm trong biển số hay không
                    other = cleanedVehicleSearchQuery, // truyền từ khóa cần kiểm tra
                    ignoreCase = true // không phân biệt chữ hoa và chữ thường
                ) || // tiếp tục kiểm tra loại xe khi biển số không khớp
                vehicle.vehicleType.contains( // kiểm tra từ khóa có nằm trong loại xe hay không
                    other = cleanedVehicleSearchQuery, // truyền từ khóa cần kiểm tra
                    ignoreCase = true // không phân biệt chữ hoa và chữ thường
                ) // kết thúc kiểm tra loại xe
    } // kết thúc quá trình lọc danh sách
    Column(
        modifier = Modifier
            .fillMaxSize() // cho màn hình phủ toàn bộ thiết bị
            .background(VehicleBackground) // đặt màu nền chung
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // cho phần đầu phủ toàn bộ chiều ngang
                .background(VehicleBlue) // đặt nền xanh cho phần đầu
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top // tránh camera và thanh trạng thái phía trên
                    )
                )
                .padding(
                    horizontal = 16.dp, // tạo lề trái và phải
                    vertical = 14.dp // tạo khoảng cách trên và dưới
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng tiêu đề phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {
                TextButton(
                    onClick = onBack // gọi hành động quay về
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // dùng chữ trắng
                        fontSize = 24.sp // đặt kích thước biểu tượng
                    )
                }

                Column {
                    Text(
                        text = "QUẢN LÝ PHƯƠNG TIỆN", // tiêu đề màn hình
                        color = Color.White, // dùng chữ trắng trên nền xanh
                        fontSize = 20.sp, // đặt kích thước tiêu đề
                        fontWeight = FontWeight.Bold // in đậm tiêu đề
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp) // cách tiêu đề với mô tả
                    )

                    Text(
                        text = "Danh sách xe và trạng thái vận hành", // mô tả chức năng
                        color = Color.White.copy(alpha = 0.9f), // dùng màu trắng nhẹ
                        fontSize = 13.sp // đặt kích thước mô tả
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize() // cho nội dung sử dụng toàn bộ không gian còn lại
                .verticalScroll(rememberScrollState()) // cho phép cuộn khi danh sách phương tiện dài
                .padding(20.dp), // tạo khoảng cách xung quanh nội dung
            verticalArrangement = Arrangement.Top // đặt nội dung từ phía trên
        ) {
            Text(
                text = "TỔNG QUAN PHƯƠNG TIỆN", // tiêu đề khu vực tổng quan
                color = VehicleBlue, // dùng màu xanh chính
                fontSize = 17.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách đến nội dung tiếp theo
            )

            Column( // chứa hai hàng thẻ thống kê phương tiện
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang màn hình
                verticalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai hàng
            ) {
                Row( // hàng thứ nhất gồm Tổng xe và Hoạt động
                    modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
                ) {
                    VehicleSummaryCard( // hiển thị tổng số phương tiện
                        icon = "🚌", // biểu tượng xe buýt
                        value = 20, // dữ liệu mẫu tổng số xe
                        label = "Tổng xe", // tên chỉ số
                        valueColor = VehicleBlue, // dùng màu xanh chính
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )

                    VehicleSummaryCard( // hiển thị số xe đang hoạt động
                        icon = "✅", // biểu tượng hoạt động
                        value = 16, // dữ liệu mẫu xe hoạt động
                        label = "Hoạt động", // tên trạng thái
                        valueColor = Color(0xFF1A9B54), // dùng màu xanh lá
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )
                }

                Row( // hàng thứ hai gồm Bảo trì và Tạm dừng
                    modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
                ) {
                    VehicleSummaryCard( // hiển thị số xe đang bảo trì
                        icon = "🔧", // biểu tượng bảo trì
                        value = 2, // dữ liệu mẫu xe bảo trì
                        label = "Bảo trì", // tên trạng thái
                        valueColor = Color(0xFFFF9800), // dùng màu cam
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )

                    VehicleSummaryCard( // hiển thị số xe đang tạm dừng
                        icon = "⏸️", // biểu tượng tạm dừng
                        value = 2, // dữ liệu mẫu xe tạm dừng
                        label = "Tạm dừng", // tên trạng thái
                        valueColor = Color(0xFFE53935), // dùng màu đỏ
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )
                }
                Spacer( // tạo khoảng cách sau khu vực tổng quan phương tiện
                    modifier = Modifier.height(20.dp) // đặt khoảng cách dọc
                )

                Text( // hiển thị tiêu đề khu vực danh sách xe
                    text = "DANH SÁCH PHƯƠNG TIỆN", // tên khu vực quản lý danh sách
                    color = VehicleBlue, // sử dụng màu xanh chính
                    fontSize = 17.sp, // đặt kích thước tiêu đề
                    fontWeight = FontWeight.Bold // in đậm tiêu đề
                )

                Spacer( // tạo khoảng cách giữa tiêu đề và ô tìm kiếm
                    modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                )

                OutlinedTextField( // tạo ô nhập từ khóa tìm phương tiện
                    value = vehicleSearchQuery, // hiển thị từ khóa hiện tại
                    onValueChange = { newValue -> // nhận nội dung mới khi người dùng nhập
                        vehicleSearchQuery = newValue // cập nhật từ khóa tìm kiếm
                    },
                    modifier = Modifier.fillMaxWidth(), // cho ô tìm kiếm phủ toàn bộ chiều ngang
                    label = { // tạo nhãn hướng dẫn trong ô
                        Text(
                            text = "Tìm biển số / loại xe" // nội dung hướng dẫn tìm kiếm
                        )
                    },
                    singleLine = true, // chỉ cho phép nhập trên một dòng
                    shape = RoundedCornerShape(14.dp) // bo tròn góc ô tìm kiếm
                )
                Spacer( // tạo khoảng cách giữa ô tìm kiếm và số lượng kết quả
                    modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
                )

                Text( // hiển thị số phương tiện phù hợp với từ khóa
                    text = "TÌM THẤY ${filteredBusinessVehicles.size} PHƯƠNG TIỆN", // ghép số lượng kết quả
                    color = Color.Gray, // dùng màu xám cho thông tin phụ
                    fontSize = 13.sp, // đặt kích thước chữ kết quả
                    fontWeight = FontWeight.Medium // dùng độ đậm vừa
                )

                Spacer( // tạo khoảng cách trước danh sách xe
                    modifier = Modifier.height(10.dp) // đặt chiều cao khoảng cách
                )

                if (filteredBusinessVehicles.isEmpty()) { // kiểm tra không có phương tiện phù hợp
                    Card( // tạo thẻ thông báo không tìm thấy dữ liệu
                        modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
                        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
                        colors = CardDefaults.cardColors( // thiết lập màu nền thẻ
                            containerColor = Color.White // dùng nền trắng
                        ) // kết thúc thiết lập màu
                    ) {
                        Text( // hiển thị thông báo không có kết quả
                            text = "Không tìm thấy phương tiện phù hợp.", // nội dung thông báo
                            color = Color.Gray, // dùng màu xám
                            fontSize = 14.sp, // đặt kích thước chữ
                            modifier = Modifier.padding(20.dp) // tạo khoảng cách bên trong thẻ
                        )
                    }
                } else { // xử lý khi có phương tiện phù hợp
                    filteredBusinessVehicles.forEach { vehicle -> // duyệt từng phương tiện đã lọc
                        BusinessVehicleListCard( // hiển thị thẻ thông tin phương tiện
                            vehicle = vehicle // truyền dữ liệu xe vào thẻ
                        )

                        Spacer( // tạo khoảng cách giữa hai phương tiện
                            modifier = Modifier.height(10.dp) // đặt chiều cao khoảng cách
                        )
                    }
                }
            }
        }
    }
}
@Composable
private fun VehicleSummaryCard( // tạo một thẻ thống kê phương tiện dùng chung
    icon: String, // biểu tượng của trạng thái phương tiện
    value: Int, // số lượng phương tiện
    label: String, // tên trạng thái phương tiện
    valueColor: Color, // màu của số liệu
    modifier: Modifier = Modifier // cho phép màn cha điều chỉnh kích thước thẻ
) {
    Card( // tạo nền trắng dạng thẻ
        modifier = modifier
            .height(126.dp), // đặt chiều cao đồng đều cho tất cả thẻ
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors(
            containerColor = Color.White // dùng nền trắng cho thẻ
        )
    ) {
        Column( // xếp biểu tượng, số lượng và tên trạng thái theo chiều dọc
            modifier = Modifier
                .fillMaxSize() // cho nội dung phủ toàn bộ thẻ
                .padding(12.dp), // tạo khoảng cách bên trong thẻ
            horizontalAlignment = Alignment.CenterHorizontally, // căn giữa theo chiều ngang
            verticalArrangement = Arrangement.Center // căn giữa theo chiều dọc
        ) {
            Text( // hiển thị biểu tượng trạng thái
                text = icon, // nhận biểu tượng được truyền vào
                fontSize = 24.sp // đặt kích thước biểu tượng
            )

            Spacer(
                modifier = Modifier.height(4.dp) // tạo khoảng cách dưới biểu tượng
            )

            Text( // hiển thị số lượng phương tiện
                text = value.toString(), // chuyển số lượng thành chữ
                color = valueColor, // dùng màu tương ứng trạng thái
                fontSize = 25.sp, // làm số liệu nổi bật
                fontWeight = FontWeight.Bold, // in đậm số liệu
                textAlign = TextAlign.Center // căn giữa số liệu
            )

            Spacer(
                modifier = Modifier.height(3.dp) // tạo khoảng cách trước tên trạng thái
            )

            Text( // hiển thị tên trạng thái
                text = label, // nhận tên trạng thái được truyền vào
                color = Color.DarkGray, // dùng màu xám đậm dễ đọc
                fontSize = 13.sp, // đặt kích thước chữ mô tả
                textAlign = TextAlign.Center // căn giữa tên trạng thái
            )
        }
    }
}
@Composable
private fun BusinessVehicleListCard( // tạo thẻ hiển thị thông tin một phương tiện
    vehicle: BusinessVehicleItem // nhận dữ liệu phương tiện cần hiển thị
) {
    val vehicleStatusColor = when (vehicle.status) { // chọn màu dựa trên trạng thái phương tiện
        "Hoạt động" -> Color(0xFF1A9B54) // dùng màu xanh cho xe đang hoạt động
        "Bảo trì" -> Color(0xFFFF9800) // dùng màu cam cho xe đang bảo trì
        else -> Color(0xFFE53935) // dùng màu đỏ cho xe đang tạm dừng
    } // kết thúc chọn màu trạng thái

    Card( // tạo nền thẻ thông tin phương tiện
        modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo tròn góc của thẻ
        colors = CardDefaults.cardColors( // thiết lập màu nền cho thẻ
            containerColor = Color.White // sử dụng nền trắng
        ) // kết thúc thiết lập màu
    ) {
        Column( // xếp thông tin phương tiện theo chiều dọc
            modifier = Modifier
                .fillMaxWidth() // cho nội dung phủ toàn bộ chiều ngang
                .padding(16.dp) // tạo khoảng cách bên trong thẻ
        ) {
            Row( // xếp biển số và trạng thái trên cùng một hàng
                modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween, // đẩy hai nội dung sang hai phía
                verticalAlignment = Alignment.CenterVertically // căn giữa nội dung theo chiều dọc
            ) {
                Text( // hiển thị biển số phương tiện
                    text = vehicle.plateNumber, // lấy biển số từ dữ liệu xe
                    color = VehicleBlue, // sử dụng màu xanh chính
                    fontSize = 17.sp, // đặt kích thước biển số
                    fontWeight = FontWeight.Bold // in đậm biển số
                )

                Text( // hiển thị trạng thái phương tiện
                    text = vehicle.status, // lấy trạng thái từ dữ liệu xe
                    color = vehicleStatusColor, // sử dụng màu tương ứng trạng thái
                    fontSize = 13.sp, // đặt kích thước chữ trạng thái
                    fontWeight = FontWeight.Bold // in đậm trạng thái
                )
            }

            Spacer( // tạo khoảng cách sau hàng biển số
                modifier = Modifier.height(8.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị loại phương tiện
                text = "Loại xe: ${vehicle.vehicleType}", // ghép nhãn với loại xe
                color = Color.DarkGray, // sử dụng màu xám đậm
                fontSize = 14.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Medium // dùng độ đậm vừa
            )

            Spacer( // tạo khoảng cách đến thông tin tài xế
                modifier = Modifier.height(5.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tài xế phụ trách
                text = "Tài xế: ${vehicle.driverName}", // ghép nhãn với tên tài xế
                color = Color.Gray, // sử dụng màu xám
                fontSize = 13.sp // đặt kích thước chữ
            )

            Spacer( // tạo khoảng cách đến ngày bảo trì
                modifier = Modifier.height(5.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị ngày bảo trì gần nhất
                text = "Bảo trì gần nhất: ${vehicle.maintenanceDate}", // ghép nhãn với ngày bảo trì
                color = Color.Gray, // sử dụng màu xám
                fontSize = 13.sp // đặt kích thước chữ
            )
        }
    }
}
