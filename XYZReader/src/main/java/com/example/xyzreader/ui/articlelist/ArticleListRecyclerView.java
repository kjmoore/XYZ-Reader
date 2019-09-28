package com.example.xyzreader.ui.articlelist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.xyzreader.databinding.ArticleItemBinding;
import com.example.xyzreader.model.Article;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleListRecyclerView extends RecyclerView.Adapter<ArticleListRecyclerView.ArticleListViewHolder> {
    private final String TAG = ArticleListRecyclerView.class.getSimpleName();

    private List<Article> articles = Collections.emptyList();

    @NonNull
    @Override
    public ArticleListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ArticleItemBinding itemBinding =
                ArticleItemBinding.inflate(layoutInflater, viewGroup, false);
        return new ArticleListViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListViewHolder ingredientViewHolder, int i) {
        ingredientViewHolder.bind(articles.get(i));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    void setArticles(List<Article> articles) {
        Log.d(TAG, "Updating articles");
        Log.v(TAG, Arrays.toString(articles.toArray()));
        this.articles = articles;
        this.notifyDataSetChanged();
    }

    class ArticleListViewHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding binding;
        Article article;

        ArticleListViewHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(@NonNull final Article article) {
            this.article = article;
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }
}