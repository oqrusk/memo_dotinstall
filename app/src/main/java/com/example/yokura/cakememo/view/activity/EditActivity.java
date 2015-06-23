package com.example.yokura.cakememo.view.activity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yokura.cakememo.controller.provider.MyContentProvider;
import com.example.yokura.cakememo.R;
import com.example.yokura.cakememo.model.system.MyContract;


public class EditActivity extends ActionBarActivity {

    private boolean isNewMemo = true;

    private EditText memoTitle;
    private EditText memoBody;
    private TextView memoUpdated;
    private String title = "";
    private String body = "";
    private String updated = "";
    private long memoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        memoTitle = (EditText) findViewById(R.id.memoTitle);
        memoBody = (EditText) findViewById(R.id.memoBody);
        memoUpdated = (TextView) findViewById(R.id.memoUpdated);

        Intent intent = getIntent();
        memoId = intent.getLongExtra(MainActivity.EXTRA_MEMOID, 0L);
        isNewMemo = memoId == 0L ? true : false;

        if (isNewMemo){
            //newMemo();
            //getActionBar().setTitle("New Memo");
        }else{
            //editMemo();
            //getActionBar().setTitle("Edit Memo");
            Uri uri = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, memoId);
            String[] projection = new String[] {
                    MyContract.Memos.COLUMN_TITLE,
                    MyContract.Memos.COLUMN_BODY,
                    MyContract.Memos.COLUMN_UPDATED
            };
            String selection = MyContract.Memos.COLUMN_ID + " = ?";
            String[] selectionArgs = new String[] { Long.toString(memoId)};
            Cursor cursor = getContentResolver().query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    null
            );
            while(cursor.moveToNext()){
                title = cursor.getString(cursor.getColumnIndex(MyContract.Memos.COLUMN_TITLE));
                body = cursor.getString(cursor.getColumnIndex(MyContract.Memos.COLUMN_BODY));
                updated = "Updated: " + cursor.getString(cursor.getColumnIndex(MyContract.Memos.COLUMN_UPDATED));
            }
            memoTitle.setText(title);
            memoBody.setText(body);
            memoUpdated.setText(updated);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        if (isNewMemo) {
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_save:
                title = memoTitle.getText().toString().trim();
                body = memoBody.getText().toString().trim();
                if(title.equals("")){
                    Toast.makeText(this,"please enter title",Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues values = new ContentValues();
                    values.put(MyContract.Memos.COLUMN_TITLE, title);
                    values.put(MyContract.Memos.COLUMN_BODY, body);
                    if(isNewMemo){
                       //insert
                        getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                    }else{
                        //update
                        values.put(MyContract.Memos.COLUMN_UPDATED,
                                android.text.format.DateFormat.format(
                                        "yyyy-MM-dd kk:mm:ss",
                                        new java.util.Date()
                                        ).toString()
                        );
                        Uri uri = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, memoId);
                        String selection = MyContract.Memos.COLUMN_ID + " = ?";
                        String[] selectionArgs = new String[] { Long.toString(memoId) };

                        getContentResolver().update(
                                uri,
                                values,
                                selection,
                                selectionArgs);
                    }
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.action_delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Delete Memo");
                alertDialog.setMessage("Are you sure to delete this memo?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, memoId);
                        String selection = MyContract.Memos.COLUMN_ID + " = ?";
                        String[] selectionArgs = new String[] { Long.toString(memoId) };

                        getContentResolver().delete(
                                uri,
                                selection,
                                selectionArgs
                        );
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
