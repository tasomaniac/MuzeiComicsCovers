package com.tasomaniac.muzei.comiccovers;

import com.google.gson.annotations.SerializedName;
import com.tasomaniac.muzei.comiccovers.model.Comic;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

interface ComicVineService {

//    http://www.comicvine.com/api/issues/?format=json&api_key=4c6bf8be589e7483077955b0bf1398dfbafd7f58
    @GET("/issues/?format=json&limit=1")
    ComicVineResponse getIssues(@Query("offset") String offset);

    static class ComicVineResponse {

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

        public ComicVineResponse(){
        }

        public void setError(String error) {
            mError = error;
        }

        public String getError() {
            return mError;
        }

        public void setStatusCode(int statusCode) {
            mStatusCode = statusCode;
        }

        public int getStatusCode() {
            return mStatusCode;
        }

        public void setResults(List<Comic> comics) {
            mComics = comics;
        }

        public List<Comic> getResults() {
            return mComics;
        }

        public void setNumberOfTotalResult(int numberOfTotalResult) {
            mNumberOfTotalResult = numberOfTotalResult;
        }

        public int getNumberOfTotalResult() {
            return mNumberOfTotalResult;
        }
    }
}
