package com.example.xyzreader.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.databinding.ActivityMainBinding;
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
    private ActivityMainBinding mainBinding;

    private boolean isTabletMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        isTabletMode = this.getResources().getBoolean(R.bool.tablet_mode);

        if (savedInstanceState == null) {
            Log.d(TAG, "Replacing fragment");
            // Do this manually on on-create so we don't end up with an extra step in the back stack
            if (isTabletMode) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_holder_master, articleList)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_holder, articleList)
                        .commit();
            }
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

        fragmentTransaction.addToBackStack(null);
        
        if (!isTabletMode) {
            mainBinding.selectArticle.setVisibility(View.INVISIBLE);
        }
        fragmentTransaction.commit();
    }

    private BroadcastReceiver viewArticle = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Navigating to article");

        final Bundle bundle = intent.getExtras();
        Log.d(TAG, "Navigation bundle: " + bundle);

        if (bundle != null) {
            final Fragment fragment = new ArticleFragment();
            fragment.setArguments(bundle);
            setFragment(fragment);
        } else {
            Log.e(TAG, "There was no bundle, so couldn't move to article");
        }

        }
    };

    /**
     * @return true if Up navigation completed successfully <b>and</b> this Activity was finished, false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return false;
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(viewArticle);
    }
}
