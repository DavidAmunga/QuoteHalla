package com.labs.buttercell.quotehalla.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by amush on 28-Dec-17.
 */

public class Quote implements Serializable {
    @SerializedName("quote")
    private String quote;
    @SerializedName("cat")
    private String cat;
    @SerializedName("author")
    private String author;


    public Quote(String quote, String cat, String author) {
        this.quote = quote;
        this.cat = cat;
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
