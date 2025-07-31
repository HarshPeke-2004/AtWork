package com.example.atwork;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.cardview.widget.CardView; // Import for CardView

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee);

        // Set padding for insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.employee), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the first CardView by its ID
        CardView firstActivityButton = findViewById(R.id.first_activity_button);

        // Set OnClickListener on the first CardView
        firstActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NextActivity
                Intent intent = new Intent(EmployeeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        CardView manualAttendanceButton = findViewById(R.id.manual_attendence);

        // Set OnClickListener on the manual_attendence CardView
        manualAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start NextActivity
                Intent intent = new Intent(EmployeeActivity.this, ManualAttendence.class);
                startActivity(intent);
            }
        });

        CardView employeeAttendanceButton = findViewById(R.id.employee_attendance);

        // Set OnClickListener on the employee_attendance CardView
        employeeAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EmployeeAttendanceActivity
                Intent intent = new Intent(EmployeeActivity.this, AttendanceDetails.class);
                startActivity(intent);
            }
        });
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}
