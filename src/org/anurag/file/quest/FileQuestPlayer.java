/**
 * Copyright(c) 2014 DRAWNZER.ORG PROJECTS -> ANURAG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *                             
 *                             anurag.dev1512@gmail.com
 *
 */


package org.anurag.file.quest;


import java.io.IOException;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * THIS CLASS IS BASICALLY TO SHOW THE PREVIEW TO THE MP3,OR ANY ANDROID SUPPORTED AUDIO
 * FILE...
 * 
 * THIS IS NOT A FULL FLEDGED MUSIC PLAYER ACTIVITY....
 * @author Anurag
 *
 */
public class FileQuestPlayer extends Activity{

	boolean playing;
	MediaPlayer player;
	SeekBar seekbar;
	MediaMetadataRetriever retreive;
	Play play;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		playing = true;
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		Intent intent = getIntent();
		Dialog dialog = new Dialog(FileQuestPlayer.this, R.style.custom_dialog_theme);
		dialog.setCancelable(true); 
		dialog.setContentView(R.layout.file_quest_player);
		seekbar = (SeekBar)dialog.findViewById(R.id.seek);
		
		TextView albumname = (TextView)dialog.findViewById(R.id.albumName);
		TextView artist = (TextView)dialog.findViewById(R.id.artistName);
		ImageView album = (ImageView)dialog.findViewById(R.id.albumart);
		try {
			player = new MediaPlayer();
			player.setDataSource(FileQuestPlayer.this, intent.getData());
			player.prepare();
			player.start();
			player.setLooping(true);
			seekbar.setMax(player.getDuration());
			retreive = new MediaMetadataRetriever();
			retreive.setDataSource(FileQuestPlayer.this, intent.getData());
			play = new Play();
			/**
			 * extracting mediametadata like album name,artist,album art...
			 */
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player = null;
			Toast.makeText(FileQuestPlayer.this, R.string.failedtoplay , Toast.LENGTH_SHORT).show();
			FileQuestPlayer.this.finish();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player = null;
			Toast.makeText(FileQuestPlayer.this, R.string.failedtoplay , Toast.LENGTH_SHORT).show();
			FileQuestPlayer.this.finish();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player = null;
			Toast.makeText(FileQuestPlayer.this, R.string.failedtoplay , Toast.LENGTH_SHORT).show();
			FileQuestPlayer.this.finish();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player = null;
			Toast.makeText(FileQuestPlayer.this, R.string.failedtoplay , Toast.LENGTH_SHORT).show();
			FileQuestPlayer.this.finish();
		}
		
		if(player!=null){
			
			//setting the album art....
			try{
				byte art[] = retreive.getEmbeddedPicture();
				Bitmap img = BitmapFactory.decodeByteArray(art, 0, art.length);
				album.setImageBitmap(img);
			}catch(Exception e){
				album.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_albumart));
			}
			
			//setting the album name...
			try{
				String name = retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
				if(name.length() != 0)
					albumname.setText(retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
				else
					albumname.setText(R.string.notavailable);
			}catch(Exception e){
				albumname.setText(R.string.notavailable);
			}
			
			//setting the artist name...
			try{
				String name = retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
				if(name.length() != 0)
					artist.setText(retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
				else
					albumname.setText(R.string.notavailable);
			}catch(Exception e){
				albumname.setText(R.string.notavailable);
			}
		}			
		
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser)
					player.seekTo(progress);
			}
		});
		
		dialog.getWindow().getAttributes().width = size.x*8/9;
		if(player!=null)
			dialog.show();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				try{
					player.release();
					playing = false;
					play.cancel(true);
				}catch(Exception e){
					
				}
				FileQuestPlayer.this.finish();
			}
		});
		
		if(player != null)
			play.execute();
		else{
			FileQuestPlayer.this.finish();
		}
	}	
	
	private class Play extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			try{
				seekbar.setProgress(player.getCurrentPosition());
			}catch(IllegalStateException e){
				
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while(playing){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				publishProgress((Void[])null);
			}
			return null;
		}		
	}
}
