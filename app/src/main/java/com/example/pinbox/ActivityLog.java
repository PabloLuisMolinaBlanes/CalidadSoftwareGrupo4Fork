package com.example.pinbox;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;



public class ActivityLog extends AppCompatActivity {
    private ArrayList<String> activityLog = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log);
            this.activityLog=GestorArchivos.loadLog(getFilesDir().getPath() + "/" + "log.dat");

            TextView log = findViewById(R.id.textViewLog);
            appendArrayToTextView(activityLog, log);
        }
        private void appendArrayToTextView(ArrayList<String> stringArray, TextView textView) {
            StringBuilder content = new StringBuilder();

            for (String str : stringArray) {
                content.append(str).append("\n");
            }

            textView.append(content.toString());
        }
}
