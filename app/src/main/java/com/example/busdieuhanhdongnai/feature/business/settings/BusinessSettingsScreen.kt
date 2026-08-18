package com.example.busdieuhanhdongnai.feature.business.settings

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
import androidx.compose.foundation.layout.padding // tạo khoảng cách
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng camera
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu thẻ
import androidx.compose.material3.HorizontalDivider // tạo đường phân cách
import androidx.compose.material3.Switch // tạo công tắc bật tắt
import androidx.compose.material3.SwitchDefaults // thiết lập màu công tắc
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ
import androidx.compose.runtime.Composable // đánh dấu hàm Compose
import androidx.compose.runtime.getValue // đọc state
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi màn hình tạo lại
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // khai báo màu
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val SettingsBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val SettingsBackground = Color(0xFFF6F8FC) // màu nền chung
private val SettingsGreen = Color(0xFF1A9B54) // màu xanh trạng thái
private val SettingsLightBlue = Color(0xFFEAF3FF) // màu xanh nhạt
private val SettingsDivider = Color(0xFFEEEEEE) // màu đường phân cách

@Composable
fun BusinessSettingsScreen( // tạo màn hình cài đặt doanh nghiệp
    onBack: () -> Unit = {} // xử lý quay lại màn trước
) {

    var receiveOperationNotifications by rememberSaveable { // lưu trạng thái nhận thông báo điều hành
        mutableStateOf(true) // mặc định bật
    }

    var receiveWarningNotifications by rememberSaveable { // lưu trạng thái cảnh báo vận hành
        mutableStateOf(true) // mặc định bật
    }

    var soundEnabled by rememberSaveable { // lưu trạng thái âm thanh
        mutableStateOf(true) // mặc định bật
    }

    var autoSyncEnabled by rememberSaveable { // lưu trạng thái đồng bộ tự động
        mutableStateOf(true) // mặc định bật
    }

    Column( // bố cục chính của màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(SettingsBackground) // đặt màu nền
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {

        Column( // khu vực header màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(SettingsBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top // tránh camera và status bar
                    )
                )
                .padding(
                    horizontal = 18.dp, // khoảng cách hai bên
                    vertical = 18.dp // khoảng cách trên dưới
                )
        ) {

            Row( // hàng chứa nút quay lại và tiêu đề
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {

                TextButton(
                    onClick = onBack // quay lại màn hình trước
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // chữ trắng
                        fontSize = 22.sp // kích thước biểu tượng
                    )
                }

                Column {
                    Text(
                        text = "CÀI ĐẶT", // tiêu đề màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // chữ đậm
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp) // tạo khoảng cách nhỏ
                    )

                    Text(
                        text = "Thiết lập ứng dụng dành cho doanh nghiệp", // mô tả màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 13.sp // kích thước chữ
                    )
                }
            }
        }

        Column( // khu vực nội dung chính
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(18.dp) // khoảng cách xung quanh
        ) {

            Text(
                text = "TÀI KHOẢN", // tiêu đề nhóm tài khoản
                color = SettingsBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            SettingsMenuCard( // thẻ nhóm tài khoản
                items = listOf(
                    SettingsMenuItem(
                        icon = "🏢", // biểu tượng doanh nghiệp
                        title = "Thông tin doanh nghiệp", // tên chức năng
                        description = "Hồ sơ và thông tin đơn vị vận tải" // mô tả
                    ),
                    SettingsMenuItem(
                        icon = "👤", // biểu tượng người dùng
                        title = "Tài khoản quản trị", // tên chức năng
                        description = "Thông tin tài khoản đang đăng nhập" // mô tả
                    ),
                    SettingsMenuItem(
                        icon = "🔑", // biểu tượng khóa
                        title = "Đổi mật khẩu", // tên chức năng
                        description = "Cập nhật mật khẩu đăng nhập" // mô tả
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(22.dp) // tạo khoảng cách giữa các nhóm
            )

            Text(
                text = "THÔNG BÁO", // tiêu đề nhóm thông báo
                color = SettingsBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // thẻ cài đặt thông báo
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn bộ chiều ngang
                        .padding(horizontal = 16.dp) // khoảng cách hai bên
                ) {

                    SettingsSwitchRow(
                        icon = "🔔", // biểu tượng chuông
                        title = "Thông báo điều hành", // tên cài đặt
                        description = "Nhận thông tin điều hành mới", // mô tả
                        checked = receiveOperationNotifications, // trạng thái hiện tại
                        onCheckedChange = {
                            receiveOperationNotifications = it // cập nhật state UI
                        }
                    )

                    HorizontalDivider(
                        color = SettingsDivider // màu đường phân cách
                    )

                    SettingsSwitchRow(
                        icon = "⚠️", // biểu tượng cảnh báo
                        title = "Cảnh báo vận hành", // tên cài đặt
                        description = "Chậm chuyến, sự cố và bất thường", // mô tả
                        checked = receiveWarningNotifications, // trạng thái hiện tại
                        onCheckedChange = {
                            receiveWarningNotifications = it // cập nhật state UI
                        }
                    )

                    HorizontalDivider(
                        color = SettingsDivider // màu đường phân cách
                    )

                    SettingsSwitchRow(
                        icon = "🔊", // biểu tượng âm thanh
                        title = "Âm thanh thông báo", // tên cài đặt
                        description = "Phát âm thanh khi có thông báo mới", // mô tả
                        checked = soundEnabled, // trạng thái hiện tại
                        onCheckedChange = {
                            soundEnabled = it // cập nhật state UI
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách giữa các nhóm
            )

            Text(
                text = "DỮ LIỆU & ĐỒNG BỘ", // tiêu đề nhóm dữ liệu
                color = SettingsBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // thẻ cài đặt dữ liệu
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn bộ chiều ngang
                        .padding(horizontal = 16.dp) // khoảng cách hai bên
                ) {

                    SettingsSwitchRow(
                        icon = "🔄", // biểu tượng đồng bộ
                        title = "Tự động đồng bộ", // tên cài đặt
                        description = "Cập nhật dữ liệu vận hành tự động", // mô tả
                        checked = autoSyncEnabled, // trạng thái hiện tại
                        onCheckedChange = {
                            autoSyncEnabled = it // cập nhật state UI
                        }
                    )

                    HorizontalDivider(
                        color = SettingsDivider // đường phân cách
                    )

                    SettingsSimpleRow(
                        icon = "☁️", // biểu tượng dữ liệu
                        title = "Trạng thái đồng bộ", // tên trường
                        description = "Dữ liệu đã được đồng bộ", // mô tả
                        value = "Bình thường", // trạng thái mẫu
                        valueColor = SettingsGreen // màu xanh
                    )

                    HorizontalDivider(
                        color = SettingsDivider // đường phân cách
                    )

                    SettingsSimpleRow(
                        icon = "🕒", // biểu tượng thời gian
                        title = "Đồng bộ gần nhất", // tên trường
                        description = "Thời điểm dữ liệu được cập nhật", // mô tả
                        value = "Hôm nay, 11:00", // dữ liệu mẫu
                        valueColor = Color.DarkGray // màu giá trị
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách giữa nhóm
            )

            Text(
                text = "GIAO DIỆN", // tiêu đề nhóm giao diện
                color = SettingsBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // thẻ cài đặt giao diện
                modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn chiều ngang
                        .padding(horizontal = 16.dp) // khoảng cách hai bên
                ) {

                    SettingsSimpleRow(
                        icon = "🎨", // biểu tượng giao diện
                        title = "Chế độ hiển thị", // tên cài đặt
                        description = "Giao diện hiện tại của ứng dụng", // mô tả
                        value = "Sáng", // giá trị hiện tại
                        valueColor = SettingsBlue // màu xanh
                    )

                    HorizontalDivider(
                        color = SettingsDivider // đường phân cách
                    )

                    SettingsSimpleRow(
                        icon = "🌐", // biểu tượng ngôn ngữ
                        title = "Ngôn ngữ", // tên cài đặt
                        description = "Ngôn ngữ sử dụng trong ứng dụng", // mô tả
                        value = "Tiếng Việt", // giá trị hiện tại
                        valueColor = SettingsBlue // màu xanh
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách giữa nhóm
            )

            Text(
                text = "BẢO MẬT", // tiêu đề nhóm bảo mật
                color = SettingsBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            SettingsMenuCard(
                items = listOf(
                    SettingsMenuItem(
                        icon = "🛡️", // biểu tượng bảo mật
                        title = "Bảo mật tài khoản", // tên chức năng
                        description = "Quản lý các thiết lập bảo mật" // mô tả
                    ),
                    SettingsMenuItem(
                        icon = "📱", // biểu tượng thiết bị
                        title = "Thiết bị đăng nhập", // tên chức năng
                        description = "Kiểm tra các thiết bị đã đăng nhập" // mô tả
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách
            )

            Text(
                text = "THÔNG TIN ỨNG DỤNG", // tiêu đề nhóm ứng dụng
                color = SettingsBlue, // chữ xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // chữ đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // tạo khoảng cách
            )

            Card( // thẻ thông tin phiên bản
                modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = SettingsLightBlue // nền xanh nhạt
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn chiều ngang
                        .padding(16.dp) // khoảng cách bên trong
                ) {

                    Text(
                        text = "ỨNG DỤNG ĐIỀU HÀNH VẬN TẢI", // tên ứng dụng
                        color = SettingsBlue, // chữ xanh
                        fontSize = 15.sp, // kích thước chữ
                        fontWeight = FontWeight.Bold // chữ đậm
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp) // tạo khoảng cách
                    )

                    SettingsInformationLine(
                        label = "Phiên bản", // tên trường
                        value = "1.0.0" // giá trị
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp) // tạo khoảng cách
                    )

                    SettingsInformationLine(
                        label = "Môi trường", // tên trường
                        value = "Development" // giá trị
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp) // tạo khoảng cách
                    )

                    SettingsInformationLine(
                        label = "Đơn vị", // tên trường
                        value = "Doanh nghiệp vận tải" // giá trị
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp) // khoảng cách cuối màn hình
            )
        }
    }
}

private data class SettingsMenuItem( // kiểu dữ liệu tạm cho menu cài đặt
    val icon: String, // biểu tượng
    val title: String, // tiêu đề
    val description: String // mô tả
)

@Composable
private fun SettingsMenuCard( // tạo thẻ chứa các lựa chọn cài đặt
    items: List<SettingsMenuItem> // nhận danh sách mục
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        ),
        shape = RoundedCornerShape(14.dp) // bo góc
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // phủ toàn chiều ngang
                .padding(horizontal = 16.dp) // khoảng cách hai bên
        ) {

            items.forEachIndexed { index, item -> // lặp qua từng mục
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn chiều ngang
                        .padding(vertical = 15.dp), // tạo khoảng cách trên dưới
                    verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
                ) {

                    Text(
                        text = item.icon, // hiển thị biểu tượng
                        fontSize = 22.sp // kích thước biểu tượng
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp) // tạo khoảng cách với icon
                    ) {

                        Text(
                            text = item.title, // hiển thị tiêu đề
                            color = Color.DarkGray, // chữ xám đậm
                            fontSize = 14.sp, // kích thước chữ
                            fontWeight = FontWeight.Medium // độ đậm vừa
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp) // khoảng cách nhỏ
                        )

                        Text(
                            text = item.description, // hiển thị mô tả
                            color = Color.Gray, // chữ xám
                            fontSize = 12.sp // kích thước chữ
                        )
                    }
                }

                if (index < items.lastIndex) { // không kẻ dưới mục cuối
                    HorizontalDivider(
                        color = SettingsDivider // màu đường phân cách
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitchRow( // tạo một dòng cài đặt có công tắc
    icon: String, // biểu tượng
    title: String, // tiêu đề
    description: String, // mô tả
    checked: Boolean, // trạng thái công tắc
    onCheckedChange: (Boolean) -> Unit // callback thay đổi trạng thái
) {
    Row(
        modifier = Modifier
            .fillMaxWidth() // phủ toàn chiều ngang
            .padding(vertical = 13.dp), // khoảng cách trên dưới
        verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
    ) {

        Text(
            text = icon, // hiển thị biểu tượng
            fontSize = 21.sp // kích thước biểu tượng
        )

        Column(
            modifier = Modifier
                .padding(
                    start = 12.dp, // cách icon
                    end = 8.dp // cách switch
                )
        ) {

            Text(
                text = title, // hiển thị tiêu đề
                color = Color.DarkGray, // chữ xám đậm
                fontSize = 14.sp, // kích thước chữ
                fontWeight = FontWeight.Medium // độ đậm vừa
            )

            Spacer(
                modifier = Modifier.height(3.dp) // tạo khoảng cách nhỏ
            )

            Text(
                text = description, // hiển thị mô tả
                color = Color.Gray, // chữ xám
                fontSize = 11.sp // kích thước nhỏ
            )
        }

        Spacer(
            modifier = Modifier
                .padding(horizontal = 3.dp) // giữ khoảng cách
        )

        Switch(
            checked = checked, // trạng thái hiện tại
            onCheckedChange = onCheckedChange, // cập nhật state khi bấm
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White, // nút tròn trắng khi bật
                checkedTrackColor = SettingsBlue // nền xanh khi bật
            )
        )
    }
}

@Composable
private fun SettingsSimpleRow( // tạo dòng thông tin không có công tắc
    icon: String, // biểu tượng
    title: String, // tiêu đề
    description: String, // mô tả
    value: String, // giá trị
    valueColor: Color // màu giá trị
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // phủ toàn chiều ngang
            .padding(vertical = 14.dp) // tạo khoảng cách
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
        ) {

            Text(
                text = icon, // biểu tượng
                fontSize = 21.sp // kích thước icon
            )

            Column(
                modifier = Modifier.padding(start = 12.dp) // khoảng cách với icon
            ) {

                Text(
                    text = title, // tiêu đề
                    color = Color.DarkGray, // chữ xám đậm
                    fontSize = 14.sp, // kích thước chữ
                    fontWeight = FontWeight.Medium // độ đậm vừa
                )

                Spacer(
                    modifier = Modifier.height(3.dp) // khoảng cách nhỏ
                )

                Text(
                    text = description, // mô tả
                    color = Color.Gray, // chữ xám
                    fontSize = 11.sp // kích thước nhỏ
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp) // khoảng cách với giá trị
        )

        Text(
            text = value, // hiển thị giá trị
            color = valueColor, // màu giá trị truyền vào
            fontSize = 13.sp, // kích thước chữ
            fontWeight = FontWeight.Bold, // chữ đậm
            modifier = Modifier.padding(start = 33.dp) // căn thẳng với nội dung
        )
    }
}

@Composable
private fun SettingsInformationLine( // tạo dòng thông tin phiên bản
    label: String, // tên trường
    value: String // giá trị
) {
    Row(
        modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
        verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
    ) {

        Text(
            text = label, // hiển thị tên trường
            color = Color.Gray, // chữ xám
            fontSize = 12.sp // kích thước chữ
        )

        Text(
            text = "  $value", // hiển thị giá trị
            color = Color.DarkGray, // chữ xám đậm
            fontSize = 12.sp, // kích thước chữ
            fontWeight = FontWeight.Medium // độ đậm vừa
        )
    }
}