package cursotitulacion.holamundo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class Hilo extends AsyncTask<ProgressBar, Integer, Void> {

    SplashScreenActivity sa;

    public Hilo(SplashScreenActivity sa){
        sa = sa;
    }

    private ProgressBar progress;
    @Override
    protected Void doInBackground(ProgressBar... progressBars) {
        progress = progressBars[0];

        for(int i=0; i<3; i++){
            try {
                publishProgress(i*50);
                Thread.sleep(1000);
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
        Intent intent = new Intent(sa, MediaPlayerActivity.class);
        //sa.startActivity(intent);
        //sa.finish();
    }
}
