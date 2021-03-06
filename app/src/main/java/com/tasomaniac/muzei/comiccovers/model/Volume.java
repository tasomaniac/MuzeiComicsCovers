package com.tasomaniac.muzei.comiccovers.model;

import com.google.gson.annotations.SerializedName;


public class Volume {

    private static final String FIELD_API_DETAIL_URL = "api_detail_url";
    private static final String FIELD_ID = "id";
    private static final String FIELD_SITE_DETAIL_URL = "site_detail_url";
    private static final String FIELD_NAME = "name";

    @SerializedName(FIELD_API_DETAIL_URL)
    private String mApiDetailUrl;
    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_SITE_DETAIL_URL)
    private String mSiteDetailUrl;
    @SerializedName(FIELD_NAME)
    private String mName;

    public Volume() {

    }

    public String getApiDetailUrl() {
        return mApiDetailUrl;
    }

    public long getId() {
        return mId;
    }

    public String getSiteDetailUrl() {
        return mSiteDetailUrl;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Volume) {
            return ((Volume) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((Long) mId).hashCode();
    }


}
