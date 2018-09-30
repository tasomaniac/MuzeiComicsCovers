package com.tasomaniac.muzei.comiccovers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComicVineResponse {

    private static final String FIELD_ERROR = "error";
    private static final String FIELD_STATUS_CODE = "status_code";
    private static final String FIELD_RESULTS = "results";
    private static final String FIELD_NUMBER_OF_TOTAL_RESULTS = "number_of_total_results";

    @SerializedName(FIELD_ERROR)
    private String mError;
    @SerializedName(FIELD_STATUS_CODE)
    private int mStatusCode;
    @SerializedName(FIELD_RESULTS)
    private List<Comic> mComics;
    @SerializedName(FIELD_NUMBER_OF_TOTAL_RESULTS)
    private int mNumberOfTotalResult;

    public ComicVineResponse() {
    }

    public String getError() {
        return mError;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public List<Comic> getResults() {
        return mComics;
    }

    public int getNumberOfTotalResult() {
        return mNumberOfTotalResult;
    }
}
