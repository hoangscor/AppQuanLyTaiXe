package com.example.busdieuhanhdongnai.feature.business.information

import androidx.compose.foundation.background // tạo màu nền cho màn hình
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ áp dụng cạnh vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // tránh camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng không an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn màn hình
import androidx.compose.material3.Button // tạo nút thao tác
import androidx.compose.material3.ButtonDefaults // thiết lập màu nút
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu nền thẻ
import androidx.compose.material3.HorizontalDivider // tạo đường phân cách
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // khai báo màu
import androidx.compose.ui.text.font.FontWeight // thiết lập độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val InformationBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val InformationBackground = Color(0xFFF6F8FC) // màu nền chung
private val InformationGreen = Color(0xFF1A9B54) // màu trạng thái hoạt động
private val InformationLightBlue = Color(0xFFEAF3FF) // màu nền xanh nhạt

@Composable
fun BusinessInformationScreen( // tạo màn hình thông tin doanh nghiệp
    onBack: () -> Unit = {} // nhận thao tác quay về màn trước
) {
    Column( // tạo bố cục chính của toàn màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(InformationBackground) // đặt màu nền chung
            .verticalScroll(rememberScrollState()) // cho phép cuộn khi nội dung dài
    ) {

        Column( // tạo khu vực tiêu đề màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(InformationBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top // tránh vùng camera và status bar phía trên
                    )
                )
                .padding(
                    horizontal = 18.dp, // tạo khoảng cách hai bên
                    vertical = 18.dp // tạo khoảng cách trên dưới
                )
        ) {

            Row( // tạo hàng chứa nút quay lại và tiêu đề
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {

                TextButton(
                    onClick = onBack // quay lại màn hình trước
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // chữ màu trắng
                        fontSize = 22.sp // kích thước biểu tượng
                    )
                }

                Column {
                    Text(
                        text = "THÔNG TIN DOANH NGHIỆP", // tiêu đề màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // chữ đậm
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp) // tạo khoảng cách nhỏ
                    )

                    Text(
                        text = "Hồ sơ và thông tin đơn vị vận tải", // mô tả màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 13.sp // kích thước mô tả
                    )
                }
            }
        }

        Column( // tạo khu vực nội dung chính
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(18.dp) // tạo khoảng cách xung quanh
        ) {

            Text(
                text = "HỒ SƠ DOANH NGHIỆP", // tiêu đề khu vực
                color = InformationBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // tạo thẻ thông tin tổng quan doanh nghiệp
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo tròn góc
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn bộ chiều ngang
                        .padding(18.dp) // tạo khoảng cách bên trong
                ) {

                    Text(
                        text = "🏢", // biểu tượng doanh nghiệp
                        fontSize = 34.sp // kích thước biểu tượng
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp) // tạo khoảng cách
                    )

                    Text(
                        text = "CÔNG TY TNHH VẬN TẢI PHƯƠNG TRANG", // tên doanh nghiệp mẫu
                        color = InformationBlue, // chữ xanh
                        fontSize = 17.sp, // kích thước chữ
                        fontWeight = FontWeight.Bold // chữ đậm
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp) // tạo khoảng cách
                    )

                    Text(
                        text = "Doanh nghiệp vận tải hành khách", // loại hình doanh nghiệp
                        color = Color.DarkGray, // chữ xám đậm
                        fontSize = 13.sp // kích thước chữ
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp) // tạo khoảng cách
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically // căn giữa nội dung
                    ) {
                        Text(
                            text = "●", // chấm trạng thái
                            color = InformationGreen, // màu xanh hoạt động
                            fontSize = 14.sp // kích thước chấm
                        )

                        Spacer(
                            modifier = Modifier.padding(horizontal = 3.dp) // tạo khoảng cách ngang
                        )

                        Text(
                            text = "Đang hoạt động", // trạng thái doanh nghiệp
                            color = InformationGreen, // chữ xanh
                            fontSize = 13.sp, // kích thước chữ
                            fontWeight = FontWeight.Bold // chữ đậm
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng cách
            )

            Text(
                text = "THÔNG TIN PHÁP LÝ", // tiêu đề khu vực pháp lý
                color = InformationBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            BusinessInformationCard(
                rows = listOf( // tạo danh sách thông tin pháp lý mẫu
                    "Mã doanh nghiệp" to "DN-001",
                    "Mã số thuế" to "3601234567",
                    "Loại hình vận tải" to "Vận tải hành khách",
                    "Giấy phép kinh doanh" to "GPVT-2026-001"
                )
            )

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng cách
            )

            Text(
                text = "THÔNG TIN LIÊN HỆ", // tiêu đề khu vực liên hệ
                color = InformationBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            BusinessInformationCard(
                rows = listOf( // tạo dữ liệu liên hệ mẫu
                    "Người đại diện" to "Nguyễn Văn Minh",
                    "Số điện thoại" to "0901 234 567",
                    "Email" to "dieuhanh@phuongtrang.vn",
                    "Địa chỉ" to "Đồng Nai, Việt Nam"
                )
            )

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng cách
            )

            Text(
                text = "THÔNG TIN VẬN HÀNH", // tiêu đề khu vực vận hành
                color = InformationBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // tạo thẻ thông tin vận hành
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = InformationLightBlue // dùng nền xanh nhạt
                ),
                shape = RoundedCornerShape(14.dp) // bo tròn góc
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn bộ chiều ngang
                        .padding(16.dp) // tạo khoảng cách bên trong
                ) {

                    BusinessInformationRow(
                        label = "Khu vực hoạt động", // tên trường
                        value = "Đồng Nai" // giá trị mẫu
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp) // tạo khoảng cách
                    )

                    BusinessInformationRow(
                        label = "Số tuyến đăng ký", // tên trường
                        value = "05 tuyến" // giá trị mẫu
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp) // tạo khoảng cách
                    )

                    BusinessInformationRow(
                        label = "Quy mô phương tiện", // tên trường
                        value = "08 xe" // giá trị mẫu
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp) // tạo khoảng cách
                    )

                    BusinessInformationRow(
                        label = "Tài xế quản lý", // tên trường
                        value = "03 tài xế" // giá trị mẫu
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp) // tạo khoảng cách trước nút
            )

            Button( // tạo nút chỉnh sửa thông tin
                onClick = {
                    // UI ONLY: chức năng chỉnh sửa sẽ thực hiện ở giai đoạn chức năng
                },
                modifier = Modifier
                    .fillMaxWidth() // cho nút phủ toàn bộ chiều ngang
                    .height(48.dp), // đặt chiều cao nút
                colors = ButtonDefaults.buttonColors(
                    containerColor = InformationBlue // đặt nền xanh
                ),
                shape = RoundedCornerShape(14.dp) // bo tròn góc nút
            ) {
                Text(
                    text = "CHỈNH SỬA THÔNG TIN", // tên nút
                    color = Color.White, // chữ trắng
                    fontSize = 14.sp, // kích thước chữ
                    fontWeight = FontWeight.Bold // chữ đậm
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng cách cuối màn hình
            )
        }
    }
}

@Composable
private fun BusinessInformationCard( // tạo thẻ gồm nhiều dòng thông tin
    rows: List<Pair<String, String>> // nhận danh sách tên trường và giá trị
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        colors = CardDefaults.cardColors(
            containerColor = Color.White // đặt nền trắng
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(
                    horizontal = 16.dp, // tạo khoảng cách hai bên
                    vertical = 6.dp // tạo khoảng cách trên dưới
                )
        ) {
            rows.forEachIndexed { index, row -> // lần lượt hiển thị từng dòng
                BusinessInformationRow(
                    label = row.first, // lấy tên trường
                    value = row.second // lấy giá trị
                )

                if (index < rows.lastIndex) { // chỉ tạo đường kẻ nếu chưa phải dòng cuối
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp), // tạo khoảng cách quanh đường kẻ
                        color = Color(0xFFEEEEEE) // đặt màu xám nhạt
                    )
                }
            }
        }
    }
}

@Composable
private fun BusinessInformationRow( // tạo một dòng thông tin doanh nghiệp
    label: String, // tên trường
    value: String // giá trị trường
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // phủ toàn bộ chiều ngang
            .padding(vertical = 8.dp) // tạo khoảng cách trên dưới
    ) {
        Text(
            text = label, // hiển thị tên trường
            color = Color.Gray, // chữ xám
            fontSize = 12.sp // kích thước chữ nhỏ
        )

        Spacer(
            modifier = Modifier.height(4.dp) // tạo khoảng cách giữa tên và giá trị
        )

        Text(
            text = value, // hiển thị giá trị
            color = Color.DarkGray, // chữ xám đậm
            fontSize = 14.sp, // kích thước chữ
            fontWeight = FontWeight.Medium // làm giá trị rõ hơn
        )
    }
}