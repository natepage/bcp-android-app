package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import java.util.List;

public class BcpPostsList {
    private List<BcpPost> _list = null;
    private Boolean _hasMore = false;

    public BcpPostsList() {
    }

    public BcpPostsList(List<BcpPost> _list, Boolean _hasMore) {
        this._list = _list;
        this._hasMore = _hasMore;
    }

    public List<BcpPost> get_list() {
        return _list;
    }

    public BcpPostsList set_list(List<BcpPost> _list) {
        this._list = _list;
        return this;
    }

    public Boolean get_hasMore() {
        return _hasMore;
    }

    public BcpPostsList set_hasMore(Boolean _hasMore) {
        this._hasMore = _hasMore;
        return this;
    }
}
