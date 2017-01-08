package com.bowling_club_poitevin.bowlingclubpoitevin.interfaces;


import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Callback;

public interface ImageLoader {
    void loadImage(Uri uri, ImageView imageView, Callback callback);
}
