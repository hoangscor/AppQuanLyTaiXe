package com.example.busdieuhanhdongnai.feature.business.notification


import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.layout.Arrangement // căn khoảng cách các thành phần
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ áp dụng cạnh được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // tránh camera và thanh trạng thái

import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng không an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Button // tạo nút chính
import androidx.compose.material3.ButtonDefaults // thiết lập màu nút
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.getValue // đọc giá trị state
import androidx.compose.runtime.mutableStateOf // tạo state thay đổi được
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi màn hình tạo lại
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh kích thước và bố cục
import androidx.compose.ui.graphics.Color // khai báo màu
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val NotificationBlue = Color(0xFF0066CC) // màu xanh chính
private val NotificationBackground = Color(0xFFF6F8FC) // màu nền chung
private val NotificationGreen = Color(0xFF1A9B54) // màu xanh trạng thái tốt
private val NotificationOrange = Color(0xFFFF9800) // màu cảnh báo
private val NotificationRed = Color(0xFFE53935) // màu khẩn cấp
private val NotificationLightBlue = Color(0xFFEAF3FF) // màu xanh nhạt cho bộ lọc

private data class BusinessNotificationUiModel( // dữ liệu mẫu phục vụ giao diện
    val title: String, // tiêu đề thông báo
    val recipient: String, // đối tượng nhận thông báo
    val time: String, // thời gian gửi
    val type: String, // loại thông báo
    val description: String, // nội dung mô tả
    val isUrgent: Boolean = false, // xác định thông báo khẩn cấp
    val isRead: Boolean = true // xác định trạng thái đã đọc
)

private val sampleBusinessNotifications = listOf( // danh sách dữ liệu mẫu
    BusinessNotificationUiModel(
        title = "Chậm chuyến Tuyến 05",
        recipient = "Tài xế Nguyễn Văn Test",
        time = "09:15",
        type = "Cảnh báo",
        description = "Chuyến đang chậm khoảng 15 phút so với kế hoạch.",
        isUrgent = true,
        isRead = false
    ),
    BusinessNotificationUiModel(
        title = "Điều chỉnh lịch chạy Tuyến 03",
        recipient = "Tài xế Lê Quốc Huy",
        time = "08:40",
        type = "Điều hành",
        description = "Thời gian xuất bến được điều chỉnh sang 10:30.",
        isRead = false
    ),
    BusinessNotificationUiModel(
        title = "Phương tiện 79B-160.04 cần kiểm tra",
        recipient = "Bộ phận vận hành",
        time = "08:10",
        type = "Cảnh báo",
        description = "Phương tiện cần được kiểm tra trước chuyến tiếp theo."
    ),
    BusinessNotificationUiModel(
        title = "Phân công chuyến hoàn tất",
        recipient = "Tài xế và điều hành",
        time = "07:30",
        type = "Điều hành",
        description = "Đã hoàn tất phân công tài xế và phương tiện cho lịch chạy."
    ),
    BusinessNotificationUiModel(
        title = "Đồng bộ dữ liệu thành công",
        recipient = "Doanh nghiệp",
        time = "07:15",
        type = "Hệ thống",
        description = "Dữ liệu vận hành đã được cập nhật thành công."
    ),
    BusinessNotificationUiModel(
        title = "Nhắc kiểm tra lịch chạy",
        recipient = "Bộ phận điều hành",
        time = "06:45",
        type = "Hệ thống",
        description = "Vui lòng kiểm tra các chuyến chưa được phân công."
    )
)

@Composable
fun BusinessNotificationScreen( // tạo màn thông báo điều hành
    onBack: () -> Unit = {} // nhận thao tác quay lại
) {
    var selectedNotificationType by rememberSaveable { // lưu bộ lọc đang được chọn
        mutableStateOf("Tất cả") // mặc định hiển thị tất cả thông báo
    }

    val notificationTypeOptions = listOf( // danh sách bộ lọc
        "Tất cả",
        "Điều hành",
        "Cảnh báo",
        "Hệ thống"
    )

    val filteredNotifications = if (selectedNotificationType == "Tất cả") {
        sampleBusinessNotifications // lấy toàn bộ thông báo
    } else {
        sampleBusinessNotifications.filter { notification ->
            notification.type == selectedNotificationType // lọc theo loại
        }
    }

    Column( // tạo bố cục toàn màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(NotificationBackground) // đặt nền chung
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {
        Column( // tạo phần đầu màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(NotificationBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top // chỉ tránh vùng camera phía trên
                    )
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 18.dp
                )
        ) {
            Row( // tạo hàng nút quay lại và tiêu đề
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onBack // quay về trang trước
                ) {
                    Text(
                        text = "←",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }

                Column {
                    Text(
                        text = "THÔNG BÁO ĐIỀU HÀNH",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Gửi và theo dõi thông tin vận hành",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Column( // tạo khu vực nội dung chính
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "TỔNG QUAN THÔNG BÁO",
                color = NotificationBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NotificationSummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "Tổng thông báo",
                    value = "6",
                    description = "Thông báo hiện có",
                    valueColor = NotificationBlue
                )

                NotificationSummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "Chưa đọc",
                    value = "2",
                    description = "Cần kiểm tra",
                    valueColor = NotificationOrange
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NotificationSummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "Khẩn cấp",
                    value = "1",
                    description = "Cần xử lý ngay",
                    valueColor = NotificationRed
                )

                NotificationSummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "Đã gửi",
                    value = "4",
                    description = "Thông báo điều hành",
                    valueColor = NotificationGreen
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button( // tạo nút tạo thông báo
                onClick = {
                    // UI ONLY: chức năng tạo thông báo sẽ làm ở giai đoạn chức năng
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NotificationBlue
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "+ TẠO THÔNG BÁO",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "LOẠI THÔNG BÁO",
                color = NotificationBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NotificationFilterButton(
                    modifier = Modifier.weight(1f),
                    text = notificationTypeOptions[0],
                    isSelected = selectedNotificationType == notificationTypeOptions[0],
                    onClick = {
                        selectedNotificationType = notificationTypeOptions[0]
                    }
                )

                NotificationFilterButton(
                    modifier = Modifier.weight(1f),
                    text = notificationTypeOptions[1],
                    isSelected = selectedNotificationType == notificationTypeOptions[1],
                    onClick = {
                        selectedNotificationType = notificationTypeOptions[1]
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NotificationFilterButton(
                    modifier = Modifier.weight(1f),
                    text = notificationTypeOptions[2],
                    isSelected = selectedNotificationType == notificationTypeOptions[2],
                    onClick = {
                        selectedNotificationType = notificationTypeOptions[2]
                    }
                )

                NotificationFilterButton(
                    modifier = Modifier.weight(1f),
                    text = notificationTypeOptions[3],
                    isSelected = selectedNotificationType == notificationTypeOptions[3],
                    onClick = {
                        selectedNotificationType = notificationTypeOptions[3]
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "DANH SÁCH THÔNG BÁO",
                color = NotificationBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "TÌM THẤY ${filteredNotifications.size} THÔNG BÁO",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            filteredNotifications.forEach { notification ->
                BusinessNotificationCard(
                    notification = notification
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Composable
private fun NotificationSummaryCard( // tạo thẻ tổng quan
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    description: String,
    valueColor: Color
) {
    Card(
        modifier = modifier
            .height(122.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.DarkGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = value,
                color = valueColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = description,
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun NotificationFilterButton( // tạo nút lọc thông báo
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(43.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                NotificationBlue
            } else {
                NotificationLightBlue
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) {
                Color.White
            } else {
                NotificationBlue
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BusinessNotificationCard( // tạo thẻ từng thông báo
    notification: BusinessNotificationUiModel
) {
    val typeColor = when (notification.type) { // chọn màu dựa theo loại thông báo
        "Cảnh báo" -> NotificationOrange
        "Điều hành" -> NotificationBlue
        else -> NotificationGreen
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = notification.title,
                    modifier = Modifier.weight(1f),
                    color = NotificationBlue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                Text(
                    text = notification.type,
                    color = typeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(9.dp)
            )

            Text(
                text = notification.description,
                color = Color.DarkGray,
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            NotificationInfoRow(
                label = "Gửi đến",
                value = notification.recipient
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            NotificationInfoRow(
                label = "Thời gian",
                value = notification.time
            )

            if (notification.isUrgent || !notification.isRead) {
                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = when {
                        notification.isUrgent -> "CẦN XỬ LÝ"
                        !notification.isRead -> "CHƯA ĐỌC"
                        else -> ""
                    },
                    color = if (notification.isUrgent) {
                        NotificationRed
                    } else {
                        NotificationOrange
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun NotificationInfoRow( // tạo dòng thông tin phụ
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )

        Text(
            text = value,
            color = Color.DarkGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}