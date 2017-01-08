package com.bowling_club_poitevin.bowlingclubpoitevin.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.ZoomImageAnimator;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.PicassoImageLoader;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpConstants;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpDataProvider;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.GetPostAsynkTask;
import com.squareup.picasso.Callback;

public class ShowPostActivity extends AppCompatActivity implements ZoomImageAnimator {
    String _title;
    String _slug;
    GetPostAsynkTask _task;
    private Animator _currentAnimator;
    private int _shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        _title = getIntent().getStringExtra(BcpConstants.POST_EXTRA_TITLE);
        _slug = getIntent().getStringExtra(BcpConstants.POST_EXTRA_SLUG);

        setupActionBar();
        showPost();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sharePost();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share) {
            sharePost();
        }

        return false;
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(_title);
        getSupportActionBar().setLogo(R.mipmap.logo_bcp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void showPost() {
        View rootView = findViewById(R.id.showPostRootView);
        TextView title = (TextView) findViewById(R.id.postTitle);

        title.setText(_title);

        _task = new GetPostAsynkTask(_slug, rootView, getLayoutInflater(), this);
        _task.execute();
    }

    private void sharePost() {
        String link = "[" +_title + "] \n" + BcpDataProvider.getSharePostUrl(_slug);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);

        startActivity(Intent.createChooser(intent, getString(R.string.share_post)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _task.cancel(true);
    }

    public void zoomImage(final ImageView thumbView, Uri uri, final ProgressBar progressBar) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (_currentAnimator != null) {
            _currentAnimator.cancel();
        }

        _shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expandedImage);

        progressBar.setVisibility(View.VISIBLE);

        // Load the high-resolution "zoomed-in" image.
        PicassoImageLoader imageLoader = new PicassoImageLoader();
        imageLoader.loadImage(uri, expandedImageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);

                // Calculate the starting and ending bounds for the zoomed-in image. This step
                // involves lots of math. Yay, math.
                final Rect startBounds = new Rect();
                final Rect finalBounds = new Rect();
                final Point globalOffset = new Point();

                // The start bounds are the global visible rectangle of the thumbnail, and the
                // final bounds are the global visible rectangle of the container view. Also
                // set the container view's offset as the origin for the bounds, since that's
                // the origin for the positioning animation properties (X, Y).
                thumbView.getGlobalVisibleRect(startBounds);
                expandedImageView.getRootView().getGlobalVisibleRect(finalBounds, globalOffset);
                startBounds.offset(-globalOffset.x, -globalOffset.y);
                finalBounds.offset(-globalOffset.x, -globalOffset.y);

                // Adjust the start bounds to be the same aspect ratio as the final bounds using the
                // "center crop" technique. This prevents undesirable stretching during the animation.
                // Also calculate the start scaling factor (the end scaling factor is always 1.0).
                float startScale;
                if ((float) finalBounds.width() / finalBounds.height()
                        > (float) startBounds.width() / startBounds.height()) {
                    // Extend start bounds horizontally
                    startScale = (float) startBounds.height() / finalBounds.height();
                    float startWidth = startScale * finalBounds.width();
                    float deltaWidth = (startWidth - startBounds.width()) / 2;
                    startBounds.left -= deltaWidth;
                    startBounds.right += deltaWidth;
                } else {
                    // Extend start bounds vertically
                    startScale = (float) startBounds.width() / finalBounds.width();
                    float startHeight = startScale * finalBounds.height();
                    float deltaHeight = (startHeight - startBounds.height()) / 2;
                    startBounds.top -= deltaHeight;
                    startBounds.bottom += deltaHeight;
                }

                // Hide the thumbnail and show the zoomed-in view. When the animation begins,
                // it will position the zoomed-in view in the place of the thumbnail.
                thumbView.setAlpha(0f);
                expandedImageView.setVisibility(View.VISIBLE);

                // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
                // the zoomed-in view (the default is the center of the view).
                expandedImageView.setPivotX(0f);
                expandedImageView.setPivotY(0f);

                // Construct and run the parallel animation of the four translation and scale properties
                // (X, Y, SCALE_X, and SCALE_Y).
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                                finalBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                                finalBounds.top))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
                set.setDuration(_shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        _currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        _currentAnimator = null;
                    }
                });
                set.start();
                _currentAnimator = set;

                // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
                // and show the thumbnail instead of the expanded image.
                final float startScaleFinal = startScale;
                expandedImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (_currentAnimator != null) {
                            _currentAnimator.cancel();
                        }

                        // Animate the four positioning/sizing properties in parallel, back to their
                        // original values.
                        AnimatorSet set = new AnimatorSet();
                        set
                                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                                .with(ObjectAnimator
                                        .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                                .with(ObjectAnimator
                                        .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                        set.setDuration(_shortAnimationDuration);
                        set.setInterpolator(new DecelerateInterpolator());
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                thumbView.setAlpha(1f);
                                expandedImageView.setVisibility(View.GONE);
                                _currentAnimator = null;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                thumbView.setAlpha(1f);
                                expandedImageView.setVisibility(View.GONE);
                                _currentAnimator = null;
                            }
                        });
                        set.start();
                        _currentAnimator = set;
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }
}
