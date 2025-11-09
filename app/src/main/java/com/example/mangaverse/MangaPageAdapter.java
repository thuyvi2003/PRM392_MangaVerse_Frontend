package com.example.mangaverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Adapter để hiển thị từng trang manga
 * Support zoom và pan với PhotoView
 */
public class MangaPageAdapter extends RecyclerView.Adapter<MangaPageAdapter.PageViewHolder> {
    
    private Context context;
    private List<String> imageUrls;
    private OnPageClickListener onPageClickListener;
    
    public interface OnPageClickListener {
        void onPageClick();
    }
    
    public MangaPageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls; 
    }
    
    public void setOnPageClickListener(OnPageClickListener listener) {
        this.onPageClickListener = listener;
    }
    
    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga_page, parent, false);
        return new PageViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        
        // Show loading
        holder.progressBar.setVisibility(View.VISIBLE);
        
        // Load image với Glide
        Glide.with(context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
                    @Override
                    public boolean onLoadFailed(@androidx.annotation.Nullable com.bumptech.glide.load.engine.GlideException e,
                                                Object model,
                                                com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target,
                                                boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(android.graphics.drawable.Drawable resource,
                                                  Object model,
                                                  com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target,
                                                  com.bumptech.glide.load.DataSource dataSource,
                                                  boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.photoView);
        
        // Set PhotoView to enable zoom
        holder.photoView.setZoomable(true);
        holder.photoView.setMaximumScale(5.0f); // Max zoom 5x
        holder.photoView.setMediumScale(2.5f); // Medium zoom 2.5x
        holder.photoView.setMinimumScale(1.0f); // Min zoom 1x (fit screen)
        
        // Click to toggle controls (only when not zoomed)
        holder.photoView.setOnPhotoTapListener((view, x, y) -> {
            if (onPageClickListener != null) {
                onPageClickListener.onPageClick();
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
    
    static class PageViewHolder extends RecyclerView.ViewHolder {
        PhotoView photoView;
        ProgressBar progressBar;
        
        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photoViewMangaPage);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
