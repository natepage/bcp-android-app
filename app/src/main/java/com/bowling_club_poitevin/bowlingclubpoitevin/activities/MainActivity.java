package com.bowling_club_poitevin.bowlingclubpoitevin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.adapters.BcpPostsListAdapter;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.BcpPostsManager;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpConstants;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.GetPostsListAsynkTask;

public class MainActivity extends AppCompatActivity implements BcpPostsManager {

    private BcpPostsListAdapter _postsListAdapter = null;
    private GetPostsListAsynkTask _getPostsListTask = null;
    private ProgressBar _postsListProgressBar = null;
    private int _page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setupRecyclerViews();
        downloadContents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.logo_bcp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    /**
     * Setup the recyclerViews.
     */
    private void setupRecyclerViews() {
        _postsListProgressBar = (ProgressBar) findViewById(R.id.progressBarPostsList);
        _postsListAdapter = new BcpPostsListAdapter(this, getLayoutInflater());

        _postsListAdapter.setPackageName(getPackageName());
        _postsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if(_postsListAdapter.getItemCount() == 0) {
                    _postsListProgressBar.setVisibility(View.VISIBLE);
                } else {
                    _postsListProgressBar.setVisibility(View.GONE);
                }
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPostsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(_postsListAdapter);
    }

    /**
     * Download the Activity's contents.
     */
    private void downloadContents() {
        downloadBcpPostsList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _getPostsListTask.cancel(true);
    }

    @Override
    public void downloadBcpPostsList() {
        _getPostsListTask = new GetPostsListAsynkTask(_postsListAdapter, _page++);
        _getPostsListTask.execute();
    }

    @Override
    public void load(BcpPost post) {
        Intent intent = new Intent(this, ShowPostActivity.class);

        intent.putExtra(BcpConstants.POST_EXTRA_TITLE, post.get_title());
        intent.putExtra(BcpConstants.POST_EXTRA_SLUG, post.get_slug());
        startActivity(intent);
    }
}
