package com.example.busdieuhanhdongnai.feature.driver.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState // đọc Flow dữ liệu Room thành state Compose
import androidx.compose.runtime.getValue // đọc state bằng từ khóa by
import androidx.lifecycle.viewmodel.compose.viewModel // lấy TripViewModel trong Compose
import com.example.busdieuhanhdongnai.feature.driver.trip.TripViewModel // dùng ViewModel để đọc chuyến đã lưu
import java.time.LocalDate // lấy ngày hiện tại từ thiết bị
import java.time.LocalTime // lấy giờ hiện tại của thiết bị
import java.time.format.DateTimeFormatter // định dạng ngày để lưu và hiển thị
import java.util.Locale // dùng tiếng Việt khi hiển thị thứ trong tuần
private val ScheduleBlue = Color(0xFF0066CC)
private val ScheduleBackground = Color(0xFFF6F8FC)
private val ScheduleGreen = Color(0xFF1A9B54)
private val ScheduleRed = Color(0xFFE53935)
private data class ScheduleStatusInfo( // chứa trạng thái hiển thị của một chuyến
    val text: String, // chữ trạng thái hiển thị trên thẻ
    val color: Color, // màu của trạng thái
    val canSelect: Boolean // xác định tài xế có được chọn chuyến hay không
)

private fun calculateScheduleStatus( // tự tính trạng thái theo Room và giờ hiện tại
    scheduledTime: String, // khung giờ dự kiến, ví dụ 11:00 - 12:00
    completedScheduledTimes: Set<String>, // danh sách khung giờ đã hoàn thành hôm nay
    currentTime: LocalTime // giờ hiện tại của thiết bị
): ScheduleStatusInfo {
    val departureTime = LocalTime.parse( // lấy giờ xuất bến ở đầu khung giờ
        scheduledTime.substringBefore(" - ") // ví dụ lấy 11:00 từ 11:00 - 12:00
    )

    return when {
        scheduledTime in completedScheduledTimes -> { // chuyến đã được hoàn thành hôm nay
            ScheduleStatusInfo(
                text = "Đã thực hiện", // hiển thị trạng thái hoàn thành
                color = ScheduleGreen, // dùng màu xanh
                canSelect = false // khóa không cho chọn lại
            )
        }

        !currentTime.isBefore(departureTime) -> { // đã tới hoặc quá giờ xuất bến nhưng chưa hoàn thành
            ScheduleStatusInfo(
                text = "Chậm chuyến", // báo chuyến đang bị chậm
                color = ScheduleRed, // dùng màu đỏ
                canSelect = true // vẫn cho phép tài xế mở để thực hiện chuyến
            )
        }

        else -> { // chưa đến giờ xuất bến
            ScheduleStatusInfo(
                text = "Chưa đến giờ", // hiển thị trạng thái chờ
                color = Color.Gray, // dùng màu xám
                canSelect = true // cho phép tài xế chọn chuyến
            )
        }
    }
}

@Composable
fun ScheduleScreen( // hiển thị lịch trình và cho phép tài xế chọn chuyến
    onBack: () -> Unit = {}, // nhận lệnh quay lại màn trước
    onSelectTrip: (String, String, String) -> Unit = { _, _, _ -> }, // gửi tuyến, biển số xe và giờ dự kiến
    tripViewModel: TripViewModel = viewModel() // đọc các chuyến đã lưu từ Room
) {
    val roomTrips by tripViewModel.allTrips.collectAsState(
        initial = emptyList()
    ) // theo dõi danh sách chuyến Room thay đổi
    val today = LocalDate.now() // lấy ngày hiện tại của thiết bị
    val currentTime = LocalTime.now() // lấy giờ hiện tại của thiết bị để tính trạng thái chuyến

    val todayDatabaseFormat = today.format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    ) // định dạng ngày giống dữ liệu đang lưu trong Room

    val todayDisplayFormat = today.format(
        DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", Locale("vi", "VN"))
    ) // định dạng ngày để hiển thị trên giao diện

    val completedScheduledTimes = roomTrips
        .filter {
            it.status == "Đã hoàn thành" && // chỉ lấy chuyến đã hoàn thành
                    it.date == todayDatabaseFormat // chỉ lấy chuyến được lưu trong hôm nay
        }
        .map { it.scheduledTime } // lấy khung giờ dự kiến của từng chuyến
        .toSet() // loại bỏ các giờ bị trùng
    val trip07Status = calculateScheduleStatus( // tính trạng thái chuyến 07 giờ
        scheduledTime = "07:00 - 08:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    val trip08Status = calculateScheduleStatus( // tính trạng thái chuyến 08 giờ
        scheduledTime = "08:00 - 09:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    val trip09Status = calculateScheduleStatus( // tính trạng thái chuyến 09 giờ
        scheduledTime = "09:00 - 10:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    val trip10Status = calculateScheduleStatus( // tính trạng thái chuyến 10 giờ
        scheduledTime = "10:00 - 11:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    val trip11Status = calculateScheduleStatus( // tính trạng thái chuyến 11 giờ
        scheduledTime = "11:00 - 12:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    val trip12Status = calculateScheduleStatus( // tính trạng thái chuyến 12 giờ
        scheduledTime = "12:00 - 13:00", // khung giờ dự kiến của chuyến
        completedScheduledTimes = completedScheduledTimes, // danh sách chuyến đã xong hôm nay
        currentTime = currentTime // giờ hiện tại của thiết bị
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScheduleBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ScheduleBlue)
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(end = 14.dp)
                    .clickable { onBack() }
            )

            Column {
                Text(
                    text = "LỊCH TRÌNH NGÀY",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = todayDisplayFormat, // hiển thị thứ và ngày thực tế của thiết bị
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "TUYẾN 01",
                        color = ScheduleBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "BẾN XE A  →  BẾN XE B",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Danh sách chuyến trong ngày",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            ScheduleItem(
                time = "07:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                routeName = "Tuyến 01", // tên tuyến hiển thị trên lịch
                status = trip07Status.text, // lấy trạng thái tự tính của chuyến 07 giờ
                statusColor = trip07Status.color, // lấy màu trạng thái tự tính
                canSelect = trip07Status.canSelect, // chỉ cho chọn khi chuyến chưa hoàn thành
                onSelect = {
                    onSelectTrip(
                        "Tuyến 01: Bến xe A → Bến xe B", // gửi tuyến đã chọn sang màn nhập chuyến
                        "51B-123.45", // gửi biển số xe của chuyến
                        "07:00 - 08:00" // gửi khung giờ dự kiến của chuyến
                    )
                }
            )

            ScheduleItem(
                time = "08:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                routeName = "Tuyến 01", // tên tuyến hiển thị trên lịch
                status = trip08Status.text, // lấy trạng thái tự tính của chuyến 08 giờ
                statusColor = trip08Status.color, // lấy màu trạng thái tự tính
                canSelect = trip08Status.canSelect, // chỉ cho chọn khi chuyến chưa hoàn thành
                onSelect = {
                    onSelectTrip(
                        "Tuyến 01: Bến xe A → Bến xe B", // gửi tuyến đã chọn sang màn nhập chuyến
                        "51B-123.45", // gửi biển số xe của chuyến
                        "08:00 - 09:00" // gửi khung giờ dự kiến của chuyến
                    )
                }
            )
            ScheduleItem(
                time = "09:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                routeName = "Tuyến 01", // tên tuyến hiển thị trên lịch
                status = trip09Status.text, // lấy trạng thái tự tính của chuyến 09 giờ
                statusColor = trip09Status.color, // lấy màu trạng thái tự tính
                canSelect = trip09Status.canSelect, // chỉ cho chọn khi chuyến chưa hoàn thành
                onSelect = {
                    onSelectTrip(
                        "Tuyến 01: Bến xe A → Bến xe B", // gửi tuyến đã chọn sang màn nhập chuyến
                        "51B-123.45", // gửi biển số xe của chuyến
                        "09:00 - 10:00" // gửi khung giờ dự kiến của chuyến
                    )
                }
            )

            ScheduleItem(
                time = "10:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                routeName = "Tuyến 01", // tên tuyến hiển thị trên lịch
                status = trip10Status.text, // lấy trạng thái tự tính của chuyến 10 giờ
                statusColor = trip10Status.color, // lấy màu trạng thái tự tính
                canSelect = trip10Status.canSelect, // chỉ cho chọn khi chuyến chưa hoàn thành
                onSelect = {
                    onSelectTrip(
                        "Tuyến 01: Bến xe A → Bến xe B", // gửi tuyến đã chọn sang màn nhập chuyến
                        "51B-123.45", // gửi biển số xe của chuyến
                        "10:00 - 11:00" // gửi khung giờ dự kiến của chuyến
                    )
                }
            )

            ScheduleItem(
                time = "11:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                status = trip11Status.text, // lấy trạng thái tự tính cho chuyến 11 giờ
                statusColor = trip11Status.color, // lấy màu trạng thái tự tính
                canSelect = trip11Status.canSelect, // khóa hoặc cho chọn theo trạng thái
                onSelect = {
                    onSelectTrip(
                        "Tuyến 01: Bến xe A → Bến xe B", // gửi tên tuyến sang màn nhập chuyến
                        "51B-123.45", // gửi biển số xe sang màn nhập chuyến
                        "11:00 - 12:00" // gửi khung giờ dự kiến sang màn nhập chuyến
                    )
                }
            )

            ScheduleItem(
                time = "12:00", // giờ xuất bến dự kiến
                title = "Xuất bến", // tên hoạt động của chuyến
                routeName = "Tuyến 02", // hiển thị tuyến khác trên lịch trình
                status = trip12Status.text, // lấy trạng thái tự tính cho chuyến 12 giờ
                statusColor = trip12Status.color, // lấy màu trạng thái tự tính
                canSelect = trip12Status.canSelect, // khóa hoặc cho chọn theo trạng thái
                onSelect = {
                    onSelectTrip(
                        "Tuyến 02: Bến xe B → Bến xe C", // gửi tuyến khác sang màn nhập chuyến
                        "60C-456.78", // gửi biển số xe khác
                        "12:00 - 13:00" // gửi giờ dự kiến khác
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ScheduleItem( // hiển thị một chuyến trong lịch trình
    time: String, // giờ xuất bến dự kiến
    title: String, // tên hoạt động của chuyến
    routeName: String = "Tuyến 01", // tên tuyến hiển thị của chuyến
    status: String, // trạng thái chuyến xe
    statusColor: Color, // màu hiển thị trạng thái
    canSelect: Boolean = false, // xác định chuyến này có được phép chọn hay không
    onSelect: () -> Unit = {} // nhận lệnh khi tài xế chọn chuyến
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // phủ ngang màn hình
            .padding(bottom = 10.dp) // tạo khoảng cách với thẻ kế tiếp
            .clickable(
                enabled = canSelect // chỉ cho bấm khi chuyến được phép chọn
            ) {
                onSelect() // báo lại rằng tài xế đã chọn chuyến này
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                color = ScheduleBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )

                Text(
                    text = routeName, // hiển thị đúng tên tuyến của từng chuyến
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Text(
                text = status,
                color = statusColor,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}