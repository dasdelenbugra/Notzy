# 📝 Notzy - Akıllı Not Alma Uygulaması

<div align="center">

![Notzy](https://img.shields.io/badge/Notzy-Note%20Taking%20App-FFC107?style=for-the-badge)
![Android](https://img.shields.io/badge/Android-14%2B-green?style=for-the-badge)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-purple?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

**Modern not alma uygulaması el yazısı tanıma özelliği ile**

[Özellikler](#özellikler) • [Gereksinimler](#gereksinimler) • [Kurulum](#kurulum) • [Kullanım](#kullanım) • [Teknoloji](#teknoloji)

</div>

---

## 📖 İçindekiler

- [Hakkında](#hakkında)
- [Özellikler](#özellikler)
- [Ekran Görüntüleri](#ekran-görüntüleri)
- [Gereksinimler](#gereksinimler)
- [Kurulum](#kurulum)
- [Mimari](#mimari)
- [Teknoloji Stack](#teknoloji-stack)
- [Kullanım](#kullanım)
- [API'ler](#apiler)
- [Katkıda Bulunma](#katkıda-bulunma)
- [Lisans](#lisans)

---

## 🎯 Hakkında

**Notzy**, not alma işlemini modernleştirir. Hızlı metin giriştir, el yazısı tanıması ve akıllı arama özellikleri sayesinde notlarınız daima erişiminiz içinde.

Günlük yaşamınızda hızlı notlar almak, önemli bilgileri saklamak ve organize etmek için tasarlanmış bir mobil uygulamadır.

---

## ✨ Özellikler

### 📝 Temel Özellikler
- ✅ **Not Oluşturma ve Düzenleme**: Hızlı ve kolay not yazma arayüzü
- ✅ **Not Silme**: Gereksiz notları hızlıca temizleyin
- ✅ **Not Listesi**: Tüm notlarınız organize bir listede görünür
- ✅ **Boş Durum Gösterimi**: Henüz not yokken kullanıcı dostu mesajlar

### 🖊️ El Yazısı Tanıma (Handwriting Recognition)
- ✅ **Kamera ile Yakalama**: Cihazınızın kamerasını kullanarak el yazısını çekin
- ✅ **Google ML Kit Integration**: Güçlü makine öğrenmesi tabanlı tanıma
- ✅ **Otomatik Metin Dönüştürme**: El yazısını anında metne dönüştürün
- ✅ **Metin Düzenleme**: Tanınan metni düzenleyip kaydedin
- ✅ **Yüksek Doğruluk**: Profesyonel seviye tanıma kalitesi

### 🔍 Arama Özelliği
- ✅ **Gerçek Zamanlı Arama**: Notlar arasında hızlı arama yapın
- ✅ **Gelişmiş Filtreleme**: Başlık ve içeriğe göre ara

### 🎨 Görünüm ve Tema
- ✅ **Material Design 3**: Modern ve profesyonel UI
- ✅ **Açık/Koyu Tema**: Gözlere rahat kullanım
- ✅ **Sistem Teması**: Cihaz ayarlarına göre otomatik tema
- ✅ **Renkli ve Etkileyici Tasarım**: Gradient başlıklar ve smooth animasyonlar

### 🌐 Yerelleştirme
- ✅ **Türkçe Desteği**: Tam Türkçe arayüz
- ✅ **İngilizce Desteği**: Çok dilli destek (genişletilebilir)

### ⚙️ Ayarlar
- ✅ **Tema Seçimi**: Açık, Koyu veya Sistem Teması
- ✅ **Dil Seçimi**: Tercih ettiğiniz dili seçin

---

## 🖼️ Ekran Görüntüleri

### Ana Sayfa
- Notlar listesi şık MaterialCardView'lerle gösterilir
- Genişletilmiş FAB'lar ("El Yazısı" ve "Kamera" işlevleri için)
- Boş durum gösterimi

### El Yazısı Tanıma
- Gradient başlıklı profesyonel giriş ekranı
- Kamera entegrasyonu
- İlerleme diyaloğu (CircularProgressIndicator)
- Sonuç gösterimi ve düzenleme diyaloğu

### Not Detayı
- Rich metin editörü
- Başlık ve içerik alanları
- Kaydet ve sil işlevleri

---

## 📋 Gereksinimler

### Minimum Sürümler
- **Android SDK**: API 24+ (Android 7.0+)
- **Target SDK**: API 35 (Android 15)
- **Kotlin**: 1.9+
- **Java**: 11+

### Cihaz Gereksinimleri
- Kamera izni (El yazısı tanıma için)
- Depolama izni (Resimleri kaydetmek için)

### Bağımlılıklar
- AndroidX (AppCompat, ConstraintLayout, vb.)
- Google Material Components
- Google ML Kit Vision API
- Room Database
- Retrofit + Gson
- Coroutines
- Navigation Components

---

## 🚀 Kurulum

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/bugradasdelen/Notzy.git
cd Notzy
```

### 2. Android Studio'da Açın
```bash
# Android Studio'da File > Open yoluyla projeyi açın
# veya terminalden:
open -a "Android Studio" .
```

### 3. Gradle Senkronizasyonu
Android Studio otomatik olarak Gradle dosyalarını senkronize edecektir. 
El ile senkronize etmek için:
```bash
./gradlew sync
```

### 4. Emülatör veya Cihaz Bağlayın
```bash
# Bağlı cihazları listeleyin
adb devices

# Uygulamayı çalıştırın
./gradlew installDebug
```

### 5. Uygulamayı Başlatın
Android Studio'da **Run** butonuna basın veya:
```bash
./gradlew runDebug
```

---

## 🏗️ Mimari

Notzy, **MVVM (Model-View-ViewModel)** mimarisi kullanır:

```
app/
├── src/main/java/com/bugradasdelen/notzy/
│   ├── api/                    # API servisleri
│   ├── data/                   # Veri katmanı
│   │   ├── repository/        # Repository pattern
│   │   └── source/            # Veri kaynakları
│   ├── di/                    # Dependency Injection
│   ├── domain/                # İşletme kuralları
│   │   ├── model/            # Entity modelleri
│   │   ├── repository/       # Repository arayüzleri
│   │   └── usecase/          # Use Case'ler
│   ├── presentation/          # Sunuş katmanı
│   │   ├── view/             # Activities, Fragments
│   │   ├── viewmodel/        # ViewModels
│   │   └── state/            # UI States
│   ├── service/              # Servisler
│   ├── utils/                # Yardımcı sınıflar
│   └── NotzyApplication.kt
└── res/
    ├── layout/               # XML layout dosyaları
    ├── drawable/             # Drawable kaynakları
    ├── values/               # Renk, string vb. kaynaklar
    ├── menu/                 # Menu tanımları
    └── xml/                  # Diğer XML kaynakları
```

### Veri Akışı
```
UI Layer (Activities/Fragments)
        ↓
ViewModel (State Management)
        ↓
Repository (Data Abstraction)
        ↓
Data Sources (Room Database, API, vb.)
```

---

## 🛠️ Teknoloji Stack

### Diller ve Framework
- **Kotlin**: 1.9.x - Modern Android geliştirme
- **Android Framework**: API 24-35

### UI Framework
- **Material Design 3**: Modern tasarım sistemi
- **ConstraintLayout**: Responsive layout
- **MaterialCardView**: Şık kart tasarımları
- **FloatingActionButton**: Modern aksiyonlar

### Veri Yönetimi
- **Room Database**: Local veri tabanı
- **Retrofit 2**: HTTP istemci
- **Gson**: JSON serialization
- **Coroutines**: Asynchronous programlama

### Machine Learning
- **Google ML Kit Vision API**: El yazısı tanıma

### Android Components
- **LiveData**: Reaktif veri bağlama
- **ViewModel**: Lifecycle-aware state management
- **Navigation**: Fragment navigasyonu
- **Camera X**: Kamera işlevleri

### Build Tools
- **Gradle 8.x**: Build management
- **Kotlin Gradle Plugin**: Kotlin derleme
- **Data Binding**: View binding
- **Kapt**: Annotation processing

---

## 📱 Kullanım

### Not Oluşturma

1. **Ana sayfada** sarı FAB'a (`+`) basın
2. **Not Detayı** ekranında başlık ve içerik yazın
3. **Kaydet** butonuna basın

### El Yazısı Tanıma

1. **Ana sayfada** kırmızı FAB'a ("El Yazısı") basın
2. **El yazısı tanıma ekranında** kamera açılır
3. El yazınızın net fotoğrafını çekin
4. Sistem otomatik olarak metni algılar
5. **Sonuç diyaloğunda** metni gözden geçirin
6. **Kabul Et** veya **Düzenle** seçeneğini seçin

### Not Arama

1. **Ana sayfada** arama ikonuna basın
2. **Arama kutusuna** sorguyu yazın
3. **Sonuçları** hemen görün

### Tema Değiştirme

1. **Ayarlar** seçeneğine gidin
2. **Tema Seçin** bölümünde tercihini seç
3. Uygulama otomatik olarak güncelleşir

---

## 🔌 API'ler

### Google ML Kit
```kotlin
// Başlıklandırma
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

// Kullanım
val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
val result = recognizer.process(image)
    .addOnSuccessListener { visionText ->
        // Tanınan metin işle
    }
```

### Room Database
```kotlin
// Entity tanımı
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long
)

// DAO
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>
}
```

---

## 🐛 Sorun Giderme

### Kamera Açılmıyor
- ✅ Kamera izni verilenmi kontrol edin
- ✅ Başka bir uygulama kamerayı kullanmıyor mu kontrol edin
- ✅ Cihazı yeniden başlatın

### El Yazısı Tanınamıyor
- ✅ Fotoğraf yeterince net mi kontrol edin
- ✅ İyi aydınlatma sağlayın
- ✅ Google ML Kit API'nin düzgün kurulu olduğunu kontrol edin

### Veri Kaybı
- ✅ Uygulamayı silmeyin (veriler silinir)
- ✅ Düzenli yedek alın

---

## 🤝 Katkıda Bulunma

Notzy'ye katkıda bulunmayı hedefleyen geliştirici ve tasarımcıları bekliyoruz!

### Katkı Adımları

1. **Fork** yapın
2. **Feature branch** oluşturun (`git checkout -b feature/AmazingFeature`)
3. **Değişiklikleri commit** edin (`git commit -m 'Add some AmazingFeature'`)
4. **Branch'e push** yapın (`git push origin feature/AmazingFeature`)
5. **Pull Request** açın

### Kodlama Standartları
- Kotlin style guide'ı takip edin
- Meaningful commit mesajları yazın
- Kod yorumları ekleyin
- Test yazın

---

## 📝 Lisans

Bu proje **MIT Lisansı** altında lisanslanmıştır - detaylar için [LICENSE](LICENSE) dosyasına bakın.

---

## 👨‍💻 Geliştirici

**Buğra Dasdelen**
- GitHub: [@bugradasdelen](https://github.com/bugradasdelen)
- Email: bugra.dasdelen@example.com

---

## 🙏 Teşekkürler

- Google ML Kit ekibi - Harika ML çözümleri için
- Android Jetpack ekibi - Güçlü kütüphaneler için
- Material Design ekibi - Modern tasarım sistemi için
- Tüm açık kaynak kütüphane geliştiricileri

---

## 📊 Proje İstatistikleri

- **Diller**: Kotlin, XML
- **Dosya Sayısı**: 50+
- **Kod Satırı**: 2000+
- **Görüntü Kaynağı**: 15+

---

## 🔗 Bağlantılar

- 📄 [Changelog](CHANGELOG.md)
- 🐛 [Bug Reports](https://github.com/bugradasdelen/Notzy/issues)
- 💡 [Feature Requests](https://github.com/bugradasdelen/Notzy/discussions)
- 📚 [Dokümantasyon](docs/)

---

<div align="center">

**Notzy ile notlarınızı daha akıllıca yönetin! 📝✨**

[⬆ Başa Dön](#-notzy---akıllı-not-alma-uygulaması)

</div>

