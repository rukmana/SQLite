package com.example.nanark.sqlite;

import android.app.AlertDialog;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDB;

    EditText name, surname, marks, id;

    Button save_btn, list_btn, update_btn, delete_btn;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);


        name = (EditText) findViewById(R.id.name_txt);

        surname = (EditText) findViewById(R.id.surname_txt);

        marks = (EditText) findViewById(R.id.marks_txt);

        id = (EditText) findViewById(R.id.id_txt);


        save_btn = (Button) findViewById(R.id.send_btn);

        list_btn = (Button) findViewById(R.id.students_btn);

        update_btn = (Button) findViewById(R.id.update_btn);

        delete_btn = (Button) findViewById(R.id.delete_btn);


        save_btn.setOnClickListener(this);

        list_btn.setOnClickListener(this);

        update_btn.setOnClickListener(this);

        delete_btn.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.send_btn:

                boolean result = myDB.save_student(name.getText().toString(),

                        surname.getText().toString(),

                        marks.getText().toString()

                );

                if (result)

                    Toast.makeText(MainActivity.this, "Success Add Student", Toast.LENGTH_LONG).show();

                else

                    Toast.makeText(MainActivity.this, "Fails Add Student", Toast.LENGTH_LONG).show();

                break;

            case R.id.students_btn:

                Cursor students = myDB.list_student();

                if (students.getCount() == 0) {

                    alert_message("Message", "No data student found");

                    return;

                }

                //append data student to buffer

                StringBuffer buffer = new StringBuffer();

                while (students.moveToNext()) {

                    buffer.append("Id : " + students.getString(0) + "\n");

                    buffer.append("Name : " + students.getString(1) + "\n");

                    buffer.append("Surname : " + students.getString(2) + "\n");

                    buffer.append("Marks : " + students.getString(3) + "\n\n\n");

                }

                //show data student

                alert_message("List Students", buffer.toString());

                break;

            case R.id.update_btn:

                result = myDB.update_student(id.getText().toString(),

                        name.getText().toString(),

                        surname.getText().toString(),

                        marks.getText().toString());

                if (result)

                    Toast.makeText(MainActivity.this, "Success update data Student", Toast.LENGTH_LONG).show();

                else

                    Toast.makeText(MainActivity.this, "Fails update data Student", Toast.LENGTH_LONG).show();

                break;

            case R.id.delete_btn:


                Integer results = myDB.delete_student(id.getText().toString());

                if (results > 0)

                    Toast.makeText(MainActivity.this, "Success delete a Student", Toast.LENGTH_LONG).show();

                else

                    Toast.makeText(MainActivity.this, "Fails delete a Student", Toast.LENGTH_LONG).show();

                break;

        }

    }


    public void alert_message(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.show();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}


