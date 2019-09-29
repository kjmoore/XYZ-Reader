package com.example.xyzreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.databinding.ArticleDetailBinding;
import com.example.xyzreader.databinding.ArticleItemBinding;
import com.example.xyzreader.model.Article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {
    private static final String TAG = ArticleFragment.class.getSimpleName();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Starting article list fragment");
        final ArticleDetailBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.article_detail, container, false);

        if (getArguments() != null) {
            final Article article = getArguments().getParcelable(MainActivity.ARTICLE_SELECTED);
            viewBinding.setArticle(article);

            if (!this.getResources().getBoolean(R.bool.tablet_mode)) {
                final AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.setSupportActionBar(viewBinding.toolbar);
                    final ActionBar supportActionBar = mainActivity.getSupportActionBar();
                    if (supportActionBar != null) {
                        supportActionBar.setDisplayHomeAsUpEnabled(true);
                    }

                    viewBinding.fab.setOnClickListener(view -> {
                        Intent shareIntent = Intent.createChooser(
                                ShareCompat.IntentBuilder.from(getActivity())
                                        .setType("text/plain")
                                        .setText(getShareText(article))
                                        .getIntent(),
                                getString(R.string.title_share_article)
                        );
                        startActivity(shareIntent);
                    });
                }
            }

        } else {
            Log.e(TAG, "No article found");
        }



        return viewBinding.getRoot();
    }

    private String getShareText(Article article) {
        return article.title + " - " + article.author;
    }
}
