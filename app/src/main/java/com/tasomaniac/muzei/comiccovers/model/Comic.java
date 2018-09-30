package com.tasomaniac.muzei.comiccovers.model;

import com.google.gson.annotations.SerializedName;

public class Comic {

    private static final String FIELD_COVER_DATE = "cover_date";
    private static final String FIELD_STORE_DATE = "store_date";
    private static final String FIELD_VOLUME = "volume";
    private static final String FIELD_SITE_DETAIL_URL = "site_detail_url";
    private static final String FIELD_IMAGE = "image";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_ISSUE_NUMBER = "issue_number";
    private static final String FIELD_API_DETAIL_URL = "api_detail_url";
    private static final String FIELD_ID = "id";
    private static final String FIELD_ALIASES = "aliases";
    private static final String FIELD_HAS_STAFF_REVIEW = "has_staff_review";
    private static final String FIELD_DATE_ADDED = "date_added";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DATE_LAST_UPDATED = "date_last_updated";
    private static final String FIELD_DECK = "deck";

    @SerializedName(FIELD_COVER_DATE)
    private String mCoverDate;
    @SerializedName(FIELD_STORE_DATE)
    private String mStoreDate;
    @SerializedName(FIELD_VOLUME)
    private Volume mVolume;
    @SerializedName(FIELD_SITE_DETAIL_URL)
    private String mSiteDetailUrl;
    @SerializedName(FIELD_IMAGE)
    private Image mImage;
    @SerializedName(FIELD_DESCRIPTION)
    private String mDescription;
    @SerializedName(FIELD_ISSUE_NUMBER)
    private int mIssueNumber;
    @SerializedName(FIELD_API_DETAIL_URL)
    private String mApiDetailUrl;
    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_ALIASES)
    private String mAlias;
    @SerializedName(FIELD_HAS_STAFF_REVIEW)
    private boolean mHasStaffReview;
    @SerializedName(FIELD_DATE_ADDED)
    private String mDateAdded;
    @SerializedName(FIELD_NAME)
    private String mName;
    @SerializedName(FIELD_DATE_LAST_UPDATED)
    private String mDateLastUpdated;
    @SerializedName(FIELD_DECK)
    private String mDeck;


    public Comic() {

    }

    public String getCoverDate() {
        return mCoverDate;
    }

    public String getStoreDate() {
        return mStoreDate;
    }

    public Volume getVolume() {
        return mVolume;
    }

    public String getSiteDetailUrl() {
        return mSiteDetailUrl;
    }

    public Image getImage() {
        return mImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getIssueNumber() {
        return mIssueNumber;
    }

    public String getApiDetailUrl() {
        return mApiDetailUrl;
    }

    public long getId() {
        return mId;
    }

    public String getAlias() {
        return mAlias;
    }

    public boolean isHasStaffReview() {
        return mHasStaffReview;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public String getName() {
        return mName;
    }

    public String getDateLastUpdated() {
        return mDateLastUpdated;
    }

    public String getDeck() {
        return mDeck;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comic) {
            return ((Comic) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((Long) mId).hashCode();
    }


}
