package com.glasgow.mhci.socktranslation.history;

import java.io.Serializable;

public class Recording implements Serializable {

    private static int ID = 0;

    private int id;
    private String name;
    private String text;
    private String fromLanguage;
    private String toLanguage;

    Recording(String name, String text, String fromLanguage, String toLanguage){
        this.id = ID++;
        this.name = name;
        this.text = text;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }
}
