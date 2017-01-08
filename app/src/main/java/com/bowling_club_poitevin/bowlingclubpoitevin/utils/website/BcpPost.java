package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BcpPost {

    private Date _created;
    private Date _updated;
    private String _authorName;
    private Boolean _published;
    private String _title;
    private String _slug;
    private String _description;
    private String _content;
    private List<BcpCategory> _categories;
    private List<BcpImage> _images;
    private List<BcpPdf> _pdfs;
    private Integer _previewImageKey;

    public BcpPost(){}

    public BcpPost(
            Date _created,
            Date _updated,
            String _authorName,
            Boolean _published,
            String _title,
            String _slug,
            String _description,
            String _content,
            List<BcpCategory> _categories,
            List<BcpImage> _images,
            List<BcpPdf> _pdfs,
            int _previewImageKey
    ) {
        this._created = _created;
        this._updated = _updated;
        this._authorName = _authorName;
        this._published = _published;
        this._title = _title;
        this._slug = _slug;
        this._description = _description;
        this._content = _content;
        this._categories = _categories;
        this._images = _images;
        this._pdfs = _pdfs;
        this._previewImageKey = _previewImageKey;
    }

    public Date get_created() {
        return _created;
    }

    public BcpPost set_created(Date _created) {
        this._created = _created;
        return this;
    }

    public Date get_updated() {
        return _updated;
    }

    public BcpPost set_updated(Date _updated) {
        this._updated = _updated;
        return this;
    }

    public String get_authorName() {
        return _authorName;
    }

    public BcpPost set_authorName(String _authorName) {
        this._authorName = _authorName;
        return this;
    }

    public Boolean get_published() {
        return _published;
    }

    public BcpPost set_published(Boolean _published) {
        this._published = _published;
        return this;
    }

    public String get_title() {
        return _title;
    }

    public BcpPost set_title(String _title) {
        this._title = _title;
        return this;
    }

    public String get_slug() {
        return _slug;
    }

    public BcpPost set_slug(String _slug) {
        this._slug = _slug;
        return this;
    }

    public String get_description() {
        return _description;
    }

    public BcpPost set_description(String _description) {
        this._description = _description;
        return this;
    }

    public String get_content() {
        return _content;
    }

    public BcpPost set_content(String _content) {
        this._content = _content;
        return this;
    }

    public List<BcpCategory> get_categories() {
        return _categories;
    }

    public BcpPost set_categories(List<BcpCategory> _categories) {
        this._categories = _categories;
        return this;
    }

    public List<BcpImage> get_images() {
        return _images;
    }

    public BcpPost set_images(List<BcpImage> _images) {
        this._images = _images;
        return this;
    }

    public Integer get_previewImageKey() {
        return _previewImageKey;
    }

    public BcpPost set_previewImageKey(Integer _previewImageKey) {
        this._previewImageKey = _previewImageKey;
        return this;
    }

    public List<BcpPdf> get_pdfs() {
        return _pdfs;
    }

    public BcpPost set_pdfs(List<BcpPdf> _pdfs) {
        this._pdfs = _pdfs;
        return this;
    }
}
