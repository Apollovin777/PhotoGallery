package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by UBanit on 1/17/2018.
 */

public class PhotoPageActivity extends SingleFragmentActivity {

    public interface OnBackPressedListener {
        public boolean onBackPressed();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        boolean handled = false;
        for (Fragment fragment: fm.getFragments()) {
            if (fragment instanceof  OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            handled = backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }

        if (handled){
            super.onBackPressed();
        }
    }

    public static Intent newIntent(Context context, Uri photoPageUri) {
        Intent i = new Intent(context,PhotoPageActivity.class);
        i.setData(photoPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }


}
