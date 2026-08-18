package com.example.busdieuhanhdongnai.feature.business.help

import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ áp dụng vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // tránh camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // áp dụng vùng an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc
import androidx.compose.foundation.verticalScroll // cho phép cuộn dọc
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ
import androidx.compose.runtime.Composable // đánh dấu hàm Compose
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh kích thước và bố cục
import androidx.compose.ui.graphics.Color // khai báo màu
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ


private val HelpBlue = Color(0xFF0066CC) // màu xanh chính của màn hình
private val HelpBackground = Color(0xFFF6F8FC) // màu nền chung
private val HelpLightBlue = Color(0xFFE8F2FF) // màu xanh nhạt cho thẻ hướng dẫn
private val HelpGreen = Color(0xFF1A9B54) // màu xanh trạng thái hỗ trợ


@Composable
fun BusinessHelpScreen( // tạo màn hình hướng dẫn sử dụng dành cho doanh nghiệp
    onBack: () -> Unit = {} // callback quay lại màn trước
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(HelpBackground) // đặt màu nền màn hình
            .verticalScroll(rememberScrollState()) // cho phép cuộn toàn màn hình
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth() // phần đầu phủ toàn chiều ngang
                .background(HelpBlue) // dùng nền xanh
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                ) // tránh camera và thanh trạng thái phía trên
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 14.dp,
                    bottom = 18.dp
                ) // tạo khoảng cách trong header
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {

                TextButton(
                    onClick = onBack // quay lại khi bấm mũi tên
                ) {
                    Text(
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // chữ màu trắng
                        fontSize = 22.sp // kích thước mũi tên
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 4.dp) // cách nhẹ khỏi nút quay lại
                ) {

                    Text(
                        text = "HƯỚNG DẪN SỬ DỤNG", // tiêu đề màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 20.sp, // kích thước tiêu đề
                        fontWeight = FontWeight.Bold // làm đậm
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp) // tạo khoảng cách nhỏ
                    )

                    Text(
                        text = "Hỗ trợ sử dụng hệ thống điều hành vận tải", // mô tả màn hình
                        color = Color.White, // chữ trắng
                        fontSize = 13.sp // kích thước mô tả
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth() // nội dung phủ ngang
                .padding(horizontal = 18.dp) // khoảng cách hai bên
        ) {

            Spacer(
                modifier = Modifier.height(20.dp) // khoảng cách sau header
            )

            Text(
                text = "BẮT ĐẦU NHANH", // tiêu đề nhóm
                color = HelpBlue, // màu xanh chính
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // làm đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách tới thẻ
            )

            Card(
                modifier = Modifier.fillMaxWidth(), // thẻ phủ toàn chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = HelpLightBlue // nền xanh nhạt
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {
                Column(
                    modifier = Modifier.padding(16.dp) // khoảng cách nội dung thẻ
                ) {

                    Text(
                        text = "🚍  QUY TRÌNH ĐIỀU HÀNH CƠ BẢN", // tiêu đề hướng dẫn nhanh
                        color = HelpBlue, // màu xanh
                        fontSize = 15.sp, // kích thước chữ
                        fontWeight = FontWeight.Bold // làm đậm
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp) // khoảng cách
                    )

                    HelpStep(
                        number = "1",
                        title = "Quản lý tài xế và phương tiện",
                        description = "Kiểm tra hồ sơ tài xế, trạng thái và danh sách phương tiện."
                    )

                    HelpStep(
                        number = "2",
                        title = "Phân công lịch chạy",
                        description = "Bố trí tài xế và phương tiện phù hợp cho từng chuyến."
                    )

                    HelpStep(
                        number = "3",
                        title = "Theo dõi hoạt động",
                        description = "Giám sát chuyến đang chạy, sắp chạy, chậm hoặc có sự cố."
                    )

                    HelpStep(
                        number = "4",
                        title = "Xử lý thông báo",
                        description = "Theo dõi cảnh báo và gửi thông tin điều hành khi cần thiết."
                    )

                    HelpStep(
                        number = "5",
                        title = "Xem báo cáo",
                        description = "Theo dõi số chuyến, tình trạng vận hành và hiệu quả hoạt động."
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp) // khoảng cách tới nhóm tiếp theo
            )

            Text(
                text = "HƯỚNG DẪN THEO CHỨC NĂNG", // tiêu đề nhóm chức năng
                color = HelpBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // làm đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp) // khoảng cách
            )

            HelpFeatureCard(
                icon = "👤",
                title = "Quản lý tài xế",
                description = "Theo dõi hồ sơ, giấy phép lái xe, phương tiện phụ trách và trạng thái làm việc."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "🚌",
                title = "Quản lý phương tiện",
                description = "Quản lý danh sách xe, trạng thái hoạt động, bảo trì và thông tin phương tiện."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "📅",
                title = "Phân công lịch chạy",
                description = "Sắp xếp tài xế và phương tiện cho từng tuyến, khung giờ và chuyến vận hành."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "📍",
                title = "Theo dõi hoạt động",
                description = "Theo dõi trạng thái chuyến theo thời gian và nhanh chóng nhận biết chuyến cần chú ý."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "🎫",
                title = "Khách hàng",
                description = "Quản lý thông tin khách hàng, vé xe và dữ liệu liên quan đến hành khách."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "📊",
                title = "Báo cáo & thống kê",
                description = "Theo dõi số liệu vận hành theo ngày, tuần hoặc tháng để đánh giá hoạt động."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpFeatureCard(
                icon = "🔔",
                title = "Thông báo điều hành",
                description = "Xem cảnh báo, thông báo hệ thống và các thông tin cần gửi tới tài xế."
            )


            Spacer(
                modifier = Modifier.height(24.dp) // khoảng cách tới câu hỏi thường gặp
            )

            Text(
                text = "CÂU HỎI THƯỜNG GẶP", // tiêu đề FAQ
                color = HelpBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // làm đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            HelpQuestionCard(
                question = "Làm sao để thêm tài xế mới?",
                answer = "Vào Quản lý tài xế → chọn THÊM TÀI XẾ và nhập đầy đủ thông tin."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpQuestionCard(
                question = "Làm sao để thêm phương tiện?",
                answer = "Vào Phương tiện → chọn THÊM PHƯƠNG TIỆN và nhập thông tin xe."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpQuestionCard(
                question = "Khi chuyến xe bị chậm thì kiểm tra ở đâu?",
                answer = "Mở Theo dõi hoạt động hoặc Thông báo điều hành để kiểm tra chuyến cần chú ý."
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            HelpQuestionCard(
                question = "Xem tình hình hoạt động tổng thể ở đâu?",
                answer = "Vào Báo cáo & thống kê để xem số chuyến và các chỉ số vận hành."
            )


            Spacer(
                modifier = Modifier.height(24.dp) // khoảng cách tới phần hỗ trợ
            )

            Text(
                text = "HỖ TRỢ", // tiêu đề hỗ trợ
                color = HelpBlue, // màu xanh
                fontSize = 18.sp, // kích thước chữ
                fontWeight = FontWeight.Bold // làm đậm
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(), // thẻ hỗ trợ phủ chiều ngang
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // nền trắng
                ),
                shape = RoundedCornerShape(14.dp) // bo góc
            ) {
                Column(
                    modifier = Modifier.padding(16.dp) // khoảng cách bên trong
                ) {

                    Text(
                        text = "🛟  Hỗ trợ kỹ thuật", // tiêu đề hỗ trợ
                        color = HelpBlue, // màu xanh
                        fontSize = 16.sp, // kích thước chữ
                        fontWeight = FontWeight.Bold // làm đậm
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Nếu gặp lỗi trong quá trình sử dụng, doanh nghiệp có thể liên hệ bộ phận hỗ trợ hệ thống.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "● Hệ thống đang hoạt động bình thường",
                        color = HelpGreen,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "Hotline: 1900 xxxx",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Email: hotro@phuongtrang.vn",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp) // khoảng trống cuối màn hình
            )
        }
    }
}


@Composable
private fun HelpStep( // tạo một bước trong quy trình hướng dẫn
    number: String, // số thứ tự
    title: String, // tên bước
    description: String // mô tả bước
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp), // khoảng cách giữa các bước
        verticalAlignment = Alignment.Top // căn nội dung lên trên
    ) {

        Text(
            text = "$number.", // hiển thị số bước
            color = HelpBlue, // màu xanh
            fontSize = 14.sp, // kích thước chữ
            fontWeight = FontWeight.Bold // làm đậm
        )

        Column(
            modifier = Modifier.padding(start = 10.dp) // cách số thứ tự
        ) {

            Text(
                text = title, // tên bước
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = description, // mô tả bước
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
private fun HelpFeatureCard( // tạo thẻ hướng dẫn cho từng chức năng
    icon: String, // biểu tượng chức năng
    title: String, // tên chức năng
    description: String // mô tả chức năng
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        ),
        shape = RoundedCornerShape(14.dp) // bo góc
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // khoảng cách bên trong
            verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
        ) {

            Text(
                text = icon, // hiển thị biểu tượng
                fontSize = 24.sp // kích thước biểu tượng
            )

            Column(
                modifier = Modifier.padding(start = 14.dp) // cách biểu tượng
            ) {

                Text(
                    text = title, // tên chức năng
                    color = HelpBlue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = description, // mô tả chức năng
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
private fun HelpQuestionCard( // tạo thẻ câu hỏi thường gặp
    question: String, // nội dung câu hỏi
    answer: String // nội dung trả lời
) {
    Card(
        modifier = Modifier.fillMaxWidth(), // phủ toàn chiều ngang
        colors = CardDefaults.cardColors(
            containerColor = Color.White // nền trắng
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // khoảng cách nội dung
        ) {

            Text(
                text = "❓  $question", // hiển thị câu hỏi
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = answer, // hiển thị câu trả lời
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}