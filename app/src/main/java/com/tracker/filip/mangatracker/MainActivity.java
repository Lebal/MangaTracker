package com.tracker.filip.mangatracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView mangaEntryList = (ListView) findViewById(R.id.customListView);
        MangaEntryAdapter mangaEntryAdapter = new MangaEntryAdapter(this,R.layout.list_layout);
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
}
