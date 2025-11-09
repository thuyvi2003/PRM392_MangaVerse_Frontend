package com.example.mangaverse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaverse.R;
import com.example.mangaverse.model.Manga;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private Context context;
    private List<Manga> mangaList;
    private OnMangaClickListener listener;

    public interface OnMangaClickListener {
        void onMangaClick(Manga manga);
    }

    public MangaAdapter(Context context, List<Manga> mangaList) {
        this.context = context;
        this.mangaList = mangaList;
    }

    public void setOnMangaClickListener(OnMangaClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga_card, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        
        holder.tvTitle.setText(manga.getTitle());
        holder.tvScore.setText(String.valueOf(manga.getScore()));
        holder.tvChapters.setText(manga.getChapters() + " chapters");

        // Load image with Glide
        if (manga.getCoverUrl() != null && !manga.getCoverUrl().isEmpty()) {
            Glide.with(context)
                    .load(manga.getCoverUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.ivCover);
        } else {
            holder.ivCover.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMangaClick(manga);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvScore, tvChapters;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivMangaCover);
            tvTitle = itemView.findViewById(R.id.tvMangaTitle);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvChapters = itemView.findViewById(R.id.tvChapters);
        }
    }
}
