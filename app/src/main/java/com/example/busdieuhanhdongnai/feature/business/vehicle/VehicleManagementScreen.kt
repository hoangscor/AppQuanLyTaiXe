package com.example.busdieuhanhdongnai.feature.business.vehicle // đặt màn hình trong phân hệ phương tiện doanh nghiệp

import androidx.compose.foundation.background // tạo màu nền màn hình
import androidx.compose.foundation.layout.Arrangement // sắp xếp khoảng cách các thành phần
import androidx.compose.foundation.layout.Column // xếp nội dung theo chiều dọc
import androidx.compose.foundation.layout.Row // xếp nội dung theo chiều ngang
import androidx.compose.foundation.layout.Spacer // tạo khoảng cách
import androidx.compose.foundation.layout.WindowInsets // lấy vùng an toàn của thiết bị
import androidx.compose.foundation.layout.WindowInsetsSides // chọn cạnh cần áp dụng vùng an toàn
import androidx.compose.foundation.layout.fillMaxSize // phủ toàn bộ màn hình
import androidx.compose.foundation.layout.fillMaxWidth // phủ toàn bộ chiều ngang
import androidx.compose.foundation.layout.height // đặt chiều cao
import androidx.compose.foundation.layout.only // chỉ dùng cạnh vùng an toàn được chọn
import androidx.compose.foundation.layout.padding // tạo khoảng cách bên trong
import androidx.compose.foundation.layout.safeDrawing // lấy vùng an toàn camera và thanh trạng thái
import androidx.compose.foundation.layout.windowInsetsPadding // đẩy nội dung khỏi vùng camera
import androidx.compose.foundation.rememberScrollState // ghi nhớ vị trí cuộn của màn hình
import androidx.compose.foundation.shape.RoundedCornerShape // bo tròn góc các thành phần
import androidx.compose.foundation.verticalScroll // cho phép nội dung màn hình cuộn dọc
import androidx.compose.material3.AlertDialog // tạo hộp thoại xác nhận trước khi xóa phương tiện
import androidx.compose.material3.Card // tạo thẻ giao diện
import androidx.compose.material3.CardDefaults // thiết lập màu nền cho thẻ
import androidx.compose.material3.Button // tạo nút thêm phương tiện
import androidx.compose.material3.ButtonDefaults // thiết lập màu sắc cho nút thêm phương tiện
import androidx.compose.material3.OutlinedTextField // tạo ô tìm kiếm phương tiện
import androidx.compose.material3.Text // hiển thị chữ
import androidx.compose.material3.TextButton // tạo nút quay lại
import androidx.compose.runtime.Composable // đánh dấu hàm giao diện Compose
import androidx.compose.runtime.collectAsState // chuyển StateFlow thành state để giao diện theo dõi
import androidx.compose.runtime.getValue // đọc giá trị state bằng từ khóa by
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi cho form thêm phương tiện
import androidx.compose.runtime.saveable.rememberSaveable // giữ trạng thái form khi màn hình được tạo lại
import androidx.compose.runtime.setValue // cập nhật giá trị state bằng từ khóa by
import androidx.compose.ui.Alignment // căn chỉnh thành phần
import androidx.compose.ui.Modifier // điều chỉnh giao diện
import androidx.compose.ui.graphics.Color // sử dụng màu sắc
import androidx.compose.ui.text.font.FontWeight // điều chỉnh độ đậm chữ
import androidx.compose.ui.text.style.TextAlign // căn giữa nội dung chữ
import androidx.compose.ui.unit.dp // đơn vị kích thước giao diện
import androidx.compose.ui.unit.sp // đơn vị kích thước chữ
import androidx.lifecycle.viewmodel.compose.viewModel // tự lấy ViewModel cho màn hình Compose
import com.example.busdieuhanhdongnai.data.local.BusinessVehicleEntity // dữ liệu phương tiện lấy từ Room

private val VehicleBlue = Color(0xFF0066CC) // màu xanh chính của ứng dụng
private val VehicleBackground = Color(0xFFF6F8FC) // màu nền xám trắng

@Composable
fun VehicleManagementScreen( // tạo màn hình quản lý phương tiện
    onBack: () -> Unit = {}, // nhận hành động quay về trang chủ doanh nghiệp
    businessVehicleViewModel: BusinessVehicleViewModel = viewModel() // lấy ViewModel quản lý phương tiện
) {
    var showAddVehicleForm by rememberSaveable { // lưu trạng thái mở hoặc đóng form thêm xe
        mutableStateOf(false) // mặc định chưa hiển thị form thêm phương tiện
    }
    var editingVehicleId by rememberSaveable { // lưu mã phương tiện đang được chỉnh sửa
        mutableStateOf<Int?>(null) // null là thêm mới, có id là đang chỉnh sửa phương tiện
    }
    var newVehiclePlateNumber by rememberSaveable { // lưu biển số phương tiện đang nhập
        mutableStateOf("") // mặc định biển số để trống
    }

    var newVehicleType by rememberSaveable { // lưu loại phương tiện đang nhập
        mutableStateOf("") // mặc định loại phương tiện để trống
    }

    var newVehicleDriverName by rememberSaveable { // lưu tên tài xế đang nhập
        mutableStateOf("") // mặc định tên tài xế để trống
    }

    var newVehicleMaintenanceDate by rememberSaveable { // lưu ngày bảo trì gần nhất đang nhập
        mutableStateOf("") // mặc định ngày bảo trì để trống
    }

    var newVehicleStatus by rememberSaveable { // lưu trạng thái phương tiện đang nhập
        mutableStateOf("Hoạt động") // mặc định phương tiện có trạng thái Hoạt động
    }
    var vehicleFormError by rememberSaveable { // lưu nội dung lỗi của form thêm phương tiện
        mutableStateOf("") // mặc định chưa có thông báo lỗi
    }

    var deletingVehicleId by rememberSaveable { // lưu id của phương tiện đang chờ người dùng xác nhận xóa
        mutableStateOf<Int?>(null) // null nghĩa là hiện tại chưa có phương tiện nào chờ xóa
    }

    val vehicleSearchQuery by businessVehicleViewModel.searchQuery.collectAsState() // đọc từ khóa tìm kiếm từ ViewModel
    val filteredBusinessVehicles by businessVehicleViewModel.filteredVehicles.collectAsState() // đọc danh sách xe đã lọc từ Room
    val allBusinessVehicles by businessVehicleViewModel.allVehicles.collectAsState() // đọc toàn bộ xe từ Room

    val activeVehicleCount = allBusinessVehicles.count { vehicle -> // đếm xe đang hoạt động
        vehicle.status == "Hoạt động" // chỉ lấy xe có trạng thái Hoạt động
    }

    val maintenanceVehicleCount = allBusinessVehicles.count { vehicle -> // đếm xe đang bảo trì
        vehicle.status == "Bảo trì" // chỉ lấy xe có trạng thái Bảo trì
    }

    val stoppedVehicleCount = allBusinessVehicles.count { vehicle -> // đếm xe đang tạm dừng
        vehicle.status == "Tạm dừng" // chỉ lấy xe có trạng thái Tạm dừng
    }

    val vehiclePendingDelete = allBusinessVehicles.firstOrNull { vehicle -> // tìm phương tiện tương ứng với id đang chờ xóa
        vehicle.id == deletingVehicleId // chỉ lấy phương tiện có đúng id người dùng đã chọn
    }

    vehiclePendingDelete?.let { vehicleToDelete -> // chỉ hiện hộp thoại khi đã có phương tiện được chọn để xóa
        AlertDialog( // tạo hộp thoại yêu cầu xác nhận xóa phương tiện
            onDismissRequest = { // xử lý khi người dùng bấm ra ngoài hộp thoại hoặc nút quay lại
                deletingVehicleId = null // hủy trạng thái chờ xóa và đóng hộp thoại
            },
            title = { // tạo phần tiêu đề của hộp thoại
                Text( // hiển thị tiêu đề cảnh báo
                    text = "XÓA PHƯƠNG TIỆN", // đặt nội dung tiêu đề hộp thoại
                    fontWeight = FontWeight.Bold // làm tiêu đề nổi bật
                )
            },
            text = { // tạo phần nội dung xác nhận
                Text( // hiển thị câu hỏi xác nhận xóa
                    text = "Bạn có chắc muốn xóa phương tiện ${vehicleToDelete.plateNumber} không?" // hiển thị đúng biển số xe sắp bị xóa
                )
            },
            confirmButton = { // tạo nút xác nhận xóa
                TextButton( // tạo nút chữ cho thao tác xóa
                    onClick = { // xử lý khi người dùng đồng ý xóa
                        businessVehicleViewModel.deleteVehicle( // yêu cầu ViewModel xóa phương tiện khỏi Room
                            vehicle = vehicleToDelete // truyền đúng phương tiện đang được xác nhận xóa
                        )
                        deletingVehicleId = null // đóng hộp thoại sau khi gửi yêu cầu xóa
                    }
                ) {
                    Text( // hiển thị chữ trên nút xác nhận
                        text = "XÓA", // đặt tên thao tác xác nhận xóa
                        color = Color.Red, // dùng màu đỏ cho thao tác nguy hiểm
                        fontWeight = FontWeight.Bold // làm chữ nút xóa nổi bật
                    )
                }
            },
            dismissButton = { // tạo nút hủy xóa
                TextButton( // tạo nút chữ cho thao tác hủy
                    onClick = { // xử lý khi người dùng không muốn xóa
                        deletingVehicleId = null // hủy phương tiện đang chờ xóa và đóng hộp thoại
                    }
                ) {
                    Text( // hiển thị chữ trên nút hủy
                        text = "HỦY", // đặt tên thao tác hủy
                        color = VehicleBlue, // dùng màu xanh chính của ứng dụng
                        fontWeight = FontWeight.Bold // làm chữ nút hủy rõ ràng
                    )
                }
            }
        )
    }

    Column( // tạo bố cục chính của màn hình
        modifier = Modifier
            .fillMaxSize() // cho màn hình phủ toàn bộ thiết bị
            .background(VehicleBackground) // đặt màu nền chung
    ) {
        Column( // tạo khu vực tiêu đề màu xanh
            modifier = Modifier
                .fillMaxWidth() // cho phần đầu phủ toàn bộ chiều ngang
                .background(VehicleBlue) // đặt nền xanh cho phần đầu
                .windowInsetsPadding( // tránh vùng camera và thanh trạng thái
                    WindowInsets.safeDrawing.only( // chỉ lấy vùng an toàn phía trên
                        WindowInsetsSides.Top // chọn cạnh trên
                    )
                )
                .padding( // tạo lề bên trong phần đầu
                    horizontal = 16.dp, // tạo lề trái và phải
                    vertical = 14.dp // tạo khoảng cách trên và dưới
                )
        ) {
            Row( // xếp nút quay lại và tiêu đề theo chiều ngang
                modifier = Modifier.fillMaxWidth(), // cho hàng tiêu đề phủ toàn bộ chiều ngang
                verticalAlignment = Alignment.CenterVertically // căn giữa theo chiều dọc
            ) {
                TextButton( // tạo nút quay lại
                    onClick = onBack // gọi hành động quay về
                ) {
                    Text( // hiển thị biểu tượng quay lại
                        text = "←", // biểu tượng quay lại
                        color = Color.White, // dùng chữ trắng
                        fontSize = 24.sp // đặt kích thước biểu tượng
                    )
                }

                Column { // xếp tiêu đề và mô tả theo chiều dọc
                    Text( // hiển thị tiêu đề màn hình
                        text = "QUẢN LÝ PHƯƠNG TIỆN", // nội dung tiêu đề
                        color = Color.White, // dùng chữ trắng trên nền xanh
                        fontSize = 20.sp, // đặt kích thước tiêu đề
                        fontWeight = FontWeight.Bold // in đậm tiêu đề
                    )

                    Spacer( // tạo khoảng cách giữa tiêu đề và mô tả
                        modifier = Modifier.height(4.dp) // đặt chiều cao khoảng cách
                    )

                    Text( // hiển thị mô tả màn hình
                        text = "Danh sách xe và trạng thái vận hành", // nội dung mô tả
                        color = Color.White.copy(alpha = 0.9f), // dùng màu trắng nhẹ
                        fontSize = 13.sp // đặt kích thước mô tả
                    )
                }
            }
        }

        Column( // tạo khu vực nội dung có thể cuộn
            modifier = Modifier
                .fillMaxSize() // cho nội dung sử dụng toàn bộ không gian còn lại
                .verticalScroll(rememberScrollState()) // cho phép cuộn khi danh sách dài
                .padding(20.dp), // tạo khoảng cách xung quanh nội dung
            verticalArrangement = Arrangement.Top // đặt nội dung từ phía trên
        ) {
            Text( // hiển thị tiêu đề khu vực tổng quan
                text = "TỔNG QUAN PHƯƠNG TIỆN", // nội dung tiêu đề
                color = VehicleBlue, // dùng màu xanh chính
                fontSize = 17.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )

            Spacer( // tạo khoảng cách đến các thẻ thống kê
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            Column( // chứa hai hàng thẻ thống kê
                modifier = Modifier.fillMaxWidth(), // phủ toàn bộ chiều ngang màn hình
                verticalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai hàng
            ) {
                Row( // hàng thứ nhất gồm Tổng xe và Hoạt động
                    modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
                ) {
                    VehicleSummaryCard( // hiển thị tổng số xe
                        icon = "🚌", // biểu tượng xe buýt
                        value = allBusinessVehicles.size, // tổng số xe thật trong Room
                        label = "Tổng xe", // tên chỉ số
                        valueColor = VehicleBlue, // dùng màu xanh chính
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )

                    VehicleSummaryCard( // hiển thị số xe hoạt động
                        icon = "✅", // biểu tượng hoạt động
                        value = activeVehicleCount, // số xe hoạt động thật trong Room
                        label = "Hoạt động", // tên trạng thái
                        valueColor = Color(0xFF1A9B54), // dùng màu xanh lá
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )
                }

                Row( // hàng thứ hai gồm Bảo trì và Tạm dừng
                    modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // tạo khoảng cách giữa hai thẻ
                ) {
                    VehicleSummaryCard( // hiển thị số xe bảo trì
                        icon = "🔧", // biểu tượng bảo trì
                        value = maintenanceVehicleCount, // số xe bảo trì thật trong Room
                        label = "Bảo trì", // tên trạng thái
                        valueColor = Color(0xFFFF9800), // dùng màu cam
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )

                    VehicleSummaryCard( // hiển thị số xe tạm dừng
                        icon = "⏸️", // biểu tượng tạm dừng
                        value = stoppedVehicleCount, // số xe tạm dừng thật trong Room
                        label = "Tạm dừng", // tên trạng thái
                        valueColor = Color(0xFFE53935), // dùng màu đỏ
                        modifier = Modifier.weight(1f) // chia một nửa chiều rộng hàng
                    )
                }
            }

            Spacer( // tạo khoảng cách sau khu vực tổng quan
                modifier = Modifier.height(20.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tiêu đề danh sách phương tiện
                text = "DANH SÁCH PHƯƠNG TIỆN", // nội dung tiêu đề
                color = VehicleBlue, // sử dụng màu xanh chính
                fontSize = 17.sp, // đặt kích thước tiêu đề
                fontWeight = FontWeight.Bold // in đậm tiêu đề
            )
            Spacer( // tạo khoảng cách giữa tiêu đề và nút thêm phương tiện
                modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
            )

            Button( // tạo nút mở form thêm phương tiện
                onClick = {
                    showAddVehicleForm = true // ghi nhận yêu cầu mở form thêm phương tiện
                },
                modifier = Modifier.fillMaxWidth(), // cho nút phủ toàn bộ chiều ngang
                colors = ButtonDefaults.buttonColors(
                    containerColor = VehicleBlue // sử dụng màu xanh chính của ứng dụng
                ),
                shape = RoundedCornerShape(14.dp) // bo tròn góc nút
            ) {
                Text(
                    text = "THÊM PHƯƠNG TIỆN", // nội dung hiển thị trên nút
                    color = Color.White, // dùng chữ màu trắng
                    fontSize = 14.sp, // đặt kích thước chữ
                    fontWeight = FontWeight.Bold // làm chữ nút nổi bật
                )
            }
            if (showAddVehicleForm) { // chỉ hiển thị form khi người dùng bấm nút thêm phương tiện
                Card( // tạo thẻ chứa các ô nhập thông tin phương tiện mới
                    modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
                    shape = RoundedCornerShape(16.dp), // bo tròn các góc của thẻ
                    colors = CardDefaults.cardColors( // thiết lập màu nền cho thẻ
                        containerColor = Color.White // sử dụng nền trắng cho form
                    ) // kết thúc thiết lập màu thẻ
                ) { // bắt đầu nội dung bên trong thẻ
                    Column( // xếp các thành phần của form theo chiều dọc
                        modifier = Modifier.padding(16.dp) // tạo khoảng cách bên trong thẻ
                    ) { // bắt đầu nội dung của form
                        Text( // hiển thị tiêu đề form thêm phương tiện
                            text = "THÔNG TIN PHƯƠNG TIỆN MỚI", // đặt nội dung tiêu đề form
                            color = VehicleBlue, // sử dụng màu xanh chính của ứng dụng
                            fontSize = 16.sp, // đặt kích thước chữ tiêu đề
                            fontWeight = FontWeight.Bold // in đậm tiêu đề form
                        ) // kết thúc tiêu đề form

                        Spacer( // tạo khoảng cách từ tiêu đề đến ô biển số
                            modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
                        ) // kết thúc khoảng cách

                        OutlinedTextField( // tạo ô nhập biển số phương tiện
                            value = newVehiclePlateNumber, // hiển thị biển số đang nhập
                            onValueChange = { newValue -> // nhận biển số mới khi người dùng nhập
                                newVehiclePlateNumber = newValue // lưu biển số mới vào state
                            }, // kết thúc xử lý thay đổi biển số
                            modifier = Modifier.fillMaxWidth(), // cho ô biển số phủ toàn bộ chiều ngang
                            label = { // tạo nhãn hướng dẫn cho ô biển số
                                Text( // hiển thị nội dung nhãn biển số
                                    text = "Biển số phương tiện" // đặt tên nhãn của ô biển số
                                ) // kết thúc nội dung nhãn biển số
                            }, // kết thúc nhãn biển số
                            singleLine = true, // chỉ cho phép nhập biển số trên một dòng
                            shape = RoundedCornerShape(14.dp) // bo tròn góc ô nhập biển số
                        ) // kết thúc ô nhập biển số

                        Spacer( // tạo khoảng cách giữa hai ô nhập
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        ) // kết thúc khoảng cách

                        OutlinedTextField( // tạo ô nhập loại phương tiện
                            value = newVehicleType, // hiển thị loại phương tiện đang nhập
                            onValueChange = { newValue -> // nhận loại xe mới khi người dùng nhập
                                newVehicleType = newValue // lưu loại phương tiện mới vào state
                            }, // kết thúc xử lý thay đổi loại xe
                            modifier = Modifier.fillMaxWidth(), // cho ô loại xe phủ toàn bộ chiều ngang
                            label = { // tạo nhãn hướng dẫn cho ô loại xe
                                Text( // hiển thị nội dung nhãn loại xe
                                    text = "Loại xe hoặc dòng xe" // đặt tên nhãn của ô loại xe
                                ) // kết thúc nội dung nhãn loại xe
                            }, // kết thúc nhãn loại xe
                            singleLine = true, // chỉ cho phép nhập loại xe trên một dòng
                            shape = RoundedCornerShape(14.dp) // bo tròn góc ô nhập loại xe
                        ) // kết thúc ô nhập loại phương tiện
                        Spacer( // tạo khoảng cách giữa ô loại xe và ô tài xế
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        ) // kết thúc khoảng cách

                        OutlinedTextField( // tạo ô nhập tên tài xế phụ trách
                            value = newVehicleDriverName, // hiển thị tên tài xế đang nhập
                            onValueChange = { newValue -> // nhận tên tài xế mới khi người dùng nhập
                                newVehicleDriverName = newValue // lưu tên tài xế mới vào state
                            }, // kết thúc xử lý thay đổi tên tài xế
                            modifier = Modifier.fillMaxWidth(), // cho ô tài xế phủ toàn bộ chiều ngang
                            label = { // tạo nhãn hướng dẫn cho ô tài xế
                                Text( // hiển thị nội dung nhãn tài xế
                                    text = "Tài xế phụ trách" // đặt tên nhãn của ô tài xế
                                ) // kết thúc nội dung nhãn tài xế
                            }, // kết thúc nhãn tài xế
                            singleLine = true, // chỉ cho phép nhập tên trên một dòng
                            shape = RoundedCornerShape(14.dp) // bo tròn góc ô nhập tài xế
                        ) // kết thúc ô nhập tài xế

                        Spacer( // tạo khoảng cách giữa ô tài xế và ngày bảo trì
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        ) // kết thúc khoảng cách

                        OutlinedTextField( // tạo ô nhập ngày bảo trì gần nhất
                            value = newVehicleMaintenanceDate, // hiển thị ngày bảo trì đang nhập
                            onValueChange = { newValue -> // nhận ngày bảo trì mới khi người dùng nhập
                                newVehicleMaintenanceDate = newValue // lưu ngày bảo trì mới vào state
                            }, // kết thúc xử lý thay đổi ngày bảo trì
                            modifier = Modifier.fillMaxWidth(), // cho ô ngày bảo trì phủ toàn bộ chiều ngang
                            label = { // tạo nhãn hướng dẫn cho ô ngày bảo trì
                                Text( // hiển thị nội dung nhãn ngày bảo trì
                                    text = "Ngày bảo trì gần nhất: yyyy-MM-dd" // hướng dẫn định dạng ngày
                                ) // kết thúc nội dung nhãn ngày bảo trì
                            }, // kết thúc nhãn ngày bảo trì
                            singleLine = true, // chỉ cho phép nhập ngày trên một dòng
                            shape = RoundedCornerShape(14.dp) // bo tròn góc ô ngày bảo trì
                        ) // kết thúc ô nhập ngày bảo trì

                        Spacer( // tạo khoảng cách giữa ngày bảo trì và trạng thái
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        ) // kết thúc khoảng cách

                        OutlinedTextField( // tạo ô nhập trạng thái phương tiện
                            value = newVehicleStatus, // hiển thị trạng thái đang nhập
                            onValueChange = { newValue -> // nhận trạng thái mới khi người dùng nhập
                                newVehicleStatus = newValue // lưu trạng thái mới vào state
                            }, // kết thúc xử lý thay đổi trạng thái
                            modifier = Modifier.fillMaxWidth(), // cho ô trạng thái phủ toàn bộ chiều ngang
                            label = { // tạo nhãn hướng dẫn cho ô trạng thái
                                Text( // hiển thị nội dung nhãn trạng thái
                                    text = "Trạng thái: Hoạt động / Bảo trì / Tạm dừng" // hướng dẫn trạng thái hợp lệ
                                ) // kết thúc nội dung nhãn trạng thái
                            }, // kết thúc nhãn trạng thái
                            singleLine = true, // chỉ cho phép nhập trạng thái trên một dòng
                            shape = RoundedCornerShape(14.dp) // bo tròn góc ô trạng thái
                        ) // kết thúc ô nhập trạng thái
                        Spacer( // tạo khoảng cách giữa ô trạng thái và thông báo lỗi
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        )

                        if (vehicleFormError.isNotEmpty()) { // chỉ hiện thông báo khi form đang có lỗi
                            Text( // hiển thị nội dung lỗi cho người dùng
                                text = vehicleFormError, // lấy nội dung từ biến lưu lỗi
                                color = Color.Red, // dùng màu đỏ để cảnh báo
                                fontSize = 13.sp, // đặt kích thước chữ thông báo
                                modifier = Modifier.fillMaxWidth() // cho thông báo sử dụng toàn bộ chiều ngang
                            )
                        }

                        Spacer( // tạo khoảng cách trước nút lưu phương tiện
                            modifier = Modifier.height(10.dp) // đặt khoảng cách dọc
                        )

                        Button( // tạo nút lưu phương tiện mới vào Room
                            onClick = { // xử lý khi doanh nghiệp bấm nút lưu
                                val cleanedPlateNumber = newVehiclePlateNumber.trim() // loại bỏ khoảng trắng của biển số
                                val cleanedVehicleType = newVehicleType.trim() // loại bỏ khoảng trắng của loại xe
                                val cleanedDriverName = newVehicleDriverName.trim() // loại bỏ khoảng trắng của tên tài xế
                                val cleanedMaintenanceDate = newVehicleMaintenanceDate.trim() // loại bỏ khoảng trắng của ngày bảo trì
                                val cleanedStatus = newVehicleStatus.trim() // loại bỏ khoảng trắng của trạng thái

                                if ( // kiểm tra có trường dữ liệu nào đang để trống hay không
                                    cleanedPlateNumber.isBlank() ||
                                    cleanedVehicleType.isBlank() ||
                                    cleanedDriverName.isBlank() ||
                                    cleanedMaintenanceDate.isBlank() ||
                                    cleanedStatus.isBlank()
                                ) {
                                    vehicleFormError = "Vui lòng nhập đầy đủ thông tin phương tiện." // thông báo khi thiếu dữ liệu
                                } else { // xử lý khi tất cả trường thông tin đã được nhập
                                    vehicleFormError = "" // xóa thông báo lỗi cũ trước khi kiểm tra biển số

                                    val newVehicle = BusinessVehicleEntity( // tạo phương tiện mới từ dữ liệu form
                                        id = 0, // để Room tự động tạo mã phương tiện
                                        plateNumber = cleanedPlateNumber, // lưu biển số đã loại bỏ khoảng trắng
                                        vehicleType = cleanedVehicleType, // lưu loại xe đã loại bỏ khoảng trắng
                                        driverName = cleanedDriverName, // lưu tên tài xế đã loại bỏ khoảng trắng
                                        maintenanceDate = cleanedMaintenanceDate, // lưu ngày bảo trì đã loại bỏ khoảng trắng
                                        status = cleanedStatus // lưu trạng thái đã loại bỏ khoảng trắng
                                    ) // kết thúc dữ liệu phương tiện mới

                                    businessVehicleViewModel.saveVehicleIfPlateNumberAvailable( // kiểm tra và lưu phương tiện
                                        vehicle = BusinessVehicleEntity( // tạo dữ liệu phương tiện từ nội dung form
                                            id = editingVehicleId ?: 0, // giữ id cũ khi sửa và dùng 0 khi thêm mới
                                            plateNumber = newVehiclePlateNumber.trim(), // loại bỏ khoảng trắng thừa của biển số
                                            vehicleType = newVehicleType.trim(), // loại bỏ khoảng trắng thừa của loại xe
                                            driverName = newVehicleDriverName.trim(), // loại bỏ khoảng trắng thừa của tên tài xế
                                            maintenanceDate = newVehicleMaintenanceDate.trim(), // loại bỏ khoảng trắng thừa của ngày bảo trì
                                            status = newVehicleStatus.trim() // loại bỏ khoảng trắng thừa của trạng thái
                                        ),
                                        editingVehicleId = editingVehicleId, // truyền id để ViewModel phân biệt thêm mới và chỉnh sửa
                                        onResult = { saveSucceeded -> // nhận kết quả kiểm tra và lưu từ ViewModel
                                            if (saveSucceeded) { // xử lý khi lưu phương tiện thành công
                                                newVehiclePlateNumber = "" // xóa biển số sau khi lưu
                                                newVehicleType = "" // xóa loại xe sau khi lưu
                                                newVehicleDriverName = "" // xóa tên tài xế sau khi lưu
                                                newVehicleMaintenanceDate = "" // xóa ngày bảo trì sau khi lưu
                                                newVehicleStatus = "Hoạt động" // đưa trạng thái về giá trị mặc định
                                                vehicleFormError = "" // xóa thông báo lỗi cũ
                                                editingVehicleId = null // thoát khỏi chế độ chỉnh sửa
                                                showAddVehicleForm = false // đóng form sau khi lưu thành công
                                            } else { // xử lý khi biển số thuộc một phương tiện khác
                                                vehicleFormError = "Biển số phương tiện đã tồn tại." // hiển thị lỗi trùng biển số
                                            }
                                        }
                                    )
                                } // kết thúc xử lý khi form đã nhập đầy đủ
                            },
                            modifier = Modifier.fillMaxWidth(), // cho nút phủ toàn bộ chiều ngang form
                            colors = ButtonDefaults.buttonColors( // thiết lập màu của nút lưu
                                containerColor = VehicleBlue // sử dụng màu xanh chính của ứng dụng
                            ),
                            shape = RoundedCornerShape(14.dp) // bo tròn góc nút lưu
                        ) {
                            Text( // hiển thị chữ bên trong nút
                                text = "LƯU PHƯƠNG TIỆN", // đặt tên thao tác lưu
                                color = Color.White, // dùng chữ màu trắng
                                fontSize = 14.sp, // đặt kích thước chữ
                                fontWeight = FontWeight.Bold // làm chữ nút nổi bật
                            )
                        }
                    } // kết thúc nội dung Column của form
                } // kết thúc thẻ chứa form

                Spacer( // tạo khoảng cách giữa form và ô tìm kiếm
                    modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
                ) // kết thúc khoảng cách
            } // kết thúc điều kiện hiển thị form
            Spacer( // tạo khoảng cách từ nút đến ô tìm kiếm
                modifier = Modifier.height(12.dp) // đặt khoảng cách dọc
            )

            Spacer( // tạo khoảng cách đến ô tìm kiếm
                modifier = Modifier.height(10.dp) // đặt chiều cao khoảng cách
            )

            OutlinedTextField( // tạo ô tìm kiếm phương tiện
                value = vehicleSearchQuery, // hiển thị từ khóa đang lưu trong ViewModel
                onValueChange = { newValue -> // nhận nội dung mới khi người dùng nhập
                    businessVehicleViewModel.updateSearchQuery( // gửi từ khóa sang ViewModel
                        newValue = newValue // truyền từ khóa mới
                    )
                },
                modifier = Modifier.fillMaxWidth(), // cho ô tìm kiếm phủ toàn bộ chiều ngang
                label = { // tạo nhãn hướng dẫn trong ô
                    Text( // hiển thị nội dung hướng dẫn
                        text = "Tìm biển số / loại xe" // nội dung hướng dẫn tìm kiếm
                    )
                },
                singleLine = true, // chỉ cho phép nhập trên một dòng
                shape = RoundedCornerShape(14.dp) // bo tròn góc ô tìm kiếm
            )

            Spacer( // tạo khoảng cách đến số lượng kết quả
                modifier = Modifier.height(12.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị số phương tiện phù hợp
                text = "TÌM THẤY ${filteredBusinessVehicles.size} PHƯƠNG TIỆN", // ghép số lượng kết quả
                color = Color.Gray, // dùng màu xám cho thông tin phụ
                fontSize = 13.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Medium // dùng độ đậm vừa
            )

            Spacer( // tạo khoảng cách trước danh sách xe
                modifier = Modifier.height(10.dp) // đặt chiều cao khoảng cách
            )

            if (filteredBusinessVehicles.isEmpty()) { // kiểm tra không có xe phù hợp
                Card( // tạo thẻ thông báo không tìm thấy dữ liệu
                    modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
                    shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
                    colors = CardDefaults.cardColors( // thiết lập màu nền thẻ
                        containerColor = Color.White // dùng nền trắng
                    )
                ) {
                    Text( // hiển thị thông báo không có kết quả
                        text = "Không tìm thấy phương tiện phù hợp.", // nội dung thông báo
                        color = Color.Gray, // dùng màu xám
                        fontSize = 14.sp, // đặt kích thước chữ
                        modifier = Modifier.padding(20.dp) // tạo khoảng cách bên trong thẻ
                    )
                }
            } else { // xử lý khi có phương tiện phù hợp
                filteredBusinessVehicles.forEach { vehicle -> // lần lượt lấy từng phương tiện trong danh sách đã lọc
                    BusinessVehicleListCard( // hiển thị thẻ thông tin xe hiện tại
                        vehicle = vehicle, // truyền phương tiện hiện tại vào thẻ
                        onEdit = { selectedVehicle -> // nhận phương tiện người dùng chọn chỉnh sửa
                            editingVehicleId = selectedVehicle.id // lưu id của phương tiện đang chỉnh sửa
                            newVehiclePlateNumber = selectedVehicle.plateNumber // đưa biển số hiện tại lên form
                            newVehicleType = selectedVehicle.vehicleType // đưa loại xe hiện tại lên form
                            newVehicleDriverName = selectedVehicle.driverName // đưa tài xế hiện tại lên form
                            newVehicleMaintenanceDate = selectedVehicle.maintenanceDate // đưa ngày bảo trì hiện tại lên form
                            newVehicleStatus = selectedVehicle.status // đưa trạng thái hiện tại lên form
                            vehicleFormError = "" // xóa thông báo lỗi cũ của form
                            showAddVehicleForm = true // mở form để chỉnh sửa phương tiện
                        },
                        onDelete = { selectedVehicle -> // nhận phương tiện người dùng vừa chọn xóa
                            deletingVehicleId = selectedVehicle.id // lưu id để mở hộp thoại xác nhận xóa đúng xe
                        }
                    )

                    Spacer( // tạo khoảng cách giữa hai thẻ phương tiện
                        modifier = Modifier.height(10.dp) // đặt khoảng cách dọc giữa hai thẻ
                    )
                } // kết thúc vòng lặp danh sách phương tiện
            } // kết thúc xử lý khi có phương tiện phù hợp
        }
    }
}

@Composable
private fun VehicleSummaryCard( // tạo thẻ thống kê dùng chung
    icon: String, // biểu tượng của trạng thái phương tiện
    value: Int, // số lượng phương tiện
    label: String, // tên trạng thái phương tiện
    valueColor: Color, // màu của số liệu
    modifier: Modifier = Modifier // cho phép màn cha điều chỉnh kích thước thẻ
) {
    Card( // tạo nền trắng dạng thẻ
        modifier = modifier.height(126.dp), // đặt chiều cao đồng đều cho tất cả thẻ
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors( // thiết lập màu nền thẻ
            containerColor = Color.White // dùng nền trắng cho thẻ
        )
    ) {
        Column( // xếp biểu tượng, số lượng và tên trạng thái theo chiều dọc
            modifier = Modifier
                .fillMaxSize() // cho nội dung phủ toàn bộ thẻ
                .padding(12.dp), // tạo khoảng cách bên trong thẻ
            horizontalAlignment = Alignment.CenterHorizontally, // căn giữa theo chiều ngang
            verticalArrangement = Arrangement.Center // căn giữa theo chiều dọc
        ) {
            Text( // hiển thị biểu tượng trạng thái
                text = icon, // nhận biểu tượng được truyền vào
                fontSize = 24.sp // đặt kích thước biểu tượng
            )

            Spacer( // tạo khoảng cách dưới biểu tượng
                modifier = Modifier.height(4.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị số lượng phương tiện
                text = value.toString(), // chuyển số lượng thành chữ
                color = valueColor, // dùng màu tương ứng trạng thái
                fontSize = 25.sp, // làm số liệu nổi bật
                fontWeight = FontWeight.Bold, // in đậm số liệu
                textAlign = TextAlign.Center // căn giữa số liệu
            )

            Spacer( // tạo khoảng cách trước tên trạng thái
                modifier = Modifier.height(3.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tên trạng thái
                text = label, // nhận tên trạng thái được truyền vào
                color = Color.DarkGray, // dùng màu xám đậm
                fontSize = 13.sp, // đặt kích thước chữ
                textAlign = TextAlign.Center // căn giữa tên trạng thái
            )
        }
    }
}

@Composable
private fun BusinessVehicleListCard( // tạo thẻ hiển thị một phương tiện
    vehicle: BusinessVehicleEntity, // nhận dữ liệu phương tiện trực tiếp từ Room
    onEdit: (BusinessVehicleEntity) -> Unit, // gửi phương tiện được chọn lên màn hình để chỉnh sửa
    onDelete: (BusinessVehicleEntity) -> Unit // gửi phương tiện được chọn lên màn hình để xác nhận xóa
) {
    val vehicleStatusColor = when (vehicle.status) { // chọn màu theo trạng thái xe
        "Hoạt động" -> Color(0xFF1A9B54) // dùng màu xanh cho xe đang hoạt động
        "Bảo trì" -> Color(0xFFFF9800) // dùng màu cam cho xe đang bảo trì
        else -> Color(0xFFE53935) // dùng màu đỏ cho xe đang tạm dừng
    }

    Card( // tạo nền thẻ thông tin phương tiện
        modifier = Modifier.fillMaxWidth(), // cho thẻ phủ toàn bộ chiều ngang
        shape = RoundedCornerShape(16.dp), // bo tròn góc thẻ
        colors = CardDefaults.cardColors( // thiết lập màu nền thẻ
            containerColor = Color.White // sử dụng nền trắng
        )
    ) {
        Column( // xếp thông tin phương tiện theo chiều dọc
            modifier = Modifier
                .fillMaxWidth() // cho nội dung phủ toàn bộ chiều ngang
                .padding(16.dp) // tạo khoảng cách bên trong thẻ
        ) {
            Row( // xếp biển số và trạng thái trên cùng một hàng
                modifier = Modifier.fillMaxWidth(), // cho hàng phủ toàn bộ chiều ngang
                horizontalArrangement = Arrangement.SpaceBetween, // đẩy hai nội dung sang hai phía
                verticalAlignment = Alignment.CenterVertically // căn giữa nội dung theo chiều dọc
            ) {
                Text( // hiển thị biển số phương tiện
                    text = vehicle.plateNumber, // lấy biển số từ dữ liệu xe
                    color = VehicleBlue, // sử dụng màu xanh chính
                    fontSize = 17.sp, // đặt kích thước biển số
                    fontWeight = FontWeight.Bold // in đậm biển số
                )

                Column( // xếp trạng thái và nút sửa theo chiều dọc
                    horizontalAlignment = Alignment.End // căn nội dung về phía bên phải thẻ
                ) {
                    Text( // hiển thị trạng thái phương tiện
                        text = vehicle.status, // lấy trạng thái từ dữ liệu xe
                        color = vehicleStatusColor, // dùng màu tương ứng trạng thái
                        fontSize = 13.sp, // đặt kích thước chữ
                        fontWeight = FontWeight.Bold // in đậm trạng thái
                    )

                    TextButton( // tạo nút chỉnh sửa phương tiện
                        onClick = { // xử lý khi người dùng bấm nút sửa
                            onEdit(vehicle) // gửi phương tiện hiện tại lên màn hình chính
                        }
                    ) {
                        Text( // hiển thị chữ trên nút sửa
                            text = "SỬA", // đặt tên thao tác chỉnh sửa
                            color = VehicleBlue, // dùng màu xanh chính của ứng dụng
                            fontSize = 12.sp, // đặt kích thước chữ nút sửa
                            fontWeight = FontWeight.Bold // làm chữ nút sửa nổi bật
                        )
                    }

                    TextButton( // tạo nút yêu cầu xóa phương tiện
                        onClick = { // xử lý khi người dùng bấm nút xóa
                            onDelete(vehicle) // gửi đúng phương tiện hiện tại lên hộp thoại xác nhận
                        }
                    ) {
                        Text( // hiển thị chữ trên nút xóa
                            text = "XÓA", // đặt tên thao tác xóa phương tiện
                            color = Color.Red, // dùng màu đỏ để thể hiện thao tác nguy hiểm
                            fontSize = 12.sp, // đặt kích thước chữ nút xóa
                            fontWeight = FontWeight.Bold // làm chữ nút xóa nổi bật
                        )
                    }
                }
            }

            Spacer( // tạo khoảng cách sau hàng biển số
                modifier = Modifier.height(8.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị loại phương tiện
                text = "Loại xe: ${vehicle.vehicleType}", // ghép nhãn với loại xe
                color = Color.DarkGray, // sử dụng màu xám đậm
                fontSize = 14.sp, // đặt kích thước chữ
                fontWeight = FontWeight.Medium // dùng độ đậm vừa
            )

            Spacer( // tạo khoảng cách đến thông tin tài xế
                modifier = Modifier.height(5.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị tài xế phụ trách
                text = "Tài xế: ${vehicle.driverName}", // ghép nhãn với tên tài xế
                color = Color.Gray, // sử dụng màu xám
                fontSize = 13.sp // đặt kích thước chữ
            )

            Spacer( // tạo khoảng cách đến ngày bảo trì
                modifier = Modifier.height(5.dp) // đặt chiều cao khoảng cách
            )

            Text( // hiển thị ngày bảo trì gần nhất
                text = "Bảo trì gần nhất: ${vehicle.maintenanceDate}", // ghép nhãn với ngày bảo trì
                color = Color.Gray, // sử dụng màu xám
                fontSize = 13.sp // đặt kích thước chữ
            )
        }
    }
}