package com.example.yokura.cakememo.model;

/**
 * Created by YOKURA on 4/12/15.
 */
public class Prefecture {
    private String pref_id;
    private String pref_name;
    private String area_code;

    public String getPref_id() {
        return pref_id;
    }

    public void setPref_id(String pref_id) {
        this.pref_id = pref_id;
    }

    public String getPref_name() {
        return pref_name;
    }

    public void setPref_name(String pref_name) {
        this.pref_name = pref_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    @Override
    public String toString(){
        return "Prefecture [pref_id=" + pref_id + ", pref_name=" + pref_name + ", area_code=" +area_code +"]";
    }

}
