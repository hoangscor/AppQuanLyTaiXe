package com.example.busdieuhanhdongnai.feature.business.report

import androidx.compose.foundation.background // tạo màu nền cho màn hình
import androidx.compose.foundation.layout.Arrangement // căn khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh áp dụng vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ lấy cạnh vùng an toàn cần thiết
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn camera và thanh trạng thái

import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng không an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // tạo góc bo tròn
import androidx.compose.foundation.verticalScroll // cho phép màn hình cuộn dọc
import androidx.compose.material3.Button // tạo nút thao tác
import androidx.compose.material3.ButtonDefaults // thiết lập màu nút
import androidx.compose.material3.Card // tạo thẻ thông tin
import androidx.compose.material3.CardDefaults // thiết lập màu nền thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.getValue // đọc giá trị state
import androidx.compose.runtime.mutableStateOf // tạo state thay đổi được
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi màn hình được tạo lại
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val ReportBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val ReportBackground = Color(0xFFF6F8FC) // màu nền chung
private val ReportGreen = Color(0xFF1A9B54) // màu xanh cho chỉ số tích cực
private val ReportOrange = Color(0xFFFF9800) // màu cam cho chỉ số cần chú ý
private val ReportRed = Color(0xFFE53935) // màu đỏ cho chỉ số cảnh báo
private val ReportLightBlue = Color(0xFFEAF3FF) // màu xanh nhạt cho nút chưa chọn

@Composable
fun BusinessReportScreen( // tạo màn hình báo cáo và thống kê doanh nghiệp
    onBack: () -> Unit = {} // nhận hành động quay lại màn trước
) {
    var selectedPeriod by rememberSaveable { // lưu khoảng thời gian đang được chọn
        mutableStateOf("Hôm nay") // mặc định xem báo cáo hôm nay
    }

    val reportPeriods = listOf( // tạo các khoảng thời gian mẫu
        "Hôm nay", // xem dữ liệu hôm nay
        "7 ngày", // xem dữ liệu bảy ngày
        "30 ngày" // xem dữ liệu ba mươi ngày
    )

    Column( // tạo bố cục chính của màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(ReportBackground) // đặt màu nền chung
            .verticalScroll(rememberScrollState()) // cho phép màn hình cuộn dọc
    ) {

        Column( // tạo khu vực tiêu đề màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(ReportBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top) // tránh camera và status bar
                )
                .padding(horizontal = 20.dp, vertical = 18.dp) // tạo khoảng cách trong header
        ) {

            Row( // xếp nút quay lại và tiêu đề trên cùng một hàng
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {

                TextButton( // tạo nút quay lại
                    onClick = onBack // gọi callback quay lại
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // chữ màu trắng
                        fontSize = 22.sp // kích thước biểu tượng
                    )
                }

                Column( // chứa tiêu đề và mô tả màn hình
                    modifier = Modifier.padding(start = 4.dp) // cách nút quay lại một khoảng nhỏ
                ) {

                    Text(
                        text = "BÁO CÁO & THỐNG KÊ", // tiêu đề chính
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // in đậm
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp) // khoảng cách giữa tiêu đề và mô tả
                    )

                    Text(
                        text = "Theo dõi hiệu quả hoạt động vận tải", // mô tả màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 13.sp // kích thước mô tả
                    )
                }
            }
        }

        Column( // tạo phần nội dung chính
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(horizontal = 20.dp, vertical = 20.dp) // tạo khoảng cách quanh nội dung
        ) {

            Text(
                text = "THỜI GIAN BÁO CÁO", // tiêu đề bộ lọc
                color = ReportBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước các nút lọc
            )

            Row( // xếp các lựa chọn thời gian trên cùng một hàng
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(8.dp) // tạo khoảng cách giữa các nút
            ) {

                reportPeriods.forEach { period -> // lần lượt tạo từng lựa chọn thời gian

                    val isSelected = selectedPeriod == period // kiểm tra nút hiện tại có đang được chọn hay không

                    Button(
                        onClick = {
                            selectedPeriod = period // đổi khoảng thời gian báo cáo
                        },
                        modifier = Modifier.weight(1f), // chia đều chiều ngang cho ba nút
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) {
                                ReportBlue // nền xanh khi đang chọn
                            } else {
                                ReportLightBlue // nền xanh nhạt khi chưa chọn
                            },
                            contentColor = if (isSelected) {
                                Color.White // chữ trắng khi đang chọn
                            } else {
                                ReportBlue // chữ xanh khi chưa chọn
                            }
                        ),
                        shape = RoundedCornerShape(12.dp) // bo góc nút
                    ) {
                        Text(
                            text = period, // hiển thị tên khoảng thời gian
                            fontSize = 12.sp, // kích thước chữ
                            fontWeight = FontWeight.Bold // in đậm
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách trước phần tổng quan
            )

            Text(
                text = "TỔNG QUAN VẬN HÀNH", // tiêu đề phần KPI
                color = ReportBlue, // màu xanh
                fontSize = 18.sp, // kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước hàng KPI đầu tiên
            )

            Row( // tạo hàng KPI thứ nhất
                modifier = Modifier.fillMaxWidth(), // phủ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(10.dp) // khoảng cách giữa hai thẻ
            ) {

                ReportMetricCard(
                    title = "Tổng chuyến", // tên KPI
                    value = "24", // dữ liệu mẫu
                    description = "Chuyến được lập lịch", // mô tả KPI
                    valueColor = ReportBlue, // màu giá trị
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )

                ReportMetricCard(
                    title = "Hoàn thành", // tên KPI
                    value = "21", // dữ liệu mẫu
                    description = "Chuyến đã kết thúc", // mô tả KPI
                    valueColor = ReportGreen, // màu xanh lá
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách giữa hai hàng KPI
            )

            Row( // tạo hàng KPI thứ hai
                modifier = Modifier.fillMaxWidth(), // phủ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(10.dp) // khoảng cách giữa hai thẻ
            ) {

                ReportMetricCard(
                    title = "Đúng giờ", // tên KPI
                    value = "19", // dữ liệu mẫu
                    description = "Chuyến đúng kế hoạch", // mô tả KPI
                    valueColor = ReportGreen, // màu xanh lá
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )

                ReportMetricCard(
                    title = "Cần chú ý", // tên KPI
                    value = "3", // dữ liệu mẫu
                    description = "Chậm hoặc phát sinh", // mô tả KPI
                    valueColor = ReportOrange, // màu cam
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách trước thống kê vận tải
            )

            Text(
                text = "THỐNG KÊ HOẠT ĐỘNG", // tiêu đề thống kê chi tiết
                color = ReportBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước thẻ đầu tiên
            )

            ReportDetailCard(
                title = "Chuyến xe", // tên nhóm báo cáo
                firstLabel = "Đã hoàn thành", // nhãn dòng đầu
                firstValue = "21 chuyến", // giá trị mẫu
                secondLabel = "Chậm giờ", // nhãn dòng thứ hai
                secondValue = "2 chuyến", // giá trị mẫu
                thirdLabel = "Hủy chuyến", // nhãn dòng thứ ba
                thirdValue = "1 chuyến" // giá trị mẫu
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách giữa các thẻ
            )

            ReportDetailCard(
                title = "Phương tiện", // tên nhóm báo cáo
                firstLabel = "Đang hoạt động", // nhãn dòng đầu
                firstValue = "5 xe", // giá trị mẫu
                secondLabel = "Bảo trì", // nhãn dòng thứ hai
                secondValue = "1 xe", // giá trị mẫu
                thirdLabel = "Tạm dừng", // nhãn dòng thứ ba
                thirdValue = "1 xe" // giá trị mẫu
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách giữa các thẻ
            )

            ReportDetailCard(
                title = "Tài xế", // tên nhóm báo cáo
                firstLabel = "Đang hoạt động", // nhãn dòng đầu
                firstValue = "2 tài xế", // giá trị mẫu
                secondLabel = "Tạm nghỉ", // nhãn dòng thứ hai
                secondValue = "1 tài xế", // giá trị mẫu
                thirdLabel = "Chưa phân công", // nhãn dòng thứ ba
                thirdValue = "1 tài xế" // giá trị mẫu
            )

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách trước phần kinh doanh
            )

            Text(
                text = "THỐNG KÊ KINH DOANH", // tiêu đề phần kinh doanh
                color = ReportBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước thẻ kinh doanh
            )

            Card( // tạo thẻ thống kê doanh thu
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                shape = RoundedCornerShape(16.dp), // bo góc thẻ
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp) // tạo khoảng cách bên trong thẻ
                ) {

                    Text(
                        text = "Doanh thu vé", // tên chỉ số doanh thu
                        color = Color.DarkGray, // màu xám đậm
                        fontSize = 14.sp, // kích thước chữ
                        fontWeight = FontWeight.Bold // in đậm
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp) // khoảng cách nhỏ
                    )

                    Text(
                        text = "12.850.000 đ", // dữ liệu doanh thu mẫu
                        color = ReportGreen, // màu xanh lá
                        fontSize = 25.sp, // làm nổi bật giá trị
                        fontWeight = FontWeight.Bold // in đậm
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp) // khoảng cách trước thông tin phụ
                    )

                    ReportInfoRow(
                        label = "Số vé đã bán", // tên chỉ số
                        value = "286 vé" // dữ liệu mẫu
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp) // khoảng cách giữa các dòng
                    )

                    ReportInfoRow(
                        label = "Khách hàng", // tên chỉ số
                        value = "214 khách" // dữ liệu mẫu
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp) // khoảng cách giữa các dòng
                    )

                    ReportInfoRow(
                        label = "Tỷ lệ hoàn thành", // tên chỉ số
                        value = "87,5%" // dữ liệu mẫu
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng trống cuối màn hình
            )
        }
    }
}

@Composable
private fun ReportMetricCard( // tạo thẻ hiển thị một KPI
    title: String, // tên KPI
    value: String, // giá trị KPI
    description: String, // mô tả KPI
    valueColor: Color, // màu giá trị
    modifier: Modifier = Modifier // cho phép màn cha điều chỉnh kích thước
) {

    Card(
        modifier = modifier, // nhận modifier từ màn cha
        shape = RoundedCornerShape(16.dp), // bo tròn góc
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp) // tạo khoảng cách trong thẻ
        ) {

            Text(
                text = title, // tên KPI
                color = Color.DarkGray, // màu xám đậm
                fontSize = 13.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(6.dp) // khoảng cách trước giá trị
            )

            Text(
                text = value, // hiển thị giá trị KPI
                color = valueColor, // màu theo loại KPI
                fontSize = 25.sp, // kích thước số lớn
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(5.dp) // khoảng cách trước mô tả
            )

            Text(
                text = description, // mô tả KPI
                color = Color.Gray, // màu xám
                fontSize = 11.sp // kích thước nhỏ
            )
        }
    }
}

@Composable
private fun ReportDetailCard( // tạo thẻ thống kê chi tiết
    title: String, // tiêu đề nhóm thống kê
    firstLabel: String, // nhãn dòng thứ nhất
    firstValue: String, // giá trị dòng thứ nhất
    secondLabel: String, // nhãn dòng thứ hai
    secondValue: String, // giá trị dòng thứ hai
    thirdLabel: String, // nhãn dòng thứ ba
    thirdValue: String // giá trị dòng thứ ba
) {

    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo góc
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp) // khoảng cách bên trong
        ) {

            Text(
                text = title, // hiển thị tên nhóm
                color = ReportBlue, // màu xanh
                fontSize = 16.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước dữ liệu
            )

            ReportInfoRow(
                label = firstLabel, // nhãn thứ nhất
                value = firstValue // giá trị thứ nhất
            )

            Spacer(
                modifier = Modifier.height(8.dp) // khoảng cách giữa các dòng
            )

            ReportInfoRow(
                label = secondLabel, // nhãn thứ hai
                value = secondValue // giá trị thứ hai
            )

            Spacer(
                modifier = Modifier.height(8.dp) // khoảng cách giữa các dòng
            )

            ReportInfoRow(
                label = thirdLabel, // nhãn thứ ba
                value = thirdValue // giá trị thứ ba
            )
        }
    }
}

@Composable
private fun ReportInfoRow( // tạo một dòng tên chỉ số và giá trị
    label: String, // tên chỉ số
    value: String // giá trị chỉ số
) {

    Row(
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        horizontalArrangement = Arrangement.SpaceBetween, // đẩy tên và giá trị về hai phía
        verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
    ) {

        Text(
            text = label, // hiển thị tên chỉ số
            color = Color.DarkGray, // màu xám đậm
            fontSize = 14.sp // kích thước chữ
        )

        Text(
            text = value, // hiển thị giá trị
            color = ReportBlue, // màu xanh
            fontSize = 14.sp, // kích thước chữ
            fontWeight = FontWeight.Bold // in đậm giá trị
        )
    }
}