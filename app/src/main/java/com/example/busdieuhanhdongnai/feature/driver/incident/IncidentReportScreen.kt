package com.example.busdieuhanhdongnai.feature.driver.incident

import androidx.compose.foundation.layout.size // đặt kích thước ảnh
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

import androidx.compose.runtime.remember // nhớ ảnh khi đang ở màn hình
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
import androidx.compose.ui.graphics.asImageBitmap // đổi Bitmap sang ảnh Compose
import androidx.compose.ui.layout.ContentScale // chỉnh ảnh vừa khung

import android.graphics.Bitmap // lưu ảnh chụp tạm
import androidx.activity.compose.rememberLauncherForActivityResult // mở camera hệ thống
import androidx.activity.result.contract.ActivityResultContracts // dùng chức năng chụp ảnh
import androidx.compose.foundation.Image // hiển thị ảnh

private val IncidentBlue = Color(0xFF0066CC) // màu xanh chính
private val IncidentBackground = Color(0xFFF6F8FC) // màu nền trang
private val IncidentRed = Color(0xFFE53935) // màu sự cố nghiêm trọng
private val IncidentOrange = Color(0xFFFF9800) // màu cảnh báo
private val IncidentGreen = Color(0xFF1A9B54) // màu gửi thành công

data class IncidentOption( // dữ liệu một loại sự cố
    val title: String,
    val description: String
)

@Composable
fun IncidentReportScreen(onBack: () -> Unit = {}) { // nhận lệnh quay lại
    var selectedType by rememberSaveable { mutableStateOf("Chậm chuyến") } // loại sự cố đã chọn
    var description by rememberSaveable { mutableStateOf("") } // nội dung sự cố
    var resultMessage by rememberSaveable { mutableStateOf("") } // thông báo kết quả
    var isSubmitted by rememberSaveable { mutableStateOf(false) } // đã gửi thành công hay chưa
    var incidentPhoto by remember { mutableStateOf<Bitmap?>(null) } // lưu ảnh vừa chụp
    var photoMessage by rememberSaveable { mutableStateOf("") } // thông báo trạng thái ảnh

    val incidentOptions = listOf( // danh sách loại sự cố
        IncidentOption("Chậm chuyến", "Xe xuất bến hoặc đến trễ so với kế hoạch."),
        IncidentOption("Bỏ chuyến", "Chuyến xe chưa được thực hiện."),
        IncidentOption("Sự cố phương tiện", "Xe gặp lỗi kỹ thuật hoặc cần kiểm tra."),
        IncidentOption("Tai nạn / va chạm", "Có va chạm cần báo Trung tâm.")
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview() // mở camera chụp ảnh
    ) { bitmap ->
        incidentPhoto = bitmap // nhận ảnh từ camera

        photoMessage = if (bitmap != null) {
            "Đã đính kèm 1 ảnh sự cố." // chụp thành công
        } else {
            "Chưa chụp được ảnh." // người dùng hủy hoặc camera lỗi
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // chiếm toàn màn hình
            .background(IncidentBackground) // tô nền trang
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // phủ ngang màn hình
                .background(IncidentBlue) // nền thanh tiêu đề
                .padding(horizontal = 18.dp, vertical = 18.dp), // lề tiêu đề
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
                    text = "BÁO CÁO SỰ CỐ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Text(
                    text = "Gửi thông tin đến Trung tâm điều hành",
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
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách từng dòng
                ) {
                    Text(
                        text = "THÔNG TIN CHUYẾN XE",
                        color = IncidentBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    IncidentInfoRow(
                        label = "Tuyến",
                        value = "Tuyến 01: Bến xe A → Bến xe B"
                    )

                    IncidentInfoRow(
                        label = "Xe",
                        value = "51B-123.45"
                    )

                    IncidentInfoRow(
                        label = "Thời gian báo",
                        value = "10:15"
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
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(10.dp) // cách các lựa chọn
                ) {
                    Text(
                        text = "CHỌN LOẠI SỰ CỐ",
                        color = IncidentBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    incidentOptions.forEach { option ->
                        IncidentTypeCard(
                            option = option,
                            selected = selectedType == option.title,
                            onClick = {
                                selectedType = option.title // đổi loại sự cố
                                resultMessage = "" // xóa thông báo cũ
                            }
                        )
                    }
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
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(12.dp) // cách các phần
                ) {
                    Text(
                        text = "MÔ TẢ SỰ CỐ",
                        color = IncidentBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it // cập nhật mô tả
                            resultMessage = "" // xóa thông báo cũ
                        },
                        label = {
                            Text("Nội dung chi tiết") // nhãn ô nhập
                        },
                        placeholder = {
                            Text("Ví dụ: Xe gặp ùn tắc tại km 12, dự kiến chậm 10 phút.") // gợi ý nhập
                        },
                        modifier = Modifier
                            .fillMaxWidth() // phủ ngang màn hình
                            .height(145.dp) // tăng chiều cao ô nhập
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng cho thẻ ảnh
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(12.dp) // khoảng cách các phần
                ) {
                    Text(
                        text = "ẢNH SỰ CỐ",
                        color = IncidentBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Text(
                        text = "Chụp ảnh hiện trường để gửi kèm báo cáo.",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )

                    Button(
                        onClick = {
                            cameraLauncher.launch(null) // mở camera để chụp ảnh
                        },
                        modifier = Modifier
                            .fillMaxWidth() // nút chiếm toàn chiều ngang
                            .height(50.dp), // chiều cao nút
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IncidentBlue // nền nút xanh
                        ),
                        shape = RoundedCornerShape(10.dp) // bo góc nút
                    ) {
                        Text(
                            text = "CHỤP ẢNH SỰ CỐ",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    incidentPhoto?.let { photo ->
                        Image(
                            bitmap = photo.asImageBitmap(), // đổi ảnh Bitmap sang ảnh Compose
                            contentDescription = "Ảnh sự cố đã chụp",
                            modifier = Modifier.size(220.dp), // kích thước ảnh xem trước
                            contentScale = ContentScale.Crop // cắt ảnh vừa khung
                        )
                    }

                    if (photoMessage.isNotBlank()) {
                        Text(
                            text = photoMessage,
                            color = if (incidentPhoto != null) IncidentGreen else IncidentRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (description.trim().length < 5) {
                        resultMessage = "Vui lòng nhập mô tả sự cố rõ hơn." // báo thiếu dữ liệu
                        isSubmitted = false // đánh dấu gửi chưa thành công
                    } else {
                        resultMessage = if (incidentPhoto != null) {
                            "Đã gửi báo cáo \"$selectedType\" kèm ảnh đến Trung tâm." // đã gửi có ảnh
                        } else {
                            "Đã gửi báo cáo \"$selectedType\" đến Trung tâm." // gửi không có ảnh
                        }
                        isSubmitted = true // đánh dấu gửi thành công
                    }
                },
                modifier = Modifier
                    .fillMaxWidth() // phủ ngang màn hình
                    .height(52.dp), // chiều cao nút
                colors = ButtonDefaults.buttonColors(
                    containerColor = IncidentRed // nền nút đỏ
                ),
                shape = RoundedCornerShape(10.dp) // bo góc nút
            ) {
                Text(
                    text = "GỬI BÁO CÁO SỰ CỐ",
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
                        color = if (isSubmitted) IncidentGreen else IncidentRed, // đổi màu theo kết quả
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(16.dp) // lề thông báo
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), // phủ ngang màn hình
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng hướng dẫn
                ),
                shape = RoundedCornerShape(14.dp) // bo góc thẻ
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // lề bên trong
                    verticalArrangement = Arrangement.spacedBy(8.dp) // cách từng dòng
                ) {
                    Text(
                        text = "LƯU Ý",
                        color = IncidentOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Text(
                        text = "• Báo cáo ngay khi phát hiện sự cố.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Text(
                        text = "• Với tai nạn hoặc sự cố nghiêm trọng, cần liên hệ Trung tâm ngay.",
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
fun IncidentTypeCard(
    option: IncidentOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // phủ ngang màn hình
            .clickable { onClick() }, // bấm để chọn loại sự cố
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                IncidentBlue.copy(alpha = 0.10f) // nền xanh khi được chọn
            } else {
                Color.White // nền trắng khi chưa chọn
            }
        ),
        shape = RoundedCornerShape(10.dp) // bo góc thẻ
    ) {
        Row(
            modifier = Modifier.padding(14.dp), // lề trong thẻ
            verticalAlignment = Alignment.CenterVertically // căn giữa nội dung
        ) {
            Text(
                text = if (selected) "✓" else "○", // hiển thị trạng thái chọn
                color = if (selected) IncidentBlue else Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                modifier = Modifier.padding(end = 12.dp) // cách phần chữ
            )

            Column {
                Text(
                    text = option.title,
                    color = if (selected) IncidentBlue else Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = option.description,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun IncidentInfoRow(
    label: String,
    value: String
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
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}