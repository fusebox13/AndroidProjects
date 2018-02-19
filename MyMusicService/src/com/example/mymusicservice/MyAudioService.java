package com.example.mymusicservice;

import android.app.Service;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyAudioService extends Service implements MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer=null;
    int songNum;
    String songName;
    
    public static final String MY_NEXT_SONG_BROADCAST="edu.wccnet.clem.MyNextSongBroadcast";


    int[] mp_resources={R.raw.baby_love, R.raw.gitarzan, R.raw.lady_godiva, R.raw.mrs_robinson, 
                        R.raw.puff, R.raw.rocky, R.raw.snoopy, R.raw.sukiaki}; 
    String[] mp_names={"Baby Love", "Gitarzan", "Lady Godiva", "Mrs. Robinson",
                       "Puff the Magic Dragon", "Rocky", "Snoopy and the Red Barron", "Sukiaki"};

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong(); // Go to the next song
    }

    private void stopSong()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void playFirstSong()
    {
        songNum = 0;
        playSong();
    }
    private void playNextSong()
    {
        songNum+=1;
        songNum = songNum % mp_resources.length;

        playSong();
    }
    private void playSong()
    {
        stopSong(); // good to stop the last song if necessary before starting a new song
        
        mediaPlayer = MediaPlayer.create(this, mp_resources[songNum]);
        songName=mp_names[songNum];
        
        if (mediaPlayer != null)
        {   
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);

            int duration = mediaPlayer.getDuration();
            Toast.makeText(this, "("+songNum+")"+songName+" : "+duration,     Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(MY_NEXT_SONG_BROADCAST);
            myIntent.putExtra("songName", songName);
            myIntent.putExtra("songNum", songNum);
            myIntent.putExtra("duration", duration);
            sendBroadcast(myIntent);
        }
    }
    private void logToast(String s)
    {
        Log.d("Mine",s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void onCreate() {
        logToast( "onCreate of MyAudioService");
    }

    @Override
    public void onDestroy() {
        stopSong(); // Very important to free up resources
        logToast( "MyAudioService onDestroy");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null)
        {
            Log.d("Mine", "Why are we getting a null Intent in onStartCommand????");
            return Service.START_NOT_STICKY;
        }
        Log.d("Mine", "onStartCommand: "+ intent.getStringExtra("cmd"));

        if ("play".equals(intent.getStringExtra("cmd")))
        {
            songNum=0;
            playFirstSong();
        } 
        else if ("next".equals(intent.getStringExtra("cmd")))
        {
            playNextSong();
        }
        else if ("stop".equals(intent.getStringExtra("cmd")))
        {
            stopSong();
        }
        else if ("stopService".equals(intent.getStringExtra("cmd")))
        {
            stopSong(); // You should free up resources.  
            // Scary, but see what happens if you comment this out and
            // stop the Service.
            stopSelf();
        }

        return Service.START_NOT_STICKY;
    }

} // End of Service
