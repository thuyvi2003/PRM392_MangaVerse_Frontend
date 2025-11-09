package com.example.mangaverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<DiscoverActivity.Book> books;
    private Context context;

    public BooksAdapter(List<DiscoverActivity.Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        DiscoverActivity.Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle, tvBookAuthor;
        ImageView imgBookCover;
        MaterialCardView btnBookmark, btnFavorite;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            imgBookCover = itemView.findViewById(R.id.imgBookCover);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        void bind(DiscoverActivity.Book book) {
            tvBookTitle.setText(book.getTitle());
            tvBookAuthor.setText(book.getAuthor());
            
            // Set default book cover
            imgBookCover.setImageResource(R.drawable.default_book_cover);

            btnBookmark.setOnClickListener(v -> {
                Toast.makeText(context, "Bookmarked: " + book.getTitle(), Toast.LENGTH_SHORT).show();
            });

            btnFavorite.setOnClickListener(v -> {
                Toast.makeText(context, "Added to favorites: " + book.getTitle(), Toast.LENGTH_SHORT).show();
            });

            itemView.setOnClickListener(v -> {
                // Open MangaReaderActivity - load images from assets/manga/sample_manga/
                String mangaFolder = "sample_manga"; // TODO: Map book to folder name
                Intent intent = MangaReaderActivity.createIntent(context, book.getTitle(), mangaFolder);
                context.startActivity(intent);
            });
        }
    }
}
