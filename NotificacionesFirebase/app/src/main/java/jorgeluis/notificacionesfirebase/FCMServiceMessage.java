package jorgeluis.notificacionesfirebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMServiceMessage extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        verNotificacion(remoteMessage.getData().get("message"));
    }

    private void verNotificacion(String message) {
        Intent i = new Intent(this, PrincipalActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendiente = PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Notificacion Push")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendiente);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
