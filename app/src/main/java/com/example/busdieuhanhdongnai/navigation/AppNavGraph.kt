package com.example.busdieuhanhdongnai.navigation

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
import com.example.busdieuhanhdongnai.feature.driver.DriverHomeScreen

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
                onLogin = {
                    navController.navigate(Routes.DRIVER_HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
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
        composable(Routes.TRIP_HISTORY) { // khai báo màn nhật ký chuyến xe
            TripHistoryScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
                }
            )
        }
    }
}