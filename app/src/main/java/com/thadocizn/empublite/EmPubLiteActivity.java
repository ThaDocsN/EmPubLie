package com.thadocizn.empublite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.karim.MaterialTabs;

public class EmPubLiteActivity extends Activity {

    public static final String MODEL = "model";

    @BindView(R.id.tabs)
    MaterialTabs tabs;
    @BindView(R.id.pager)
    ViewPager pager;

    private ContentsAdapter adapter;

    private void setupStrictMode(){
        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (BuildConfig.DEBUG){
            builder.penaltyFlashScreen();
        }

        StrictMode.setThreadPolicy(builder.build());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBookLoaded(BookLoadedEvent event){
        setupPager(event.getBook());
    }

    private void setupPager(BookContents contents){
        adapter = new ContentsAdapter(this, contents);
        pager.setAdapter(adapter);

        tabs.setViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, SimpleContentActivity.class)
                .putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/about.html");
                startActivity(intent);
                return (true);

            case R.id.help:
                intent = new Intent(this, SimpleContentActivity.class)
                        .putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/help.html");
                startActivity(intent);
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        if (adapter == null){
            ModelFragment modelFragment = (ModelFragment) getFragmentManager().findFragmentByTag(MODEL);
            if (modelFragment == null){
                getFragmentManager().beginTransaction()
                        .add(new ModelFragment(), MODEL).commit();
            }else if (modelFragment.getBook() != null){
                setupPager(modelFragment.getBook());
            }
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        setupStrictMode();

    }
}
