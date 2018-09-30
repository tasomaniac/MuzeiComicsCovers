package com.tasomaniac.muzei.comiccovers.model;

import com.google.gson.annotations.SerializedName;


public class Image {

    private static final String FIELD_THUMB_URL = "thumb_url";
    private static final String FIELD_SUPER_URL = "super_url";
    private static final String FIELD_ICON_URL = "icon_url";
    private static final String FIELD_TINY_URL = "tiny_url";
    private static final String FIELD_SMALL_URL = "small_url";
    private static final String FIELD_MEDIUM_URL = "medium_url";
    private static final String FIELD_SCREEN_URL = "screen_url";

    @SerializedName(FIELD_THUMB_URL)
    private String mThumbUrl;
    @SerializedName(FIELD_SUPER_URL)
    private String mSuperUrl;
    @SerializedName(FIELD_ICON_URL)
    private String mIconUrl;
    @SerializedName(FIELD_TINY_URL)
    private String mTinyUrl;
    @SerializedName(FIELD_SMALL_URL)
    private String mSmallUrl;
    @SerializedName(FIELD_MEDIUM_URL)
    private String mMediumUrl;
    @SerializedName(FIELD_SCREEN_URL)
    private String mScreenUrl;

    public Image() {

    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getSuperUrl() {
        return mSuperUrl;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public String getTinyUrl() {
        return mTinyUrl;
    }

    public String getSmallUrl() {
        return mSmallUrl;
    }

    public String getMediumUrl() {
        return mMediumUrl;
    }

    public String getScreenUrl() {
        return mScreenUrl;
    }

}
