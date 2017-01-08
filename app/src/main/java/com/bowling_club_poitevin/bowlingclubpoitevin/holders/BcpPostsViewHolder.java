package com.bowling_club_poitevin.bowlingclubpoitevin.holders;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.BcpPostsListViewHolder;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.BcpPostsManager;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.PicassoImageLoader;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpCategory;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpElementsRenderer;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpImage;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;
import com.squareup.picasso.Callback;

import java.text.SimpleDateFormat;
import java.util.List;

public class BcpPostsViewHolder extends RecyclerView.ViewHolder implements BcpPostsListViewHolder {

    private BcpPostsManager _manager;
    private String _packageName;
    private BcpPost _currentPost;
    private LayoutInflater _layoutInflater;

    private TextView _title;
    private ImageView _image;
    private ProgressBar _progressBar;
    private TextView _description;
    private TextView _authorAndDate;
    private LinearLayout _categoriesContainer;

    public BcpPostsViewHolder(View itemView) {
        super(itemView);

        _title = (TextView) itemView.findViewById(R.id.postsListCellTitle);
        _image = (ImageView) itemView.findViewById(R.id.imagePreview);
        _progressBar = (ProgressBar) itemView.findViewById(R.id.imagePreviewProgressBar);
        _description = (TextView) itemView.findViewById(R.id.postsListCellDescription);
        _authorAndDate = (TextView) itemView.findViewById(R.id.postsListCellAuthorAndDate);
        _categoriesContainer = (LinearLayout) itemView.findViewById(R.id.postsListCellCategoriesContainer);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _manager.load(_currentPost);
            }
        });
    }

    @Override
    public void bind(BcpPost post) {
        _currentPost = post;

        loadImage(post.get_images());

        _title.setText(post.get_title());
        _description.setText(post.get_description());

        //Author and date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        _authorAndDate.setText(post.get_authorName().toUpperCase() + ", " + sdf.format(post.get_created()));

        //Categories
        BcpElementsRenderer.renderCategoriesList(_layoutInflater, post.get_categories(), _categoriesContainer);
    }

    private void loadImage(List<BcpImage> images) {
        PicassoImageLoader imageLoader = new PicassoImageLoader();
        final Uri uri;

        if(images.size() > 0){
            BcpImage image = images.get(0);
            uri = Uri.parse(image.get_webPaths().get("post_preview"));
        } else {
            uri = Uri.parse("android.resource://" + _packageName + "/" + R.mipmap.logo_bcp);
        }

        imageLoader.loadImage(uri, _image, new Callback() {
            @Override
            public void onSuccess() {
                _progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.i("PicassoLoad_ERROR", uri.toString());
            }
        });
    }

    @Override
    public void setPackageName(String packageName) {
        _packageName = packageName;
    }

    @Override
    public void setPostsManager(BcpPostsManager manager) {
        _manager = manager;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        _layoutInflater = layoutInflater;
    }
}
