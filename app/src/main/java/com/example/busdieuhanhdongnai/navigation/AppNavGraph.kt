package com.example.busdieuhanhdongnai.navigation

import com.example.busdieuhanhdongnai.feature.driver.qr.QrCheckInScreen // màn quét QR
import com.example.busdieuhanhdongnai.feature.driver.trip.TripEntryScreen // màn nhập dữ liệu chuyến
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
            onOpenQrCheckIn = {
                navController.navigate(Routes.QR_CHECKIN) // mở màn quét QR
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
    }
}