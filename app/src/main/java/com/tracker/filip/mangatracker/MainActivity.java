package com.tracker.filip.mangatracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InputFragment.InputListener,EditFragment.UpdateListener {

    MangaEntryAdapter mangaEntryAdapter;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addEntry =(Button) findViewById(R.id.addEntry);
        dbHandler = new MyDBHandler(this,null,null,1);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry(v);
            }
        });

        final ListView mangaEntryList = (ListView) findViewById(R.id.customListView);
        mangaEntryAdapter = new MangaEntryAdapter(this,R.layout.list_layout);
        mangaEntryList.setAdapter(mangaEntryAdapter);

        updateEntries();
        sortAdapter();

        mangaEntryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MangaEntry o = (MangaEntry) mangaEntryList.getItemAtPosition(position);
                updateEntry(o);
            }
        });
    }

    private List<MangaEntry> getMangaEntries() {
        return dbHandler.databaseToList();

    }


    private void addEntry(View v){
        InputFragment inFrag = new InputFragment();
        inFrag.show(getSupportFragmentManager(),"Input fragment");
    }

    private void updateEntry(MangaEntry entry){

        EditFragment editFrag = new EditFragment(entry);
        editFrag.show(getSupportFragmentManager(),"Edit fragment");

    }

    @Override
    public void onClick(MangaEntry entry) {
        Toast.makeText(getBaseContext(),"Added",Toast.LENGTH_SHORT).show();

        mangaEntryAdapter.add(entry);
        sortAdapter();
        dbHandler.addEntry(entry);
    }

    @Override
    public void onClickUpdate(MangaEntry entry1, MangaEntry entry2) {
        mangaEntryAdapter.remove(entry2);
        dbHandler.removeEntry(entry2);

        mangaEntryAdapter.add(entry1);
        sortAdapter();
        dbHandler.addEntry(entry1);
    }

    @Override
    public void removeEntry(MangaEntry entry) {
        mangaEntryAdapter.remove(entry);
        sortAdapter();
        dbHandler.removeEntry(entry);
    }

    private void updateEntries(){
        for(MangaEntry entry: getMangaEntries()){

            mangaEntryAdapter.add(entry);
        }

    }

    private void sortAdapter(){

        mangaEntryAdapter.sort(new Comparator<MangaEntry>() {
            @Override
            public int compare(MangaEntry o1, MangaEntry o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

    }

}
