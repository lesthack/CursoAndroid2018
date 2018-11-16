package cursotitulacion.holamundo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import static java.lang.Thread.*;

public class Reproductor extends AppCompatActivity implements View.OnClickListener{

    private ImageView album_cover;
    private ImageButton play_button;
    private ImageButton stop_button;
    private ImageButton back_button;
    private ImageButton next_button;
    private TextView main_title;
    private ProgressBar progress_song;

    private static MediaPlayer player;
    private MediaMetadataRetriever meta;

    private String base_raw;
    private int[] lista_audios = {
            R.raw.cover_1,
            R.raw.cover_2,
            R.raw.cover_3,
            R.raw.cover_4,
            R.raw.cover_5
    };
    private int[] lista_albums = {
            R.mipmap.cover_1,
            R.mipmap.cover_2,
            R.mipmap.cover_3,
            R.mipmap.cover_4,
            R.mipmap.cover_5
    };
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor_layout);

        base_raw = "android.resource://" + getApplicationContext().getPackageName() + "/raw/";

        album_cover = findViewById(R.id.album_cover);
        main_title = findViewById(R.id.main_title);
        play_button = findViewById(R.id.play_button);
        stop_button = findViewById(R.id.stop_button);
        next_button = findViewById(R.id.foward_button);
        back_button = findViewById(R.id.backward_button);
        progress_song = findViewById(R.id.progress_song);

        play_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
        back_button.setOnClickListener(this);
        next_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(player==null){
            player = MediaPlayer.create(this, lista_audios[pos]);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //Log.i("Dev", "Se acabo la cancion");
                    next_button.callOnClick();
                }
            });
            meta = new MediaMetadataRetriever();
            change_info();
            (new ProgressTask()).execute(player);
        }

        switch (v.getId()){
            case R.id.play_button:
                if(player.isPlaying()){
                    play_button.setImageResource(R.drawable.ic_play);
                    player.pause();
                }
                else{
                    play_button.setImageResource(R.drawable.ic_pause);
                    player.start();
                }
                return;
            case R.id.stop_button:
                if(player.isPlaying()){
                    progress_song.setProgress(0);
                    player.stop();
                }
                return;
            case R.id.foward_button:
                pos = pos == lista_audios.length - 1 ? 0 : pos+1;
                break;
            case R.id.backward_button:
                pos = pos == 0 ? lista_audios.length - 1 : pos-1;
                break;
        }

        try {
            progress_song.setProgress(0);
            player.stop();
            player.reset();
            player.setDataSource(this, getDataSourceRaw(lista_audios[pos]));
            player.prepareAsync();
            change_info();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    private void change_info(){
        //album_cover.setImageResource(lista_albums[pos]);

        // Obteniendo metadatos
        meta.setDataSource(this, getDataSourceRaw(lista_audios[pos]));

        // Seteando el titulo de la cancion
        main_title.setText(
                meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                + " - " +
                meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        );

        // Obteniendo el album cover del archivo
        byte[] rawArt = meta.getEmbeddedPicture();
        if(rawArt!=null){
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            Bitmap art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
            album_cover.setImageBitmap(art);
        }

    }

    private Uri getDataSourceRaw(int Id){
        return Uri.parse(base_raw + Id);
    }

    private class ProgressTask extends AsyncTask<MediaPlayer, Void, String> {
        private boolean running = true;
        @Override
        protected String doInBackground(MediaPlayer... players) {
            running = true;
            while(running){
                MediaPlayer player = players[0];
                //Log.i("Dev: length = ", String.valueOf(players.length));
                try {
                    if(player.isPlaying()){
                        //Log.i("Dev", String.valueOf(player.getCurrentPosition()));
                        float advance_percent = (player.getCurrentPosition()*100) / player.getDuration();
                        ProgressBar p = findViewById(R.id.progress_song);
                        p.setProgress(Math.round(advance_percent));
                    }
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }


        public void cancel() {
            running = false;
        }
    }
}
