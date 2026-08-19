package com.example.busdieuhanhdongnai.feature.business.assignment // THÊM: package màn biểu mẫu phân công

import androidx.compose.foundation.background // THÊM: tạo nền màn hình
import androidx.compose.foundation.layout.Column // THÊM: bố trí nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // THÊM: bố trí nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // THÊM: tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // THÊM: lấy vùng an toàn thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // THÊM: chọn cạnh vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // THÊM: phủ toàn màn hình
import androidx.compose.foundation.layout.fillMaxWidth // THÊM: phủ toàn chiều ngang
import androidx.compose.foundation.layout.height // THÊM: đặt chiều cao
import androidx.compose.foundation.layout.only // THÊM: chỉ áp dụng cạnh cần thiết
import androidx.compose.foundation.layout.padding // THÊM: tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // THÊM: tránh camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // THÊM: áp dụng vùng an toàn
import androidx.compose.foundation.rememberScrollState // THÊM: ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // THÊM: tạo góc bo tròn
import androidx.compose.foundation.verticalScroll // THÊM: cho phép cuộn dọc
import androidx.compose.material3.Button // THÊM: tạo nút chính
import androidx.compose.material3.ButtonDefaults // THÊM: thiết lập màu nút
import androidx.compose.material3.Card // THÊM: tạo thẻ chứa biểu mẫu
import androidx.compose.material3.CardDefaults // THÊM: thiết lập màu thẻ
import androidx.compose.material3.OutlinedTextField // THÊM: tạo ô nhập liệu
import androidx.compose.material3.Text // THÊM: hiển thị chữ
import androidx.compose.material3.TextButton // THÊM: tạo nút quay lại
import androidx.compose.runtime.Composable // THÊM: đánh dấu hàm Compose
import androidx.compose.runtime.getValue // THÊM: đọc state
import androidx.compose.runtime.mutableStateOf // THÊM: tạo state
import androidx.compose.runtime.saveable.rememberSaveable // THÊM: giữ state khi màn hình tạo lại
import androidx.compose.runtime.setValue // THÊM: cập nhật state
import androidx.compose.ui.Alignment // THÊM: căn chỉnh thành phần
import androidx.compose.ui.Modifier // THÊM: cấu hình bố cục
import androidx.compose.ui.graphics.Color // THÊM: sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // THÊM: điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // THÊM: đơn vị kích thước
import androidx.compose.ui.unit.sp // THÊM: đơn vị kích thước chữ

private val AssignmentFormBlue = Color(0xFF0066CC) // THÊM: màu xanh chính
private val AssignmentFormBackground = Color(0xFFF6F8FC) // THÊM: màu nền màn hình

@Composable // THÊM: đánh dấu màn hình Compose
fun AssignmentFormScreen( // THÊM: tạo màn biểu mẫu phân công dùng chung
    routeName: String = "", // THÊM: nhận tên tuyến khi mở chế độ chỉnh sửa
    onBack: () -> Unit = {}, // THÊM: nhận thao tác quay lại
    onSave: () -> Unit = {} // THÊM: để sẵn callback lưu cho giai đoạn chức năng
) { // THÊM: bắt đầu màn hình

    val isEditMode = routeName.isNotBlank() // THÊM: xác định đang tạo mới hay chỉnh sửa

    var selectedRoute by rememberSaveable { // THÊM: giữ tuyến đang nhập
        mutableStateOf(routeName) // THÊM: dùng tuyến truyền vào làm giá trị ban đầu
    } // THÊM: kết thúc state tuyến

    var scheduledTime by rememberSaveable { // THÊM: giữ giờ chạy
        mutableStateOf(if (isEditMode) "07:00 - 08:00" else "") // THÊM: dữ liệu mẫu khi chỉnh sửa
    } // THÊM: kết thúc state giờ chạy

    var driverName by rememberSaveable { // THÊM: giữ tên tài xế
        mutableStateOf(if (isEditMode) "Nguyễn Văn An" else "") // THÊM: dữ liệu mẫu khi chỉnh sửa
    } // THÊM: kết thúc state tài xế

    var vehiclePlate by rememberSaveable { // THÊM: giữ biển số phương tiện
        mutableStateOf(if (isEditMode) "51B-123.45" else "") // THÊM: dữ liệu mẫu khi chỉnh sửa
    } // THÊM: kết thúc state phương tiện

    Column( // THÊM: tạo bố cục toàn màn hình
        modifier = Modifier // THÊM: bắt đầu cấu hình màn hình
            .fillMaxSize() // THÊM: phủ toàn màn hình
            .background(AssignmentFormBackground) // THÊM: đặt màu nền
            .verticalScroll(rememberScrollState()) // THÊM: cho phép cuộn dọc
    ) { // THÊM: bắt đầu nội dung

        Column( // THÊM: tạo phần tiêu đề màu xanh
            modifier = Modifier // THÊM: cấu hình tiêu đề
                .fillMaxWidth() // THÊM: phủ toàn chiều ngang
                .background(AssignmentFormBlue) // THÊM: dùng nền xanh
                .windowInsetsPadding( // THÊM: tránh vùng camera
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top) // THÊM: chỉ lấy vùng an toàn phía trên
                ) // THÊM: kết thúc vùng an toàn
                .padding(horizontal = 20.dp, vertical = 18.dp) // THÊM: tạo khoảng cách trong tiêu đề
        ) { // THÊM: bắt đầu tiêu đề

            Row( // THÊM: đặt nút quay lại và tiêu đề cùng hàng
                modifier = Modifier.fillMaxWidth(), // THÊM: phủ toàn chiều ngang
                verticalAlignment = Alignment.CenterVertically // THÊM: căn giữa theo chiều dọc
            ) { // THÊM: bắt đầu hàng

                TextButton( // THÊM: tạo nút quay lại
                    onClick = onBack // THÊM: gọi callback quay lại
                ) { // THÊM: bắt đầu nội dung nút
                    Text( // THÊM: hiển thị mũi tên
                        text = "←", // THÊM: biểu tượng quay lại
                        color = Color.White, // THÊM: dùng màu trắng
                        fontSize = 22.sp // THÊM: đặt kích thước
                    ) // THÊM: kết thúc chữ
                } // THÊM: kết thúc nút

                Column( // THÊM: chứa tiêu đề và mô tả
                    modifier = Modifier.padding(start = 4.dp) // THÊM: cách nút quay lại
                ) { // THÊM: bắt đầu nội dung tiêu đề

                    Text( // THÊM: hiển thị tên màn hình
                        text = if (isEditMode) "CHI TIẾT PHÂN CÔNG" else "TẠO PHÂN CÔNG", // THÊM: đổi tiêu đề theo chế độ
                        color = Color.White, // THÊM: chữ màu trắng
                        fontSize = 20.sp, // THÊM: kích thước tiêu đề
                        fontWeight = FontWeight.Bold // THÊM: làm chữ đậm
                    ) // THÊM: kết thúc tiêu đề

                    Spacer( // THÊM: tạo khoảng cách
                        modifier = Modifier.height(4.dp) // THÊM: khoảng cách dọc
                    ) // THÊM: kết thúc khoảng cách

                    Text( // THÊM: hiển thị mô tả
                        text = if (isEditMode) "Xem và điều chỉnh lịch chạy" else "Bố trí tài xế và phương tiện cho chuyến", // THÊM: mô tả theo chế độ
                        color = Color.White, // THÊM: chữ trắng
                        fontSize = 13.sp // THÊM: kích thước mô tả
                    ) // THÊM: kết thúc mô tả
                } // THÊM: kết thúc cột tiêu đề
            } // THÊM: kết thúc hàng
        } // THÊM: kết thúc phần tiêu đề

        Column( // THÊM: tạo khu vực nội dung
            modifier = Modifier // THÊM: cấu hình nội dung
                .fillMaxWidth() // THÊM: phủ chiều ngang
                .padding(20.dp) // THÊM: tạo khoảng cách ngoài
        ) { // THÊM: bắt đầu nội dung

            Text( // THÊM: hiển thị tiêu đề biểu mẫu
                text = "THÔNG TIN LỊCH CHẠY", // THÊM: đặt tên khu vực
                color = AssignmentFormBlue, // THÊM: dùng màu xanh
                fontSize = 18.sp, // THÊM: kích thước chữ
                fontWeight = FontWeight.Bold // THÊM: làm chữ nổi bật
            ) // THÊM: kết thúc tiêu đề

            Spacer( // THÊM: tạo khoảng cách
                modifier = Modifier.height(12.dp) // THÊM: khoảng cách dọc
            ) // THÊM: kết thúc khoảng cách

            Card( // THÊM: tạo thẻ chứa biểu mẫu
                modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                shape = RoundedCornerShape(16.dp), // THÊM: bo tròn góc
                colors = CardDefaults.cardColors( // THÊM: cấu hình màu thẻ
                    containerColor = Color.White // THÊM: nền trắng
                ) // THÊM: kết thúc màu thẻ
            ) { // THÊM: bắt đầu thẻ

                Column( // THÊM: xếp các ô nhập theo chiều dọc
                    modifier = Modifier.padding(16.dp) // THÊM: tạo khoảng cách trong thẻ
                ) { // THÊM: bắt đầu biểu mẫu

                    OutlinedTextField( // THÊM: tạo ô nhập tuyến
                        value = selectedRoute, // THÊM: hiển thị tuyến hiện tại
                        onValueChange = { selectedRoute = it }, // THÊM: cập nhật tuyến khi nhập
                        label = { Text("Tuyến / lịch chạy") }, // THÊM: đặt nhãn ô nhập
                        modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                        singleLine = true // THÊM: chỉ dùng một dòng
                    ) // THÊM: kết thúc ô tuyến

                    Spacer(modifier = Modifier.height(12.dp)) // THÊM: cách ô tiếp theo

                    OutlinedTextField( // THÊM: tạo ô nhập giờ chạy
                        value = scheduledTime, // THÊM: hiển thị giờ chạy
                        onValueChange = { scheduledTime = it }, // THÊM: cập nhật giờ chạy
                        label = { Text("Giờ chạy") }, // THÊM: đặt nhãn
                        placeholder = { Text("Ví dụ: 07:00 - 08:00") }, // THÊM: gợi ý định dạng
                        modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                        singleLine = true // THÊM: chỉ dùng một dòng
                    ) // THÊM: kết thúc ô giờ

                    Spacer(modifier = Modifier.height(12.dp)) // THÊM: cách ô tiếp theo

                    OutlinedTextField( // THÊM: tạo ô chọn tài xế tạm thời
                        value = driverName, // THÊM: hiển thị tài xế
                        onValueChange = { driverName = it }, // THÊM: cập nhật tên tài xế
                        label = { Text("Tài xế") }, // THÊM: đặt nhãn
                        placeholder = { Text("Chọn tài xế") }, // THÊM: hiển thị gợi ý
                        modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                        singleLine = true // THÊM: chỉ dùng một dòng
                    ) // THÊM: kết thúc ô tài xế

                    Spacer(modifier = Modifier.height(12.dp)) // THÊM: cách ô tiếp theo

                    OutlinedTextField( // THÊM: tạo ô phương tiện tạm thời
                        value = vehiclePlate, // THÊM: hiển thị biển số
                        onValueChange = { vehiclePlate = it }, // THÊM: cập nhật biển số
                        label = { Text("Phương tiện") }, // THÊM: đặt nhãn
                        placeholder = { Text("Chọn phương tiện") }, // THÊM: hiển thị gợi ý
                        modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                        singleLine = true // THÊM: chỉ dùng một dòng
                    ) // THÊM: kết thúc ô phương tiện
                } // THÊM: kết thúc biểu mẫu
            } // THÊM: kết thúc thẻ

            Spacer( // THÊM: tạo khoảng cách trước nút
                modifier = Modifier.height(18.dp) // THÊM: khoảng cách dọc
            ) // THÊM: kết thúc khoảng cách

            Button( // THÊM: tạo nút lưu
                onClick = onSave, // THÊM: để sẵn callback lưu cho giai đoạn chức năng
                modifier = Modifier.fillMaxWidth(), // THÊM: phủ chiều ngang
                colors = ButtonDefaults.buttonColors( // THÊM: thiết lập màu nút
                    containerColor = AssignmentFormBlue // THÊM: dùng màu xanh chính
                ), // THÊM: kết thúc màu nút
                shape = RoundedCornerShape(14.dp) // THÊM: bo tròn góc
            ) { // THÊM: bắt đầu nút

                Text( // THÊM: hiển thị tên nút
                    text = if (isEditMode) "LƯU THAY ĐỔI" else "TẠO PHÂN CÔNG", // THÊM: đổi chữ theo chế độ
                    color = Color.White, // THÊM: chữ trắng
                    fontWeight = FontWeight.Bold // THÊM: làm chữ đậm
                ) // THÊM: kết thúc chữ
            } // THÊM: kết thúc nút
        } // THÊM: kết thúc nội dung
    } // THÊM: kết thúc màn hình
} // THÊM: kết thúc hàm