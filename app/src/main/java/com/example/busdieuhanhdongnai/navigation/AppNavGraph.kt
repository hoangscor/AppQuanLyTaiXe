package com.example.busdieuhanhdongnai.navigation
import com.example.busdieuhanhdongnai.feature.driver.incident.IncidentHistoryScreen // màn danh sách báo cáo sự cố đã lưu
import androidx.compose.runtime.getValue // đọc giá trị state
import androidx.compose.runtime.mutableStateOf // tạo state có thể thay đổi
import androidx.compose.runtime.saveable.rememberSaveable // giữ dữ liệu khi xoay màn hình
import androidx.compose.runtime.setValue // cập nhật state

import com.example.busdieuhanhdongnai.feature.driver.qr.QrCheckInScreen // màn quét QR
import com.example.busdieuhanhdongnai.feature.driver.incident.IncidentReportScreen // màn báo cáo sự cố
import com.example.busdieuhanhdongnai.feature.driver.trip.TripEntryScreen // màn nhập dữ liệu chuyến
import com.example.busdieuhanhdongnai.feature.driver.history.TripHistoryScreen // màn nhật ký chuyến xe
import com.example.busdieuhanhdongnai.feature.driver.notification.NotificationScreen // màn hình thông báo
import com.example.busdieuhanhdongnai.feature.driver.schedule.ScheduleScreen // màn hình lịch trình
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.busdieuhanhdongnai.feature.auth.LoginScreen
import com.example.busdieuhanhdongnai.feature.business.BusinessHomeScreen // màn trang chủ doanh nghiệp
import com.example.busdieuhanhdongnai.feature.business.assignment.AssignmentManagementScreen // màn phân công lịch chạy của doanh nghiệp
import com.example.busdieuhanhdongnai.feature.business.vehicle.VehicleManagementScreen // màn quản lý phương tiện doanh nghiệp
import com.example.busdieuhanhdongnai.feature.driver.DriverHomeScreen
import com.example.busdieuhanhdongnai.feature.business.customer.CustomerManagementScreen // màn quản lý khách hàng và vé

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    var selectedRoute by rememberSaveable { mutableStateOf("Tuyến 01: Bến xe A → Bến xe B") } // giữ tuyến xe được chọn
    var selectedVehiclePlate by rememberSaveable { mutableStateOf("51B-123.45") } // giữ biển số xe được chọn
    var selectedScheduledTime by rememberSaveable { mutableStateOf("07:00 - 08:00") } // giữ giờ dự kiến của chuyến được chọn

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onDriverLogin = { // xử lý khi bấm đăng nhập tài xế
                    navController.navigate(Routes.DRIVER_HOME) { // mở trang chủ tài xế
                        popUpTo(Routes.LOGIN) { // xóa màn đăng nhập khỏi lịch sử
                            inclusive = true // không cho quay lại bằng nút Back
                        }
                    }
                },
                onBusinessLogin = { // xử lý khi bấm đăng nhập doanh nghiệp
                    navController.navigate(Routes.BUSINESS_HOME) { // mở trang chủ doanh nghiệp
                        popUpTo(Routes.LOGIN) { // xóa màn đăng nhập khỏi lịch sử
                            inclusive = true // không cho quay lại bằng nút Back
                        }
                    }
                }
            )
        }

        composable(Routes.DRIVER_HOME) {
            DriverHomeScreen(
                onOpenSchedule = {
                    navController.navigate(Routes.SCHEDULE) // mở màn lịch trình
                },
                onOpenNotifications = {
                    navController.navigate(Routes.NOTIFICATIONS) // mở màn thông báo
                },
                onOpenTripEntry = {
                    navController.navigate(Routes.TRIP_ENTRY) // mở màn nhập dữ liệu chuyến
                },
                onOpenQrCheckIn = {
                    navController.navigate(Routes.QR_CHECKIN) // mở màn quét QR hoặc thẻ điện tử
                },
                onOpenIncidentReport = {
                    navController.navigate(Routes.INCIDENT_REPORT) // mở màn báo cáo sự cố
                },
                onOpenIncidentHistory = {
                    navController.navigate(Routes.INCIDENT_HISTORY) // mở danh sách sự cố đã lưu trong Room
                },
                onOpenTripHistory = {
                    navController.navigate(Routes.TRIP_HISTORY) // mở màn nhật ký chuyến xe
                }, // ngăn cách với callback kế tiếp
                        onOpenNextTrip = { route, vehiclePlate, scheduledTime -> // nhận dữ liệu từ thẻ chuyến kế tiếp
                    selectedRoute = route // lưu tuyến được chọn
                    selectedVehiclePlate = vehiclePlate // lưu biển số xe được chọn
                    selectedScheduledTime = scheduledTime // lưu khung giờ dự kiến được chọn
                    navController.navigate(Routes.TRIP_ENTRY) // mở màn nhập dữ liệu chuyến
                }
            )
        }
        composable(Routes.BUSINESS_HOME) { // khai báo màn trang chủ doanh nghiệp
            BusinessHomeScreen(
                onOpenCustomers = { // xử lý khi doanh nghiệp mở chức năng khách hàng
                    navController.navigate(Routes.BUSINESS_CUSTOMERS) // chuyển sang màn quản lý khách hàng
                },
                onOpenVehicles = { // xử lý khi doanh nghiệp bấm Quản lý phương tiện
                    navController.navigate(Routes.BUSINESS_VEHICLES) // chuyển sang màn quản lý phương tiện
                },
                onOpenAssignments = { // xử lý khi doanh nghiệp bấm Phân công lịch chạy
                    navController.navigate(Routes.BUSINESS_ASSIGNMENTS) // chuyển sang màn phân công lịch chạy
                },
                onLogout = { // xử lý khi doanh nghiệp đăng xuất
                    navController.navigate(Routes.LOGIN) { // quay về màn đăng nhập
                        popUpTo(Routes.BUSINESS_HOME) { // xóa trang chủ doanh nghiệp khỏi lịch sử
                            inclusive = true // không cho quay lại trang doanh nghiệp bằng nút Back
                        }
                    }
                }
            )
        }
        composable(Routes.BUSINESS_CUSTOMERS) { // khai báo màn quản lý khách hàng
            CustomerManagementScreen(
                onBack = { // xử lý khi bấm nút quay lại
                    navController.popBackStack() // quay về trang chủ doanh nghiệp
                }
            )
        }
        composable(Routes.BUSINESS_VEHICLES) { // khai báo màn quản lý phương tiện doanh nghiệp
            VehicleManagementScreen(
                onBack = { // xử lý khi bấm nút quay lại
                    navController.popBackStack() // quay về trang chủ doanh nghiệp
                }
            )
        }
        composable(Routes.BUSINESS_ASSIGNMENTS) { // khai báo màn phân công lịch chạy của doanh nghiệp
            AssignmentManagementScreen( // hiển thị giao diện phân công lịch chạy
                onBack = { // xử lý khi người dùng bấm nút quay lại
                    navController.popBackStack() // quay về màn hình doanh nghiệp trước đó
                }
            ) // kết thúc màn phân công lịch chạy
        } // kết thúc route phân công lịch chạy
        composable(Routes.SCHEDULE) { // khai báo màn lịch trình
            ScheduleScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                },
                onSelectTrip = { route, vehiclePlate, scheduledTime -> // nhận dữ liệu chuyến tài xế vừa chọn
                    selectedRoute = route // lưu tuyến xe đã chọn
                    selectedVehiclePlate = vehiclePlate // lưu biển số xe đã chọn
                    selectedScheduledTime = scheduledTime // lưu giờ dự kiến đã chọn
                    navController.navigate(Routes.TRIP_ENTRY) // chuyển sang màn nhập dữ liệu chuyến
                }
            )
        }
        composable(Routes.NOTIFICATIONS) { // khai báo màn thông báo
            NotificationScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                }
            )
        }
        composable(Routes.TRIP_ENTRY) { // khai báo màn nhập dữ liệu chuyến
            TripEntryScreen(
                onBack = {
                    navController.popBackStack() // quay lại màn trước
                },
                selectedRoute = selectedRoute, // truyền tuyến xe đã chọn từ lịch trình
                selectedVehiclePlate = selectedVehiclePlate, // truyền biển số xe đã chọn từ lịch trình
                selectedScheduledTime = selectedScheduledTime // truyền giờ dự kiến đã chọn từ lịch trình
            )
        }
        composable(Routes.QR_CHECKIN) { // khai báo màn quét QR
            QrCheckInScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                }
            )
        }
        composable(Routes.INCIDENT_REPORT) { // khai báo màn báo cáo sự cố
            IncidentReportScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                }
            )
        }
        composable(Routes.INCIDENT_HISTORY) { // khai báo màn danh sách báo cáo sự cố
            IncidentHistoryScreen(
                onBack = {
                    navController.popBackStack() // quay về màn trước
                }
            )
        }
        composable(Routes.TRIP_HISTORY) { // khai báo màn nhật ký chuyến xe
            TripHistoryScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                }
            )
        }
    }
}