# AgriSynergi_App - Alam Bahtera Massive 🌾

AgriSynergi_App adalah aplikasi berbasis Android yang dirancang untuk mendukung kolaborasi dan pemberdayaan petani dalam ekosistem pertanian modern. Aplikasi ini menyediakan fitur navigasi peta, pasar pertanian, forum diskusi, dan profil pengguna yang terintegrasi untuk mendukung kegiatan komunitas.

## 📱 Fitur Utama
1. **Beranda**: Tampilan utama dengan akses cepat ke fitur-fitur aplikasi.
2. **Navigasi Peta**: Panduan lokasi dengan informasi geografis untuk membantu pengguna menemukan pasar atau lokasi penting lainnya.
3. **Pasar**: Akses ke pasar digital untuk menjual atau membeli hasil pertanian.
4. **Forum**: Diskusi komunitas untuk berbagi pengetahuan dan pengalaman.
5. **Profil Pengguna**: Kelola informasi pengguna, riwayat pesanan, dan fitur lainnya.

## 🛠️ Teknologi yang Digunakan
- **Kotlin**: Bahasa pemrograman utama.
- **Jetpack Compose**: Library modern untuk membangun UI deklaratif.
- **Android Navigation Component**: Untuk mengelola navigasi antar layar.
- **Material Design 3**: Untuk menciptakan tampilan yang konsisten dan menarik.
- **Room Database**: Untuk menyimpan data lokal secara efisien.
- **Retrofit**: Untuk integrasi API.

## 📂 Struktur Proyek
AgriSynergi_App/<br>
├── app/ <br>
│ ├── src/<br>
│ │ ├── main/<br>
│ │ │ ├── java/com/example/agrisynergi_mobile/<br>
│ │ │ │ ├── data # dummy data<br>
│ │ │ │ ├── pages/ # Halaman utama aplikasi<br> 
│ │ │ │ ├── navigation/ # Navigasi aplikasi <br>
│ │ │ │ ├── User/ # Fitur profil pengguna<br>
│ │ │ ├── res/ <br>
│ │ │ │ ├── drawable/ <br>
│ │ │ │ ├── layout/ # Layout XML <br>
│ │ │ │ ├── values/ # Warna, string, dan tema <br>
├── build.gradle ├── settings.gradle.kts <br>
└── README.md<br>

## 👩‍💻 Kontributor
- **Belva**
- **Sulfia**
- **Raka**
- **Ario**  



## 🚀 Cara Menggunakan
1. Clone repository ini:
   ```bash
   git clone https://github.com/username/AgriSynergi_App.git
2. Buka proyek di Android Studio. (Koala Feature Drop Version)
3. Sinkronisasi dependensi Gradle.
4. Jalankan aplikasi menggunakan emulator atau perangkat fisik.


## ⚙️ Dependensi Utama

Tambahkan dependensi berikut di `build.gradle`:

```gradle
dependencies {
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.5.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.0")
}



