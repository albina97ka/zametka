package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int NOTE_ACTIVITY_REQUEST_CODE = 1;
    public ArrayList<Notes> NoteList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database.createInstance(this);
        if(Database.info()){
            NoteList = Database.getInstance().getItem("list");
        } else {
            NoteList = new ArrayList<>();
        }

        ListView listView = findViewById(R.id.list);
        adapter = new Adapter(this, android.R.layout.simple_expandable_list_item_1, NoteList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("item", NoteList.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            NoteList.remove(position);
            adapter.notifyDataSetChanged();
            return false;
        });
    }

    public void onClickButtonAdd(View view){
        Notes a = new Notes("Заметка", "Текст заметки");
        NoteList.add(a);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Notes newItem = (Notes) data.getSerializableExtra("newItem");
                int position1 = data.getIntExtra("position1", -1);
                NoteList.set(position1, newItem);
                Database.getInstance().addItem(NoteList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}