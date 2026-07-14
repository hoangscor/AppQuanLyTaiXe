package com.example.busdieuhanhdongnai.data.local

import android.content.Context // dùng Context để tạo database
import androidx.room.Database // khai báo Room database
import androidx.room.Room // tạo database bằng Room
import androidx.room.RoomDatabase // lớp cha của database Room
import androidx.room.migration.Migration // khai báo migration khi thay đổi database
import androidx.sqlite.db.SupportSQLiteDatabase // thao tác SQL trực tiếp trong migration

@Database( // khai báo cấu hình database
    entities = [TripEntity::class, IncidentEntity::class], // Room quản lý cả bảng chuyến xe và sự cố
    version = 4, // tăng phiên bản database vì có thêm bảng incidents
    exportSchema = false // chưa xuất file schema ở giai đoạn này
)
abstract class AppDatabase : RoomDatabase() { // database chính của ứng dụng

    abstract fun tripDao(): TripDao // cung cấp DAO để thao tác bảng trips
    abstract fun incidentDao(): IncidentDao // cung cấp DAO để thao tác bảng incidents

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
                    MIGRATION_3_4 // thêm bảng báo cáo sự cố
                )
                    .build() // hoàn tất tạo database

                INSTANCE = instance // lưu lại để lần sau dùng tiếp
                instance // trả database vừa tạo ra
            }
        }
    }
}