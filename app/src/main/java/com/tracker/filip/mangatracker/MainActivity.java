package com.tracker.filip.mangatracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InputFragment.InputListener{

    MangaEntryAdapter mangaEntryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button addEntry =(Button) findViewById(R.id.addEntry);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry(v);
            }
        });









        final ListView mangaEntryList = (ListView) findViewById(R.id.customListView);
        mangaEntryAdapter = new MangaEntryAdapter(this,R.layout.list_layout);
        mangaEntryList.setAdapter(mangaEntryAdapter);











        for(MangaEntry entry: getMangaEntries()){

            mangaEntryAdapter.add(entry);
        }

        mangaEntryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MangaEntry o = (MangaEntry) mangaEntryList.getItemAtPosition(position);
                Toast.makeText(getBaseContext(),o.getName() + " " + o.getChapter(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<MangaEntry> getMangaEntries() {

        List<MangaEntry> entries = new ArrayList<MangaEntry>();

        for (int i =0;i<10;i++){

            entries.add(
                    new MangaEntry(
                            "TestEntry",
                            i,
                            i % 2 == 0 ? R.drawable.aot : R.drawable.opm
                    )

            );

        }

        return entries;

    }


    private void addEntry(View v){

        InputFragment inFrag = new InputFragment();

        inFrag.show(getSupportFragmentManager(),"Input fragment");

    }

    @Override
    public void onClick(MangaEntry entry) {
        Toast.makeText(getBaseContext(),"Added",Toast.LENGTH_SHORT).show();
        mangaEntryAdapter.add(entry);
    }
}
