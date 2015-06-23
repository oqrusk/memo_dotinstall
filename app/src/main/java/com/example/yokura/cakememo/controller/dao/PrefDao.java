package com.example.yokura.cakememo.controller.dao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.yokura.cakememo.R;
import com.example.yokura.cakememo.controller.provider.HttpAccessProvider;
import com.example.yokura.cakememo.model.Prefecture;
import com.example.yokura.cakememo.model.system.MyContract;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOKURA on 4/19/15.
 */
public class PrefDao {
    Prefecture pref;
    private static final String TAG = PrefDao.class.getSimpleName();

    public static ArrayList<Prefecture> getPrefList() {
        ArrayList<Prefecture> prefList = new ArrayList<Prefecture>();

        HttpAccessProvider httpAccessProvider = new HttpAccessProvider();
        String jsonString = httpAccessProvider.getJsonString(MyContract.GNaviApis.URL_PREF_S);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root =  mapper.readTree(jsonString).get("pref");

            JsonNode current;
            for (int i=0; (current = root.get(i)) != null; i++) {
                Prefecture pref = new Prefecture();
                pref.setPref_id(current.get("pref_code").toString());
                pref.setPref_name(current.get("pref_name").toString());
                pref.setArea_code(current.get("area_code").toString());

                prefList.add(pref);
                Log.d(TAG, pref.toString());
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return prefList;
    }

}
