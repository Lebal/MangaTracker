package com.tracker.filip.mangatracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView mangaEntryList = (ListView) findViewById(R.id.customListView);
        MangaEntryAdapter mangaEntryAdapter = new MangaEntryAdapter(this,R.layout.list_layout);
        mangaEntryList.setAdapter(mangaEntryAdapter);

        for(MangaEntry entry: getMangaEntries()){

            mangaEntryAdapter.add(entry);
        }
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
