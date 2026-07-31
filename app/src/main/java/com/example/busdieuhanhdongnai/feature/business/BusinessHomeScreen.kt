package com.example.busdieuhanhdongnai.feature.business // đặt file trong phân hệ doanh nghiệp

import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.clickable // cho phép bấm vào thẻ chức năng
import androidx.compose.foundation.layout.Arrangement // sắp xếp khoảng cách các thành phần
import androidx.compose.foundation.layout.Box // dùng để đặt nút ba gạch đè trên phần đầu màn hình
import androidx.compose.foundation.layout.Column // xếp thành phần theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp thành phần theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao thành phần
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn của màn hình
import androidx.compose.foundation.layout.width // đặt chiều rộng khoảng cách
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc thẻ
import androidx.compose.foundation.verticalScroll // cho phép cuộn màn hình
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu thẻ
import androidx.compose.material3.DrawerValue // dùng trạng thái đóng hoặc mở menu bên
import androidx.compose.material3.ModalDrawerSheet // tạo phần nội dung menu bên trái
import androidx.compose.material3.ModalNavigationDrawer // tạo menu trượt từ cạnh trái
import androidx.compose.material3.NavigationDrawerItem // tạo từng lựa chọn trong menu bên
import androidx.compose.material3.rememberDrawerState // ghi nhớ trạng thái menu bên
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ đăng xuất
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.rememberCoroutineScope // tạo coroutine để mở và đóng menu
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh kích thước và vị trí
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.text.style.TextAlign // căn chỉnh nội dung chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ
import kotlinx.coroutines.launch // chạy lệnh mở và đóng menu dạng suspend
import androidx.compose.foundation.layout.WindowInsets // lấy thông tin vùng hệ thống và camera
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh cần chừa khoảng an toàn
import androidx.compose.foundation.layout.only // chỉ áp dụng vùng an toàn cho cạnh được chọn
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn gồm thanh trạng thái và camera
import androidx.compose.foundation.layout.windowInsetsPadding // thêm khoảng cách theo vùng an toàn
private val BusinessBlue = Color(0xFF0066CC) // màu xanh chính của doanh nghiệp
private val BusinessBackground = Color(0xFFF6F8FC) // màu nền chung của màn hình
private val BusinessGreen = Color(0xFF1A9B54) // màu xanh dùng cho thống kê hoạt động
private val BusinessOrange = Color(0xFFFF9800) // màu cam dùng cho cảnh báo

@Composable
fun BusinessHomeScreen(
    onOpenDrivers: () -> Unit = {}, // mở màn quản lý tài xế
    onOpenVehicles: () -> Unit = {}, // mở màn quản lý phương tiện
    onOpenCustomers: () -> Unit = {}, // mở màn quản lý khách hàng và vé
    onOpenAssignments: () -> Unit = {}, // mở màn phân công lịch chạy
    onOpenReports: () -> Unit = {}, // mở màn báo cáo thống kê
    onOpenOperationMonitoring: () -> Unit = {}, // mở màn theo dõi vận hành
    onOpenBusinessNotifications: () -> Unit = {}, // mở thông báo điều hành doanh nghiệp
    onOpenBusinessInformation: () -> Unit = {}, // mở thông tin doanh nghiệp
    onOpenSettings: () -> Unit = {}, // mở cài đặt ứng dụng
    onOpenHelp: () -> Unit = {}, // mở hướng dẫn sử dụng
    onLogout: () -> Unit = {} // đăng xuất về màn đăng nhập
) {
    val drawerState = rememberDrawerState( // tạo trạng thái cho menu bên trái
        initialValue = DrawerValue.Closed // mặc định menu đang đóng
    )

    val coroutineScope = rememberCoroutineScope() // tạo coroutine điều khiển menu

    fun closeDrawerThenRun( // tạo hàm đóng menu rồi mới chạy chức năng
        action: () -> Unit // nhận chức năng cần thực hiện sau khi đóng menu
    ) {
        coroutineScope.launch { // chạy thao tác đóng menu trong coroutine
            drawerState.close() // đóng menu bên trái
            action() // chạy chức năng người dùng vừa chọn
        }
    }

    ModalNavigationDrawer( // tạo menu trượt từ cạnh trái màn hình
        drawerState = drawerState, // sử dụng trạng thái menu đã khai báo
        drawerContent = { // khai báo phần nội dung bên trong menu
            ModalDrawerSheet( // tạo khung menu bên trái
                modifier = Modifier.width(300.dp), // đặt chiều rộng menu
                drawerContainerColor = Color.White // dùng nền trắng cho menu
            ) {
                Column( // xếp các mục menu theo chiều dọc
                    modifier = Modifier
                        .fillMaxSize() // cho nội dung phủ toàn bộ menu
                        .padding(vertical = 18.dp) // tạo khoảng cách trên và dưới
                ) {
                    Column( // tạo khu vực tên doanh nghiệp ở đầu menu
                        modifier = Modifier
                            .fillMaxWidth() // phủ toàn bộ chiều ngang menu
                            .background(BusinessBlue) // dùng nền xanh thương hiệu
                            .padding(20.dp) // tạo khoảng cách bên trong đầu menu
                    ) {
                        Text( // hiển thị tên doanh nghiệp mẫu
                            text = "PHƯƠNG TRANG", // tên doanh nghiệp hiện tại
                            color = Color.White, // dùng chữ trắng trên nền xanh
                            fontSize = 20.sp, // đặt kích thước tên doanh nghiệp
                            fontWeight = FontWeight.Bold // in đậm tên doanh nghiệp
                        )

                        Spacer( // tạo khoảng cách với mô tả doanh nghiệp
                            modifier = Modifier.height(6.dp) // đặt chiều cao khoảng cách
                        )

                        Text( // hiển thị loại hình doanh nghiệp
                            text = "Doanh nghiệp vận tải hành khách", // mô tả doanh nghiệp
                            color = Color.White.copy(alpha = 0.9f), // dùng chữ trắng nhẹ
                            fontSize = 13.sp // đặt kích thước phần mô tả
                        )
                    }

                    Spacer( // cách đầu menu với mục Trang chủ
                        modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
                    )

                    BusinessDrawerItem( // tạo mục Trang chủ
                        icon = "🏠", // biểu tượng trang chủ
                        title = "Trang chủ", // tên mục menu
                        selected = true, // đánh dấu đây là màn hiện tại
                        onClick = { // xử lý khi bấm Trang chủ
                            coroutineScope.launch { // chạy đóng menu trong coroutine
                                drawerState.close() // chỉ đóng menu vì đang ở Trang chủ
                            }
                        }
                    )

                    BusinessDrawerItem( // tạo mục phân công lịch chạy
                        icon = "📅", // biểu tượng lịch
                        title = "Phân công lịch chạy", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenAssignments) // đóng menu rồi mở phân công
                        }
                    )

                    BusinessDrawerItem( // tạo mục quản lý phương tiện
                        icon = "🚌", // biểu tượng xe buýt
                        title = "Quản lý phương tiện", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenVehicles) // đóng menu rồi mở phương tiện
                        }
                    )
                    BusinessDrawerItem( // tạo mục quản lý khách hàng và vé
                        icon = "🎫", // biểu tượng vé hành khách
                        title = "Khách hàng", // tên chức năng trong menu
                        onClick = { // xử lý khi doanh nghiệp bấm vào mục
                            closeDrawerThenRun( // đóng menu trước khi mở màn mới
                                action = onOpenCustomers // gọi chức năng mở quản lý khách hàng
                            )
                        }
                    )
                    BusinessDrawerItem( // tạo mục theo dõi vận hành
                        icon = "📍", // biểu tượng theo dõi
                        title = "Theo dõi vận hành", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenOperationMonitoring) // đóng menu rồi mở theo dõi
                        }
                    )

                    BusinessDrawerItem( // tạo mục quản lý tài xế
                        icon = "👤", // biểu tượng tài xế
                        title = "Quản lý tài xế", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenDrivers) // đóng menu rồi mở tài xế
                        }
                    )

                    BusinessDrawerItem( // tạo mục báo cáo thống kê
                        icon = "📊", // biểu tượng báo cáo
                        title = "Báo cáo thống kê", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenReports) // đóng menu rồi mở báo cáo
                        }
                    )

                    BusinessDrawerItem( // tạo mục thông báo điều hành
                        icon = "🔔", // biểu tượng thông báo
                        title = "Thông báo điều hành", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenBusinessNotifications) // đóng menu rồi mở thông báo
                        }
                    )

                    BusinessDrawerItem( // tạo mục thông tin doanh nghiệp
                        icon = "🏢", // biểu tượng doanh nghiệp
                        title = "Thông tin doanh nghiệp", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenBusinessInformation) // đóng menu rồi mở thông tin
                        }
                    )

                    BusinessDrawerItem( // tạo mục cài đặt
                        icon = "⚙️", // biểu tượng cài đặt
                        title = "Cài đặt", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenSettings) // đóng menu rồi mở cài đặt
                        }
                    )

                    BusinessDrawerItem( // tạo mục hướng dẫn sử dụng
                        icon = "❓", // biểu tượng trợ giúp
                        title = "Hướng dẫn sử dụng", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onOpenHelp) // đóng menu rồi mở hướng dẫn
                        }
                    )

                    Spacer( // đẩy nút đăng xuất xuống gần cuối menu
                        modifier = Modifier.weight(1f) // dùng toàn bộ khoảng trống còn lại
                    )

                    BusinessDrawerItem( // tạo mục đăng xuất
                        icon = "🚪", // biểu tượng đăng xuất
                        title = "Đăng xuất", // tên mục menu
                        onClick = { // xử lý khi người dùng bấm
                            closeDrawerThenRun(onLogout) // đóng menu rồi đăng xuất
                        }
                    )
                }
            }
        }
    ) {
        Box( // tạo lớp chứa nội dung và nút ba gạch
            modifier = Modifier.fillMaxSize() // phủ toàn bộ màn hình
        ) {
            BusinessHomeContent(
                onOpenDrivers = onOpenDrivers, // truyền chức năng quản lý tài xế
                onOpenVehicles = onOpenVehicles, // truyền chức năng quản lý phương tiện
                onOpenCustomers = onOpenCustomers, // truyền chức năng khách hàng và vé
                onOpenAssignments = onOpenAssignments, // truyền chức năng phân công
                onOpenReports = onOpenReports, // truyền chức năng báo cáo
                onLogout = onLogout // truyền chức năng đăng xuất hiện tại
            )

            TextButton( // tạo nút ba gạch ở góc trên bên trái
                onClick = { // xử lý khi người dùng bấm nút
                    coroutineScope.launch { // chạy mở menu trong coroutine
                        drawerState.open() // mở menu bên trái
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopStart) // đặt nút ở góc trên bên trái
                    .windowInsetsPadding( // đẩy nút xuống dưới camera và thanh trạng thái
                        WindowInsets.safeDrawing.only( // chỉ lấy vùng an toàn cần thiết
                            WindowInsetsSides.Top // chỉ áp dụng khoảng an toàn phía trên
                        )
                    )
                    .padding( // căn chỉnh nút bên trong phần đầu màu xanh
                        start = 8.dp, // tạo khoảng cách với cạnh trái
                        top = 1.dp // tạo khoảng cách nhẹ sau vùng trạng thái
                    )
            ) {
                Text( // hiển thị biểu tượng ba gạch
                    text = "☰", // ký hiệu menu ba gạch
                    color = Color.White, // dùng màu trắng trên nền xanh
                    fontSize = 28.sp, // đặt kích thước biểu tượng
                    fontWeight = FontWeight.Bold // làm biểu tượng rõ hơn
                )
            }
        }
    }
}
@Composable
private fun BusinessHomeContent(
    onOpenDrivers: () -> Unit = {}, // mở màn quản lý tài xế
    onOpenVehicles: () -> Unit = {}, // mở màn quản lý phương tiện
    onOpenCustomers: () -> Unit = {}, // mở màn khách hàng và quản lý vé
    onOpenAssignments: () -> Unit = {}, // mở màn phân công vận hành
    onOpenReports: () -> Unit = {}, // mở màn báo cáo thống kê
    onLogout: () -> Unit = {} // quay về màn đăng nhập
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // cho màn hình phủ toàn bộ thiết bị
            .background(BusinessBackground) // dùng nền xám trắng
            .verticalScroll(rememberScrollState()) // cho phép cuộn khi nội dung dài
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // cho phần đầu phủ toàn chiều ngang
                .background(color = BusinessBlue) // giữ nền xanh phủ cả vùng thanh trạng thái
                .windowInsetsPadding( // đẩy nội dung xuống dưới camera và thanh trạng thái
                    WindowInsets.safeDrawing.only( // chỉ lấy vùng an toàn cần thiết
                        WindowInsetsSides.Top // chỉ chừa khoảng an toàn phía trên
                    )
                )
                .padding( // tạo khoảng cách cho nội dung phần đầu
                    start = 70.dp, // chừa bên trái cho nút ba gạch
                    end = 20.dp, // giữ lề phải của tiêu đề
                    top = 12.dp, // cách vùng an toàn phía trên một khoảng vừa phải
                    bottom = 18.dp // tạo khoảng cách phía dưới tiêu đề
                )
        ) {
            Text(
                text = "TRANG CHỦ DOANH NGHIỆP", // tiêu đề phân hệ doanh nghiệp
                color = Color.White, // dùng chữ trắng trên nền xanh
                fontSize = 21.sp, // đặt kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer(modifier = Modifier.height(6.dp)) // cách tiêu đề với mô tả

            Text(
                text = "Quản lý và theo dõi hoạt động vận tải", // mô tả màn hình
                color = Color.White.copy(alpha = 0.9f), // dùng chữ trắng nhẹ
                fontSize = 14.sp // đặt cỡ chữ mô tả
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth() // cho nội dung phủ toàn chiều ngang
                .padding(16.dp) // tạo lề cho nội dung chính
        ) {
            Text(
                text = "Tổng quan hôm nay", // tiêu đề khu vực tổng quan
                color = Color.Black, // dùng chữ đen
                fontSize = 19.sp, // đặt kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer(modifier = Modifier.height(12.dp)) // cách tiêu đề với thẻ thống kê

            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng thống kê phủ toàn chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween // chia khoảng cách hai thẻ
            ) {
                BusinessSummaryCard(
                    value = "0", // dữ liệu tài xế tạm thời
                    label = "Tài xế hoạt động", // nhãn thống kê tài xế
                    valueColor = BusinessGreen, // dùng màu xanh cho số liệu
                    modifier = Modifier.weight(1f) // cho thẻ chiếm một nửa hàng
                )

                Spacer(modifier = Modifier.width(12.dp)) // tạo khoảng cách giữa hai thẻ

                BusinessSummaryCard(
                    value = "0", // dữ liệu phương tiện tạm thời
                    label = "Phương tiện", // nhãn thống kê phương tiện
                    valueColor = BusinessBlue, // dùng màu xanh chính
                    modifier = Modifier.weight(1f) // cho thẻ chiếm một nửa hàng
                )
            }

            Spacer(modifier = Modifier.height(12.dp)) // cách hàng thống kê thứ nhất

            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng thống kê phủ toàn chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween // chia khoảng cách hai thẻ
            ) {
                BusinessSummaryCard(
                    value = "0", // dữ liệu chuyến hôm nay tạm thời
                    label = "Chuyến hôm nay", // nhãn thống kê chuyến
                    valueColor = BusinessGreen, // dùng màu xanh hoạt động
                    modifier = Modifier.weight(1f) // cho thẻ chiếm một nửa hàng
                )

                Spacer(modifier = Modifier.width(12.dp)) // tạo khoảng cách giữa hai thẻ

                BusinessSummaryCard(
                    value = "0", // dữ liệu cảnh báo tạm thời
                    label = "Cần chú ý", // nhãn thống kê cảnh báo
                    valueColor = BusinessOrange, // dùng màu cam cảnh báo
                    modifier = Modifier.weight(1f) // cho thẻ chiếm một nửa hàng
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // cách tổng quan với chức năng

            Text(
                text = "Chức năng quản lý", // tiêu đề khu vực chức năng
                color = Color.Black, // dùng chữ đen
                fontSize = 19.sp, // đặt kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer(modifier = Modifier.height(12.dp)) // cách tiêu đề với hàng chức năng

            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng chức năng phủ toàn chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween // chia khoảng cách hai thẻ
            ) {
                BusinessMenuCard(
                    icon = "👤", // biểu tượng quản lý tài xế
                    title = "Quản lý tài xế", // tên chức năng tài xế
                    description = "Hồ sơ và trạng thái", // mô tả chức năng tài xế
                    modifier = Modifier.weight(1f), // cho thẻ chiếm một nửa hàng
                    onClick = onOpenDrivers // xử lý khi bấm thẻ
                )

                Spacer(modifier = Modifier.width(12.dp)) // tạo khoảng cách giữa hai thẻ

                BusinessMenuCard(
                    icon = "🚌", // biểu tượng quản lý phương tiện
                    title = "Phương tiện", // tên chức năng phương tiện
                    description = "Danh sách đội xe", // mô tả chức năng phương tiện
                    modifier = Modifier.weight(1f), // cho thẻ chiếm một nửa hàng
                    onClick = onOpenVehicles // xử lý khi bấm thẻ
                )
            }

            Spacer(modifier = Modifier.height(12.dp)) // cách hai hàng chức năng

            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng chức năng phủ toàn chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween // chia khoảng cách hai thẻ
            ) {
                BusinessMenuCard(
                    icon = "📅", // biểu tượng phân công
                    title = "Phân công", // tên chức năng phân công
                    description = "Tài xế, xe và lịch chạy", // mô tả chức năng phân công
                    modifier = Modifier.weight(1f), // cho thẻ chiếm một nửa hàng
                    onClick = onOpenAssignments // xử lý khi bấm thẻ
                )

                Spacer(modifier = Modifier.width(12.dp)) // tạo khoảng cách giữa hai thẻ

                BusinessMenuCard(
                    icon = "📊", // biểu tượng báo cáo
                    title = "Báo cáo", // tên chức năng báo cáo
                    description = "Thống kê vận hành", // mô tả chức năng báo cáo
                    modifier = Modifier.weight(1f), // cho thẻ chiếm một nửa hàng
                    onClick = onOpenReports // xử lý khi bấm thẻ
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // cách chức năng với nút đăng xuất
            Spacer( // tạo khoảng cách với hàng chức năng phía trên
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            Card( // tạo thẻ chức năng Khách hàng toàn chiều ngang
                modifier = Modifier
                    .fillMaxWidth() // cho thẻ phủ toàn bộ chiều ngang
                    .height(112.dp) // đặt chiều cao thẻ
                    .clickable { // cho phép người dùng bấm vào toàn bộ thẻ
                        onOpenCustomers() // mở màn quản lý khách hàng và vé
                    },
                colors = CardDefaults.cardColors( // thiết lập màu của thẻ
                    containerColor = Color.White // sử dụng nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo tròn góc thẻ
            ) {
                Row( // xếp biểu tượng và nội dung theo chiều ngang
                    modifier = Modifier
                        .fillMaxSize() // cho nội dung phủ toàn bộ thẻ
                        .padding(horizontal = 20.dp), // tạo khoảng cách hai bên
                    verticalAlignment = Alignment.CenterVertically // căn giữa nội dung theo chiều dọc
                ) {
                    Text( // hiển thị biểu tượng vé
                        text = "🎫", // biểu tượng chức năng khách hàng
                        fontSize = 38.sp // đặt kích thước biểu tượng
                    )

                    Spacer( // tạo khoảng cách giữa biểu tượng và chữ
                        modifier = Modifier.width(18.dp) // đặt chiều rộng khoảng cách
                    )

                    Column( // xếp tiêu đề và mô tả theo chiều dọc
                        modifier = Modifier.weight(1f) // dùng phần chiều rộng còn lại
                    ) {
                        Text( // hiển thị tên chức năng
                            text = "Khách hàng", // tiêu đề của thẻ
                            color = BusinessBlue, // dùng màu xanh chính
                            fontSize = 16.sp, // đặt kích thước chữ
                            fontWeight = FontWeight.Bold // in đậm tiêu đề
                        )

                        Spacer( // tạo khoảng cách giữa tiêu đề và mô tả
                            modifier = Modifier.height(6.dp) // đặt chiều cao khoảng cách
                        )

                        Text( // hiển thị mô tả chức năng
                            text = "Vé xe và khảo sát hành khách", // mô tả ngắn gọn
                            color = Color.Gray, // dùng màu chữ phụ
                            fontSize = 13.sp // đặt kích thước chữ mô tả
                        )
                    }

                    Text( // hiển thị ký hiệu mở chi tiết
                        text = "›", // ký hiệu điều hướng sang màn sau
                        color = BusinessBlue, // dùng màu xanh chính
                        fontSize = 30.sp, // đặt kích thước ký hiệu
                        fontWeight = FontWeight.Medium // làm ký hiệu rõ hơn
                    )
                }
            }
            TextButton(
                onClick = onLogout, // quay lại màn hình đăng nhập
                modifier = Modifier.fillMaxWidth() // cho nút phủ toàn chiều ngang
            ) {
                Text(
                    text = "ĐĂNG XUẤT", // chữ trên nút đăng xuất
                    color = BusinessBlue, // dùng màu xanh chính
                    fontWeight = FontWeight.Bold // in đậm chữ nút
                )
            }

            Spacer(modifier = Modifier.height(20.dp)) // tạo khoảng trống cuối màn hình
        }
    }
}

@Composable
private fun BusinessSummaryCard(
    value: String, // số liệu cần hiển thị
    label: String, // nội dung mô tả số liệu
    valueColor: Color, // màu của số liệu
    modifier: Modifier = Modifier // nhận kích thước từ bên ngoài
) {
    Card(
        modifier = modifier.height(112.dp), // đặt chiều cao thẻ thống kê
        colors = CardDefaults.cardColors(
            containerColor = Color.White // dùng nền trắng cho thẻ
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc thẻ
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // cho nội dung phủ toàn bộ thẻ
                .padding(16.dp), // tạo khoảng cách trong thẻ
            verticalArrangement = Arrangement.Center // căn giữa nội dung theo chiều dọc
        ) {
            Text(
                text = value, // hiển thị số liệu
                color = valueColor, // dùng màu được truyền vào
                fontSize = 24.sp, // đặt cỡ chữ số liệu
                fontWeight = FontWeight.Bold // in đậm số liệu
            )

            Spacer(modifier = Modifier.height(6.dp)) // cách số liệu với nhãn

            Text(
                text = label, // hiển thị nhãn thống kê
                color = Color.Gray, // dùng màu xám cho nhãn
                fontSize = 13.sp // đặt cỡ chữ nhãn
            )
        }
    }
}

@Composable
private fun BusinessMenuCard(
    icon: String, // biểu tượng chức năng
    title: String, // tên chức năng
    description: String, // mô tả chức năng
    modifier: Modifier = Modifier, // nhận kích thước từ bên ngoài
    onClick: () -> Unit // xử lý sự kiện bấm
) {
    Card(
        modifier = modifier
            .height(150.dp) // đặt chiều cao thẻ chức năng
            .clickable(onClick = onClick), // cho phép bấm vào thẻ
        colors = CardDefaults.cardColors(
            containerColor = Color.White // dùng nền trắng cho thẻ
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc thẻ
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // cho nội dung phủ toàn bộ thẻ
                .padding(14.dp), // tạo khoảng cách trong thẻ
            horizontalAlignment = Alignment.CenterHorizontally, // căn giữa theo chiều ngang
            verticalArrangement = Arrangement.Center // căn giữa theo chiều dọc
        ) {
            Text(
                text = icon, // hiển thị biểu tượng
                fontSize = 31.sp // đặt kích thước biểu tượng
            )

            Spacer(modifier = Modifier.height(8.dp)) // cách biểu tượng với tiêu đề

            Text(
                text = title, // hiển thị tên chức năng
                color = BusinessBlue, // dùng màu xanh chính
                fontSize = 14.sp, // đặt cỡ chữ tiêu đề
                fontWeight = FontWeight.Bold, // in đậm tên chức năng
                textAlign = TextAlign.Center // căn giữa tên chức năng
            )

            Spacer(modifier = Modifier.height(5.dp)) // cách tiêu đề với mô tả

            Text(
                text = description, // hiển thị mô tả chức năng
                color = Color.Gray, // dùng màu xám cho mô tả
                fontSize = 11.sp, // đặt cỡ chữ mô tả
                textAlign = TextAlign.Center // căn giữa phần mô tả
            )
        }
    }
}
@Composable
private fun BusinessDrawerItem( // tạo một lựa chọn dùng chung trong menu bên
    icon: String, // biểu tượng của mục menu
    title: String, // tên của mục menu
    selected: Boolean = false, // xác định mục hiện tại có được chọn không
    onClick: () -> Unit // hành động khi người dùng bấm
) {
    NavigationDrawerItem( // tạo mục chuẩn của Material 3
        label = { // khai báo phần chữ của mục menu
            Text( // hiển thị tên chức năng
                text = title, // dùng tên được truyền vào
                fontSize = 14.sp, // đặt kích thước chữ menu
                fontWeight = if (selected) { // kiểm tra mục có đang được chọn
                    FontWeight.Bold // in đậm mục đang được chọn
                } else {
                    FontWeight.Medium // dùng độ đậm vừa cho mục bình thường
                }
            )
        },
        selected = selected, // truyền trạng thái đang chọn vào mục
        onClick = onClick, // chạy hành động khi người dùng bấm
        icon = { // khai báo phần biểu tượng bên trái
            Text( // hiển thị biểu tượng dạng ký tự
                text = icon, // dùng biểu tượng được truyền vào
                fontSize = 20.sp // đặt kích thước biểu tượng
            )
        },
        modifier = Modifier.padding( // tạo lề cho từng mục menu
            horizontal = 10.dp, // tạo lề trái và phải
            vertical = 1.dp // tạo khoảng cách nhẹ giữa các mục
        )
    )
}