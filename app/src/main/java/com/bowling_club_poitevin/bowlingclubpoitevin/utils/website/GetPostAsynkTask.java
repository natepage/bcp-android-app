package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;


import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.ZoomImageAnimator;

public class GetPostAsynkTask extends AsyncTask<Void, Void, BcpPost> {

    private String _slug;
    private View _rootView;
    private LayoutInflater _layoutInflater;
    private ZoomImageAnimator _zoomImageAnimator;

    public GetPostAsynkTask(String slug, View rootView, LayoutInflater layoutInflater, ZoomImageAnimator zoomImageAnimator) {
        _slug = slug;
        _rootView = rootView;
        _layoutInflater = layoutInflater;
        _zoomImageAnimator = zoomImageAnimator;
    }

    @Override
    protected BcpPost doInBackground(Void... voids) {
        return BcpDataProvider.getPostBySlug(_slug);
    }

    @Override
    protected void onPostExecute(final BcpPost post) {
        ProgressBar progressBar = (ProgressBar) _rootView.findViewById(R.id.postContentProgressBar);
        WebView content = (WebView) _rootView.findViewById(R.id.postContent);

        if(post != null) {
            progressBar.setVisibility(View.GONE);
            content.loadData(post.get_content(), "text/html", null);

            LinearLayout categoriesContainer = (LinearLayout) _rootView.findViewById(R.id.showPostCategoriesContainer);
            BcpElementsRenderer.renderCategoriesList(_layoutInflater, post.get_categories(), categoriesContainer);

            content.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    GridLayout imagesContainer = (GridLayout) _rootView.findViewById(R.id.showPostImagesContainer);
                    GridLayout pdfsContainer = (GridLayout) _rootView.findViewById(R.id.showPostPdfsContainer);

                    BcpElementsRenderer.renderImagesList(_layoutInflater, post.get_images(), imagesContainer, _zoomImageAnimator);
                    BcpElementsRenderer.renderPdfsList(_layoutInflater, post.get_pdfs(), pdfsContainer);
                }
            });
        } else {
            content.loadData("<p>Un problème est survenu lors du téléchargement de l\'article</p>", "text/html", null);
        }
    }
}
