package com.example.jedtan.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View;
import java.util.*;
import java.io.*;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> tasks = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Scanner scan = new Scanner(openFileInput("filename.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                tasks.add(line);
            }
        }catch(IOException e){
            System.out.println("No File Found");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertListView();


    }

    private void updateList(){
        try{
            PrintStream out = new PrintStream(openFileOutput("filename.txt", MODE_PRIVATE));
            for(int i = 0; i < tasks.size(); i++){
                out.println(tasks.get(i));

            }
            out.close();
        }catch(IOException e){
            System.out.println("Could not write file.");
        }

    }

    private void insertListView(){
        final ListView listview = (ListView) findViewById(R.id.todolist);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks);
        listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                updateList();
                return true;
            }
        });
    }

    public void addTask(View view){
        final ListView listview = (ListView) findViewById(R.id.todolist);
        ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();

        EditText addText = (EditText)findViewById(R.id.addTaskText);
        tasks.add(addText.getText().toString());

        adapter.notifyDataSetChanged();
        updateList();

    }
}
