package com.example.badvok.popularmovies.DataBase;

import io.realm.RealmObject;

/**
 * Created by simon on 05-May-16.
 */
public class Trailer extends RealmObject {

    private String id;
    private String iso_693_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public Trailer(String id, String iso_693_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.id = id;
        this.iso_693_1 = iso_693_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public Trailer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_693_1() {
        return iso_693_1;
    }

    public void setIso_693_1(String iso_693_1) {
        this.iso_693_1 = iso_693_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
