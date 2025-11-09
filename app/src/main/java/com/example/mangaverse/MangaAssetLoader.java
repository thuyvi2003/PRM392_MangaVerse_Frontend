package com.example.mangaverse;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Helper class để load ảnh manga từ assets folder
 */
public class MangaAssetLoader {
    
    private Context context;
    private AssetManager assetManager;
    
    public MangaAssetLoader(Context context) {
        this.context = context;
        this.assetManager = context.getAssets();
    }
    
    /**
     * Lấy danh sách tất cả folder manga trong assets/manga
     * @return List tên folder manga
     */
    public List<String> getMangaFolders() {
        List<String> folders = new ArrayList<>();
        try {
            String[] files = assetManager.list("manga");
            if (files != null) {
                for (String file : files) {
                    // Bỏ qua file README.md
                    if (!file.endsWith(".md") && !file.equals("README.md")) {
                        folders.add(file);
                    }
                }
            }
            Collections.sort(folders);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folders;
    }
    
    /**
     * Lấy danh sách tất cả ảnh trong 1 folder manga
     * @param mangaFolder Tên folder manga (vd: "sample_manga")
     * @return List đường dẫn ảnh đã sort theo thứ tự
     */
    public List<String> getMangaPages(String mangaFolder) {
        List<String> pages = new ArrayList<>();
        try {
            String mangaPath = "manga/" + mangaFolder;
            String[] files = assetManager.list(mangaPath);
            
            if (files != null) {
                // Filter chỉ lấy file ảnh
                for (String file : files) {
                    if (isImageFile(file)) {
                        pages.add("file:///android_asset/" + mangaPath + "/" + file);
                    }
                }
                
                // Sort theo tên (manga_1, manga_2, ...)
                Collections.sort(pages, (a, b) -> {
                    int numA = extractNumber(a);
                    int numB = extractNumber(b);
                    return Integer.compare(numA, numB);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }
    
    /**
     * Kiểm tra có phải file ảnh không
     */
    private boolean isImageFile(String filename) {
        String lower = filename.toLowerCase();
        return lower.endsWith(".jpg") || 
               lower.endsWith(".jpeg") || 
               lower.endsWith(".png") || 
               lower.endsWith(".webp");
    }
    
    /**
     * Extract số từ tên file (vd: manga_1.jpg -> 1)
     */
    private int extractNumber(String filename) {
        try {
            // Lấy tên file từ path
            String name = filename.substring(filename.lastIndexOf("/") + 1);
            // Bỏ extension
            name = name.substring(0, name.lastIndexOf("."));
            // Extract số sau dấu _
            String[] parts = name.split("_");
            if (parts.length > 1) {
                return Integer.parseInt(parts[parts.length - 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Đếm số trang của 1 manga
     */
    public int getMangaPageCount(String mangaFolder) {
        return getMangaPages(mangaFolder).size();
    }
    
    /**
     * Kiểm tra manga folder có tồn tại không
     */
    public boolean mangaExists(String mangaFolder) {
        try {
            String[] files = assetManager.list("manga/" + mangaFolder);
            return files != null && files.length > 0;
        } catch (IOException e) {
            return false;
        }
    }
}
