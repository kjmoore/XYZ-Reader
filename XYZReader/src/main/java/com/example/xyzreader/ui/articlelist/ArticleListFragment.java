package com.example.xyzreader.ui.articlelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.databinding.ArticleItemBinding;
import com.example.xyzreader.databinding.ArticleListBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

public class ArticleListFragment extends Fragment {

    private final ArticleListRecyclerView articleListRecyclerView = new ArticleListRecyclerView();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ArticleListBinding viewBinding =
                DataBindingUtil.inflate(inflater, R.layout.article_list, container, false);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        viewBinding.recyclerView.setLayoutManager(layoutManager);
        viewBinding.recyclerView.setAdapter(articleListRecyclerView);

        return viewBinding.getRoot();
    }
}
