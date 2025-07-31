package com.example.atwork;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManualAttendence extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manual_attendence);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.manualAttendence), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        Button btnCheckIn = findViewById(R.id.btn_check_in);
        Button btnCheckOut = findViewById(R.id.btn_check_out);

        // Set onClick listeners
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store Check In time in database
                boolean isInserted = databaseHelper.insertGeofenceLog("check_in", "entry");
                if (isInserted) {
                    Toast.makeText(ManualAttendence.this, "Checked In", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManualAttendence.this, "Check In failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store Check Out time in database
                boolean isInserted = databaseHelper.insertGeofenceLog("check_out", "exit");
                if (isInserted) {
                    Toast.makeText(ManualAttendence.this, "Checked Out", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManualAttendence.this, "Check Out failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
