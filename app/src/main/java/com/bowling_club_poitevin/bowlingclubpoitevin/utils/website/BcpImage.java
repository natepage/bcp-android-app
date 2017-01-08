package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import java.util.Map;

public class BcpImage {
    private String _alt;
    private Map<String, String> _webPaths;

    public BcpImage() {
    }

    public BcpImage(Map<String, String> _webPaths, String _alt) {
        this._webPaths = _webPaths;
        this._alt = _alt;
    }

    public Map<String, String> get_webPaths() {
        return _webPaths;
    }

    public BcpImage set_webPaths(Map<String, String> _webPaths) {
        this._webPaths = _webPaths;
        return this;
    }

    public String get_alt() {
        return _alt;
    }

    public BcpImage set_alt(String _alt) {
        this._alt = _alt;
        return this;
    }
}
