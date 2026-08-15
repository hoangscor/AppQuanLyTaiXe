package com.example.busdieuhanhdongnai.feature.business.driver

import androidx.compose.foundation.background // tạo màu nền cho màn hình
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh áp dụng vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ áp dụng cạnh vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách trong thành phần
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn tránh camera và status bar
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng không an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn màn hình
import androidx.compose.foundation.shape.RoundedCornerShape // tạo góc bo tròn
import androidx.compose.foundation.verticalScroll // cho phép cuộn màn hình theo chiều dọc
import androidx.compose.material3.Button // tạo nút thao tác chính
import androidx.compose.material3.ButtonDefaults // thiết lập màu cho nút
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập nền của thẻ
import androidx.compose.material3.OutlinedTextField // tạo ô tìm kiếm
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ
import androidx.compose.runtime.Composable // đánh dấu hàm Compose
import androidx.compose.runtime.getValue // đọc giá trị state
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi màn hình được tái tạo
import androidx.compose.runtime.setValue // cập nhật giá trị state
import androidx.compose.ui.Alignment // căn chỉnh các thành phần giao diện
import androidx.compose.ui.Modifier // điều chỉnh bố cục thành phần
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm của chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val DriverBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val DriverBackground = Color(0xFFF6F8FC) // màu nền chung của màn hình
private val DriverGreen = Color(0xFF1A9B54) // màu trạng thái tài xế đang hoạt động
private val DriverOrange = Color(0xFFFF9800) // màu trạng thái tài xế đang nghỉ hoặc cần chú ý

private data class BusinessDriverUiModel( // tạo dữ liệu tạm phục vụ giao diện
    val name: String, // tên tài xế
    val phoneNumber: String, // số điện thoại tài xế
    val licenseClass: String, // hạng giấy phép lái xe
    val assignedVehicle: String, // phương tiện đang phụ trách
    val status: String // trạng thái hiện tại
) // kết thúc dữ liệu tài xế tạm

private val sampleBusinessDrivers = listOf( // tạo danh sách tài xế mẫu để dựng giao diện
    BusinessDriverUiModel( // tài xế mẫu thứ nhất
        name = "Nguyễn Văn An", // tên tài xế
        phoneNumber = "0901 234 567", // số điện thoại mẫu
        licenseClass = "Hạng E", // hạng bằng lái
        assignedVehicle = "51B-123.45", // xe đang phụ trách
        status = "Hoạt động" // trạng thái tài xế
    ),
    BusinessDriverUiModel( // tài xế mẫu thứ hai
        name = "Trần Minh Tuấn", // tên tài xế
        phoneNumber = "0902 345 678", // số điện thoại mẫu
        licenseClass = "Hạng D", // hạng bằng lái
        assignedVehicle = "51B-234.56", // xe đang phụ trách
        status = "Hoạt động" // trạng thái tài xế
    ),
    BusinessDriverUiModel( // tài xế mẫu thứ ba
        name = "Lê Quốc Hùng", // tên tài xế
        phoneNumber = "0903 456 789", // số điện thoại mẫu
        licenseClass = "Hạng E", // hạng bằng lái
        assignedVehicle = "Chưa phân công", // chưa gán xe
        status = "Tạm nghỉ" // trạng thái tài xế
    )
) // kết thúc danh sách dữ liệu mẫu

@Composable
fun DriverManagementScreen( // tạo màn hình quản lý tài xế doanh nghiệp
    onBack: () -> Unit = {} // nhận hành động quay lại màn trước
) {
    var searchQuery by rememberSaveable { // lưu từ khóa tìm kiếm
        mutableStateOf("") // mặc định ô tìm kiếm để trống
    }

    val filteredDrivers = sampleBusinessDrivers.filter { driver -> // lọc danh sách theo từ khóa
        searchQuery.isBlank() || // khi chưa tìm kiếm thì hiển thị toàn bộ
                driver.name.contains(searchQuery, ignoreCase = true) || // tìm theo tên tài xế
                driver.phoneNumber.contains(searchQuery, ignoreCase = true) || // tìm theo số điện thoại
                driver.assignedVehicle.contains(searchQuery, ignoreCase = true) // tìm theo biển số xe
    }

    Column( // tạo bố cục chính của màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(DriverBackground) // đặt nền xám trắng
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {
        Column( // tạo phần header màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(DriverBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top) // tránh camera và status bar
                )
                .padding(horizontal = 20.dp, vertical = 18.dp) // tạo khoảng cách trong header
        ) {
            Row( // xếp nút quay lại và tiêu đề cùng hàng
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {
                TextButton( // tạo nút quay lại
                    onClick = onBack // gọi callback quay lại
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // màu trắng
                        fontSize = 22.sp // kích thước mũi tên
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 4.dp) // cách nút quay lại một khoảng nhỏ
                ) {
                    Text(
                        text = "QUẢN LÝ TÀI XẾ", // tiêu đề màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // in đậm tiêu đề
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp) // khoảng cách giữa tiêu đề và mô tả
                    )

                    Text(
                        text = "Hồ sơ và trạng thái tài xế", // mô tả màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 13.sp // kích thước mô tả
                    )
                }
            }
        }

        Column( // tạo phần nội dung chính
            modifier = Modifier
                .fillMaxWidth() // phủ chiều ngang
                .padding(horizontal = 20.dp, vertical = 20.dp) // tạo khoảng cách quanh nội dung
        ) {
            Text(
                text = "TỔNG QUAN TÀI XẾ", // tiêu đề tổng quan
                color = DriverBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước thẻ tổng quan
            )

            DriverOverviewCard(
                title = "Tổng tài xế", // tên chỉ số
                value = sampleBusinessDrivers.size.toString(), // tổng tài xế mẫu
                valueColor = DriverBlue // màu xanh
            )

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách giữa các thẻ
            )

            DriverOverviewCard(
                title = "Đang hoạt động", // tên chỉ số
                value = sampleBusinessDrivers
                    .count { driver -> driver.status == "Hoạt động" }
                    .toString(), // đếm tài xế hoạt động
                valueColor = DriverGreen // màu xanh lá
            )

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách giữa các thẻ
            )

            DriverOverviewCard(
                title = "Tạm nghỉ", // tên chỉ số
                value = sampleBusinessDrivers
                    .count { driver -> driver.status == "Tạm nghỉ" }
                    .toString(), // đếm tài xế tạm nghỉ
                valueColor = DriverOrange // màu cam
            )

            Spacer(
                modifier = Modifier.height(18.dp) // khoảng cách trước phần danh sách
            )

            Text(
                text = "DANH SÁCH TÀI XẾ", // tiêu đề danh sách
                color = DriverBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước nút thêm
            )

            Button( // tạo nút thêm tài xế
                onClick = {}, // chưa xử lý chức năng ở giai đoạn UI
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = ButtonDefaults.buttonColors(
                    containerColor = DriverBlue // nền xanh
                ),
                shape = RoundedCornerShape(14.dp) // bo góc nút
            ) {
                Text(
                    text = "THÊM TÀI XẾ", // tên nút
                    color = Color.White, // chữ trắng
                    fontWeight = FontWeight.Bold // in đậm
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp) // khoảng cách trước ô tìm kiếm
            )

            OutlinedTextField( // tạo ô tìm tài xế
                value = searchQuery, // lấy từ khóa hiện tại
                onValueChange = { newValue -> // nhận nội dung mới
                    searchQuery = newValue // cập nhật từ khóa tìm kiếm
                },
                modifier = Modifier.fillMaxWidth(), // phủ chiều ngang
                label = {
                    Text("Tìm tên / SĐT / biển số") // gợi ý nội dung tìm kiếm
                },
                singleLine = true // chỉ nhập một dòng
            )

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách trước số lượng kết quả
            )

            Text(
                text = "TÌM THẤY ${filteredDrivers.size} TÀI XẾ", // hiển thị số tài xế tìm được
                color = Color.Gray, // chữ xám
                fontSize = 13.sp, // kích thước nhỏ
                fontWeight = FontWeight.Bold // in đậm nhẹ
            )

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách trước thẻ đầu tiên
            )

            filteredDrivers.forEach { driver -> // hiển thị lần lượt từng tài xế
                BusinessDriverListCard(
                    driver = driver // truyền dữ liệu tài xế vào thẻ
                )

                Spacer(
                    modifier = Modifier.height(12.dp) // khoảng cách giữa hai thẻ
                )
            }
        }
    }
}

@Composable
private fun DriverOverviewCard( // tạo thẻ tổng quan tài xế
    title: String, // tên chỉ số
    value: String, // giá trị chỉ số
    valueColor: Color // màu giá trị
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo tròn góc
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // tạo khoảng cách trong thẻ
        ) {
            Text(
                text = title, // tên chỉ số
                color = Color.DarkGray, // màu xám đậm
                fontSize = 14.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(4.dp) // khoảng cách nhỏ
            )

            Text(
                text = value, // giá trị chỉ số
                color = valueColor, // màu theo loại chỉ số
                fontSize = 26.sp, // số lớn
                fontWeight = FontWeight.Bold // in đậm
            )
        }
    }
}

@Composable
private fun BusinessDriverListCard( // tạo thẻ hiển thị thông tin một tài xế
    driver: BusinessDriverUiModel // nhận dữ liệu tài xế
) {
    val statusColor = when (driver.status) { // xác định màu trạng thái
        "Hoạt động" -> DriverGreen // xanh khi đang hoạt động
        else -> DriverOrange // cam khi không hoạt động
    }

    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo góc thẻ
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // khoảng cách trong thẻ
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), // phủ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa dọc
            ) {
                Text(
                    text = driver.name, // hiển thị tên tài xế
                    color = DriverBlue, // màu xanh chính
                    fontSize = 16.sp, // kích thước tên
                    fontWeight = FontWeight.Bold, // in đậm tên
                    modifier = Modifier.weight(1f) // chiếm phần không gian còn lại
                )

                Text(
                    text = driver.status, // hiển thị trạng thái
                    color = statusColor, // màu theo trạng thái
                    fontSize = 12.sp, // kích thước nhỏ
                    fontWeight = FontWeight.Bold // in đậm
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách sau tên
            )

            Text(
                text = "SĐT: ${driver.phoneNumber}", // hiển thị số điện thoại
                color = Color.DarkGray, // màu xám đậm
                fontSize = 14.sp // kích thước chữ
            )

            Spacer(
                modifier = Modifier.height(6.dp) // khoảng cách nhỏ
            )

            Text(
                text = "GPLX: ${driver.licenseClass}", // hiển thị hạng bằng lái
                color = Color.DarkGray, // màu xám đậm
                fontSize = 14.sp // kích thước chữ
            )

            Spacer(
                modifier = Modifier.height(6.dp) // khoảng cách nhỏ
            )

            Text(
                text = "Phương tiện: ${driver.assignedVehicle}", // hiển thị xe phụ trách
                color = Color.DarkGray, // màu xám đậm
                fontSize = 14.sp // kích thước chữ
            )

            Spacer(
                modifier = Modifier.height(8.dp) // khoảng cách trước thao tác
            )

            TextButton(
                onClick = {}, // chưa xử lý chức năng chỉnh sửa
                modifier = Modifier.fillMaxWidth() // phủ chiều ngang
            ) {
                Text(
                    text = "XEM / CHỈNH SỬA", // tên thao tác
                    color = DriverBlue, // màu xanh
                    fontSize = 13.sp, // kích thước chữ
                    fontWeight = FontWeight.Bold // in đậm
                )
            }
        }
    }
}