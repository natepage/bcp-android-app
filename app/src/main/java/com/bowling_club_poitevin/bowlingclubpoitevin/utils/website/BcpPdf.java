package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;


public class BcpPdf {

    private String _alt;
    private String _webPath;

    public BcpPdf(){}

    public BcpPdf(String _alt, String _webPath) {
        this._alt = _alt;
        this._webPath = _webPath;
    }

    public String get_alt() {
        return _alt;
    }

    public BcpPdf set_alt(String _alt) {
        this._alt = _alt;
        return this;
    }

    public String get_webPath() {
        return _webPath;
    }

    public BcpPdf set_webPath(String _webPath) {
        this._webPath = _webPath;
        return this;
    }
}
