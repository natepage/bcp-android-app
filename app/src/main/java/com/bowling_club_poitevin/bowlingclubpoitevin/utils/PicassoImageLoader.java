package com.bowling_club_poitevin.bowlingclubpoitevin.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(Uri uri, ImageView imageView, Callback callback) {
        Picasso picasso = Picasso.with(imageView.getContext());
        //picasso.setIndicatorsEnabled(true);

        picasso.load(uri).into(imageView, callback);
    }
}
