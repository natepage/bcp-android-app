package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import android.os.AsyncTask;
import android.util.Log;

import com.bowling_club_poitevin.bowlingclubpoitevin.adapters.BcpPostsListAdapter;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.InputStreamReaderWithBuffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GetPostsListAsynkTask extends AsyncTask<Void, Void, BcpPostsList> {

    private BcpPostsListAdapter _adapter = null;
    private int _page;

    public GetPostsListAsynkTask(BcpPostsListAdapter adapter, int page) {
        _adapter = adapter;
        _page = page;
    }

    @Override
    protected BcpPostsList doInBackground(Void... voids) {
        return BcpDataProvider.getPostsList(_page);
    }

    @Override
    protected void onPostExecute(BcpPostsList bcpPosts) {
        if(bcpPosts != null) {
            _adapter.setPostsList(bcpPosts);
        }
    }
}
