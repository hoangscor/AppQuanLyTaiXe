package com.example.busdieuhanhdongnai.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BusBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val BusinessGreen = Color(0xFF1A9B54) // màu nút đăng nhập doanh nghiệp
private val ScreenBackground = Color(0xFFF6F8FC) // màu nền màn hình đăng nhập

@Composable
fun LoginScreen(
    onDriverLogin: () -> Unit = {}, // xử lý đăng nhập vào phân hệ tài xế
    onBusinessLogin: () -> Unit = {} // xử lý đăng nhập vào phân hệ doanh nghiệp
) {
    var account by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚌",
            fontSize = 68.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ỨNG DỤNG QUẢN LÝ\nĐIỀU HÀNH XE BUÝT",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = BusBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Số hóa - Minh bạch - Hiệu quả",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(42.dp))

        OutlinedTextField(
            value = account,
            onValueChange = { account = it },
            label = { Text("Số điện thoại / Tài khoản") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onDriverLogin, // mở phân hệ tài xế
            modifier = Modifier
                .fillMaxWidth() // cho nút phủ toàn chiều ngang
                .height(52.dp), // đặt chiều cao nút
            colors = ButtonDefaults.buttonColors(
                containerColor = BusBlue // dùng màu xanh cho tài xế
            ),
            shape = RoundedCornerShape(10.dp) // bo góc nút
        ) {
            Text(
                text = "ĐĂNG NHẬP TÀI XẾ", // phân biệt nút đăng nhập tài xế
                fontWeight = FontWeight.Bold // in đậm chữ nút
            )
        }

        Spacer(modifier = Modifier.height(12.dp)) // cách hai nút đăng nhập

        Button(
            onClick = onBusinessLogin, // mở phân hệ doanh nghiệp
            modifier = Modifier
                .fillMaxWidth() // cho nút phủ toàn chiều ngang
                .height(52.dp), // đặt chiều cao nút
            colors = ButtonDefaults.buttonColors(
                containerColor = BusinessGreen // dùng màu xanh lá cho doanh nghiệp
            ),
            shape = RoundedCornerShape(10.dp) // bo góc nút
        ) {
            Text(
                text = "ĐĂNG NHẬP DOANH NGHIỆP", // tên nút phân hệ doanh nghiệp
                fontWeight = FontWeight.Bold // in đậm chữ nút
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // cách nút với quên mật khẩu

        TextButton(onClick = { }) {
            Text("Quên mật khẩu?")
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Phiên bản 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}