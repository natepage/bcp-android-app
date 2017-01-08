package com.bowling_club_poitevin.bowlingclubpoitevin.interfaces;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.ProgressBar;

public interface ZoomImageAnimator {
    void zoomImage(ImageView imageView, Uri uri, ProgressBar progressBar);
}
