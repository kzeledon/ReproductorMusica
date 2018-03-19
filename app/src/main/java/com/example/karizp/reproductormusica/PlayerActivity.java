package com.example.karizp.reproductormusica;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {

    private Player player = Player.getInstance();
    private boolean playing = false;
    private MediaPlayer mediaPlayer;
    private SeekBar volumeSeekBar;
    private SeekBar progressSeekBar;
    private int currentSong = 0;
    private TextView lyrics;
    private CountDownTimer countDownTimer;
    private boolean textAnimation = false;
    private final List<Integer> resourceIdList = new ArrayList<Integer>();

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);


        loadSongs();
        loadArtist();
        loadLyrics();
        fixName();
        lyrics = findViewById(R.id.txtLyrics);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,player.getSongs());
        for(int i = 0 ; i<player.getSongs().length; i++){
            Log.i("SongLoad",": "+player.getSongs()[i]);
        }

        ListView listView = findViewById(R.id.songList);
        listView.setAdapter(adapter);

        volumeSeekBar = (SeekBar) findViewById(R.id.seekBarVolume);
        progressSeekBar =(SeekBar) findViewById(R.id.seekBarProgress);

        startSong();



        //mediaPlayer = MediaPlayer.create(this, resourceIdList.get(currentSong));
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = (int) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = (int) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);



        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                currentSong = position;
                startSong();
            }
        });


    }



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

    }

    public void setCountDownTimer(){
        if(countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000) {

            @Override
            public void onTick(long l) {
                if(playing)
                    progressSeekBar.setProgress(progressSeekBar.getProgress() + 1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }



    public void startSong() {
        TextView tvSong = findViewById(R.id.txtCancion);
        TextView tvArtist = findViewById(R.id.txtArtista);

        tvSong.setText(player.getSongs()[currentSong]);
        tvArtist.setText(player.getArtist()[currentSong]);

        if(mediaPlayer != null)
            mediaPlayer.stop();
        if(countDownTimer!=null)
            countDownTimer.cancel();
        mediaPlayer = null;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), resourceIdList.get(currentSong));

        lyrics.setText(player.getSongLyrics()[currentSong]);
        lyrics.setTranslationY(100f);

        int duration = mediaPlayer.getDuration();;

        progressSeekBar.setMax(duration);
        progressSeekBar.setProgress(0);


        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setCompletionListener();
        setCountDownTimer();

        if(playing) {
            mediaPlayer.start();
            lyrics.animate().translationYBy(-1000f).setDuration((mediaPlayer.getDuration()/3));
        }
/*
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);*/




    }



    public void setCompletionListener()
    {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp)
            {

                nextSong();
            }
        });
    }
    public void onNextSongClicked(View view)
    {
        nextSong();
    }
    public void onPrevSongClicked(View view)
    {
        prevSong();
    }

    public void onPlaySongClicked(View view)
    {
        Button button = (Button) findViewById(R.id.btnPlay);
        if(!playing)
        {
            mediaPlayer.start();
            playing = true;
            setCountDownTimer();
            button.setBackground(this.getResources().getDrawable(android.R.drawable.ic_media_pause));
            if(textAnimation == false)
            {
                lyrics.animate().translationYBy(-500f).setDuration((mediaPlayer.getDuration()/3));
                textAnimation = true;
            }
        }else
        {
            playing = false;
            countDownTimer.cancel();
            mediaPlayer.pause();
            button.setBackground(this.getResources().getDrawable(android.R.drawable.ic_media_play));
        }
    }





    public void nextSong()
    {
        currentSong += 1;
        currentSong %= player.getSongNumber();
        startSong();
    }
    public void prevSong()
    {
        if(currentSong == 0)
            currentSong = player.getSongNumber()-1;
        else
            currentSong -=1;
        startSong();
    }
}