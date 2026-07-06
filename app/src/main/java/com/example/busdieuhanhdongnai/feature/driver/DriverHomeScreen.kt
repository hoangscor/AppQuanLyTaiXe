package com.example.busdieuhanhdongnai.feature.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState // theo dõi Flow dữ liệu Room trong Compose
import androidx.compose.runtime.getValue // cho phép đọc state bằng từ khóa by
import androidx.lifecycle.viewmodel.compose.viewModel // lấy ViewModel trong Compose
import com.example.busdieuhanhdongnai.feature.driver.trip.TripViewModel // dùng ViewModel đọc lịch sử chuyến
import java.time.LocalDate // lấy ngày hiện tại của thiết bị
import java.time.LocalTime // lấy giờ hiện tại để xác định chuyến tiếp theo
import java.time.Duration // tính số phút còn lại đến giờ xuất bến
import java.time.format.DateTimeFormatter // định dạng ngày giống dữ liệu Room
private val DriverBlue = Color(0xFF0066CC)
private val DriverBackground = Color(0xFFF6F8FC)
private val DriverGreen = Color(0xFF1A9B54)
private data class HomeScheduleTrip( // dữ liệu một chuyến hiển thị ở trang chủ
    val routeName: String, // tên tuyến ngắn hiển thị trên thẻ
    val routeDetail: String, // điểm đầu và điểm cuối của tuyến
    val scheduledTime: String // khung giờ dự kiến của chuyến
)
@Composable
fun DriverHomeScreen(
    onOpenSchedule: () -> Unit = {}, // mở màn lịch trình
    onOpenNotifications: () -> Unit = {}, // mở màn thông báo
    onOpenTripEntry: () -> Unit = {}, // mở màn nhập dữ liệu chuyến
    onOpenQrCheckIn: () -> Unit = {}, // mở màn quét QR
    onOpenIncidentReport: () -> Unit = {}, // mở màn báo cáo sự cố
    onOpenTripHistory: () -> Unit = {}, // mở màn nhật ký chuyến xe
    onOpenNextTrip: (String, String, String) -> Unit = { _, _, _ -> }, // mở chuyến kế tiếp cùng tuyến, xe và giờ đã chọn
    tripViewModel: TripViewModel = viewModel() // lấy dữ liệu chuyến xe từ Room

) {
    val roomTrips by tripViewModel.allTrips.collectAsState( // theo dõi danh sách chuyến từ Room
        initial = emptyList() // dùng danh sách rỗng khi Room chưa trả dữ liệu
    )

    val todayDatabaseFormat = LocalDate.now().format( // tạo ngày hiện tại theo định dạng lưu Room
        DateTimeFormatter.ofPattern("dd/MM/yyyy") // ví dụ 06/07/2026
    )

    val todayTrips = roomTrips.filter { trip -> // chỉ lấy các chuyến được lưu trong hôm nay
        trip.date == todayDatabaseFormat // so sánh ngày chuyến với ngày hiện tại
    }

    val completedTripsToday = todayTrips.filter { trip -> // lọc các chuyến đã hoàn thành hôm nay
        trip.status == "Đã hoàn thành" // chỉ lấy trạng thái hoàn thành
    }

    val completedTripCount = completedTripsToday.size // đếm số chuyến đã hoàn thành hôm nay

    val totalPassengersToday = completedTripsToday.sumOf { trip -> // cộng số khách của các chuyến đã hoàn thành
        trip.passengers.toIntOrNull() ?: 0 // đổi chuỗi số khách thành số nguyên, lỗi thì dùng 0
    }
    val currentTime = LocalTime.now() // lấy giờ hiện tại để tìm chuyến kế tiếp

    val completedScheduledTimesToday = completedTripsToday // lấy các chuyến hoàn thành trong hôm nay
        .map { trip -> trip.scheduledTime } // lấy khung giờ dự kiến của từng chuyến
        .toSet() // loại bỏ các khung giờ bị trùng

    val dailySchedule = listOf( // khai báo lịch trình dự kiến trong ngày
        HomeScheduleTrip( // chuyến 07 giờ
            routeName = "TUYẾN 01", // tên tuyến hiển thị
            routeDetail = "BẾN XE A  →  BẾN XE B", // hành trình chuyến
            scheduledTime = "07:00 - 08:00" // khung giờ dự kiến
        ),
        HomeScheduleTrip( // chuyến 08 giờ
            routeName = "TUYẾN 01", // tên tuyến hiển thị
            routeDetail = "BẾN XE A  →  BẾN XE B", // hành trình chuyến
            scheduledTime = "08:00 - 09:00" // khung giờ dự kiến
        ),
        HomeScheduleTrip( // chuyến 09 giờ
            routeName = "TUYẾN 01", // tên tuyến hiển thị
            routeDetail = "BẾN XE A  →  BẾN XE B", // hành trình chuyến
            scheduledTime = "09:00 - 10:00" // khung giờ dự kiến
        ),
        HomeScheduleTrip( // chuyến 10 giờ
            routeName = "TUYẾN 01", // tên tuyến hiển thị
            routeDetail = "BẾN XE A  →  BẾN XE B", // hành trình chuyến
            scheduledTime = "10:00 - 11:00" // khung giờ dự kiến
        ),
        HomeScheduleTrip( // chuyến 11 giờ
            routeName = "TUYẾN 01", // tên tuyến hiển thị
            routeDetail = "BẾN XE A  →  BẾN XE B", // hành trình chuyến
            scheduledTime = "11:00 - 12:00" // khung giờ dự kiến
        ),
        HomeScheduleTrip( // chuyến 12 giờ
            routeName = "TUYẾN 02", // tên tuyến hiển thị
            routeDetail = "BẾN XE B  →  BẾN XE C", // hành trình chuyến
            scheduledTime = "12:00 - 13:00" // khung giờ dự kiến
        )
    )

    val pendingScheduledTrips = dailySchedule.filter { scheduledTrip -> // chỉ lấy chuyến chưa hoàn thành
        scheduledTrip.scheduledTime !in completedScheduledTimesToday // loại chuyến đã hoàn thành hôm nay
    }

    val nextScheduledTrip = pendingScheduledTrips.firstOrNull { scheduledTrip -> // tìm chuyến chưa đến giờ gần nhất
        val departureTime = LocalTime.parse( // lấy giờ xuất bến từ khung giờ
            scheduledTrip.scheduledTime.substringBefore(" - ") // ví dụ lấy 08:00 từ 08:00 - 09:00
        )

        !currentTime.isAfter(departureTime) // chọn chuyến có giờ xuất bến từ hiện tại trở đi
    } ?: pendingScheduledTrips.firstOrNull() // nếu mọi chuyến còn lại đều trễ thì lấy chuyến trễ đầu tiên
    val nextTripRouteName = nextScheduledTrip?.routeName ?: "HOÀN THÀNH TẤT CẢ" // tên tuyến của chuyến kế tiếp hoặc thông báo hết chuyến

    val nextTripRouteDetail = nextScheduledTrip?.routeDetail ?: "Không còn chuyến nào trong ngày" // hành trình của chuyến kế tiếp hoặc thông báo thay thế

    val nextTripDepartureTime = nextScheduledTrip?.scheduledTime
        ?.substringBefore(" - ") ?: "--:--" // lấy giờ xuất bến, ví dụ 08:00

    val nextTripMessage = nextScheduledTrip?.let { scheduledTrip -> // tạo thông báo thời gian cho chuyến kế tiếp
        val departureTime = LocalTime.parse( // đổi giờ xuất bến từ chuỗi sang LocalTime
            scheduledTrip.scheduledTime.substringBefore(" - ") // lấy giờ đầu của khung lịch
        )

        val minutesUntilDeparture = Duration.between( // tính số phút từ hiện tại đến giờ xuất bến
            currentTime, // giờ hiện tại của thiết bị
            departureTime // giờ xuất bến của chuyến kế tiếp
        ).toMinutes() // đổi khoảng thời gian sang số phút

        when {
            minutesUntilDeparture > 0 -> "Còn $minutesUntilDeparture phút nữa đến giờ xuất bến" // chuyến chưa tới giờ
            minutesUntilDeparture == 0L -> "Đến giờ xuất bến" // đúng giờ xuất bến
            else -> "Đã trễ ${-minutesUntilDeparture} phút so với giờ xuất bến" // chuyến đang trễ
        }
    } ?: "Đã hoàn thành tất cả chuyến hôm nay" // hiển thị khi không còn chuyến chưa hoàn thành
    val nextTripRouteForEntry = when (nextScheduledTrip?.routeName) { // tạo tên tuyến đầy đủ cho màn nhập dữ liệu
        "TUYẾN 02" -> "Tuyến 02: Bến xe B → Bến xe C" // dữ liệu tuyến 02
        else -> "Tuyến 01: Bến xe A → Bến xe B" // dữ liệu tuyến 01 hoặc giá trị dự phòng
    }

    val nextTripVehiclePlate = when (nextScheduledTrip?.routeName) { // chọn biển số xe đúng với tuyến kế tiếp
        "TUYẾN 02" -> "60C-456.78" // biển số xe của tuyến 02
        else -> "51B-123.45" // biển số xe của tuyến 01 hoặc giá trị dự phòng
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriverBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DriverBlue)
                .padding(20.dp)
        ) {
            Text(
                text = "Xin chào, Nguyễn Văn A",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Mã tài xế: S18-12345",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Chuyến xe tiếp theo",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth() // cho thẻ phủ hết chiều ngang
                    .clickable(
                        enabled = nextScheduledTrip != null // chỉ bấm được khi còn chuyến chưa hoàn thành
                    ) {
                        nextScheduledTrip?.let { scheduledTrip -> // lấy chuyến kế tiếp đang tìm được
                            onOpenNextTrip(
                                nextTripRouteForEntry, // gửi tên tuyến đầy đủ sang màn nhập chuyến
                                nextTripVehiclePlate, // gửi biển số xe tương ứng
                                scheduledTrip.scheduledTime // gửi đúng khung giờ dự kiến
                            )
                        }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = nextTripRouteName, // hiển thị tuyến của chuyến gần nhất chưa hoàn thành
                        color = DriverBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = nextTripRouteDetail, // hiển thị điểm đi và điểm đến của chuyến kế tiếp
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Giờ xuất bến dự kiến")
                        Text(
                            text = nextTripDepartureTime, // hiển thị giờ xuất bến của chuyến kế tiếp
                            color = DriverGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = nextTripMessage, // hiển thị còn bao lâu hoặc đang trễ bao nhiêu phút
                        color = DriverBlue,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Chức năng",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DriverMenuCard(
                    icon = "▣",
                    title = "Quét QR /\nThẻ điện tử",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenQrCheckIn // bấm để mở màn quét QR
                )

                DriverMenuCard(
                    icon = "⌁",
                    title = "Nhập dữ liệu\nchuyến",
                    modifier = Modifier.weight(1f),
                    enabled = nextScheduledTrip != null, // chỉ cho bấm khi vẫn còn chuyến chưa hoàn thành
                    onClick = {
                        nextScheduledTrip?.let { scheduledTrip -> // chỉ mở khi còn chuyến chưa hoàn thành
                            onOpenNextTrip(
                                nextTripRouteForEntry, // gửi đúng tuyến kế tiếp
                                nextTripVehiclePlate, // gửi đúng biển số xe
                                scheduledTrip.scheduledTime // gửi đúng giờ dự kiến
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DriverMenuCard(
                    icon = "▤",
                    title = "Lịch trình\nngày",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenSchedule
                )
                DriverMenuCard(
                    icon = "🔔",
                    title = "Thông báo",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenNotifications // bấm để mở thông báo
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            DriverMenuCard(
                icon = "⚠",
                title = "Báo cáo sự cố",
                modifier = Modifier.fillMaxWidth(),
                onClick = onOpenIncidentReport
            )
            Spacer(modifier = Modifier.height(12.dp)) // tạo khoảng cách giữa hai nút

            DriverMenuCard(
                icon = "☷",
                title = "Nhật ký\nchuyến xe",
                modifier = Modifier.fillMaxWidth(),
                onClick = onOpenTripHistory // bấm để mở màn nhật ký chuyến xe
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Thống kê hôm nay",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    StatisticRow(
                        label = "Chuyến đã hoàn thành", // số chuyến hoàn thành trong hôm nay
                        value = completedTripCount.toString() // hiển thị số chuyến từ Room
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    StatisticRow(
                        label = "Tổng lượt khách", // tổng khách của các chuyến đã hoàn thành
                        value = totalPassengersToday.toString() // hiển thị tổng khách từ Room
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    StatisticRow(
                        label = "Chuyến đã lưu hôm nay", // tổng số chuyến đã được lưu trong hôm nay
                        value = todayTrips.size.toString() // hiển thị số bản ghi Room hôm nay
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
@Composable
fun DriverMenuCard(
    icon: String, // biểu tượng của chức năng
    title: String, // tên chức năng
    modifier: Modifier = Modifier, // modifier truyền từ bên ngoài
    enabled: Boolean = true, // xác định thẻ có được bấm hay không
    onClick: () -> Unit = {} // hành động khi tài xế bấm thẻ
) {
    val cardBackgroundColor = if (enabled) { // chọn màu nền theo trạng thái thẻ
        Color.White // nền trắng khi có thể sử dụng
    } else {
        Color(0xFFF0F2F5) // nền xám nhạt khi bị khóa
    }

    val contentColor = if (enabled) { // chọn màu chữ và biểu tượng theo trạng thái
        DriverBlue // màu xanh khi có thể sử dụng
    } else {
        Color.Gray // màu xám khi bị khóa
    }

    Card(
        modifier = modifier
            .height(112.dp) // giữ chiều cao thẻ chức năng
            .clickable(
                enabled = enabled // chặn thao tác bấm khi thẻ bị khóa
            ) {
                onClick() // chạy hành động khi thẻ đang được phép bấm
            },
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor // dùng màu nền theo trạng thái
        ),
        shape = RoundedCornerShape(14.dp) // bo góc thẻ
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // phủ toàn bộ thẻ
                .padding(12.dp), // tạo khoảng cách nội dung
            horizontalAlignment = Alignment.CenterHorizontally, // căn giữa ngang
            verticalArrangement = Arrangement.Center // căn giữa dọc
        ) {
            Text(
                text = icon, // hiển thị biểu tượng
                fontSize = 28.sp, // cỡ biểu tượng
                color = contentColor // đổi màu theo trạng thái
            )

            Spacer(modifier = Modifier.height(8.dp)) // khoảng cách biểu tượng và chữ

            Text(
                text = title, // hiển thị tên chức năng
                color = contentColor, // đổi màu theo trạng thái
                textAlign = TextAlign.Center, // căn giữa chữ
                fontWeight = FontWeight.Bold, // in đậm chữ
                fontSize = 13.sp // cỡ chữ
            )
        }
    }
}

@Composable
fun StatisticRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = value,
            color = DriverBlue,
            fontWeight = FontWeight.Bold
        )
    }
}