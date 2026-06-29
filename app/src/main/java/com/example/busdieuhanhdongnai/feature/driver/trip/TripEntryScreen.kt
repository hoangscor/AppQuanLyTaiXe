package com.example.busdieuhanhdongnai.feature.driver.trip

import com.example.busdieuhanhdongnai.feature.driver.history.TripHistoryStore // lưu chuyến hoàn thành vào nhật ký
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

private val TripBlue = Color(0xFF0066CC) // màu xanh chính
private val TripBackground = Color(0xFFF6F8FC) // màu nền trang
private val TripGreen = Color(0xFF1A9B54) // màu trạng thái tốt
private val TripOrange = Color(0xFFFF9800) // màu cảnh báo

@Composable
fun TripEntryScreen(onBack: () -> Unit = {}) { // nhận lệnh quay lại
    var passengerCount by rememberSaveable { mutableStateOf("") } // số khách nhập vào
    var tripNote by rememberSaveable { mutableStateOf("") } // ghi chú chuyến xe
    var tripStarted by rememberSaveable { mutableStateOf(false) } // trạng thái chuyến xe
    var tripCompleted by rememberSaveable { mutableStateOf(false) } // đánh dấu chuyến đã hoàn thành
    var resultMessage by rememberSaveable { mutableStateOf("") } // thông báo kết quả
    val statusText = when { // xác định chữ trạng thái
        tripCompleted -> "Đã hoàn thành" // chuyến đã kết thúc
        tripStarted -> "Đang thực hiện" // chuyến đang chạy
        else -> "Chưa bắt đầu" // chưa bấm bắt đầu
    }

    val statusColor = when { // xác định màu trạng thái
        tripCompleted -> TripGreen // màu xanh khi đã hoàn thành
        tripStarted -> TripGreen // màu xanh khi đang chạy
        else -> TripOrange // màu cam khi chưa chạy
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // chiếm toàn màn hình
            .background(TripBackground) // tô nền trang
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // phủ ngang màn hình
                .background(TripBlue) // nền thanh tiêu đề
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
                    text = "NHẬP DỮ LIỆU CHUYẾN",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Text(
                    text = "Cập nhật thông tin vận hành",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp), // lề cho nội dung
            verticalArrangement = Arrangement.spacedBy(14.dp) // khoảng cách giữa các phần
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
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách dòng thông tin
                ) {
                    Text(
                        text = "THÔNG TIN CHUYẾN XE",
                        color = TripBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    TripInfoRow(
                        label = "Tuyến",
                        value = "Tuyến 01: Bến xe A → Bến xe B"
                    )

                    TripInfoRow(
                        label = "Xe",
                        value = "51B-123.45"
                    )

                    TripInfoRow(
                        label = "Giờ dự kiến",
                        value = "07:00 - 08:00"
                    )

                    TripInfoRow(
                        label = "Trạng thái",
                        value = statusText,
                        valueColor = statusColor
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho thẻ
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong thẻ
                    verticalArrangement = Arrangement.spacedBy(12.dp) // khoảng cách các ô nhập
                ) {
                    Text(
                        text = "DỮ LIỆU VẬN HÀNH",
                        color = TripBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    OutlinedTextField(
                        value = passengerCount,
                        onValueChange = { passengerCount = it }, // cập nhật số khách
                        label = {
                            Text("Số lượt khách") // nhãn ô nhập
                        },
                        placeholder = {
                            Text("Ví dụ: 35") // gợi ý nhập
                        },
                        modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                        singleLine = true // chỉ nhập một dòng
                    )

                    OutlinedTextField(
                        value = tripNote,
                        onValueChange = { tripNote = it }, // cập nhật ghi chú
                        label = {
                            Text("Ghi chú / sự cố") // nhãn ô ghi chú
                        },
                        placeholder = {
                            Text("Ví dụ: kẹt xe, thay đổi lộ trình...") // gợi ý nhập
                        },
                        modifier = Modifier
                            .fillMaxWidth() // phủ ngang màn hình
                            .height(130.dp) // tăng chiều cao ô ghi chú
                    )
                }
            }

            Button(
                onClick = {
                    if (!tripStarted && !tripCompleted) { // chỉ bắt đầu khi chuyến chưa kết thúc
                        tripStarted = true // chuyển sang trạng thái đang chạy
                        resultMessage = "Đã bắt đầu chuyến xe lúc 07:00." // báo đã bắt đầu
                    } else if (tripStarted && passengerCount.isBlank()) { // kiểm tra số khách trước khi kết thúc
                        resultMessage = "Vui lòng nhập số lượt khách trước khi kết thúc chuyến." // nhắc nhập dữ liệu
                    } else if (tripStarted) { // kết thúc khi chuyến đang chạy
                        tripStarted = false // dừng trạng thái đang chạy
                        tripCompleted = true // đánh dấu chuyến đã hoàn thành
                        TripHistoryStore.addCompletedTrip( // thêm chuyến hoàn thành vào nhật ký
                            passengers = passengerCount, // lấy số khách đã nhập
                            note = "" // chưa dùng ghi chú
                        )
                        resultMessage = "Đã lưu dữ liệu chuyến xe thành công." // báo đã lưu
                    }
                },
                enabled = !tripCompleted, // khóa nút sau khi chuyến hoàn thành
                modifier = Modifier
                    .fillMaxWidth() // phủ ngang màn hình
                    .height(52.dp), // chiều cao nút
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (tripStarted) TripOrange else TripBlue // đổi màu theo trạng thái
                ),
                shape = RoundedCornerShape(10.dp) // bo góc nút
            ) {
                Text(
                    text = when { // đổi chữ nút theo trạng thái
                        tripCompleted -> "CHUYẾN ĐÃ HOÀN THÀNH" // chuyến đã xong
                        tripStarted -> "KẾT THÚC CHUYẾN" // cho phép kết thúc
                        else -> "BẮT ĐẦU CHUYẾN" // cho phép bắt đầu
                    },
                    fontWeight = FontWeight.Bold
                )
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
                        color = if (tripStarted) TripGreen else TripBlue, // đổi màu thông báo
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(16.dp) // lề thông báo
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng tóm tắt
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề trong thẻ
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách các dòng
                ) {
                    Text(
                        text = "TÓM TẮT DỮ LIỆU",
                        color = TripBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    TripInfoRow(
                        label = "Số khách",
                        value = if (passengerCount.isBlank()) "Chưa nhập" else "$passengerCount khách"
                    )

                    TripInfoRow(
                        label = "Ghi chú",
                        value = if (tripNote.isBlank()) "Không có" else tripNote
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // chừa khoảng trống cuối trang
        }
    }
}

@Composable
fun TripInfoRow(
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