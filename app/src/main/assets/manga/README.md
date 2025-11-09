# Manga Images Folder

## Cách sử dụng:

1. Tạo folder cho mỗi cuốn manga trong thư mục này
2. Đặt tên folder theo tên manga (ví dụ: `one_piece`, `naruto`, `my_manga`)
3. Bỏ ảnh manga vào folder đó với tên theo thứ tự:
   - `manga_1.jpg`
   - `manga_2.jpg`
   - `manga_3.jpg`
   - ...

## Ví dụ cấu trúc:

```
manga/
├── one_piece/
│   ├── manga_1.jpg
│   ├── manga_2.jpg
│   ├── manga_3.jpg
│   └── ...
├── naruto/
│   ├── manga_1.jpg
│   ├── manga_2.jpg
│   └── ...
└── sample_manga/
    ├── manga_1.jpg
    ├── manga_2.jpg
    └── ...
```

## Định dạng ảnh hỗ trợ:
- `.jpg` / `.jpeg`
- `.png`
- `.webp`

## Lưu ý:
- Tên file PHẢI theo format: `manga_X.jpg` (X là số thứ tự)
- Ảnh sẽ được load theo thứ tự số (manga_1, manga_2, ...)
- Nên đặt tên folder không dấu, viết thường, dùng dấu gạch dưới thay khoảng trắng
