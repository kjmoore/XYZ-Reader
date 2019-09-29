package com.example.xyzreader.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.xyzreader.R;
import com.example.xyzreader.ui.articlelist.ArticleListFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String VIEW_ARTICLE = "com.example.xyzreader.VIEW_ARTICLE";
    public static final String ARTICLE_SELECTED = "com.example.xyzreader.ARTICLE_SELECTED";

    private ArticleListFragment articleList = new ArticleListFragment();
    private ArticleFragment article = new ArticleFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            Log.d(TAG, "Replacing fragment");
            // Do this manually on on-create so we don't end up with an extra step in the back stack
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, articleList)
                    .commit();
        }

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(viewArticle, new IntentFilter(VIEW_ARTICLE));
    }

    private void setFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.fragment_holder) == fragment) {
            return;
        }

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_holder, fragment);

//        if (!isTabletMode) {
            fragmentTransaction.addToBackStack(null);
//        }
        fragmentTransaction.commit();
    }

    private BroadcastReceiver viewArticle = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Navigating to article");

        final Bundle bundle = intent.getExtras();
        Log.d(TAG, "Navigation bundle: " + bundle);

        if (bundle != null) {
            article.setArguments(bundle);
            setFragment(article);
        } else {
            Log.e(TAG, "There was no bundle, so couldn't move to article");
        }

        }
    };
}
