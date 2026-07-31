package com.example.busdieuhanhdongnai.feature.business.customer // đặt file trong phần khách hàng doanh nghiệp

import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.layout.Arrangement // sắp xếp khoảng cách các thành phần
import androidx.compose.foundation.layout.Column // xếp thành phần theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp thành phần theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của hệ thống
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh cần chừa khoảng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao thành phần
import androidx.compose.foundation.layout.only // chỉ dùng cạnh vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn camera và thanh trạng thái
import androidx.compose.foundation.layout.width // đặt chiều rộng thành phần
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi camera
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn màn hình
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc
import androidx.compose.foundation.text.KeyboardOptions // thiết lập bàn phím nhập số
import androidx.compose.foundation.verticalScroll // cho phép cuộn màn hình
import androidx.compose.material3.Button // tạo nút bấm
import androidx.compose.material3.ButtonDefaults // thiết lập màu nút
import androidx.compose.material3.Card // tạo thẻ nội dung
import androidx.compose.material3.CardDefaults // thiết lập màu thẻ
import androidx.compose.material3.OutlinedTextField // tạo ô nhập dữ liệu
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.getValue // đọc state bằng từ khóa by
import androidx.compose.runtime.LaunchedEffect // đồng bộ dữ liệu Room vào các ô hiển thị
import androidx.compose.runtime.collectAsState // chuyển Flow Room thành state của Compose
import androidx.lifecycle.viewmodel.compose.viewModel // lấy CustomerViewModel trong màn hình Compose
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ state khi xoay màn hình
import androidx.compose.runtime.setValue // cập nhật state bằng từ khóa by
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.text.input.KeyboardType // chọn kiểu bàn phím số
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val CustomerBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val CustomerGreen = Color(0xFF1A9B54) // màu xanh cho số liệu tốt
private val CustomerOrange = Color(0xFFFF9800) // màu cam cho dữ liệu khảo sát
private val CustomerBackground = Color(0xFFF6F8FC) // màu nền màn hình
private val CustomerLightBlue = Color(0xFFEAF4FF) // màu nền xanh nhạt
private val CustomerLightOrange = Color(0xFFFFF3E0) // màu nền cam nhạt

@Composable
fun CustomerManagementScreen( // tạo màn quản lý khách hàng
    onBack: () -> Unit = {}, // nhận hành động quay lại màn trước
    customerViewModel: CustomerViewModel = viewModel() // nhận ViewModel quản lý dữ liệu khách hàng
) {
    var isEditing by rememberSaveable { mutableStateOf(false) } // lưu trạng thái đang sửa dữ liệu

    var singleTicketCount by rememberSaveable { mutableStateOf("218") } // lưu số khách dùng vé lượt
    var monthlyTicketCount by rememberSaveable { mutableStateOf("164") } // lưu số khách dùng vé tháng
    var freeTicketCount by rememberSaveable { mutableStateOf("54") } // lưu số khách dùng vé miễn phí

    var workerSurveyCount by rememberSaveable { mutableStateOf("87") } // lưu số người thuộc nhóm công nhân khảo sát
    var studentSurveyCount by rememberSaveable { mutableStateOf("123") } // lưu số học sinh sinh viên khảo sát
    val savedCustomerStatistics by customerViewModel.customerStatistics.collectAsState( // theo dõi dữ liệu Room
        initial = null // dùng null trong lúc Room chưa trả dữ liệu
    )

    LaunchedEffect(savedCustomerStatistics) { // chạy lại khi dữ liệu Room thay đổi
        savedCustomerStatistics?.let { customer -> // chỉ cập nhật khi Room đã có bản ghi
            singleTicketCount = customer.singleTicketCount.toString() // lấy số vé lượt từ Room
            monthlyTicketCount = customer.monthlyTicketCount.toString() // lấy số vé tháng từ Room
            freeTicketCount = customer.freeTicketCount.toString() // lấy số vé miễn phí từ Room
            workerSurveyCount = customer.workerSurveyCount.toString() // lấy khảo sát công nhân từ Room
            studentSurveyCount = customer.studentSurveyCount.toString() // lấy khảo sát học sinh sinh viên từ Room
        }
    }
    val totalPassengerCount = // tính tổng số hành khách thực tế
        (singleTicketCount.toIntOrNull() ?: 0) + // cộng số khách vé lượt
                (monthlyTicketCount.toIntOrNull() ?: 0) + // cộng số khách vé tháng
                (freeTicketCount.toIntOrNull() ?: 0) // cộng số khách vé miễn phí

    Column( // tạo bố cục chính theo chiều dọc
        modifier = Modifier
            .fillMaxSize() // phủ toàn bộ màn hình
            .background(CustomerBackground) // dùng nền xám trắng
            .verticalScroll(rememberScrollState()) // cho phép cuộn khi nội dung dài
    ) {
        Row( // tạo phần đầu màn hình
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .background(CustomerBlue) // dùng nền xanh
                .windowInsetsPadding( // đẩy nội dung xuống dưới camera
                    WindowInsets.safeDrawing.only( // lấy vùng an toàn hệ thống
                        WindowInsetsSides.Top // chỉ áp dụng cạnh phía trên
                    )
                )
                .padding( // tạo khoảng cách trong phần đầu
                    start = 8.dp, // tạo khoảng cách cạnh trái
                    end = 18.dp, // tạo khoảng cách cạnh phải
                    top = 8.dp, // tạo khoảng cách phía trên
                    bottom = 16.dp // tạo khoảng cách phía dưới
                ),
            verticalAlignment = Alignment.CenterVertically // căn giữa nội dung theo chiều dọc
        ) {
            TextButton( // tạo nút quay lại
                onClick = onBack // quay về trang chủ doanh nghiệp
            ) {
                Text( // hiển thị mũi tên quay lại
                    text = "←", // ký hiệu mũi tên
                    color = Color.White, // dùng màu trắng
                    fontSize = 30.sp // đặt kích thước mũi tên
                )
            }

            Spacer( // tạo khoảng cách với tiêu đề
                modifier = Modifier.width(4.dp) // đặt chiều rộng khoảng cách
            )

            Column { // xếp tiêu đề và mô tả theo chiều dọc
                Text( // hiển thị tiêu đề màn hình
                    text = "QUẢN LÝ KHÁCH HÀNG", // tên chức năng
                    color = Color.White, // dùng chữ trắng
                    fontSize = 21.sp, // đặt kích thước tiêu đề
                    fontWeight = FontWeight.Bold // in đậm tiêu đề
                )

                Spacer( // tạo khoảng cách nhỏ
                    modifier = Modifier.height(3.dp) // đặt chiều cao khoảng cách
                )

                Text( // hiển thị mô tả màn hình
                    text = "Vé xe và khảo sát hành khách", // mô tả chức năng
                    color = Color.White.copy(alpha = 0.9f), // dùng chữ trắng nhẹ
                    fontSize = 13.sp // đặt kích thước mô tả
                )
            }
        }

        Column( // tạo phần nội dung chính
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(20.dp) // tạo lề quanh nội dung
        ) {
            Text( // hiển thị tiêu đề tổng quan
                text = "TỔNG QUAN HÀNH KHÁCH", // tên khu vực tổng quan
                color = CustomerBlue, // dùng màu xanh chính
                fontSize = 17.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            Card( // tạo thẻ tổng số hành khách
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
                colors = CardDefaults.cardColors( // thiết lập màu thẻ
                    containerColor = CustomerLightBlue // dùng nền xanh nhạt
                ),
                shape = RoundedCornerShape(16.dp) // bo tròn góc thẻ
            ) {
                Row( // xếp biểu tượng và số liệu theo chiều ngang
                    modifier = Modifier
                        .fillMaxWidth() // phủ toàn bộ chiều ngang thẻ
                        .padding(20.dp), // tạo khoảng cách trong thẻ
                    horizontalArrangement = Arrangement.SpaceBetween, // đẩy hai phần sang hai bên
                    verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
                ) {
                    Column { // xếp tiêu đề và mô tả theo chiều dọc
                        Text( // hiển thị tên số liệu
                            text = "Tổng số hành khách", // tên dữ liệu
                            color = Color.DarkGray, // dùng màu chữ đậm
                            fontSize = 15.sp, // đặt kích thước chữ
                            fontWeight = FontWeight.Medium // làm chữ rõ hơn
                        )

                        Spacer( // tạo khoảng cách
                            modifier = Modifier.height(6.dp) // đặt chiều cao khoảng cách
                        )

                        Text( // giải thích cách tính
                            text = "Vé lượt + vé tháng + vé miễn phí", // mô tả nguồn dữ liệu
                            color = Color.Gray, // dùng màu chữ phụ
                            fontSize = 12.sp // đặt kích thước mô tả
                        )
                    }

                    Text( // hiển thị tổng số khách
                        text = "$totalPassengerCount khách", // ghép tổng số khách với đơn vị
                        color = CustomerBlue, // dùng màu xanh chính
                        fontSize = 24.sp, // đặt kích thước số liệu lớn
                        fontWeight = FontWeight.Bold // in đậm số liệu
                    )
                }
            }

            Spacer( // tạo khoảng cách đến phần vé
                modifier = Modifier.height(24.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tiêu đề cơ cấu vé
                text = "CƠ CẤU VÉ", // tên khu vực vé
                color = CustomerBlue, // dùng màu xanh chính
                fontSize = 17.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            Row( // tạo hàng vé lượt và vé tháng có chiều rộng bằng nhau
                modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.spacedBy(12.dp) // tạo khoảng cách đều giữa hai ô
            ) {
                if (isEditing) { // kiểm tra đang ở chế độ chỉnh sửa
                    CustomerNumberEditor( // tạo ô sửa vé lượt
                        title = "Vé lượt", // tên loại vé
                        value = singleTicketCount, // giá trị vé lượt hiện tại
                        onValueChange = { singleTicketCount = it }, // cập nhật số vé lượt
                        modifier = Modifier.weight(1f) // chia đều một nửa chiều rộng
                    )

                    CustomerNumberEditor( // tạo ô sửa vé tháng
                        title = "Vé tháng", // tên loại vé
                        value = monthlyTicketCount, // giá trị vé tháng hiện tại
                        onValueChange = { monthlyTicketCount = it }, // cập nhật số vé tháng
                        modifier = Modifier.weight(1f) // chia đều một nửa chiều rộng
                    )
                } else { // hiển thị dữ liệu khi không chỉnh sửa
                    CustomerNumberCard( // tạo thẻ vé lượt
                        icon = "🎟️", // biểu tượng vé lượt
                        title = "Vé lượt", // tên loại vé
                        value = singleTicketCount, // số khách sử dụng vé lượt
                        modifier = Modifier.weight(1f) // chia đều một nửa chiều rộng
                    )

                    CustomerNumberCard( // tạo thẻ vé tháng
                        icon = "📅", // biểu tượng vé tháng
                        title = "Vé tháng", // tên loại vé
                        value = monthlyTicketCount, // số khách sử dụng vé tháng
                        modifier = Modifier.weight(1f) // chia đều một nửa chiều rộng
                    )
                }
            }
            Spacer( // tạo khoảng cách sang vé miễn phí
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            if (isEditing) { // kiểm tra chế độ chỉnh sửa
                CustomerNumberEditor( // tạo ô sửa vé miễn phí
                    title = "Vé miễn phí", // tên loại vé
                    value = freeTicketCount, // giá trị hiện tại
                    onValueChange = { freeTicketCount = it }, // cập nhật vé miễn phí
                    modifier = Modifier.fillMaxWidth() // phủ toàn bộ chiều ngang
                )
            } else { // hiển thị thẻ khi không chỉnh sửa
                CustomerNumberCard( // tạo thẻ vé miễn phí
                    icon = "🆓", // biểu tượng miễn phí
                    title = "Vé miễn phí", // tên loại vé
                    value = freeTicketCount, // số khách vé miễn phí
                    modifier = Modifier.fillMaxWidth() // phủ toàn bộ chiều ngang
                )
            }

            Spacer( // tạo khoảng cách đến phần khảo sát
                modifier = Modifier.height(24.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tiêu đề khảo sát
                text = "KHẢO SÁT NHÓM HÀNH KHÁCH", // tên khu vực khảo sát
                color = CustomerBlue, // dùng màu xanh chính
                fontSize = 17.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(6.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị ghi chú khảo sát
                text = "Số liệu khảo sát không cộng vào tổng số hành khách.", // giải thích cách dùng dữ liệu
                color = Color.Gray, // dùng màu chữ phụ
                fontSize = 12.sp // đặt kích thước ghi chú
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            if (isEditing) { // kiểm tra đang sửa dữ liệu
                CustomerNumberEditor( // tạo ô sửa khảo sát công nhân
                    title = "Khảo sát công nhân", // tên nhóm khảo sát
                    value = workerSurveyCount, // giá trị hiện tại
                    onValueChange = { workerSurveyCount = it }, // cập nhật dữ liệu công nhân
                    modifier = Modifier.fillMaxWidth() // phủ toàn bộ chiều ngang
                )

                Spacer( // tạo khoảng cách giữa hai ô
                    modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
                )

                CustomerNumberEditor( // tạo ô sửa khảo sát học sinh sinh viên
                    title = "Khảo sát học sinh, sinh viên", // tên nhóm khảo sát
                    value = studentSurveyCount, // giá trị hiện tại
                    onValueChange = { studentSurveyCount = it }, // cập nhật dữ liệu học sinh sinh viên
                    modifier = Modifier.fillMaxWidth() // phủ toàn bộ chiều ngang
                )
            } else { // hiển thị thẻ khảo sát
                CustomerSurveyCard( // tạo thẻ khảo sát công nhân
                    icon = "👷", // biểu tượng công nhân
                    title = "Công nhân", // tên nhóm khảo sát
                    value = workerSurveyCount // số lượng khảo sát
                )

                Spacer( // tạo khoảng cách giữa hai thẻ
                    modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
                )

                CustomerSurveyCard( // tạo thẻ khảo sát học sinh sinh viên
                    icon = "🎓", // biểu tượng học sinh sinh viên
                    title = "Học sinh, sinh viên", // tên nhóm khảo sát
                    value = studentSurveyCount // số lượng khảo sát
                )
            }

            Spacer( // tạo khoảng cách đến nút sửa
                modifier = Modifier.height(24.dp) // đặt chiều cao khoảng cách
            )

            Button( // tạo nút sửa hoặc lưu dữ liệu
                onClick = { // xử lý khi người dùng bấm nút
                    if (isEditing) { // nếu đang ở chế độ nhập dữ liệu
                        customerViewModel.saveCustomerStatistics( // yêu cầu ViewModel lưu dữ liệu vào Room
                            singleTicketCount = singleTicketCount.toIntOrNull() ?: 0, // chuyển vé lượt thành số
                            monthlyTicketCount = monthlyTicketCount.toIntOrNull() ?: 0, // chuyển vé tháng thành số
                            freeTicketCount = freeTicketCount.toIntOrNull() ?: 0, // chuyển vé miễn phí thành số
                            workerSurveyCount = workerSurveyCount.toIntOrNull() ?: 0, // chuyển khảo sát công nhân thành số
                            studentSurveyCount = studentSurveyCount.toIntOrNull() ?: 0 // chuyển khảo sát học sinh thành số
                        )

                        isEditing = false // trở về chế độ xem sau khi gửi lệnh lưu
                    } else { // nếu đang ở chế độ xem dữ liệu
                        isEditing = true // chuyển sang chế độ chỉnh sửa
                    }
                },
                modifier = Modifier
                    .fillMaxWidth() // phủ toàn bộ chiều ngang
                    .height(54.dp), // đặt chiều cao nút
                colors = ButtonDefaults.buttonColors( // thiết lập màu nút
                    containerColor = if (isEditing) CustomerGreen else CustomerBlue // đổi màu theo trạng thái
                ),
                shape = RoundedCornerShape(12.dp) // bo tròn góc nút
            ) {
                Text( // hiển thị chữ trên nút
                    text = if (isEditing) "LƯU THAY ĐỔI" else "SỬA DỮ LIỆU", // đổi nội dung nút
                    color = Color.White, // dùng chữ trắng
                    fontSize = 15.sp, // đặt kích thước chữ
                    fontWeight = FontWeight.Bold // in đậm chữ nút
                )
            }

            Spacer( // tạo khoảng cách cuối màn hình
                modifier = Modifier.height(30.dp) // tránh nội dung sát đáy
            )
        }
    }
}

@Composable
private fun CustomerNumberCard( // tạo thẻ hiển thị một loại vé
    icon: String, // nhận biểu tượng loại vé
    title: String, // nhận tên loại vé
    value: String, // nhận số lượng khách
    modifier: Modifier = Modifier // nhận kích thước thẻ từ bên ngoài
) {
    Card( // tạo thẻ loại vé
        modifier = modifier.height(128.dp), // đặt chiều cao thẻ
        colors = CardDefaults.cardColors( // thiết lập màu thẻ
            containerColor = Color.White // dùng nền trắng
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc thẻ
    ) {
        Column( // xếp nội dung theo chiều dọc
            modifier = Modifier
                .fillMaxSize() // phủ toàn bộ thẻ
                .padding(16.dp), // tạo khoảng cách trong thẻ
            verticalArrangement = Arrangement.Center // căn giữa nội dung theo chiều dọc
        ) {
            Text( // hiển thị biểu tượng
                text = icon, // dùng biểu tượng được truyền vào
                fontSize = 26.sp // đặt kích thước biểu tượng
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(8.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị số khách
                text = "$value khách", // ghép số lượng với đơn vị
                color = CustomerGreen, // dùng màu xanh lá
                fontSize = 20.sp, // đặt kích thước số liệu
                fontWeight = FontWeight.Bold // in đậm số liệu
            )

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(4.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tên loại vé
                text = title, // dùng tên được truyền vào
                color = Color.Gray, // dùng màu chữ phụ
                fontSize = 13.sp // đặt kích thước chữ
            )
        }
    }
}

@Composable
private fun CustomerSurveyCard( // tạo thẻ số liệu khảo sát
    icon: String, // nhận biểu tượng nhóm khảo sát
    title: String, // nhận tên nhóm khảo sát
    value: String // nhận số lượng khảo sát
) {
    Card( // tạo thẻ khảo sát
        modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang
        colors = CardDefaults.cardColors( // thiết lập màu thẻ
            containerColor = CustomerLightOrange // dùng nền cam nhạt
        ),
        shape = RoundedCornerShape(14.dp) // bo tròn góc thẻ
    ) {
        Row( // xếp biểu tượng và số liệu theo chiều ngang
            modifier = Modifier
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(18.dp), // tạo khoảng cách trong thẻ
            horizontalArrangement = Arrangement.SpaceBetween, // đẩy nội dung sang hai bên
            verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
        ) {
            Row( // xếp biểu tượng và tên nhóm
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {
                Text( // hiển thị biểu tượng
                    text = icon, // dùng biểu tượng được truyền vào
                    fontSize = 28.sp // đặt kích thước biểu tượng
                )

                Spacer( // tạo khoảng cách với tên
                    modifier = Modifier.width(12.dp) // đặt chiều rộng khoảng cách
                )

                Column { // xếp tên và mô tả theo chiều dọc
                    Text( // hiển thị tên nhóm
                        text = title, // dùng tên nhóm được truyền vào
                        color = Color.DarkGray, // dùng màu chữ đậm
                        fontSize = 15.sp, // đặt kích thước chữ
                        fontWeight = FontWeight.Bold // in đậm tên nhóm
                    )

                    Text( // hiển thị mô tả
                        text = "Số liệu khảo sát", // mô tả loại dữ liệu
                        color = Color.Gray, // dùng màu chữ phụ
                        fontSize = 12.sp // đặt kích thước mô tả
                    )
                }
            }

            Text( // hiển thị số lượng khảo sát
                text = "$value người", // ghép số lượng với đơn vị
                color = CustomerOrange, // dùng màu cam
                fontSize = 19.sp, // đặt kích thước số liệu
                fontWeight = FontWeight.Bold // in đậm số liệu
            )
        }
    }
}

@Composable
private fun CustomerNumberEditor( // tạo ô chỉnh sửa dữ liệu số
    title: String, // nhận tên trường dữ liệu
    value: String, // nhận giá trị hiện tại
    onValueChange: (String) -> Unit, // nhận hành động cập nhật giá trị
    modifier: Modifier = Modifier // nhận kích thước từ bên ngoài
) {
    OutlinedTextField( // tạo ô nhập dữ liệu
        value = value, // hiển thị giá trị hiện tại
        onValueChange = { newValue -> // xử lý khi người dùng nhập
            if (newValue.all { character -> character.isDigit() }) { // chỉ cho phép nhập số
                onValueChange(newValue) // cập nhật giá trị hợp lệ
            }
        },
        modifier = modifier, // áp dụng kích thước được truyền vào
        label = { // tạo nhãn cho ô nhập
            Text( // hiển thị tên dữ liệu
                text = title // dùng tên trường được truyền vào
            )
        },
        singleLine = true, // chỉ cho nhập trên một dòng
        keyboardOptions = KeyboardOptions( // thiết lập bàn phím
            keyboardType = KeyboardType.Number // hiển thị bàn phím số
        ),
        shape = RoundedCornerShape(12.dp) // bo tròn góc ô nhập
    )
}