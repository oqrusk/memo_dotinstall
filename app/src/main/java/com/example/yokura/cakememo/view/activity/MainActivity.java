package com.example.yokura.cakememo.view.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.yokura.cakememo.controller.provider.MyContentProvider;
import com.example.yokura.cakememo.R;
import com.example.yokura.cakememo.model.system.MyContract;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks{

    private SimpleCursorAdapter adapter;
    public final static String EXTRA_MEMOID = "com.example.yokura.amazonmemo.MEMOID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] from = {
                MyContract.Memos.COLUMN_TITLE,
                MyContract.Memos.COLUMN_UPDATED
        };
        int[] to = {
                android.R.id.text1,
                android.R.id.text2
        };

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                from,
                to,
                0
        );

        ListView listView = (ListView) findViewById(R.id.memoListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXTRA_MEMOID, l);
                startActivity(intent);
            }
        });

        Button btn = (Button) findViewById(R.id.gotoSearchButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopSearchActivity.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MyContract.Memos.COLUMN_ID,
                MyContract.Memos.COLUMN_TITLE,
                MyContract.Memos.COLUMN_UPDATED
        };

        return new CursorLoader(
                this,
                MyContentProvider.CONTENT_URI,
                projection,
                null,
                null,
                "updated desc"
        );
    }

    @Override
    public void onLoadFinished(Loader loader, Object cursor) {
        adapter.swapCursor((android.database.Cursor) cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }
}
