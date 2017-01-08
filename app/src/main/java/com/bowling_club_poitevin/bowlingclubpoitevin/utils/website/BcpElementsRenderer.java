package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.ZoomImageAnimator;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.PicassoImageLoader;
import com.squareup.picasso.Callback;

import java.util.List;

public class BcpElementsRenderer {

    public static void renderCategoriesList(LayoutInflater layoutInflater, List<BcpCategory> categories, LinearLayout container) {
        container.removeAllViews();

        for(int i = 0; i < categories.size(); i++) {
            BcpCategory category = categories.get(i);

            TextView textView = (TextView) layoutInflater.inflate(R.layout.textview_categories, null);
            textView.setText(category.get_title());

            Space space = new Space(container.getContext());
            space.setMinimumWidth(10);

            container.addView(textView);
            container.addView(space);
        }
    }

    public static void renderImagesList(LayoutInflater layoutInflater, List<BcpImage> images, GridLayout container, final ZoomImageAnimator zoomImageAnimator) {
        for(int i = 0; i < images.size(); i++) {
            final BcpImage image = images.get(i);
            PicassoImageLoader imageLoader = new PicassoImageLoader();
            View rootView = layoutInflater.inflate(R.layout.framelayout_image_preview, null);

            final ImageView imageView = (ImageView) rootView.findViewById(R.id.imagePreview);
            final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.imagePreviewProgressBar);
            final Uri uri = Uri.parse(image.get_webPaths().get("post_preview"));

            imageLoader.loadImage(uri, imageView, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    Log.i("PicassoLoad_ERROR", uri.toString());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zoomImageAnimator.zoomImage(imageView, Uri.parse(image.get_webPaths().get("post_view")), progressBar);
                }
            });

            container.addView(rootView);
            Log.i("BCP", "ImageView added");
        }
    }

    public static void renderPdfsList(LayoutInflater layoutInflater, List<BcpPdf> pdfs, GridLayout container) {

    }
}
