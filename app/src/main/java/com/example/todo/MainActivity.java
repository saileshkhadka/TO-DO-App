package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.todo.task_database.TaskContract;
import com.example.todo.task_database.TaskDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDatabaseHelper taskHelper;
    private ListView TaskList;
    private ArrayAdapter<String> arrAdapter;
    TextView taskTextView;

    @Override
    //OnCreate => an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
    protected void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskHelper = new TaskDatabaseHelper(this);
        TaskList = (ListView) findViewById(R.id.MyListView);
        taskTextView = findViewById(R.id.title_task);
        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//Callback method to be invoked when an item in this AdapterView has been clicked

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete task").setMessage("Are you want to delete a task?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = (String)arrAdapter.getItem(position);
                                deleteTask(value);
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();

            }
        });

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEdit = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task").setMessage("Add some notes or task.").setView(taskEdit)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEdit.getText());
                                SQLiteDatabase db = taskHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(String view) {   //function used to delete the notes or tasks
       // View parent = (View) view.getParent();
        String task = view;

       // String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = taskHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry.COL_TASK_TITLE + " = ?", new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {    //to update the added task
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = taskHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (arrAdapter == null) {
            Log.d("Sailesh", String.valueOf(taskList.size()));
            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_task, R.id.title_task, taskList);
            TaskList.setAdapter(arrAdapter);
        } else {

            arrAdapter.clear();
            arrAdapter.addAll(taskList);
          //  arrAdapter.notifyDataSetChanged();
        }
        arrAdapter.notifyDataSetChanged();

        cursor.close();
        db.close();
    }

    @Override
    protected void onResume() {   //onResume activity
        super.onResume();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskHelper = null;   //connection stop for memory usage
    }
}