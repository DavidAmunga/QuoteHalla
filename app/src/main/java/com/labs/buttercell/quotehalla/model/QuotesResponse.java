package com.labs.buttercell.quotehalla.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amush on 28-Dec-17.
 */

public class QuotesResponse implements Serializable {

    @SerializedName("quotes")
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
