package com.example.karizp.reproductormusica;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Player player = Player.getInstance();
    private final List<Integer> resourceIdList = player.getResourceIdList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, PlayerActivity.class);
        startActivity(i);

        /*
        loadSongs();
        loadArtist();
        loadLyrics();
        fixName();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,player.getSongs());
        for(int i = 0 ; i<player.getSongs().length; i++){
            Log.i("songs","song"+player.getSongs()[i]);
        }

        ListView listView = findViewById(R.id.songListView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                player.setCurrentSong(i);

            }
        });*/

    }

    /*
    public void changeSong()
    {

    }*/

    public void onClickPlay(View view)
    {
        Intent i = new Intent(this, PlayerActivity.class);
        startActivity(i);
    }

    /*

    public void loadLyrics()
    {
        player.setSongLyrics(getResources().getStringArray(R.array.lyrics));
    }
    public void loadArtist()
    {
        player.setArtist(getResources().getStringArray(R.array.artist));
    }

    public void loadSongs()
    {
        final R.raw rawResources = new R.raw();
        final Class<R.raw> rawClass = R.raw.class;
        final Field[] fields = rawClass.getDeclaredFields();

        int songNumber = player.getSongNumber();

        for(int i = 0; i < songNumber; i++)
        {
            player.getSongs()[i] = "";
        }

        for (int i = 0, max = fields.length; i < max; i++)
        {
            final int resourceId;
            try {
                resourceId = fields[i].getInt(rawResources);
                resourceIdList.add(resourceId);
            }
            catch (Exception e) {
                continue;
            }
        }

        player.setResourceIdList(resourceIdList);

        Resources resources = getResources();
        for (int i = 0; i < resourceIdList.size(); i++) {
            String resourceName = resources.getResourceEntryName(resourceIdList.get(i));
            resourceName.replace("com.example.karizp.reproductormusica/raw"," ");
            player.getSongs()[i] = resourceName;
        }
    }

    public void fixName()
    {
        for (int i = 0; i < resourceIdList.size(); i++)
        {
            if(player.getSongs()[i] != "")
            {
                player.getSongs()[i]=player.getSongs()[i].replace('_',' ');
                player.getSongs()[i]=player.getSongs()[i].replace((char)player.getSongs()[i].charAt(0),(char)(player.getSongs()[i].charAt(0)-32));
            }

        }

    }*/

}
