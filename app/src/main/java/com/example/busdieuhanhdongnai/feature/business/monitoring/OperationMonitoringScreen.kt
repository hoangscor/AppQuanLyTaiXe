package com.example.busdieuhanhdongnai.feature.business.monitoring

import androidx.compose.foundation.background // tạo màu nền cho màn hình
import androidx.compose.foundation.layout.Arrangement // căn khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh của vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ lấy cạnh vùng an toàn cần thiết
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng không an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // tạo góc bo tròn
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Button // tạo nút lọc trạng thái
import androidx.compose.material3.ButtonDefaults // thiết lập màu cho nút
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu nền thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.getValue // đọc giá trị state
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi màn hình được tạo lại
import androidx.compose.runtime.setValue // cập nhật state
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val MonitoringBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val MonitoringBackground = Color(0xFFF6F8FC) // màu nền chung
private val MonitoringGreen = Color(0xFF1A9B54) // màu xanh cho trạng thái bình thường
private val MonitoringOrange = Color(0xFFFF9800) // màu cam cho trạng thái sắp chạy hoặc chậm
private val MonitoringRed = Color(0xFFE53935) // màu đỏ cho trạng thái sự cố
private val MonitoringLightBlue = Color(0xFFEAF3FF) // màu nền nút lọc chưa chọn

private data class OperationMonitoringUiModel( // tạo kiểu dữ liệu mẫu cho một chuyến đang được theo dõi
    val routeName: String, // lưu tên tuyến xe
    val scheduledTime: String, // lưu thời gian dự kiến
    val driverName: String, // lưu tên tài xế
    val vehiclePlate: String, // lưu biển số phương tiện
    val status: String, // lưu trạng thái vận hành
    val note: String // lưu mô tả ngắn của trạng thái
)

private val sampleMonitoringTrips = listOf( // tạo dữ liệu mẫu để dựng giao diện trước
    OperationMonitoringUiModel(
        routeName = "Tuyến 01: Bến xe A → Bến xe B", // tên tuyến mẫu thứ nhất
        scheduledTime = "07:00 - 08:00", // thời gian mẫu
        driverName = "Nguyễn Văn An", // tài xế mẫu
        vehiclePlate = "51B-123.45", // phương tiện mẫu
        status = "Đang chạy", // trạng thái hiện tại
        note = "Đang vận hành đúng kế hoạch" // mô tả tình trạng
    ),
    OperationMonitoringUiModel(
        routeName = "Tuyến 02: Bến xe B → Bến xe C", // tên tuyến mẫu thứ hai
        scheduledTime = "08:30 - 09:30", // thời gian mẫu
        driverName = "Trần Minh Tuấn", // tài xế mẫu
        vehiclePlate = "51B-234.56", // phương tiện mẫu
        status = "Đang chạy", // trạng thái hiện tại
        note = "Đang vận hành đúng kế hoạch" // mô tả tình trạng
    ),
    OperationMonitoringUiModel(
        routeName = "Tuyến 03: Bến xe A → Bến xe D", // tên tuyến mẫu thứ ba
        scheduledTime = "10:00 - 11:00", // thời gian mẫu
        driverName = "Lê Quốc Huy", // tài xế mẫu
        vehiclePlate = "60B-345.67", // phương tiện mẫu
        status = "Sắp chạy", // trạng thái chuẩn bị
        note = "Khởi hành sau khoảng 30 phút" // mô tả tình trạng
    ),
    OperationMonitoringUiModel(
        routeName = "Tuyến 04: Bến xe C → Bến xe A", // tên tuyến mẫu thứ tư
        scheduledTime = "11:30 - 12:30", // thời gian mẫu
        driverName = "Phạm Văn Bình", // tài xế mẫu
        vehiclePlate = "60B-456.78", // phương tiện mẫu
        status = "Sắp chạy", // trạng thái chuẩn bị
        note = "Đã có đủ tài xế và phương tiện" // mô tả tình trạng
    ),
    OperationMonitoringUiModel(
        routeName = "Tuyến 05: Bến xe D → Bến xe B", // tên tuyến mẫu thứ năm
        scheduledTime = "09:00 - 10:00", // thời gian mẫu
        driverName = "Nguyễn Văn Test", // tài xế mẫu
        vehiclePlate = "79B-160.04", // phương tiện mẫu
        status = "Chậm", // trạng thái cần chú ý
        note = "Chậm khoảng 15 phút so với kế hoạch" // mô tả tình trạng
    ),
    OperationMonitoringUiModel(
        routeName = "Tuyến 06: Bến xe A → Bến xe C", // tên tuyến mẫu thứ sáu
        scheduledTime = "09:30 - 10:30", // thời gian mẫu
        driverName = "Nguyễn Văn Chuẩn", // tài xế mẫu
        vehiclePlate = "77B-246.80", // phương tiện mẫu
        status = "Sự cố", // trạng thái cảnh báo
        note = "Phương tiện đang cần kiểm tra" // mô tả tình trạng
    )
)

@Composable
fun OperationMonitoringScreen( // tạo màn hình theo dõi hoạt động doanh nghiệp
    onBack: () -> Unit = {} // nhận hành động quay lại màn trước
) {
    var selectedFilter by rememberSaveable { // lưu bộ lọc trạng thái hiện tại
        mutableStateOf("Tất cả") // mặc định hiển thị toàn bộ chuyến
    }

    val monitoringFilters = listOf( // tạo danh sách bộ lọc giao diện
        "Tất cả", // hiển thị tất cả chuyến
        "Đang chạy", // chỉ hiển thị chuyến đang chạy
        "Sắp chạy", // chỉ hiển thị chuyến sắp chạy
        "Cần chú ý" // hiển thị chuyến chậm hoặc sự cố
    )

    val filteredTrips = sampleMonitoringTrips.filter { trip -> // tạo danh sách theo bộ lọc đang chọn
        when (selectedFilter) { // kiểm tra bộ lọc hiện tại
            "Đang chạy" -> trip.status == "Đang chạy" // lấy chuyến đang chạy
            "Sắp chạy" -> trip.status == "Sắp chạy" // lấy chuyến sắp chạy
            "Cần chú ý" -> trip.status == "Chậm" || trip.status == "Sự cố" // lấy chuyến bất thường
            else -> true // Tất cả thì giữ toàn bộ chuyến
        }
    }

    val runningCount = sampleMonitoringTrips.count { it.status == "Đang chạy" } // đếm chuyến đang chạy
    val upcomingCount = sampleMonitoringTrips.count { it.status == "Sắp chạy" } // đếm chuyến sắp chạy
    val delayedCount = sampleMonitoringTrips.count { it.status == "Chậm" } // đếm chuyến chậm
    val incidentCount = sampleMonitoringTrips.count { it.status == "Sự cố" } // đếm chuyến có sự cố

    Column( // tạo bố cục chính của màn hình
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(MonitoringBackground) // đặt màu nền chung
            .verticalScroll(rememberScrollState()) // cho phép cuộn dọc
    ) {

        Column( // tạo khu vực tiêu đề màu xanh
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(MonitoringBlue) // đặt nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top) // tránh camera và status bar
                )
                .padding(horizontal = 20.dp, vertical = 18.dp) // tạo khoảng cách trong header
        ) {

            Row( // xếp nút quay lại và tiêu đề theo chiều ngang
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {

                TextButton(
                    onClick = onBack // gọi callback quay lại
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // chữ màu trắng
                        fontSize = 22.sp // kích thước biểu tượng
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 4.dp) // tạo khoảng cách với nút quay lại
                ) {

                    Text(
                        text = "THEO DÕI HOẠT ĐỘNG", // tiêu đề màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // làm tiêu đề nổi bật
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp) // khoảng cách giữa tiêu đề và mô tả
                    )

                    Text(
                        text = "Giám sát tình trạng vận hành theo thời gian", // mô tả màn hình
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
                text = "TỔNG QUAN VẬN HÀNH", // tiêu đề phần tổng quan
                color = MonitoringBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước hàng chỉ số đầu
            )

            Row( // tạo hàng tổng quan thứ nhất
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
            ) {

                MonitoringSummaryCard(
                    title = "Đang chạy", // tên chỉ số
                    value = runningCount.toString(), // số chuyến đang chạy
                    description = "Chuyến đang vận hành", // mô tả chỉ số
                    valueColor = MonitoringGreen, // màu xanh
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )

                MonitoringSummaryCard(
                    title = "Sắp chạy", // tên chỉ số
                    value = upcomingCount.toString(), // số chuyến sắp chạy
                    description = "Chuyến chuẩn bị xuất bến", // mô tả chỉ số
                    valueColor = MonitoringBlue, // màu xanh chính
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách giữa hai hàng
            )

            Row( // tạo hàng tổng quan thứ hai
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
            ) {

                MonitoringSummaryCard(
                    title = "Chậm", // tên chỉ số
                    value = delayedCount.toString(), // số chuyến chậm
                    description = "Chậm so với kế hoạch", // mô tả chỉ số
                    valueColor = MonitoringOrange, // màu cam
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )

                MonitoringSummaryCard(
                    title = "Sự cố", // tên chỉ số
                    value = incidentCount.toString(), // số chuyến có sự cố
                    description = "Cần xử lý điều hành", // mô tả chỉ số
                    valueColor = MonitoringRed, // màu đỏ
                    modifier = Modifier.weight(1f) // chiếm nửa hàng
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách trước phần bộ lọc
            )

            Text(
                text = "TRẠNG THÁI HOẠT ĐỘNG", // tiêu đề bộ lọc
                color = MonitoringBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước hàng nút thứ nhất
            )

            Row( // hàng nút lọc thứ nhất
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(8.dp) // tạo khoảng cách giữa các nút
            ) {

                MonitoringFilterButton(
                    text = monitoringFilters[0], // Tất cả
                    selected = selectedFilter == monitoringFilters[0], // kiểm tra đang chọn hay không
                    onClick = {
                        selectedFilter = monitoringFilters[0] // chuyển bộ lọc sang Tất cả
                    },
                    modifier = Modifier.weight(1f) // chia đều chiều ngang
                )

                MonitoringFilterButton(
                    text = monitoringFilters[1], // Đang chạy
                    selected = selectedFilter == monitoringFilters[1], // kiểm tra đang chọn
                    onClick = {
                        selectedFilter = monitoringFilters[1] // chuyển sang Đang chạy
                    },
                    modifier = Modifier.weight(1f) // chia đều chiều ngang
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp) // khoảng cách giữa hai hàng nút lọc
            )

            Row( // hàng nút lọc thứ hai
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(8.dp) // khoảng cách giữa nút
            ) {

                MonitoringFilterButton(
                    text = monitoringFilters[2], // Sắp chạy
                    selected = selectedFilter == monitoringFilters[2], // kiểm tra trạng thái chọn
                    onClick = {
                        selectedFilter = monitoringFilters[2] // chuyển sang Sắp chạy
                    },
                    modifier = Modifier.weight(1f) // chia đều chiều ngang
                )

                MonitoringFilterButton(
                    text = monitoringFilters[3], // Cần chú ý
                    selected = selectedFilter == monitoringFilters[3], // kiểm tra trạng thái chọn
                    onClick = {
                        selectedFilter = monitoringFilters[3] // chuyển sang Cần chú ý
                    },
                    modifier = Modifier.weight(1f) // chia đều chiều ngang
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp) // khoảng cách trước danh sách
            )

            Text(
                text = "HOẠT ĐỘNG HIỆN TẠI", // tiêu đề danh sách chuyến
                color = MonitoringBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(6.dp) // khoảng cách nhỏ
            )

            Text(
                text = "TÌM THẤY ${filteredTrips.size} CHUYẾN", // hiển thị số chuyến theo bộ lọc
                color = Color.Gray, // màu xám
                fontSize = 12.sp, // kích thước chữ nhỏ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước danh sách
            )

            filteredTrips.forEach { trip -> // lần lượt hiển thị từng chuyến

                MonitoringTripCard(
                    trip = trip // truyền dữ liệu chuyến vào thẻ
                )

                Spacer(
                    modifier = Modifier.height(10.dp) // tạo khoảng cách giữa các chuyến
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp) // tạo khoảng trống cuối màn hình
            )
        }
    }
}

@Composable
private fun MonitoringSummaryCard( // tạo thẻ chỉ số tổng quan
    title: String, // tên chỉ số
    value: String, // giá trị chỉ số
    description: String, // mô tả chỉ số
    valueColor: Color, // màu của giá trị
    modifier: Modifier = Modifier // cho phép màn cha điều chỉnh kích thước
) {

    Card(
        modifier = modifier, // nhận kích thước từ màn cha
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp) // tạo khoảng cách trong thẻ
        ) {

            Text(
                text = title, // hiển thị tên chỉ số
                color = Color.DarkGray, // màu xám đậm
                fontSize = 13.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(6.dp) // khoảng cách trước số liệu
            )

            Text(
                text = value, // hiển thị số liệu
                color = valueColor, // dùng màu theo trạng thái
                fontSize = 25.sp, // làm số liệu nổi bật
                fontWeight = FontWeight.Bold // in đậm
            )

            Spacer(
                modifier = Modifier.height(5.dp) // khoảng cách trước mô tả
            )

            Text(
                text = description, // hiển thị mô tả
                color = Color.Gray, // màu xám
                fontSize = 11.sp // kích thước nhỏ
            )
        }
    }
}

@Composable
private fun MonitoringFilterButton( // tạo một nút lọc trạng thái
    text: String, // tên bộ lọc
    selected: Boolean, // xác định nút có đang được chọn hay không
    onClick: () -> Unit, // hành động khi bấm nút
    modifier: Modifier = Modifier // cho phép điều chỉnh kích thước
) {

    Button(
        onClick = onClick, // xử lý khi bấm
        modifier = modifier, // nhận modifier từ màn cha
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                MonitoringBlue // nền xanh khi được chọn
            } else {
                MonitoringLightBlue // nền xanh nhạt khi chưa chọn
            },
            contentColor = if (selected) {
                Color.White // chữ trắng khi được chọn
            } else {
                MonitoringBlue // chữ xanh khi chưa chọn
            }
        ),
        shape = RoundedCornerShape(12.dp) // bo góc nút
    ) {

        Text(
            text = text, // hiển thị tên bộ lọc
            fontSize = 12.sp, // kích thước chữ
            fontWeight = FontWeight.Bold // in đậm
        )
    }
}

@Composable
private fun MonitoringTripCard( // tạo thẻ hiển thị một chuyến đang theo dõi
    trip: OperationMonitoringUiModel // nhận dữ liệu chuyến
) {

    val statusColor = when (trip.status) { // chọn màu theo trạng thái chuyến
        "Đang chạy" -> MonitoringGreen // xanh lá cho chuyến bình thường
        "Sắp chạy" -> MonitoringBlue // xanh chính cho chuyến chuẩn bị
        "Chậm" -> MonitoringOrange // cam cho chuyến chậm
        else -> MonitoringRed // đỏ cho sự cố
    }

    Card(
        modifier = Modifier.fillMaxWidth(), // thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo góc thẻ
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp) // tạo khoảng cách bên trong thẻ
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween, // đẩy tuyến và trạng thái về hai phía
                verticalAlignment = Alignment.Top // căn theo phía trên
            ) {

                Text(
                    text = trip.routeName, // hiển thị tên tuyến
                    color = MonitoringBlue, // màu xanh
                    fontSize = 14.sp, // kích thước chữ
                    fontWeight = FontWeight.Bold, // in đậm
                    modifier = Modifier.weight(1f) // cho tên tuyến sử dụng phần chiều ngang còn lại
                )

                Text(
                    text = trip.status, // hiển thị trạng thái
                    color = statusColor, // dùng màu theo trạng thái
                    fontSize = 12.sp, // kích thước trạng thái
                    fontWeight = FontWeight.Bold, // in đậm
                    modifier = Modifier.padding(start = 10.dp) // cách tên tuyến
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách trước thông tin chi tiết
            )

            MonitoringInfoRow(
                label = "Giờ chạy", // tên thông tin
                value = trip.scheduledTime // thời gian chuyến
            )

            Spacer(
                modifier = Modifier.height(7.dp) // khoảng cách giữa các dòng
            )

            MonitoringInfoRow(
                label = "Tài xế", // tên thông tin
                value = trip.driverName // tên tài xế
            )

            Spacer(
                modifier = Modifier.height(7.dp) // khoảng cách giữa các dòng
            )

            MonitoringInfoRow(
                label = "Phương tiện", // tên thông tin
                value = trip.vehiclePlate // biển số xe
            )

            Spacer(
                modifier = Modifier.height(10.dp) // khoảng cách trước ghi chú
            )

            Text(
                text = trip.note, // hiển thị mô tả trạng thái
                color = statusColor, // sử dụng cùng màu trạng thái
                fontSize = 12.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // làm ghi chú nổi bật
            )
        }
    }
}

@Composable
private fun MonitoringInfoRow( // tạo một dòng thông tin chuyến
    label: String, // tên trường dữ liệu
    value: String // giá trị của trường dữ liệu
) {

    Row(
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        horizontalArrangement = Arrangement.SpaceBetween, // đẩy tên và giá trị sang hai phía
        verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
    ) {

        Text(
            text = label, // hiển thị tên trường
            color = Color.Gray, // màu xám
            fontSize = 13.sp // kích thước chữ
        )

        Text(
            text = value, // hiển thị giá trị
            color = Color.DarkGray, // màu xám đậm
            fontSize = 13.sp, // kích thước chữ
            fontWeight = FontWeight.Medium // làm giá trị rõ hơn
        )
    }
}