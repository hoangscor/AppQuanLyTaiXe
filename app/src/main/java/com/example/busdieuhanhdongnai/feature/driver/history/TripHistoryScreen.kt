package com.example.busdieuhanhdongnai.feature.driver.history

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // đọc Flow Room thành state Compose
import androidx.compose.runtime.mutableStateOf // tạo trạng thái bộ lọc có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ bộ lọc khi xoay màn hình
import androidx.compose.runtime.setValue // cho phép cập nhật state bằng từ khóa by
import androidx.compose.runtime.getValue // đọc state bằng từ khóa by
import androidx.lifecycle.viewmodel.compose.viewModel // lấy ViewModel trong Compose
import com.example.busdieuhanhdongnai.feature.driver.trip.TripViewModel // ViewModel lưu và đọc chuyến xe

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate // lấy ngày hiện tại của thiết bị
import java.time.format.DateTimeFormatter // định dạng ngày giống dữ liệu trong Room

private val HistoryBlue = Color(0xFF0066CC) // màu xanh chính
private val HistoryBackground = Color(0xFFF6F8FC) // màu nền màn hình
private val HistoryGreen = Color(0xFF1A9B54) // màu hoàn thành
private val HistoryOrange = Color(0xFFFF8A00) // màu cảnh báo

data class TripHistoryItem( // dữ liệu một chuyến xe hiển thị trong nhật ký
    val date: String, // ngày thực hiện chuyến
    val route: String, // tên tuyến xe
    val vehiclePlate: String, // biển số xe thực hiện chuyến
    val scheduledTime: String, // khung giờ dự kiến của chuyến xe
    val time: String, // thời gian chạy chuyến
    val passengers: String, // số lượng hành khách
    val status: String, // trạng thái chuyến xe
    val statusColor: Color, // màu hiển thị trạng thái
    val note: String // ghi chú hoặc sự cố của chuyến
)

@Composable
fun TripHistoryScreen(
    onBack: () -> Unit = {}, // nhận lệnh quay lại
    tripViewModel: TripViewModel = viewModel() // lấy ViewModel để đọc Room
) {
    val roomTrips by tripViewModel.allTrips.collectAsState( // theo dõi dữ liệu Room thay đổi
        initial = emptyList() // lúc Room chưa trả dữ liệu thì dùng danh sách rỗng
    )
    val todayDate = LocalDate.now().format( // lấy ngày hiện tại theo ngày của thiết bị
        DateTimeFormatter.ofPattern("dd/MM/yyyy") // dùng đúng định dạng ngày đang lưu trong Room
    )
    val tripList = roomTrips.map { trip -> // đổi dữ liệu Room sang dữ liệu giao diện
        TripHistoryItem(
            date = trip.date, // lấy ngày từ Room
            route = trip.route, // lấy tuyến từ Room
            vehiclePlate = trip.vehiclePlate, // lấy biển số xe từ Room
            scheduledTime = trip.scheduledTime, // lấy khung giờ dự kiến đã lưu trong Room
            time = trip.time, // lấy thời gian từ Room
            passengers = "${trip.passengers} khách", // hiển thị số khách
            status = trip.status, // lấy trạng thái từ Room
            statusColor = if (trip.status == "Đã hoàn thành") { // chọn màu theo trạng thái
                HistoryGreen // màu xanh khi hoàn thành
            } else {
                HistoryOrange // màu cam cho trạng thái cần chú ý
            },
            note = trip.note // lấy ghi chú từ Room
        )
    }

    val todayTrips = tripList.filter { trip -> // tạo danh sách chỉ chứa chuyến của ngày hôm nay
        trip.date == todayDate // giữ lại chuyến có ngày trùng với ngày hiện tại
    }
    var selectedHistoryFilter by rememberSaveable { // lưu lựa chọn bộ lọc hiện tại
        mutableStateOf("Tất cả") // mặc định hiển thị toàn bộ lịch sử chuyến xe
    }

    val displayedTrips = when (selectedHistoryFilter) { // tạo danh sách theo bộ lọc được chọn
        "Hoàn thành" -> tripList.filter { trip -> // chỉ lấy các chuyến đã hoàn thành
            trip.status == "Đã hoàn thành" // kiểm tra đúng trạng thái đã hoàn thành
        }

        "Cần chú ý" -> tripList.filter { trip -> // chỉ lấy các chuyến có vấn đề cần kiểm tra
            trip.status == "Chậm chuyến" || // giữ lại chuyến bị chậm
                    trip.status == "Chưa hoàn thành" // giữ lại chuyến bị bỏ dở hoặc treo từ ngày cũ
        }

        else -> tripList // tab Tất cả hiển thị toàn bộ lịch sử
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HistoryBackground)
            .verticalScroll(rememberScrollState()) // cho phép cuộn màn hình
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HistoryBlue)
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier.clickable { onBack() } // bấm để quay lại
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Column {
                Text(
                    text = "NHẬT KÝ CHUYẾN XE",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Theo dõi các chuyến đã thực hiện",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "TỔNG QUAN HÔM NAY",
                color = HistoryBlue,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HistorySummaryCard(
                    title = "Đã hoàn thành",
                    value = "${todayTrips.count { trip -> // đếm trong danh sách chuyến hôm nay
                        trip.status == "Đã hoàn thành" // chỉ tính chuyến đã hoàn thành
                    }} chuyến",
                    color = HistoryGreen,
                    modifier = Modifier.weight(1f)
                )

                HistorySummaryCard(
                    title = "Cần chú ý",
                    value = "${todayTrips.count { trip -> // đếm các chuyến hôm nay cần tài xế chú ý
                        trip.status == "Chậm chuyến" || // tính chuyến bị chậm
                                trip.status == "Chưa hoàn thành" // tính chuyến cũ hoặc chuyến bị bỏ dở
                    }} chuyến",
                    color = HistoryOrange,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "DANH SÁCH CHUYẾN XE",
                color = HistoryBlue,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp)) // cách tiêu đề danh sách với bộ lọc

            Row(
                modifier = Modifier.fillMaxWidth(), // cho hàng bộ lọc phủ toàn chiều ngang
                horizontalArrangement = Arrangement.spacedBy(8.dp) // tạo khoảng cách đều giữa ba nút
            ) {
                HistoryFilterButton(
                    title = "Tất cả", // tên bộ lọc hiển thị toàn bộ chuyến xe
                    selected = selectedHistoryFilter == "Tất cả", // tô xanh khi đang chọn Tất cả
                    modifier = Modifier.weight(1f), // cho nút chiếm một phần ba chiều ngang
                    onClick = {
                        selectedHistoryFilter = "Tất cả" // chuyển danh sách sang toàn bộ chuyến
                    }
                )

                HistoryFilterButton(
                    title = "Hoàn thành", // tên bộ lọc chuyến đã hoàn thành
                    selected = selectedHistoryFilter == "Hoàn thành", // tô xanh khi đang chọn Hoàn thành
                    modifier = Modifier.weight(1f), // cho nút chiếm một phần ba chiều ngang
                    onClick = {
                        selectedHistoryFilter = "Hoàn thành" // chỉ hiện chuyến đã hoàn thành
                    }
                )

                HistoryFilterButton(
                    title = "Cần chú ý", // tên bộ lọc chuyến có vấn đề
                    selected = selectedHistoryFilter == "Cần chú ý", // tô xanh khi đang chọn Cần chú ý
                    modifier = Modifier.weight(1f), // cho nút chiếm một phần ba chiều ngang
                    onClick = {
                        selectedHistoryFilter = "Cần chú ý" // chỉ hiện chuyến chậm hoặc chưa hoàn thành
                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp)) // cách bộ lọc với danh sách chuyến xe


            if (displayedTrips.isEmpty()) { // kiểm tra danh sách của bộ lọc hiện tại có rỗng hay không
                Card(
                    modifier = Modifier.fillMaxWidth(), // cho thẻ trạng thái rỗng phủ toàn chiều ngang
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // dùng nền trắng giống các thẻ chuyến xe
                    ),
                    shape = RoundedCornerShape(14.dp) // bo góc thẻ trạng thái rỗng
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth() // cho nội dung phủ toàn chiều ngang
                            .padding(20.dp), // tạo khoảng cách bên trong thẻ
                        horizontalAlignment = Alignment.CenterHorizontally // căn giữa nội dung theo chiều ngang
                    ) {
                        Text(
                            text = "Chưa có chuyến xe nào", // thông báo khi lịch sử đang rỗng
                            color = HistoryBlue, // dùng màu xanh chính
                            fontSize = 15.sp, // đặt cỡ chữ nội dung
                            fontWeight = FontWeight.Bold // làm tiêu đề dễ nhìn
                        )

                        Spacer(modifier = Modifier.height(6.dp)) // cách tiêu đề với phần mô tả

                        Text(
                            text = "Các chuyến đã bắt đầu hoặc hoàn thành sẽ xuất hiện tại đây.", // hướng dẫn người dùng
                            color = Color.Gray, // dùng màu phụ cho phần mô tả
                            fontSize = 13.sp // đặt cỡ chữ mô tả
                        )
                    }
                }
            } else {
                displayedTrips.forEach { trip -> // hiển thị các chuyến phù hợp với bộ lọc hiện tại
                    TripHistoryCard(
                        trip = trip // truyền dữ liệu chuyến vào thẻ nhật ký
                    )

                    Spacer(modifier = Modifier.height(12.dp)) // tạo khoảng cách giữa các thẻ chuyến
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun HistorySummaryCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(96.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}
@Composable
fun HistoryFilterButton( // tạo một nút dùng chung cho bộ lọc Nhật ký
    title: String, // chữ hiển thị trên nút
    selected: Boolean, // xác định nút hiện có đang được chọn hay không
    modifier: Modifier = Modifier, // nhận kích thước được truyền từ bên ngoài
    onClick: () -> Unit // hành động khi tài xế bấm vào nút
) {
    Card(
        onClick = onClick, // cập nhật bộ lọc khi người dùng bấm
        modifier = modifier, // áp dụng kích thước nút từ hàng bên ngoài
        colors = CardDefaults.cardColors(
            containerColor = if (selected) { // kiểm tra nút đang được chọn
                HistoryBlue // dùng nền xanh cho nút đang chọn
            } else {
                Color.White // dùng nền trắng cho nút chưa chọn
            }
        ),
        shape = RoundedCornerShape(18.dp) // bo tròn góc nút bộ lọc
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // cho nội dung phủ toàn chiều ngang nút
                .padding(vertical = 10.dp), // tạo chiều cao cho nút
            horizontalAlignment = Alignment.CenterHorizontally // căn chữ giữa nút
        ) {
            Text(
                text = title, // hiển thị tên bộ lọc
                color = if (selected) { // đổi màu chữ theo trạng thái nút
                    Color.White // chữ trắng khi nút đang được chọn
                } else {
                    HistoryBlue // chữ xanh khi nút chưa được chọn
                },
                fontSize = 13.sp, // đặt cỡ chữ vừa với ba nút
                fontWeight = FontWeight.Bold // làm chữ nút nổi bật
            )
        }
    }
}
@Composable
fun TripHistoryCard(
    trip: TripHistoryItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = trip.date,
                    color = HistoryBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = trip.status,
                    color = trip.statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            if (trip.scheduledTime.isNotBlank()) { // chỉ hiện giờ dự kiến khi chuyến có dữ liệu
                Spacer(modifier = Modifier.height(10.dp)) // cách phần biển số với giờ dự kiến

                Row(
                    modifier = Modifier.fillMaxWidth(), // hàng phủ toàn chiều ngang
                    horizontalArrangement = Arrangement.SpaceBetween // đẩy nhãn và giờ sang hai bên
                ) {
                    Text(
                        text = "Giờ dự kiến", // nhãn khung giờ theo lịch trình
                        color = Color.Gray, // dùng màu phụ cho nhãn
                        fontSize = 13.sp // dùng cỡ chữ phụ
                    )

                    Text(
                        text = trip.scheduledTime, // hiển thị khung giờ dự kiến từ Room
                        fontSize = 13.sp, // dùng cỡ chữ nội dung
                        fontWeight = FontWeight.Medium // làm giờ dự kiến dễ đọc hơn
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = trip.route,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            if (trip.vehiclePlate.isNotBlank()) { // chỉ hiện biển số với chuyến có dữ liệu xe
                Spacer(modifier = Modifier.height(6.dp)) // tạo khoảng cách với tên tuyến

                Text(
                    text = "Xe: ${trip.vehiclePlate}", // hiển thị biển số xe đã lưu trong Room
                    color = Color.Gray, // dùng màu phụ cho thông tin xe
                    fontSize = 13.sp // dùng cỡ chữ phụ
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Divider()

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Thời gian thực tế", // phân biệt với khung giờ dự kiến
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Text(
                    text = trip.time,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Số khách",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Text(
                    text = trip.passengers,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (trip.note.isNotBlank()) { // chỉ hiện khung ghi chú khi chuyến có nội dung
                Spacer(modifier = Modifier.height(12.dp)) // tạo khoảng cách với dòng số khách

                Card(
                    modifier = Modifier.fillMaxWidth(), // cho khung ghi chú phủ toàn chiều ngang
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF3E0) // dùng nền cam nhạt để làm nổi bật sự cố
                    ),
                    shape = RoundedCornerShape(10.dp) // bo góc khung ghi chú
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp) // tạo khoảng cách nội dung bên trong khung
                    ) {
                        Text(
                            text = "GHI CHÚ / SỰ CỐ", // tiêu đề của phần ghi chú chuyến xe
                            color = HistoryOrange, // dùng màu cam cảnh báo
                            fontSize = 13.sp, // dùng cỡ chữ phụ
                            fontWeight = FontWeight.Bold // làm tiêu đề nổi bật
                        )

                        Spacer(modifier = Modifier.height(6.dp)) // cách tiêu đề với nội dung ghi chú

                        Text(
                            text = trip.note, // hiển thị nội dung ghi chú lấy từ Room
                            color = Color(0xFF444444), // dùng màu chữ đậm để dễ đọc
                            fontSize = 13.sp, // dùng cỡ chữ nội dung
                            fontWeight = FontWeight.Medium // làm nội dung rõ hơn
                        )
                    }
                }
            }
        }
    }
}