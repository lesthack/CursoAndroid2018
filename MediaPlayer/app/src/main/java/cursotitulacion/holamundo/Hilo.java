package cursotitulacion.holamundo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class Hilo extends AsyncTask<ProgressBar, Integer, Void> {

    SplashScreenActivity context;
    ProgressBar progress;
    Integer p=0;

    public Hilo(SplashScreenActivity context){
        this.context = context;
    }


    @Override
    protected Void doInBackground(ProgressBar... progressBars) {
        progress = progressBars[0];
        while(p<110){
            try {
                publishProgress(p);
                Thread.sleep(100);
                p+=10;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... advance){
        super.onProgressUpdate(advance);
        progress.setProgress(advance[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent(context, MediaPlayerActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}
