package com.example.busdieuhanhdongnai.feature.driver.qr

import androidx.compose.foundation.background // tô nền giao diện
import androidx.compose.foundation.clickable // cho phép bấm
import androidx.compose.foundation.layout.Arrangement // tạo khoảng cách đều
import androidx.compose.foundation.layout.Column // xếp nội dung dọc
import androidx.compose.foundation.layout.Row // xếp nội dung ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng trống
import androidx.compose.foundation.layout.fillMaxSize // chiếm toàn màn hình
import androidx.compose.foundation.layout.fillMaxWidth // chiếm toàn chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.padding // tạo lề
import androidx.compose.foundation.rememberScrollState // nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Button // tạo nút bấm
import androidx.compose.material3.ButtonDefaults // chỉnh màu nút
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // chỉnh màu thẻ
import androidx.compose.material3.OutlinedTextField // ô nhập dữ liệu
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.runtime.Composable // tạo giao diện Compose
import androidx.compose.runtime.getValue // đọc state
import androidx.compose.runtime.mutableStateOf // tạo state thay đổi được
import androidx.compose.runtime.saveable.rememberSaveable // giữ dữ liệu khi xoay màn hình
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh nội dung
import androidx.compose.ui.Modifier // chỉnh giao diện
import androidx.compose.ui.graphics.Color // dùng màu
import androidx.compose.ui.text.font.FontWeight // chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị khoảng cách
import androidx.compose.ui.unit.sp // đơn vị cỡ chữ

private val QrBlue = Color(0xFF0066CC) // màu xanh chính
private val QrBackground = Color(0xFFF6F8FC) // màu nền trang
private val QrGreen = Color(0xFF1A9B54) // màu xác nhận thành công
private val QrOrange = Color(0xFFFF9800) // màu chờ xử lý
private val QrRed = Color(0xFFE53935) // màu báo lỗi

@Composable
fun QrCheckInScreen(onBack: () -> Unit = {}) { // nhận lệnh quay lại
    var passengerCode by rememberSaveable { mutableStateOf("") } // mã QR hoặc mã thẻ
    var resultMessage by rememberSaveable { mutableStateOf("") } // kết quả quét
    var isSuccess by rememberSaveable { mutableStateOf(false) } // lưu kết quả thành công hay lỗi

    val resultColor = when {
        resultMessage.isBlank() -> QrOrange // chưa có kết quả
        isSuccess -> QrGreen // check-in thành công
        else -> QrRed // có lỗi
    }
    var checkedPassengerCount by rememberSaveable { mutableStateOf(0) } // số khách đã check-in

    Column(
        modifier = Modifier
            .fillMaxSize() // chiếm toàn màn hình
            .background(QrBackground) // tô nền trang
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // phủ ngang màn hình
                .background(QrBlue) // nền thanh tiêu đề
                .padding(horizontal = 18.dp, vertical = 18.dp), // lề thanh tiêu đề
            verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(end = 14.dp) // cách tiêu đề
                    .clickable { onBack() } // quay về trang trước
            )

            Column {
                Text(
                    text = "QUÉT QR / THẺ ĐIỆN TỬ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "Xác nhận hành khách lên xe",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp), // lề nội dung
            verticalArrangement = Arrangement.spacedBy(14.dp) // khoảng cách các phần
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho thẻ
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong thẻ
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách từng dòng
                ) {
                    Text(
                        text = "CHUYẾN ĐANG THỰC HIỆN",
                        color = QrBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    QrInfoRow(
                        label = "Tuyến",
                        value = "Tuyến 01: Bến xe A → Bến xe B"
                    )

                    QrInfoRow(
                        label = "Xe",
                        value = "51B-123.45"
                    )

                    QrInfoRow(
                        label = "Giờ xuất bến",
                        value = "07:00"
                    )

                    QrInfoRow(
                        label = "Đã check-in",
                        value = "$checkedPassengerCount hành khách",
                        valueColor = QrGreen
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho khung quét
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ ngang thẻ
                        .padding(16.dp), // lề bên trong
                    horizontalAlignment = Alignment.CenterHorizontally, // căn giữa nội dung
                    verticalArrangement = Arrangement.spacedBy(12.dp) // cách từng phần
                ) {
                    Text(
                        text = "▣",
                        color = QrBlue,
                        fontSize = 74.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "KHUNG QUÉT QR",
                        color = QrBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Text(
                        text = "Bản demo: nhập mã QR hoặc mã thẻ bên dưới.",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho thẻ nhập mã
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong thẻ
                    verticalArrangement = Arrangement.spacedBy(12.dp) // cách các phần
                ) {
                    Text(
                        text = "XÁC NHẬN MÃ HÀNH KHÁCH",
                        color = QrBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    OutlinedTextField(
                        value = passengerCode,
                        onValueChange = { passengerCode = it }, // cập nhật mã hành khách
                        label = {
                            Text("Mã QR hoặc mã thẻ điện tử") // nhãn ô nhập
                        },
                        placeholder = {
                            Text("Ví dụ: QR-001-2026") // gợi ý mã
                        },
                        modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                        singleLine = true // chỉ nhập một dòng
                    )

                    Button(
                        onClick = {
                            if (passengerCode.isBlank()) {
                            resultMessage = "Vui lòng nhập mã QR hoặc mã thẻ." // báo thiếu mã
                            isSuccess = false // đánh dấu lỗi
                        } else if (passengerCode.length < 4) {
                            resultMessage = "Mã chưa hợp lệ, vui lòng kiểm tra lại." // báo mã ngắn
                            isSuccess = false // đánh dấu lỗi
                        } else {
                            checkedPassengerCount += 1 // tăng số khách check-in
                            resultMessage = "Check-in thành công: $passengerCode" // báo thành công
                            isSuccess = true // đánh dấu thành công
                            passengerCode = "" // xóa mã sau khi xác nhận
                        }
                        },
                        modifier = Modifier
                            .fillMaxWidth() // phủ ngang màn hình
                            .height(52.dp), // chiều cao nút
                        colors = ButtonDefaults.buttonColors(
                            containerColor = QrBlue // nền nút xanh
                        ),
                        shape = RoundedCornerShape(10.dp) // bo góc nút
                    ) {
                        Text(
                            text = "XÁC NHẬN CHECK-IN",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (resultMessage.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // nền trắng thông báo
                    ),
                    shape = RoundedCornerShape(14.dp) // bo góc thẻ
                ) {
                    Text(
                        text = resultMessage,
                        color = resultColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(16.dp) // lề thông báo
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho hướng dẫn
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách từng dòng
                ) {
                    Text(
                        text = "HƯỚNG DẪN",
                        color = QrBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Text(
                        text = "1. Yêu cầu hành khách xuất trình mã QR hoặc thẻ điện tử.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Text(
                        text = "2. Nhập mã vào ô xác nhận trong bản demo.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Text(
                        text = "3. Bấm XÁC NHẬN CHECK-IN để lưu lượt khách.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // chừa khoảng trống cuối trang
        }
    }
}

@Composable
fun QrInfoRow(
    label: String,
    value: String,
    valueColor: Color = Color.DarkGray
) {
    Row(
        modifier = Modifier.fillMaxWidth(), // phủ ngang phần thông tin
        verticalAlignment = Alignment.CenterVertically // căn giữa chữ
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f) // chiếm phần bên trái
        )

        Text(
            text = value,
            color = valueColor,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}