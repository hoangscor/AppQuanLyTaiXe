package com.example.busdieuhanhdongnai.feature.business.assignment

import androidx.compose.foundation.background // tạo màu nền cho khu vực giao diện
import androidx.compose.foundation.layout.Arrangement // căn khoảng cách giữa các thành phần trong hàng
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách giữa các thành phần
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh của vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // cho thành phần phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // cho thành phần phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao cho thành phần
import androidx.compose.foundation.layout.only // chỉ áp dụng vùng an toàn ở cạnh được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong thành phần
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn tránh camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng an toàn
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn của màn hình
import androidx.compose.foundation.shape.RoundedCornerShape // tạo góc bo tròn
import androidx.compose.foundation.verticalScroll // cho phép màn hình cuộn dọc
import androidx.compose.material3.Button // tạo nút thao tác chính
import androidx.compose.material3.ButtonDefaults // thiết lập màu sắc cho nút
import androidx.compose.material3.Card // tạo thẻ chứa nội dung
import androidx.compose.material3.CardDefaults // thiết lập màu nền cho thẻ
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút chữ quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.ui.Alignment // căn chỉnh thành phần giao diện
import androidx.compose.ui.Modifier // điều chỉnh kích thước và bố cục
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm của chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ

private val AssignmentBlue = Color(0xFF0066CC) // màu xanh chính của khu vực doanh nghiệp
private val AssignmentBackground = Color(0xFFF6F8FC) // màu nền chung của màn hình
private val AssignmentGreen = Color(0xFF1A9B54) // màu xanh cho trạng thái đã phân công
private val AssignmentOrange = Color(0xFFFF9800) // màu cam cho trạng thái chờ phân công

private data class AssignmentUiModel( // tạo kiểu dữ liệu tạm dùng riêng cho giao diện phân công
    val routeName: String, // lưu tên tuyến xe của lịch chạy
    val scheduledTime: String, // lưu thời gian dự kiến của chuyến
    val driverName: String, // lưu tên tài xế được phân công
    val vehiclePlate: String, // lưu biển số phương tiện được phân công
    val status: String // lưu trạng thái của lịch phân công
) // kết thúc kiểu dữ liệu giao diện phân công

private val sampleAssignments = listOf( // tạo danh sách dữ liệu mẫu để dựng giao diện trước
    AssignmentUiModel( // tạo lịch chạy mẫu thứ nhất
        routeName = "Tuyến 01: Bến xe A → Bến xe B", // đặt tên tuyến thứ nhất
        scheduledTime = "07:00 - 08:00", // đặt khung giờ chuyến thứ nhất
        driverName = "Nguyễn Văn An", // đặt tài xế mẫu cho chuyến thứ nhất
        vehiclePlate = "51B-123.45", // đặt phương tiện mẫu cho chuyến thứ nhất
        status = "Đã phân công" // đặt trạng thái chuyến thứ nhất
    ), // kết thúc lịch chạy mẫu thứ nhất
    AssignmentUiModel( // tạo lịch chạy mẫu thứ hai
        routeName = "Tuyến 02: Bến xe B → Bến xe C", // đặt tên tuyến thứ hai
        scheduledTime = "08:30 - 09:30", // đặt khung giờ chuyến thứ hai
        driverName = "Trần Minh Tuấn", // đặt tài xế mẫu cho chuyến thứ hai
        vehiclePlate = "51B-234.56", // đặt phương tiện mẫu cho chuyến thứ hai
        status = "Đã phân công" // đặt trạng thái chuyến thứ hai
    ), // kết thúc lịch chạy mẫu thứ hai
    AssignmentUiModel( // tạo lịch chạy mẫu thứ ba
        routeName = "Tuyến 03: Bến xe A → Bến xe D", // đặt tên tuyến thứ ba
        scheduledTime = "10:00 - 11:00", // đặt khung giờ chuyến thứ ba
        driverName = "Chưa chọn", // chưa có tài xế cho chuyến thứ ba
        vehiclePlate = "Chưa chọn", // chưa có phương tiện cho chuyến thứ ba
        status = "Chờ phân công" // đặt trạng thái chuyến thứ ba
    ) // kết thúc lịch chạy mẫu thứ ba
) // kết thúc danh sách dữ liệu mẫu

@Composable // đánh dấu hàm giao diện Compose
fun AssignmentManagementScreen( // tạo màn hình quản lý phân công lịch chạy
    onBack: () -> Unit = {}, // nhận hành động quay lại trang chủ doanh nghiệp
    onCreateAssignment: () -> Unit = {}, // THÊM: nhận hành động tạo phân công mới
    onOpenAssignment: (String) -> Unit = { _ -> } // THÊM: nhận hành động mở một lịch phân công theo tên tuyến
) { // bắt đầu màn hình phân công
    Column( // tạo bố cục chính theo chiều dọc
        modifier = Modifier // bắt đầu cấu hình màn hình
            .fillMaxSize() // cho giao diện phủ toàn bộ màn hình
            .background(AssignmentBackground) // đặt màu nền chung cho màn hình
            .verticalScroll(rememberScrollState()) // cho phép nội dung cuộn theo chiều dọc
    ) { // bắt đầu nội dung màn hình
        Column( // tạo khu vực tiêu đề màu xanh
            modifier = Modifier // bắt đầu cấu hình tiêu đề
                .fillMaxWidth() // cho tiêu đề phủ toàn bộ chiều ngang
                .background(AssignmentBlue) // đặt màu nền xanh chính
                .windowInsetsPadding( // đẩy nội dung tránh vùng camera và thanh trạng thái
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top) // chỉ sử dụng vùng an toàn phía trên
                ) // kết thúc vùng an toàn
                .padding(horizontal = 20.dp, vertical = 18.dp) // tạo khoảng cách trong phần tiêu đề
        ) { // bắt đầu nội dung tiêu đề
            Row( // xếp nút quay lại và tên màn hình theo chiều ngang
                modifier = Modifier.fillMaxWidth(), // cho hàng tiêu đề phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa các thành phần theo chiều dọc
            ) { // bắt đầu hàng tiêu đề
                TextButton( // tạo nút quay lại
                    onClick = onBack // gọi callback quay lại khi người dùng bấm
                ) { // bắt đầu nội dung nút quay lại
                    Text( // hiển thị biểu tượng mũi tên
                        text = "←", // đặt biểu tượng quay lại
                        color = Color.White, // dùng màu trắng
                        fontSize = 22.sp // đặt kích thước mũi tên
                    ) // kết thúc chữ nút quay lại
                } // kết thúc nút quay lại

                Column( // tạo khu vực tiêu đề và mô tả
                    modifier = Modifier.padding(start = 4.dp) // tạo khoảng cách với nút quay lại
                ) { // bắt đầu khu vực tên màn hình
                    Text( // hiển thị tiêu đề màn hình
                        text = "PHÂN CÔNG LỊCH CHẠY", // đặt tên màn hình
                        color = Color.White, // dùng chữ màu trắng
                        fontSize = 20.sp, // đặt kích thước tiêu đề
                        fontWeight = FontWeight.Bold // làm tiêu đề nổi bật
                    ) // kết thúc tiêu đề

                    Spacer( // tạo khoảng cách giữa tiêu đề và mô tả
                        modifier = Modifier.height(4.dp) // đặt khoảng cách dọc
                    ) // kết thúc khoảng cách

                    Text( // hiển thị mô tả màn hình
                        text = "Sắp xếp tài xế và phương tiện theo chuyến", // mô tả chức năng màn hình
                        color = Color.White, // dùng chữ trắng
                        fontSize = 13.sp // đặt kích thước chữ mô tả
                    ) // kết thúc mô tả
                } // kết thúc khu vực tên màn hình
            } // kết thúc hàng tiêu đề
        } // kết thúc phần tiêu đề

        Column( // tạo khu vực nội dung chính
            modifier = Modifier // bắt đầu cấu hình nội dung
                .fillMaxWidth() // phủ toàn bộ chiều ngang
                .padding(horizontal = 20.dp, vertical = 20.dp) // tạo khoảng cách quanh nội dung
        ) { // bắt đầu nội dung chính
            Text( // hiển thị tên phần tổng quan
                text = "TỔNG QUAN PHÂN CÔNG", // đặt tiêu đề khu vực tổng quan
                color = AssignmentBlue, // dùng màu xanh chính
                fontSize = 18.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // làm chữ nổi bật
            ) // kết thúc tiêu đề tổng quan

            Spacer( // tạo khoảng cách sau tiêu đề
                modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            AssignmentOverviewCard( // hiển thị thẻ tổng số chuyến
                title = "Tổng chuyến", // đặt tên chỉ số
                value = sampleAssignments.size.toString(), // lấy tổng số chuyến từ dữ liệu mẫu
                description = "Lịch chạy cần bố trí hôm nay", // mô tả ý nghĩa chỉ số
                valueColor = AssignmentBlue // dùng màu xanh cho tổng số chuyến
            ) // kết thúc thẻ tổng số chuyến

            Spacer( // tạo khoảng cách giữa các thẻ tổng quan
                modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            AssignmentOverviewCard( // hiển thị thẻ số chuyến đã phân công
                title = "Đã phân công", // đặt tên chỉ số
                value = sampleAssignments.count { assignment -> assignment.status == "Đã phân công" }.toString(), // đếm chuyến đã phân công
                description = "Đã có đầy đủ tài xế và phương tiện", // mô tả trạng thái đã hoàn tất
                valueColor = AssignmentGreen // dùng màu xanh lá cho trạng thái hoàn tất
            ) // kết thúc thẻ đã phân công

            Spacer( // tạo khoảng cách giữa các thẻ tổng quan
                modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            AssignmentOverviewCard( // hiển thị thẻ số chuyến đang chờ phân công
                title = "Chờ phân công", // đặt tên chỉ số
                value = sampleAssignments.count { assignment -> assignment.status == "Chờ phân công" }.toString(), // đếm chuyến chưa được phân công
                description = "Chưa đủ tài xế hoặc phương tiện", // mô tả trạng thái cần xử lý
                valueColor = AssignmentOrange // dùng màu cam cho trạng thái chờ
            ) // kết thúc thẻ chờ phân công

            Spacer( // tạo khoảng cách trước nút thêm phân công
                modifier = Modifier.height(18.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Button( // tạo nút thêm lịch phân công mới
                onClick = onCreateAssignment, // SỬA: chuyển thao tác bấm nút ra callback bên ngoài
                modifier = Modifier.fillMaxWidth(), // cho nút phủ toàn bộ chiều ngang
                colors = ButtonDefaults.buttonColors( // thiết lập màu nút
                    containerColor = AssignmentBlue // dùng màu xanh chính
                ), // kết thúc thiết lập màu nút
                shape = RoundedCornerShape(14.dp) // bo tròn góc nút
            ) { // bắt đầu nội dung nút
                Text( // hiển thị chữ trên nút
                    text = "TẠO PHÂN CÔNG", // đặt tên thao tác
                    color = Color.White, // dùng chữ trắng
                    fontWeight = FontWeight.Bold // làm chữ nút nổi bật
                ) // kết thúc chữ nút
            } // kết thúc nút tạo phân công

            Spacer( // tạo khoảng cách trước danh sách
                modifier = Modifier.height(22.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Text( // hiển thị tiêu đề danh sách
                text = "DANH SÁCH LỊCH CHẠY", // đặt tên khu vực danh sách
                color = AssignmentBlue, // dùng màu xanh chính
                fontSize = 18.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // làm tiêu đề nổi bật
            ) // kết thúc tiêu đề danh sách

            Spacer( // tạo khoảng cách trước thẻ đầu tiên
                modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            sampleAssignments.forEach { assignment -> // lần lượt hiển thị từng lịch chạy mẫu
                AssignmentListCard( // tạo thẻ thông tin cho lịch chạy hiện tại
                    assignment = assignment, // SỬA: truyền dữ liệu lịch chạy vào thẻ
                    onOpenAssignment = onOpenAssignment // THÊM: truyền callback mở lịch chạy xuống thẻ
                ) // kết thúc thẻ lịch chạy

                Spacer( // tạo khoảng cách giữa hai lịch chạy
                    modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
                ) // kết thúc khoảng cách
            } // kết thúc vòng lặp danh sách lịch chạy
        } // kết thúc khu vực nội dung chính
    } // kết thúc bố cục chính
} // kết thúc màn hình quản lý phân công

@Composable // đánh dấu hàm giao diện Compose
private fun AssignmentOverviewCard( // tạo thẻ hiển thị một chỉ số tổng quan
    title: String, // nhận tên chỉ số cần hiển thị
    value: String, // nhận giá trị của chỉ số
    description: String, // nhận nội dung giải thích chỉ số
    valueColor: Color // nhận màu của giá trị chỉ số
) { // bắt đầu thẻ tổng quan
    Card( // tạo nền thẻ tổng quan
        modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors( // thiết lập màu nền thẻ
            containerColor = Color.White // dùng nền trắng
        ) // kết thúc thiết lập màu thẻ
    ) { // bắt đầu nội dung thẻ
        Column( // xếp nội dung thẻ theo chiều dọc
            modifier = Modifier.padding(16.dp) // tạo khoảng cách trong thẻ
        ) { // bắt đầu nội dung thẻ tổng quan
            Text( // hiển thị tên chỉ số
                text = title, // lấy tên chỉ số được truyền vào
                color = Color.DarkGray, // dùng màu xám đậm
                fontSize = 14.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // làm tên chỉ số nổi bật
            ) // kết thúc tên chỉ số

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(4.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Text( // hiển thị giá trị chỉ số
                text = value, // lấy giá trị được truyền vào
                color = valueColor, // dùng màu tương ứng với trạng thái
                fontSize = 26.sp, // đặt kích thước số lớn
                fontWeight = FontWeight.Bold // làm giá trị nổi bật
            ) // kết thúc giá trị

            Text( // hiển thị mô tả chỉ số
                text = description, // lấy nội dung mô tả được truyền vào
                color = Color.Gray, // dùng màu xám nhẹ
                fontSize = 13.sp // đặt kích thước chữ mô tả
            ) // kết thúc mô tả
        } // kết thúc nội dung thẻ
    } // kết thúc Card tổng quan
} // kết thúc hàm thẻ tổng quan

@Composable // đánh dấu hàm giao diện Compose
private fun AssignmentListCard( // tạo thẻ hiển thị một lịch chạy
    assignment: AssignmentUiModel, // SỬA: nhận dữ liệu lịch chạy cần hiển thị
    onOpenAssignment: (String) -> Unit // THÊM: nhận callback mở lịch phân công
) { // bắt đầu thẻ lịch chạy
    val statusColor = when (assignment.status) { // chọn màu theo trạng thái lịch chạy
        "Đã phân công" -> AssignmentGreen // dùng màu xanh lá khi đã hoàn tất phân công
        else -> AssignmentOrange // dùng màu cam cho trạng thái còn chờ
    } // kết thúc xác định màu trạng thái

    Card( // tạo nền cho thẻ lịch chạy
        modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors( // thiết lập màu thẻ
            containerColor = Color.White // dùng nền trắng
        ) // kết thúc thiết lập màu
    ) { // bắt đầu nội dung thẻ
        Column( // xếp thông tin lịch chạy theo chiều dọc
            modifier = Modifier.padding(16.dp) // tạo khoảng cách bên trong thẻ
        ) { // bắt đầu nội dung lịch chạy
            Row( // xếp tên tuyến và trạng thái trên cùng một hàng
                modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween, // đẩy hai thành phần về hai phía
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) { // bắt đầu hàng đầu tiên
                Text( // hiển thị tên tuyến
                    text = assignment.routeName, // lấy tên tuyến từ dữ liệu lịch chạy
                    color = AssignmentBlue, // dùng màu xanh chính
                    fontSize = 15.sp, // đặt kích thước chữ
                    fontWeight = FontWeight.Bold, // làm tên tuyến nổi bật
                    modifier = Modifier.fillMaxWidth(0.70f) // giới hạn chiều ngang để chừa chỗ trạng thái
                ) // kết thúc tên tuyến

                Text( // hiển thị trạng thái lịch chạy
                    text = assignment.status, // lấy trạng thái từ dữ liệu
                    color = statusColor, // dùng màu tương ứng với trạng thái
                    fontSize = 12.sp, // đặt kích thước chữ trạng thái
                    fontWeight = FontWeight.Bold // làm trạng thái nổi bật
                ) // kết thúc trạng thái
            } // kết thúc hàng đầu tiên

            Spacer( // tạo khoảng cách
                modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Text( // hiển thị thời gian chuyến
                text = "Giờ chạy: ${assignment.scheduledTime}", // ghép nhãn và thời gian chuyến
                color = Color.DarkGray, // dùng màu xám đậm
                fontSize = 14.sp // đặt kích thước chữ
            ) // kết thúc thời gian chuyến

            Spacer( // tạo khoảng cách nhỏ
                modifier = Modifier.height(6.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Text( // hiển thị tài xế được phân công
                text = "Tài xế: ${assignment.driverName}", // ghép nhãn và tên tài xế
                color = Color.DarkGray, // dùng màu xám đậm
                fontSize = 14.sp // đặt kích thước chữ
            ) // kết thúc thông tin tài xế

            Spacer( // tạo khoảng cách nhỏ
                modifier = Modifier.height(6.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            Text( // hiển thị phương tiện được phân công
                text = "Phương tiện: ${assignment.vehiclePlate}", // ghép nhãn và biển số xe
                color = Color.DarkGray, // dùng màu xám đậm
                fontSize = 14.sp // đặt kích thước chữ
            ) // kết thúc thông tin phương tiện

            Spacer( // tạo khoảng cách trước nút thao tác
                modifier = Modifier.height(8.dp) // đặt khoảng cách dọc
            ) // kết thúc khoảng cách

            TextButton( // tạo nút thao tác cho lịch chạy
                onClick = { onOpenAssignment(assignment.routeName) }, // SỬA: gửi lịch chạy được chọn ra ngoài
                modifier = Modifier.fillMaxWidth() // cho vùng nút phủ toàn bộ chiều ngang
            ) { // bắt đầu nội dung nút
                Text( // hiển thị tên thao tác
                    text = if (assignment.status == "Đã phân công") "XEM / CHỈNH SỬA" else "PHÂN CÔNG NGAY", // thay tên nút theo trạng thái
                    color = AssignmentBlue, // dùng màu xanh chính
                    fontSize = 13.sp, // đặt kích thước chữ
                    fontWeight = FontWeight.Bold // làm chữ nút nổi bật
                ) // kết thúc chữ nút
            } // kết thúc nút thao tác
        } // kết thúc nội dung thẻ lịch chạy
    } // kết thúc Card lịch chạy
} // kết thúc hàm thẻ lịch chạy