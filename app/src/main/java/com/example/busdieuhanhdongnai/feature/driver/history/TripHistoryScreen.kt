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
import androidx.compose.runtime.getValue // đọc state bằng từ khóa by
import androidx.lifecycle.viewmodel.compose.viewModel // lấy ViewModel trong Compose
import com.example.busdieuhanhdongnai.feature.driver.trip.TripViewModel // ViewModel lưu và đọc chuyến xe

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HistoryBlue = Color(0xFF0066CC) // màu xanh chính
private val HistoryBackground = Color(0xFFF6F8FC) // màu nền màn hình
private val HistoryGreen = Color(0xFF1A9B54) // màu hoàn thành
private val HistoryOrange = Color(0xFFFF8A00) // màu cảnh báo

data class TripHistoryItem( // dữ liệu một chuyến xe mẫu
    val date: String,
    val route: String,
    val time: String,
    val passengers: String,
    val status: String,
    val statusColor: Color
)

@Composable
fun TripHistoryScreen(
    onBack: () -> Unit = {}, // nhận lệnh quay lại
    tripViewModel: TripViewModel = viewModel() // lấy ViewModel để đọc Room
) {
    val roomTrips by tripViewModel.allTrips.collectAsState( // theo dõi dữ liệu Room thay đổi
        initial = emptyList() // lúc Room chưa trả dữ liệu thì dùng danh sách rỗng
    )

    val tripList = roomTrips.map { trip -> // đổi dữ liệu Room sang dữ liệu giao diện
        TripHistoryItem(
            date = trip.date, // lấy ngày từ Room
            route = trip.route, // lấy tuyến từ Room
            time = trip.time, // lấy thời gian từ Room
            passengers = "${trip.passengers} khách", // hiển thị số khách
            status = trip.status, // lấy trạng thái từ Room
            statusColor = if (trip.status == "Đã hoàn thành") { // chọn màu theo trạng thái
                HistoryGreen // màu xanh khi hoàn thành
            } else {
                HistoryOrange // màu cam cho trạng thái cần chú ý
            }
        )
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
                    value = "${tripList.count { it.status == "Đã hoàn thành" }} chuyến", // tự đếm chuyến xe hoàn thành nhé
                    color = HistoryGreen,
                    modifier = Modifier.weight(1f)
                )

                HistorySummaryCard(
                    title = "Cần chú ý",
                    value = "${tripList.count { it.status == "Chậm chuyến" }} chuyến", // tự đếm chuyến cần chú ý
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

            Spacer(modifier = Modifier.height(10.dp))

            tripList.forEach { trip -> // hiển thị danh sách chuyến xe từ Room
                TripHistoryCard(trip = trip)

                Spacer(modifier = Modifier.height(12.dp))
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = trip.route,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Divider()

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Thời gian",
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
        }
    }
}