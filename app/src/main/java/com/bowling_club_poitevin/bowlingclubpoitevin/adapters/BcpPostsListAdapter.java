package com.bowling_club_poitevin.bowlingclubpoitevin.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bowling_club_poitevin.bowlingclubpoitevin.R;
import com.bowling_club_poitevin.bowlingclubpoitevin.holders.BcpPostsViewHolder;
import com.bowling_club_poitevin.bowlingclubpoitevin.holders.BcpProgressViewHolder;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.BcpPostsListConsumer;
import com.bowling_club_poitevin.bowlingclubpoitevin.interfaces.BcpPostsManager;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPostsList;

import java.util.ArrayList;
import java.util.List;

public class BcpPostsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BcpPostsListConsumer {

    private List<BcpPost> _list = null;
    private String _packageName;
    private Boolean _hasMore = false;
    private BcpPostsManager _manager = null;
    private LayoutInflater _layoutInflater = null;

    public BcpPostsListAdapter(BcpPostsManager manager, LayoutInflater layoutInflater) {
        _manager = manager;
        _layoutInflater = layoutInflater;
    }

    @Override
    public int getItemViewType(int position) {
        return position < _list.size() ? R.layout.content_posts_list_cell : R.layout.content_progress_list_cell;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

        switch (viewType){
            case R.layout.content_posts_list_cell:
                BcpPostsViewHolder viewHolder = new BcpPostsViewHolder(view);
                viewHolder.setPackageName(_packageName);
                viewHolder.setPostsManager(_manager);
                viewHolder.setLayoutInflater(_layoutInflater);

                return viewHolder;
            case R.layout.content_progress_list_cell:
                return new BcpProgressViewHolder(view);
            default:
                throw new RuntimeException("Wrong viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BcpPostsViewHolder){
            ((BcpPostsViewHolder) holder).bind(_list.get(position));
        } else {
            _manager.downloadBcpPostsList();
        }
    }

    @Override
    public int getItemCount() {
        return _list != null ? _list.size() + (_hasMore ? 1 : 0) : 0;
    }

    @Override
    public void setPostsList(BcpPostsList bcpPostsList) {
        Log.i("ADAPTER", "setPostsList[Lenght=" + String.valueOf(bcpPostsList.get_list().size()) + "]");

        if(_list == null) {
            _list = new ArrayList<>();
        }

        _list.addAll(bcpPostsList.get_list());
        _hasMore = bcpPostsList.get_hasMore();

        notifyDataSetChanged();
    }

    @Override
    public void setPackageName(String packageName) {
        _packageName = packageName;
    }
}
