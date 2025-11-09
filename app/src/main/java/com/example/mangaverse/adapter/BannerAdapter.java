package com.example.mangaverse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaverse.R;
import com.example.mangaverse.model.Banner;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private Context context;
    private List<Banner> bannerList;
    private OnBannerClickListener listener;

    public interface OnBannerClickListener {
        void onReadNowClick(Banner banner);
    }

    public BannerAdapter(Context context, List<Banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Banner banner = bannerList.get(position);
        
        holder.tvTitle.setText(banner.getTitle());
        
        // Sử dụng Glide để load ảnh (hỗ trợ cả GIF)
        Glide.with(context)
                .load(banner.getImageResId())
                .centerCrop()
                .into(holder.ivBanner);

        holder.btnReadNow.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReadNowClick(banner);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tvTitle;
        Button btnReadNow;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.ivBanner);
            tvTitle = itemView.findViewById(R.id.tvBannerTitle);
            btnReadNow = itemView.findViewById(R.id.btnReadNow);
        }
    }
}
