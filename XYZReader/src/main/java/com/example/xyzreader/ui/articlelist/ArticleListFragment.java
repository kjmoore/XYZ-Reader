package com.example.xyzreader.ui.articlelist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.databinding.ArticleListBinding;
import com.example.xyzreader.model.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ArticleListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ArticleListRecyclerView.class.getSimpleName();

    private final ArticleListRecyclerView articleListRecyclerView = new ArticleListRecyclerView();
    private ArticleListBinding viewBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "Starting article list fragment");
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.article_list, container, false);

        final Context context = getContext();
        if (context != null) {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            viewBinding.recyclerView.setLayoutManager(layoutManager);
            viewBinding.recyclerView.addItemDecoration(
                    new DividerItemDecoration(context, layoutManager.getOrientation()));
            viewBinding.recyclerView.setAdapter(articleListRecyclerView);

            viewBinding.swipeRefresh.setOnRefreshListener(this);

            LoaderManager.getInstance(this).initLoader(0, null, this);
        }


        return viewBinding.getRoot();
    }

    @Override
    @NonNull
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "Creating loader");
        return ArticleLoader.newAllArticlesInstance(this.getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Finished loading data");
        final List<Article> articleList = new ArrayList<>();
        data.moveToPosition(0);
        Log.v(TAG, "Received " + data.getCount() + " articles");
        while (!data.isAfterLast()) {
            final Article article = new Article();
            article.author = data.getString(ArticleLoader.Query.AUTHOR);
            article.photoUrl = data.getString(ArticleLoader.Query.PHOTO_URL);
            article.title = data.getString(ArticleLoader.Query.TITLE);
            article.text = data.getString(ArticleLoader.Query.BODY);
            article.date = data.getString(ArticleLoader.Query.PUBLISHED_DATE);
            data.moveToNext();

            articleList.add(article);
        }

        articleListRecyclerView.setArticles(articleList);
        viewBinding.progressBar.setVisibility(View.INVISIBLE);
        viewBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "Loader reset");
        articleListRecyclerView.setArticles(Collections.emptyList());
        articleListRecyclerView.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }
}
