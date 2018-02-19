package com.example.videomusicdbservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

// Helper class to store the record for one song
class MediaData
{
    // _id is the primary key, _data is the path name to the music file, and _display_name has a song name

    String _id, _data, _display_name;
    int duration;
    MediaData(String id, String data, String display_name, int dur)
    {
        _id=id;
        _data = data;
        _display_name=display_name;
        duration = dur;
    }
    public String toString()
    {
        return _id+":"+_display_name+ ": "+ duration+" "+ _data ;
    }
}

public class MyAudioService extends Service implements MediaPlayer.OnCompletionListener{

    MediaData[] theMedia = null; // Storage for all of our songs

    private MediaPlayer mediaPlayer=null;
    int songNum;
    String songName;

    public static final String MY_NEXT_SONG_BROADCAST="edu.wccnet.clem.MyNextSongBroadcast";

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
        playNextSong();
    }
    private void playNextSong()
    {
        songNum = (int)(Math.random() * theMedia.length);
        playSong();
    }
    private void playSong()
    {
        stopSong(); // good to stop the last song if necessary before starting a new song

        songName=theMedia[songNum]._display_name;
        Uri uri = Uri.parse("file://"+theMedia[songNum]._data);
        mediaPlayer = MediaPlayer.create(this, uri);
        Log.d("Mine","Uri="+uri);

        if (mediaPlayer != null)
        {             
            // I only want the last 30 seconds of each song ... be careful of short files
            int seekTo = theMedia[songNum].duration-30000; // last 30 seconds
            if (seekTo > 0) mediaPlayer.seekTo(seekTo); // Play at most the last 30 seconds

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
        theMedia = getMedia();
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
        String cmd = intent.getStringExtra("cmd");
        Log.d("Mine", "onStartCommand: "+ cmd);

        if ("play".equals(cmd))
        {
            songNum=0;
            playFirstSong();
        } 
        else if ("next".equals(cmd))
        {
            playNextSong();
        }
        else if ("stop".equals(cmd))
        {
            stopSong();
        }
        else if ("stopService".equals(cmd))
        {
            stopSong(); // You should free up resources.  
            // Scary, but see what happens if you comment this out and
            // stop the Service.
            stopSelf();
        }

        return Service.START_NOT_STICKY;
    }


    MediaData[] getMedia()
    {
        MediaData[] media = null;

        final Uri mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection={"_id","_data","_display_name", "duration"};
        Cursor cursor = getContentResolver().query(mediaUri,projection, "is_music=1", null, null);

        int rows = cursor.getCount();
        media= new MediaData[rows];
        int count =0;

        while (cursor.moveToNext()) { 
            media[count]= new MediaData(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3));
            count +=1;
        }
        cursor.close();

        return media;
    }

} // End of Service