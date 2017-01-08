package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

public class BcpCategory {

    private String _title;
    private String _slug;

    public BcpCategory(){}

    public BcpCategory(String title, String slug) {
        _title = title;
        _slug = slug;
    }

    public String get_title() {
        return _title;
    }

    public BcpCategory set_title(String _title) {
        this._title = _title;

        return this;
    }

    public String get_slug() {
        return _slug;
    }

    public BcpCategory set_slug(String _slug) {
        this._slug = _slug;

        return this;
    }
}
