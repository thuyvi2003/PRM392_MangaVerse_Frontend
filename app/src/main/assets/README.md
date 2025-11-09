# EPUB Reader - Hướng dẫn sử dụng

## 📁 Cách đặt file EPUB

1. **Đặt file EPUB vào assets:**
   - Đặt file EPUB của bạn vào thư mục: `app/src/main/assets/`
   - Đặt tên file là: `sach.epub`

2. **Vị trí chính xác:**
   ```
   AppManga/
   └── app/
       └── src/
           └── main/
               └── assets/
                   └── sach.epub  ← Đặt file ở đây
   ```

## 📚 Thư viện EPUB đang sử dụng

✅ **FolioReader-Android v0.6.0** - Thư viện EPUB chuyên nghiệp đã được tích hợp!

### Tính năng của FolioReader:

- ✅ Đọc file EPUB 2 và EPUB 3
- ✅ Hỗ trợ reflowable text
- ✅ Highlight và ghi chú
- ✅ Tìm kiếm trong sách
- ✅ Table of Contents (mục lục)
- ✅ Night mode / Day mode
- ✅ Thay đổi font size
- ✅ Text-to-Speech (đọc thành tiếng)
- ✅ Lưu vị trí đọc tự động
- ✅ Hỗ trợ cả chế độ ngang và dọc

### Cấu hình đã thêm:

**1. settings.gradle.kts:**
```kotlin
repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }  // ✅ Đã thêm
}
```

**2. app/build.gradle.kts:**
```kotlin
dependencies {
    // FolioReader - EPUB Reader Library
    implementation("com.github.FolioReader:FolioReader-Android:0.6.0")  // ✅ Đã thêm
}
```

## ⚙️ Cách hoạt động

1. **Click vào sách** trong trang Discover
2. App tự động **copy file `sach.epub`** từ assets vào internal storage
3. **FolioReader mở file EPUB** với đầy đủ tính năng
4. User có thể:
   - Lật trang (swipe trái/phải)
   - Thay đổi font size (pinch hoặc menu)
   - Đổi theme (sáng/tối)
   - Highlight text
   - Tìm kiếm
   - Xem mục lục
   - Text-to-Speech

## 🎨 Tính năng FolioReader có sẵn

FolioReader tự động cung cấp UI hoàn chỉnh với:

- **Top Bar**: Tiêu đề sách, back button, bookmark
- **Bottom Bar**: Settings, Table of Contents, Search, Share
- **Menu Settings**:
  - Font size adjustment (slider)
  - Theme selection (Day/Night)
  - Font type selection
  - Text alignment
  - Line spacing

**Lưu ý:** UI custom trong `activity_reader.xml` và `bottom_sheet_reader_settings.xml` không được sử dụng vì FolioReader có UI riêng, nhưng bạn có thể customize FolioReader qua `Config` object.

## 📝 Customize FolioReader (Nếu cần)

Trong `ReaderActivity.java`, bạn có thể thay đổi cấu hình:

```java
Config config = new Config();

// Cho phép cả ngang và dọc
config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);

// Chỉ cho phép dọc
// config.setAllowedDirection(Config.AllowedDirection.ONLY_VERTICAL);

// Đặt theme mặc định
// config.setNightMode(false); // Day mode
// config.setNightMode(true);  // Night mode

// Đặt font size mặc định (0-5)
// config.setFontSize(2);

// Áp dụng config
folioReader.setConfig(config, true).openBook(epubPath);
```

## 🚀 Cách test

1. **Tải file EPUB mẫu** (hoặc dùng EPUB bất kỳ)
2. **Đổi tên thành `sach.epub`**
3. **Copy vào** `app/src/main/assets/sach.epub`
4. **Build và chạy app**
5. Vào trang **Discover** → Click vào **bất kỳ sách nào**
6. FolioReader sẽ mở và render EPUB!

## 📖 Tìm file EPUB mẫu

Bạn có thể tải EPUB miễn phí từ:
- **Project Gutenberg**: https://www.gutenberg.org/
- **Standard Ebooks**: https://standardebooks.org/
- **ManyBooks**: https://manybooks.net/

## 🔧 Troubleshooting

**Lỗi: "Error loading EPUB file"**
- Kiểm tra file `sach.epub` có trong thư mục `assets` không
- Đảm bảo file EPUB không bị lỗi (test với app EPUB reader khác trước)

**FolioReader không hiển thị đúng:**
- Clean project: `Build > Clean Project`
- Rebuild project: `Build > Rebuild Project`
- Sync Gradle: `File > Sync Project with Gradle Files`

**Muốn đổi file EPUB:**
- Xóa file `sach.epub` cũ trong `assets`
- Copy file EPUB mới vào và đổi tên thành `sach.epub`
- Rebuild app

---

✅ **Hoàn thành!** App đã có thư viện EPUB chuyên nghiệp!

