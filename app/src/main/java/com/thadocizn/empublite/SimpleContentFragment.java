package com.thadocizn.empublite;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;

public class SimpleContentFragment extends WebViewFragment {

    private static final String KEY_FILE = "file";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        getWebView().getSettings().setJavaScriptEnabled(true);
        getWebView().getSettings().setSupportZoom(true);
        getWebView().getSettings().setBuiltInZoomControls(true);
        getWebView().loadUrl(getPage());
        return (result);
    }

    private String getPage(){
        return (getArguments().getString(KEY_FILE));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    static SimpleContentFragment newInstance(String file) {

    SimpleContentFragment simpleContentFragment = new SimpleContentFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FILE, file);
        simpleContentFragment.setArguments(args);

        return (simpleContentFragment);
    }
}
