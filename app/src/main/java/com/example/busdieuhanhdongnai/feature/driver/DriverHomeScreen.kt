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
private val DriverBlue = Color(0xFF0066CC)
private val DriverBackground = Color(0xFFF6F8FC)
private val DriverGreen = Color(0xFF1A9B54)

@Composable
fun DriverHomeScreen(onOpenSchedule: () -> Unit = {}) { // nối lịch trình ngày
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
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "TUYẾN 01",
                        color = DriverBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "BẾN XE A  →  BẾN XE B",
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Giờ xuất bến dự kiến")
                        Text(
                            text = "07:00",
                            color = DriverGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Còn 12 phút nữa đến giờ xuất bến",
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
                    modifier = Modifier.weight(1f)
                )

                DriverMenuCard(
                    icon = "✎",
                    title = "Nhập dữ liệu\nchuyến",
                    modifier = Modifier.weight(1f)
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
                    modifier = Modifier.weight(1f)
                )
            }

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
                    StatisticRow("Số chuyến", "8")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    StatisticRow("Số lượt khách", "156")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    StatisticRow("Vé bán ra", "120")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    StatisticRow("Vé tháng", "36")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun DriverMenuCard(
    icon: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(112.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 28.sp,
                color = DriverBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = DriverBlue,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
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