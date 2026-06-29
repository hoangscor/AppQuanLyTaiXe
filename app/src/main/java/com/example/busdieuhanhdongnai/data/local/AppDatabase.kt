package com.example.busdieuhanhdongnai.data.local

import android.content.Context // dùng Context để tạo database
import androidx.room.Database // khai báo Room database
import androidx.room.Room // tạo database bằng Room
import androidx.room.RoomDatabase // lớp cha của database Room

@Database( // khai báo cấu hình database
    entities = [TripEntity::class], // bảng trips được Room quản lý
    version = 1, // phiên bản đầu tiên của database
    exportSchema = false // chưa xuất file schema ở giai đoạn này
)
abstract class AppDatabase : RoomDatabase() { // database chính của ứng dụng

    abstract fun tripDao(): TripDao // cung cấp DAO để thao tác bảng trips

    companion object { // nơi giữ một bản database duy nhất

        @Volatile // giúp các luồng đọc đúng dữ liệu mới nhất
        private var INSTANCE: AppDatabase? = null // biến lưu database đang dùng

        fun getDatabase(context: Context): AppDatabase { // lấy hoặc tạo database
            return INSTANCE ?: synchronized(this) { // tránh tạo nhiều database cùng lúc
                val instance = Room.databaseBuilder( // bắt đầu tạo Room database
                    context.applicationContext, // dùng Context của toàn app
                    AppDatabase::class.java, // chỉ định lớp database này
                    "bus_dieu_hanh_database" // tên file database lưu trong máy
                ).build() // hoàn tất tạo database

                INSTANCE = instance // lưu lại để lần sau dùng tiếp
                instance // trả database vừa tạo ra
            }
        }
    }
}