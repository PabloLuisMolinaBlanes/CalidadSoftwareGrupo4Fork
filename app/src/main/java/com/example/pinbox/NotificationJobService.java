package com.example.pinbox;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class NotificationJobService extends JobService {

    private static final int NOTIFICATION_ID = 10058;
    private static final String CHANNEL_ID = "pinBoxChannel";

    @Override
    public boolean onStartJob(JobParameters params) {
        // Lógica para mostrar la notificación
        showNotification();

        // Indica si la tarea está en segundo plano
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Devuelve true si se debe volver a programar la tarea
        return true;
    }

    private void showNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            // Crea un canal de notificación (Requerido en Android 8.0 y versiones superiores)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("NOTIFICACIÓN", "Antes de crear el canal");
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PinBoxChannel", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Description for pinbox channel");
                channel.enableLights(true);
                channel.setLightColor(Color.BLUE);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
                Log.d("NOTIFICACIÓN", "Después de crear el canal");
            }

            // Construye la notificación
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.image)
                    .setContentTitle("PINBOX - STOCK")
                    .setContentText(createNotificationText())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setAutoCancel(false)
                    .setWhen(System.currentTimeMillis());

            // Intención para abrir la aplicación cuando se toque la notificación
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(contentIntent);


            // Muestra la notificación
            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }else{
                System.out.println("notificationManager es null");
            }
        }catch (Exception e){
            System.out.println("Error notificación");
            System.out.println(e);
        }
    }
    private String createNotificationText(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Productos bajos de stock: ");
        ArrayList<Producto> productList = GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        int lowStockIndex = 3;
        boolean ots= false;
        for (Producto prod : productList){
            if (prod.getCantidad()<=lowStockIndex){
                stringBuilder.append("Prod: "+prod.getNombre()+" C: "+prod.getCantidad()+" ");
                        ots=true;
            }
        }
        if (!ots){
            return "No hay productos bajos de stock";
        }else return stringBuilder.toString();

    }
}

