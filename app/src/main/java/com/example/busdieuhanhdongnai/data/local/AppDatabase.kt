package com.example.busdieuhanhdongnai.data.local

import android.content.Context // dùng Context để tạo database
import androidx.room.Database // khai báo Room database
import androidx.room.Room // tạo database bằng Room
import androidx.room.RoomDatabase // lớp cha của database Room
import androidx.room.migration.Migration // khai báo migration khi thay đổi database
import androidx.sqlite.db.SupportSQLiteDatabase // thao tác SQL trực tiếp trong migration

@Database( // khai báo cấu hình database
    entities = [
        TripEntity::class, // bảng dữ liệu chuyến xe
        IncidentEntity::class, // bảng báo cáo sự cố
        CheckInEntity::class, // bảng hành khách check-in
        NotificationEntity::class, // bảng thông báo
        CustomerEntity::class, // bảng thống kê khách hàng và cơ cấu vé
        BusinessVehicleEntity::class // bảng phương tiện của doanh nghiệp
    ],
    version = 9, // tăng phiên bản vì thêm bảng phương tiện doanh nghiệp
    exportSchema = false // chưa xuất file schema ở giai đoạn này
)
abstract class AppDatabase : RoomDatabase() { // database chính của ứng dụng

    abstract fun tripDao(): TripDao // cung cấp DAO để thao tác bảng trips
    abstract fun incidentDao(): IncidentDao // cung cấp DAO để thao tác bảng incidents
    abstract fun checkInDao(): CheckInDao // cung cấp DAO thao tác bảng check_ins
    abstract fun notificationDao(): NotificationDao // cung cấp DAO thao tác bảng notifications
    abstract fun customerDao(): CustomerDao // cung cấp DAO thao tác bảng thống kê khách hàng
    abstract fun businessVehicleDao(): BusinessVehicleDao // cung cấp DAO thao tác bảng phương tiện doanh nghiệp
    companion object { // nơi giữ một bản database duy nhất
        private val MIGRATION_1_2 = object : Migration(1, 2) { // chuyển database từ version 1 sang version 2
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi app đang có database cũ
                db.execSQL(
                    "ALTER TABLE trips ADD COLUMN vehiclePlate TEXT NOT NULL DEFAULT ''"
                ) // thêm cột biển số xe, dữ liệu cũ để trống
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) { // chuyển database từ version 2 sang version 3
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi app đã có database version 2
                db.execSQL(
                    "ALTER TABLE trips ADD COLUMN scheduledTime TEXT NOT NULL DEFAULT ''"
                ) // thêm cột giờ dự kiến, dữ liệu cũ để trống
            }
        }
        private val MIGRATION_3_4 = object : Migration(3, 4) { // nâng database từ version 3 lên version 4
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi app đang có database version 3
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS incidents (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                route TEXT NOT NULL,
                vehiclePlate TEXT NOT NULL,
                incidentType TEXT NOT NULL,
                description TEXT NOT NULL,
                status TEXT NOT NULL
            )
            """.trimIndent()
                ) // tạo bảng lưu báo cáo sự cố
            }
        }
        private val MIGRATION_4_5 = object : Migration(4, 5) { // nâng database từ version 4 lên 5
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi thiết bị đang có database version 4
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS check_ins (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                passengerCode TEXT NOT NULL,
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                route TEXT NOT NULL,
                vehiclePlate TEXT NOT NULL,
                scheduledTime TEXT NOT NULL,
                status TEXT NOT NULL
            )
            """.trimIndent()
                ) // tạo bảng lưu lượt hành khách check-in

                db.execSQL(
                    """
            CREATE UNIQUE INDEX IF NOT EXISTS
            index_check_ins_passengerCode_date_scheduledTime
            ON check_ins (passengerCode, date, scheduledTime)
            """.trimIndent()
                ) // ngăn cùng hành khách check-in hai lần trong một chuyến
            }
        }
        private val MIGRATION_5_6 = object : Migration(5, 6) { // nâng database từ version 5 lên 6
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi thiết bị đang có database version 5
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS notifications (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                message TEXT NOT NULL,
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                type TEXT NOT NULL,
                isRead INTEGER NOT NULL
            )
            """.trimIndent()
                ) // tạo bảng lưu thông báo
            }
        }
        private val MIGRATION_6_7 = object : Migration(6, 7) { // nâng database từ version 6 lên version 7
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi thiết bị đang có database version 6
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS customer_statistics (
                        id INTEGER NOT NULL PRIMARY KEY,
                        singleTicketCount INTEGER NOT NULL,
                        monthlyTicketCount INTEGER NOT NULL,
                        freeTicketCount INTEGER NOT NULL,
                        workerSurveyCount INTEGER NOT NULL,
                        studentSurveyCount INTEGER NOT NULL
                    )
                    """.trimIndent()
                ) // tạo bảng lưu cơ cấu vé và dữ liệu khảo sát hành khách
            }
        }
        private val MIGRATION_7_8 = object : Migration(7, 8) { // nâng database từ version 7 lên version 8
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi thiết bị đang dùng database version 7
                db.execSQL( // thực hiện lệnh thêm cột ngày thống kê
                    "ALTER TABLE customer_statistics ADD COLUMN recordDate TEXT NOT NULL DEFAULT ''" // thêm ngày vào bảng cũ
                ) // kết thúc lệnh thêm cột

                db.execSQL( // cập nhật ngày cho bản ghi cũ đang có trong máy
                    "UPDATE customer_statistics SET recordDate = strftime('%Y-%m-%d','now','localtime') WHERE recordDate = ''" // gán ngày hiện tại cho dữ liệu cũ
                ) // kết thúc lệnh cập nhật ngày

                db.execSQL( // tạo chỉ mục để một ngày không bị lưu nhiều bản
                    "CREATE UNIQUE INDEX IF NOT EXISTS index_customer_statistics_recordDate ON customer_statistics(recordDate)" // ngăn trùng ngày thống kê
                ) // kết thúc lệnh tạo chỉ mục
            } // kết thúc quá trình migration
        } // kết thúc MIGRATION_7_8
        private val MIGRATION_8_9 = object : Migration(8, 9) { // nâng database từ version 8 lên version 9
            override fun migrate(db: SupportSQLiteDatabase) { // chạy khi thiết bị đang có database version 8

                db.execSQL( // tạo bảng lưu phương tiện của doanh nghiệp
                    """
            CREATE TABLE IF NOT EXISTS business_vehicles (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                plateNumber TEXT NOT NULL,
                vehicleType TEXT NOT NULL,
                driverName TEXT NOT NULL,
                maintenanceDate TEXT NOT NULL,
                status TEXT NOT NULL
            )
            """.trimIndent()
                ) // kết thúc lệnh tạo bảng phương tiện

                db.execSQL( // tạo chỉ mục duy nhất cho biển số xe
                    """
            CREATE UNIQUE INDEX IF NOT EXISTS index_business_vehicles_plateNumber
            ON business_vehicles (plateNumber)
            """.trimIndent()
                ) // ngăn hai phương tiện có cùng biển số
            }
        }
        @Volatile // giúp các luồng đọc đúng dữ liệu mới nhất
        private var INSTANCE: AppDatabase? = null // biến lưu database đang dùng

        fun getDatabase(context: Context): AppDatabase { // lấy hoặc tạo database
            return INSTANCE ?: synchronized(this) { // tránh tạo nhiều database cùng lúc
                val instance = Room.databaseBuilder( // bắt đầu tạo Room database
                    context.applicationContext, // dùng Context của toàn app
                    AppDatabase::class.java, // chỉ định lớp database này
                    "bus_dieu_hanh_database" // tên file database lưu trong máy
                ).addMigrations(
                    MIGRATION_1_2, // thêm biển số xe
                    MIGRATION_2_3, // thêm giờ dự kiến
                    MIGRATION_3_4, // thêm bảng báo cáo sự cố
                    MIGRATION_4_5, // thêm bảng hành khách check-in
                    MIGRATION_5_6, // thêm bảng thông báo
                    MIGRATION_6_7, // thêm bảng thống kê khách hàng và cơ cấu vé
                    MIGRATION_7_8, // thêm ngày cho từng bản thống kê khách hàng
                    MIGRATION_8_9 // thêm bảng phương tiện doanh nghiệp

                )
                    .build() // hoàn tất tạo database

                INSTANCE = instance // lưu lại để lần sau dùng tiếp
                instance // trả database vừa tạo ra
            }
        }
    }
}