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

private val ScheduleBlue = Color(0xFF0066CC)
private val ScheduleBackground = Color(0xFFF6F8FC)
private val ScheduleGreen = Color(0xFF1A9B54)
private val ScheduleRed = Color(0xFFE53935)

@Composable
fun ScheduleScreen(onBack: () -> Unit = {}) {
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
                    text = "Thứ Hai, 23/05/2024",
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
                time = "07:00",
                title = "Xuất bến",
                status = "Đã thực hiện",
                statusColor = ScheduleGreen
            )

            ScheduleItem(
                time = "08:00",
                title = "Xuất bến",
                status = "Đã thực hiện",
                statusColor = ScheduleGreen
            )

            ScheduleItem(
                time = "09:00",
                title = "Xuất bến",
                status = "Đã thực hiện",
                statusColor = ScheduleGreen
            )

            ScheduleItem(
                time = "10:00",
                title = "Xuất bến",
                status = "Chậm chuyến",
                statusColor = ScheduleRed
            )

            ScheduleItem(
                time = "11:00",
                title = "Xuất bến",
                status = "Chưa đến giờ",
                statusColor = Color.Gray
            )

            ScheduleItem(
                time = "12:00",
                title = "Xuất bến",
                status = "Chưa đến giờ",
                statusColor = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ScheduleItem(
    time: String,
    title: String,
    status: String,
    statusColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
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
                    text = "Tuyến 01",
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