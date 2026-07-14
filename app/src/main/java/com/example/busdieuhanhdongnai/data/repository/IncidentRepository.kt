package com.example.busdieuhanhdongnai.data.repository

import com.example.busdieuhanhdongnai.data.local.IncidentDao // dùng DAO thao tác bảng incidents
import com.example.busdieuhanhdongnai.data.local.IncidentEntity // dùng model báo cáo sự cố

class IncidentRepository(
    private val incidentDao: IncidentDao // nhận DAO sự cố từ Room
) {

    val allIncidents = incidentDao.getAllIncidents() // cung cấp danh sách sự cố để giao diện theo dõi

    suspend fun saveIncident(incident: IncidentEntity) { // lưu một báo cáo sự cố mới
        incidentDao.insertIncident(incident) // gọi DAO thêm dữ liệu vào Room
    }
}