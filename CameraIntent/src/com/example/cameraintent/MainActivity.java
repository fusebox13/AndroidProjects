package com.example.cameraintent;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.provider.MediaStore.Images.Media;

import android.net.Uri;

import java.io.File;


public class MainActivity extends Activity {

    static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
    static final int TAKE_AVATAR_GALLERY_REQUEST = 2;
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final String GAME_PREFERENCES_AVATAR = "Avatar"; // String URL to image
    public static final String MY_DRAWABLE_RESOURCE = "android.resource://edu.wccnet.clem.mycamera/drawable/avatar";
    SharedPreferences mGameSettings;
    Bitmap myPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Retrieve the shared preferences to save location of captured file 
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);

        // Initialize the avatar button
        initAvatar();

    }

    private void initAvatar() {
        // Handle password setting dialog
        ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Avatar);        
        if (mGameSettings.contains(GAME_PREFERENCES_AVATAR)) {
            /* Recall the last image saved by this applicaton */
            String strAvatarUri = mGameSettings.getString(GAME_PREFERENCES_AVATAR, MY_DRAWABLE_RESOURCE);
            Uri imageUri = Uri.parse(strAvatarUri);
            Log.d("Mine", " Previous image="+imageUri);
            avatarButton.setImageURI(imageUri);
        } else {
            Log.d("Mine", "Loading image from resource");
            avatarButton.setImageResource(R.drawable.avatar);
        }


    
        avatarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String strAvatarPrompt = "Take your picture to store as your avatar!";
                //Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(Intent.createChooser(pictureIntent, strAvatarPrompt), TAKE_AVATAR_CAMERA_REQUEST);
            
                Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pictureIntent, TAKE_AVATAR_CAMERA_REQUEST);
            }
        });

        avatarButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                //String strAvatarPrompt = "Choose a picture to use as your avatar!";
                //Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                //pickPhoto.setType("image/*");
                //startActivityForResult(Intent.createChooser(pickPhoto, strAvatarPrompt), TAKE_AVATAR_GALLERY_REQUEST);
                
                Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto, TAKE_AVATAR_GALLERY_REQUEST);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
        case TAKE_AVATAR_CAMERA_REQUEST:

            if (resultCode == Activity.RESULT_CANCELED) {
                // Avatar camera mode was canceled.
                Log.e("Mine", " Camera failed ");
            } else if (resultCode == Activity.RESULT_OK) {
                // 
                // Get image picked
                Uri photoUri = data.getData();
                Log.d("Mine", "photoUri="+photoUri);
                if (photoUri != null) {
                    try {
                        int maxLength = 1536;
                        // Full size image likely will be large. Let's scale the graphic to a more appropriate size for an avatar
                        myPic = Media.getBitmap(getContentResolver(), photoUri);
                        myPic = createScaledBitmapKeepingAspectRatio(myPic, maxLength);
                        saveAvatar(myPic);
                    } catch (Exception e) {
                        Log.e("Mine", "saveAvatar() with gallery picker failed.", e);
                    }
                }
/*
                // Took a picture, use the downsized camera image provided by default
                Bitmap cameraPic =  (Bitmap) data.getExtras().get("data");
                if (cameraPic != null) {
                    try {
                        saveAvatar(cameraPic);
                    } catch (Exception e) {
                        Log.e("Mine", "saveAvatar() with camera image failed.", e);
                    }
                }
*/
            }
            break;
        case TAKE_AVATAR_GALLERY_REQUEST:

            if (resultCode == Activity.RESULT_CANCELED) {
                // Avatar gallery request mode was canceled.
                Log.e("Mine", " Gallery Pick failed ");
            } else if (resultCode == Activity.RESULT_OK) {

                // Get image picked
                Uri photoUri = data.getData();
                Log.d("Mine", "photoUri="+photoUri);
                if (photoUri != null) {
                    try {
                        int maxLength = 1536;
                        // Full size image likely will be large. Let's scale the graphic to a more appropriate size for an avatar
                        myPic = Media.getBitmap(getContentResolver(), photoUri);
                        myPic = createScaledBitmapKeepingAspectRatio(myPic, maxLength);
                        saveAvatar(myPic);
                    } catch (Exception e) {
                        Log.e("Mine", "saveAvatar() with gallery picker failed.", e);
                    }
                }
            }
            break;
        }
    }

    /**
     * Scale a Bitmap, keeping its aspect ratio
     * 
     * @param bitmap
     *            Bitmap to scale
     * @param maxSide
     *            Maximum length of either side
     * @return a new, scaled Bitmap
     */
    private Bitmap createScaledBitmapKeepingAspectRatio(Bitmap bitmap, int maxSide) {
        int orgHeight = bitmap.getHeight();
        int orgWidth = bitmap.getWidth();
        Log.d("Mine", "orgHeight="+orgHeight + " orgWidth="+orgWidth);

        int scaledWidth = (orgWidth >= orgHeight) ? maxSide : (int) ((float) maxSide * ((float) orgWidth / (float) orgHeight));
        int scaledHeight = (orgHeight >= orgWidth) ? maxSide : (int) ((float) maxSide * ((float) orgHeight / (float) orgWidth));
        Log.d("Mine", "scaledHeight="+scaledHeight + " scaledWidth="+scaledWidth);

        // create the scaled bitmap
        Bitmap scaledGalleryPic = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        return scaledGalleryPic;
    }

    private void saveAvatar(Bitmap avatar) {
        String strAvatarFilename = "avatar.jpg";
        try {
            // Save image in a local file
            avatar.compress(CompressFormat.JPEG, 100, openFileOutput(strAvatarFilename, MODE_PRIVATE));
        } catch (Exception e) {
            Log.e("Mine", "Avatar compression and save failed.", e);
        }

        Uri imageUriToSaveCameraImageTo = Uri.fromFile(new File(MainActivity.this.getFilesDir(), strAvatarFilename));
        Log.d("Mine", "Uri for image: "+ imageUriToSaveCameraImageTo);

        // Save pathname to our file in our Preferences object

        Editor editor = mGameSettings.edit();
        editor.putString(GAME_PREFERENCES_AVATAR, imageUriToSaveCameraImageTo.getPath());
        editor.commit();

        // Update the settings screen
        ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Avatar);
        String strAvatarUri = mGameSettings.getString(GAME_PREFERENCES_AVATAR, MY_DRAWABLE_RESOURCE);
        Uri imageUri = Uri.parse(strAvatarUri);
        avatarButton.setImageURI(null); // Workaround for refreshing an ImageButton, which tries to cache the previous image Uri. Passing null effectively resets it.
        avatarButton.setImageURI(imageUri);
    }



} // End of Activity class
