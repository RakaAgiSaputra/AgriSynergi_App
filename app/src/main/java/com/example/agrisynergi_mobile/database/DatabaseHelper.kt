package com.example.agrisynergi_mobile.data.local.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.agrisynergi_mobile.viewmodel.DetailAgenda
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.agrisynergi_mobile.data.Agenda
import android.util.Log
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_agrisynergi"
        private const val DATABASE_VERSION = 1

        //Tabel detail sawah
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

        // Tabel agenda
        const val AGENDA_TABLE = "agenda"
        const val ID = "id"
        const val IMAGE_PATH = "imagePath"
        const val JUDUL = "judul"
        const val TANGGAL = "tanggal"
        const val DESKRIPSI_AGENDA = "deskripsiAgenda"
        const val JENIS = "jenis"
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

        val createAgendaTableQuery = """
            CREATE TABLE $AGENDA_TABLE (
                $ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $IMAGE_PATH TEXT,
                $JUDUL TEXT NOT NULL,
                $TANGGAL TEXT NOT NULL,
                $DESKRIPSI_AGENDA TEXT NOT NULL,
                $JENIS TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
        db.execSQL(createAgendaTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $AGENDA_TABLE")

        onCreate(db)
    }

    fun insertDataAgenda(detail: DetailAgenda): Long {
        val values = ContentValues().apply {
            put(IMAGE_PATH, detail.imagePath)
            put(JUDUL, detail.judul)
            put(TANGGAL, detail.tanggal)
            put(DESKRIPSI_AGENDA, detail.deskripsiAgenda)
            put(JENIS, detail.jenis)
        }
        return writableDatabase.insert(AGENDA_TABLE, null, values)
    }

    fun readAgendaItems(): List<Agenda> {
        val agendaList = mutableListOf<Agenda>()
        val cursor = readableDatabase.query(AGENDA_TABLE, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val imagePath = getString(getColumnIndexOrThrow(IMAGE_PATH))
                val judul = getString(getColumnIndexOrThrow(JUDUL))
                val tanggal = getString(getColumnIndexOrThrow(TANGGAL))
                val deskripsiAgenda = getString(getColumnIndexOrThrow(DESKRIPSI_AGENDA))
                val jenis = getString(getColumnIndexOrThrow(JENIS))

                agendaList.add(
                    Agenda(
                        id = id,
                        imagePath = imagePath,
                        judul = judul,
                        tanggal = tanggal,
                        deskripsiAgenda = deskripsiAgenda,
                        jenis = jenis
                    )
                )
            }
        }
        cursor.close()
        return agendaList
    }

    fun updateDataAgenda(detail: DetailAgenda): Int {
        val values = ContentValues().apply {
            put(IMAGE_PATH, detail.imagePath)
            put(JUDUL, detail.judul)
            put(TANGGAL, detail.tanggal)
            put(DESKRIPSI_AGENDA, detail.deskripsiAgenda)
            put(JENIS, detail.jenis)
        }

        val selection = "$ID = ?"
        val selectionArgs = arrayOf(detail.id.toString())
        return writableDatabase.update(AGENDA_TABLE, values, selection, selectionArgs)
    }

    fun deleteData(idLokasi: Int): Int {
        val selection = "$ID_LOKASI = ?"
        val selectionArgs = arrayOf(idLokasi.toString())
        return writableDatabase.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun deleteDataAgenda(id: Int): Int {
        val selection = "$ID = ?"
        val selectionArgs = arrayOf(id.toString())
        return writableDatabase.delete(AGENDA_TABLE, selection, selectionArgs)
    }

//    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
//        try {
//            // Ambil input stream dari URI
//            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//
//            // Nama file untuk gambar
//            val fileName = "image_${System.currentTimeMillis()}.png"
//            val file = File(context.filesDir, fileName)
//
//            // Tulis bitmap ke internal storage
//            FileOutputStream(file).use { outputStream ->
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//            }
//
//            //Debug print
//            println("Image saved at: ${file.absolutePath}")
//
//            return file.absolutePath // Return path dari gambar
//        } catch (e: Exception) {
//            e.printStackTrace()
//            println("Error saving image: ${e.message}")
//            return null
//        }
//    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "agenda_image_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)

            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // Kembalikan path absolut file
            file.absolutePath
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error saving image", e)
            ""
        }
    }
}
