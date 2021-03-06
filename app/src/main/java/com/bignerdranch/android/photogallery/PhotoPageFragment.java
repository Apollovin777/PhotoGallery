package com.bignerdranch.android.photogallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bignerdranch.android.photogallery.PhotoPageActivity.OnBackPressedListener;

public class PhotoPageFragment extends VisibleFragment implements OnBackPressedListener {
    private static final String TAG = "PhotoPageFragment";
    private static final String ARG_URI = "photo_page_url";

    private Uri mUri;
    private WebView mWebView;
    private ProgressBar mProgressBar;



    public static PhotoPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = getArguments().getParcelable(ARG_URI);
    }

    @Nullable
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

        mProgressBar = v.findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);

        mWebView = v.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
                                        public void onProgressChanged(WebView webView, int newProgress) {
                                            if (newProgress >= 100) {
                                                mProgressBar.setVisibility(View.GONE);
                                            } else {
                                                mProgressBar.setVisibility(View.VISIBLE);
                                                mProgressBar.setProgress(newProgress);
                                            }
                                        }

                                        public void onReceivedTitle(WebView webView, String title) {
                                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                                            activity.getSupportActionBar().setSubtitle(title);
                                        }
        });

        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl(mUri.toString());
        return v;
    }

    @Override
    public boolean onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        } else {
            return true;
        }

    }
}

