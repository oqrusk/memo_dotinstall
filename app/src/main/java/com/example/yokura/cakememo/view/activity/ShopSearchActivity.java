package com.example.yokura.cakememo.view.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yokura.cakememo.R;
import com.example.yokura.cakememo.controller.dao.PrefDao;
import com.example.yokura.cakememo.model.system.MyContract;
import com.example.yokura.cakememo.model.Prefecture;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class ShopSearchActivity extends ActionBarActivity {
    private ArrayList<Prefecture> prefList = new ArrayList<Prefecture>();
    static final String TAG = ShopSearchActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prefList = (ArrayList<Prefecture>) PrefDao.getPrefList();
                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();

        Button btn = (Button) findViewById(R.id.searchButton);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d(TAG, "btnClicked");
                setPrefList();
            }
        });

    }

    private void setPrefList(){

        PrefAdapter prefAdapter = new PrefAdapter(this,0,this.prefList);

        ListView prefListView = (ListView) findViewById(R.id.prefListView);

        prefListView.setAdapter(prefAdapter);

    }

    public class PrefAdapter extends ArrayAdapter<Prefecture> {
        private LayoutInflater layoutInflater;

        public PrefAdapter(Context context, int viewResourceId, ArrayList<Prefecture> prefList) {
            super(context, viewResourceId, prefList);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null ){
                convertView = layoutInflater.inflate(R.layout.row, null);
            }

            Prefecture pref = getItem(position);
            TextView prefId = (TextView) convertView.findViewById(R.id.prefId);
            prefId.setText(pref.getPref_id());
            TextView prefName = (TextView) convertView.findViewById(R.id.prefName);
            prefName.setText(pref.getPref_name());

            return convertView;
        }
    }

    /*
    JsonNode current;

    for (int i=0; (current = rootNode.get(i)) != null; i++) {

    // "name"オブジェクトのノードを取得
    JsonNode nameNode = current.get("name");
    System.out.println("name: ");
    // "name"オブジェクトのフィールドデータを取得して表示
    Iterator<String> nameNodeFields = nameNode.getFieldNames();
    while (nameNodeFields.hasNext()) {
	String nameNodeField = nameNodeFields.next();
	System.out.println("    " + nameNodeField + ": " + nameNode.get(nameNodeField));
    }

    // "mail"フィールドのノードを取得してデータを表示
    JsonNode mailNode = current.get("mail");
    System.out.println("mail: " + mailNode.getTextValue());
}
     */

}
