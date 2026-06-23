package com.example.busdieuhanhdongnai.feature.driver.notification

import androidx.compose.foundation.background // tô nền giao diện
import androidx.compose.foundation.clickable // cho phép bấm
import androidx.compose.foundation.layout.Arrangement // căn cách phần tử
import androidx.compose.foundation.layout.Column // xếp dọc
import androidx.compose.foundation.layout.Row // xếp ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng trống
import androidx.compose.foundation.layout.fillMaxSize // chiếm toàn màn hình
import androidx.compose.foundation.layout.fillMaxWidth // chiếm toàn chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.padding // tạo khoảng cách
import androidx.compose.foundation.rememberScrollState // nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Card // thẻ giao diện
import androidx.compose.material3.CardDefaults // màu cho thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.runtime.Composable // tạo giao diện Compose
import androidx.compose.runtime.getValue // đọc state
import androidx.compose.runtime.mutableStateOf // tạo state thay đổi được
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi xoay màn hình
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh
import androidx.compose.ui.Modifier // chỉnh giao diện
import androidx.compose.ui.draw.clip // cắt theo bo góc
import androidx.compose.ui.graphics.Color // dùng màu
import androidx.compose.ui.text.font.FontWeight // chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị khoảng cách
import androidx.compose.ui.unit.sp // đơn vị cỡ chữ


private val NotificationBlue = Color(0xFF0066CC) // màu xanh chính
private val NotificationBackground = Color(0xFFF6F8FC) // màu nền trang
private val NotificationRed = Color(0xFFE53935) // màu cảnh báo đỏ
private val NotificationOrange = Color(0xFFFF9800) // màu cảnh báo cam
private val NotificationGreen = Color(0xFF1A9B54) // màu thông báo bình thường

data class BusNotification( // dữ liệu một thông báo
    val icon: String, // biểu tượng thông báo
    val title: String, // tiêu đề thông báo
    val content: String, // nội dung thông báo
    val time: String, // thời gian nhận
    val type: String, // loại thông báo
    val color: Color // màu trạng thái
)

@Composable
fun NotificationScreen(onBack: () -> Unit = {}) { // nhận lệnh quay lại
    var selectedTab by rememberSaveable { mutableStateOf("Tất cả") } // tab đang chọn

    val notificationList = listOf( // danh sách dữ liệu mẫu
        BusNotification(
            icon = "⚠",
            title = "Cảnh báo chậm chuyến",
            content = "Chuyến 10:00 tuyến 01 đang chậm 15 phút.",
            time = "10:15",
            type = "Cảnh báo",
            color = NotificationRed
        ),
        BusNotification(
            icon = "✕",
            title = "Cảnh báo bỏ chuyến",
            content = "Phương tiện 51B-123.45 chưa thực hiện chuyến 11:00.",
            time = "11:10",
            type = "Cảnh báo",
            color = NotificationRed
        ),
        BusNotification(
            icon = "i",
            title = "Thông báo từ Trung tâm",
            content = "Thay đổi lộ trình tuyến 01 từ 14:00 đến 16:00.",
            time = "09:15",
            type = "Trung tâm",
            color = NotificationBlue
        ),
        BusNotification(
            icon = "✓",
            title = "Nhắc trước giờ xuất bến",
            content = "Chuyến 12:00 tuyến 01 sẽ xuất bến sau 15 phút.",
            time = "11:45",
            type = "Nhắc giờ",
            color = NotificationGreen
        ),
        BusNotification(
            icon = "!",
            title = "Điều chỉnh biểu đồ chạy xe",
            content = "Tuyến 01 cần thực hiện đúng tần suất theo kế hoạch.",
            time = "08:30",
            type = "Trung tâm",
            color = NotificationOrange
        )
    )

    val filteredList = when (selectedTab) { // lọc danh sách theo tab
        "Cảnh báo" -> notificationList.filter { it.type == "Cảnh báo" }
        "Trung tâm" -> notificationList.filter { it.type == "Trung tâm" }
        else -> notificationList
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // chiếm toàn màn hình
            .background(NotificationBackground) // tô nền trang
            .verticalScroll(rememberScrollState()) // cho phép cuộn danh sách
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // phủ ngang màn hình
                .background(NotificationBlue) // nền thanh tiêu đề
                .padding(horizontal = 18.dp, vertical = 18.dp), // khoảng cách bên trong
            verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(end = 14.dp) // cách tiêu đề một khoảng
                    .clickable { onBack() } // bấm để quay về trang trước
            )

            Column {
                Text(
                    text = "THÔNG BÁO",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "Cảnh báo và điều hành tuyến xe",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth() // phủ toàn chiều ngang
                .padding(16.dp), // tạo lề xung quanh tab
            horizontalArrangement = Arrangement.SpaceBetween // giãn đều 3 tab
        ) {
            NotificationTab(
                title = "Tất cả",
                selected = selectedTab == "Tất cả",
                onClick = { selectedTab = "Tất cả" } // xem toàn bộ thông báo
            )

            NotificationTab(
                title = "Cảnh báo",
                selected = selectedTab == "Cảnh báo",
                onClick = { selectedTab = "Cảnh báo" } // lọc cảnh báo
            )

            NotificationTab(
                title = "Trung tâm",
                selected = selectedTab == "Trung tâm",
                onClick = { selectedTab = "Trung tâm" } // lọc thông báo trung tâm
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp) // lề cho danh sách
        ) {
            Text(
                text = "Danh sách thông báo",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp)) // cách tiêu đề với danh sách

            filteredList.forEach { item -> // hiển thị từng thông báo
                NotificationItem(item = item) // tạo thẻ thông báo
            }

            Spacer(modifier = Modifier.height(24.dp)) // chừa khoảng trống cuối trang
        }
    }
}

@Composable
fun NotificationTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = title,
        color = if (selected) Color.White else NotificationBlue, // đổi màu khi tab được chọn
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp)) // bo tròn tab
            .background(
                if (selected) NotificationBlue else Color.White // đổi nền khi chọn tab
            )
            .clickable { onClick() } // bấm để lọc thông báo
            .padding(horizontal = 18.dp, vertical = 10.dp) // kích thước tab
    )
}

@Composable
fun NotificationItem(item: BusNotification) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // phủ ngang màn hình
            .padding(bottom = 10.dp), // cách các thẻ với nhau
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng cho thẻ
        ),
        shape = RoundedCornerShape(14.dp) // bo góc thẻ
    ) {
        Row(
            modifier = Modifier.padding(16.dp), // lề bên trong thẻ
            verticalAlignment = Alignment.CenterVertically // căn giữa nội dung
        ) {
            Text(
                text = item.icon,
                color = item.color,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 14.dp) // cách nội dung
            )

            Column(
                modifier = Modifier.weight(1f) // chiếm phần chiều ngang còn lại
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp)) // cách tiêu đề với nội dung

                Text(
                    text = item.content,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            Text(
                text = item.time,
                color = item.color,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp) // cách nội dung bên trái
            )
        }
    }
}