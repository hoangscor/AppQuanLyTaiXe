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
import androidx.compose.runtime.collectAsState // chuyển Flow thông báo từ Room thành state Compose
import androidx.lifecycle.viewmodel.compose.viewModel // lấy NotificationViewModel trong màn hình
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
    val id: Int, // id của thông báo trong Room
    val isRead: Boolean, // trạng thái thông báo đã đọc hay chưa
    val icon: String, // biểu tượng thông báo
    val title: String, // tiêu đề thông báo
    val content: String, // nội dung thông báo
    val time: String, // thời gian nhận
    val type: String, // loại thông báo
    val color: Color // màu trạng thái
)

@Composable
fun NotificationScreen(
    onBack: () -> Unit = {}, // nhận lệnh quay lại
    notificationViewModel: NotificationViewModel = viewModel() // lấy ViewModel để đọc thông báo từ Room
) {
    var selectedTab by rememberSaveable { mutableStateOf("Tất cả") } // tab đang chọn
    val savedNotifications by notificationViewModel.allNotifications.collectAsState(
        initial = emptyList() // ban đầu dùng danh sách rỗng khi Room chưa trả dữ liệu
    )

    val unreadCount by notificationViewModel.unreadNotificationCount.collectAsState(
        initial = 0 // ban đầu chưa có thông báo chưa đọc
    )

    val notificationList = savedNotifications.map { notification -> // chuyển dữ liệu Room sang model giao diện hiện tại
        val displayColor = when (notification.type) { // chọn màu theo loại thông báo
            "Cảnh báo" -> NotificationRed // cảnh báo dùng màu đỏ
            "Trung tâm" -> NotificationBlue // thông báo Trung tâm dùng màu xanh
            "Nhắc giờ" -> NotificationGreen // nhắc giờ dùng màu xanh lá
            else -> NotificationOrange // loại còn lại dùng màu cam
        }

        val displayIcon = when (notification.type) { // chọn biểu tượng theo loại thông báo
            "Cảnh báo" -> "⚠" // biểu tượng cảnh báo
            "Trung tâm" -> "i" // biểu tượng thông tin
            "Nhắc giờ" -> "✓" // biểu tượng nhắc giờ
            else -> "!" // biểu tượng thông báo thông thường
        }

        BusNotification(
            id = notification.id, // lấy id thông báo từ Room
            isRead = notification.isRead, // lấy trạng thái đã đọc từ Room
            icon = displayIcon, // gán biểu tượng đã xác định
            title = notification.title, // lấy tiêu đề từ Room
            content = notification.message, // lấy nội dung từ Room
            time = notification.time, // lấy giờ tạo thông báo
            type = notification.type, // lấy loại thông báo
            color = displayColor // gán màu trạng thái
        )
    }

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
                    text = if (unreadCount > 0) { // kiểm tra có thông báo chưa đọc hay không
                        "Cảnh báo và điều hành tuyến xe • $unreadCount chưa đọc" // hiện số thông báo chưa đọc
                    } else {
                        "Cảnh báo và điều hành tuyến xe" // không hiện số khi đã đọc hết
                    },
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
            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng tiêu đề phủ toàn chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween, // đẩy tiêu đề và nút sang hai bên
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {
                Text(
                    text = "Danh sách thông báo", // tiêu đề danh sách
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                if (unreadCount > 0) { // chỉ hiện nút khi còn thông báo chưa đọc
                    Text(
                        text = "Đọc tất cả", // nút đánh dấu toàn bộ thông báo đã đọc
                        color = NotificationBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable {
                                notificationViewModel.markAllNotificationsAsRead() // cập nhật tất cả trong Room
                            }
                            .padding(horizontal = 4.dp, vertical = 6.dp) // tăng vùng bấm
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp)) // cách tiêu đề với danh sách

            if (filteredList.isEmpty()) { // kiểm tra tab hiện tại có thông báo hay không
                Card(
                    modifier = Modifier.fillMaxWidth(), // thẻ phủ toàn chiều ngang
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // nền trắng cho trạng thái rỗng
                    ),
                    shape = RoundedCornerShape(14.dp) // bo góc thẻ
                ) {
                    Text(
                        text = "Chưa có thông báo nào.", // thông báo khi Room đang rỗng
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp) // tạo lề cho nội dung
                    )
                }
            } else {
                filteredList.forEach { item -> // hiển thị từng thông báo đã lọc
                    NotificationItem(
                        item = item, // truyền dữ liệu thông báo vào thẻ
                        onClick = {
                            if (!item.isRead) { // chỉ cập nhật khi thông báo chưa được đọc
                                notificationViewModel.markNotificationAsRead(
                                    notificationId = item.id // gửi id xuống ViewModel để cập nhật Room
                                )
                            }
                        }
                    )
                }
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
fun NotificationItem(
    item: BusNotification, // dữ liệu của một thông báo
    onClick: () -> Unit // xử lý khi người dùng bấm vào thẻ
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // phủ ngang màn hình
            .padding(bottom = 10.dp) // cách các thẻ với nhau
            .clickable {
                onClick() // đánh dấu thông báo đã đọc khi bấm
            },
        colors = CardDefaults.cardColors(
            containerColor = if (item.isRead) {
                Color(0xFFF1F3F5) // nền xám nhạt khi thông báo đã đọc
            } else {
                Color.White // nền trắng khi thông báo chưa đọc
            }
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