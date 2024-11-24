package com.example.agrisynergi_mobile.data.local.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.agrisynergi_mobile.viewmodel.DetailSawah

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_detail_sawah"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "detail_sawah"
        const val ID_LOKASI = "id_lokasi"
        const val ID_SAWAH = "id_sawah"
        const val NAMA = "nama"
        const val LUAS = "luas"
        const val JENIS_TANAH = "jenis_tanah"
        const val HASIL_PANEN = "hasil_panen"
        const val PRODUKSI = "produksi"
        const val DESKRIPSI = "deskripsi"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $ID_LOKASI INTEGER PRIMARY KEY AUTOINCREMENT,
                $ID_SAWAH INTEGER NOT NULL,
                $NAMA TEXT NOT NULL,
                $LUAS REAL NOT NULL,
                $JENIS_TANAH TEXT NOT NULL,
                $HASIL_PANEN TEXT NOT NULL,
                $PRODUKSI TEXT NOT NULL,
                $DESKRIPSI TEXT NOT NULL,
                $LATITUDE REAL NOT NULL,
                $LONGITUDE REAL NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(detail: DetailSawah): Long {
        val values = ContentValues().apply {
            put(ID_SAWAH, detail.idSawah)
            put(NAMA, detail.nama)
            put(LUAS, detail.luas)
            put(JENIS_TANAH, detail.jenisTanah)
            put(HASIL_PANEN, detail.hasilPanen)
            put(PRODUKSI, detail.produksi)
            put(DESKRIPSI, detail.deskripsi)
            put(LATITUDE, detail.latitude)
            put(LONGITUDE, detail.longitude)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun readData(): List<DetailSawah> {
        val dataList = mutableListOf<DetailSawah>()
        val cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val idLokasi = getInt(getColumnIndexOrThrow(ID_LOKASI))
                val idSawah = getInt(getColumnIndexOrThrow(ID_SAWAH))
                val nama = getString(getColumnIndexOrThrow(NAMA))
                val luas = getDouble(getColumnIndexOrThrow(LUAS))
                val jenisTanah = getString(getColumnIndexOrThrow(JENIS_TANAH))
                val hasilPanen = getString(getColumnIndexOrThrow(HASIL_PANEN))
                val produksi = getString(getColumnIndexOrThrow(PRODUKSI))
                val deskripsi = getString(getColumnIndexOrThrow(DESKRIPSI))
                val latitude = getDouble(getColumnIndexOrThrow(LATITUDE))
                val longitude = getDouble(getColumnIndexOrThrow(LONGITUDE))

                dataList.add(
                    DetailSawah(
                        idLokasi, idSawah, nama, luas, jenisTanah,
                        hasilPanen, produksi, deskripsi, latitude, longitude
                    )
                )
            }
        }
        cursor.close()
        return dataList
    }

    fun updateData(detail: DetailSawah): Int {
        val values = ContentValues().apply {
            put(ID_SAWAH, detail.idSawah)
            put(NAMA, detail.nama)
            put(LUAS, detail.luas)
            put(JENIS_TANAH, detail.jenisTanah)
            put(HASIL_PANEN, detail.hasilPanen)
            put(PRODUKSI, detail.produksi)
            put(DESKRIPSI, detail.deskripsi)
            put(LATITUDE, detail.latitude)
            put(LONGITUDE, detail.longitude)
        }

        val selection = "$ID_LOKASI = ?"
        val selectionArgs = arrayOf(detail.idLokasi.toString())
        return writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)
    }

    fun deleteData(idLokasi: Int): Int {
        val selection = "$ID_LOKASI = ?"
        val selectionArgs = arrayOf(idLokasi.toString())
        return writableDatabase.delete(TABLE_NAME, selection, selectionArgs)
    }
}
