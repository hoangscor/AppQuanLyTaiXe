package com.example.busdieuhanhdongnai.feature.driver.incident

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val IncidentHistoryBlue = Color(0xFF0066CC) // màu xanh chính của màn hình
private val IncidentHistoryBackground = Color(0xFFF6F8FC) // màu nền chung
private val IncidentPendingColor = Color(0xFFE67E22) // màu trạng thái chưa xử lý

@Composable
fun IncidentHistoryScreen(
    onBack: () -> Unit = {}, // quay lại màn trước
    incidentViewModel: IncidentViewModel = viewModel() // lấy dữ liệu sự cố từ Room
) {
    val savedIncidents by incidentViewModel.allIncidents.collectAsState(
        initial = emptyList() // dùng danh sách rỗng khi Room chưa trả dữ liệu
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IncidentHistoryBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(IncidentHistoryBlue)
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←", // nút quay lại
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier.clickable {
                    onBack() // trở về màn trước
                }
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Column {
                Text(
                    text = "BÁO CÁO SỰ CỐ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "Danh sách báo cáo đã gửi",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        if (savedIncidents.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Chưa có báo cáo sự cố nào.",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = savedIncidents,
                    key = { incident -> incident.id } // dùng id Room làm khóa danh sách
                ) { incident ->
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = incident.incidentType,
                                    color = IncidentHistoryBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )

                                Text(
                                    text = incident.status,
                                    color = IncidentPendingColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${incident.date} • ${incident.time}",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = incident.route,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Xe: ${incident.vehiclePlate}",
                                color = Color.DarkGray,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = incident.description,
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}