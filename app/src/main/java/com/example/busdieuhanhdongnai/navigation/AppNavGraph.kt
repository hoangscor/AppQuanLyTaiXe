package com.example.busdieuhanhdongnai.navigation

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
                onOpenIncidentReport = {
                    navController.navigate(Routes.INCIDENT_REPORT) // mở màn báo cáo sự cố
                },
                onOpenTripHistory = {
                    navController.navigate(Routes.TRIP_HISTORY) // mở màn nhật ký chuyến xe
                }
            )
        }

        composable(Routes.SCHEDULE) { // khai báo màn lịch trình
            ScheduleScreen(
                onBack = {
                    navController.popBackStack() // quay về trang trước
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
                    navController.popBackStack() // quay về trang trước
                }
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