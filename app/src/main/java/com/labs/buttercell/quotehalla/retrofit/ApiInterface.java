package com.labs.buttercell.quotehalla.retrofit;

import com.labs.buttercell.quotehalla.model.Quote;
import com.labs.buttercell.quotehalla.model.QuotesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by amush on 28-Dec-17.
 */

public interface ApiInterface {

    @GET(".")
    Call<List<Quote>> getQuotes();

}
