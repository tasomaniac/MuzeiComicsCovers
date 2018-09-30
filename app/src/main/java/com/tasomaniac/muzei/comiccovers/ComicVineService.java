package com.tasomaniac.muzei.comiccovers;

import com.tasomaniac.muzei.comiccovers.model.ComicVineResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ComicVineService {

//    http://www.comicvine.com/api/issues/?format=json&api_key=4c6bf8be589e7483077955b0bf1398dfbafd7f58
    @GET("issues/?format=json&limit=1")
    Call<ComicVineResponse> getIssues(@Query("offset") String offset);

}
