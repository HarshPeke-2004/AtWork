package com.example.atwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Define a constant for the admin password
    private static final String ADMIN_PASSWORD = "atwork@9876";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust padding based on window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons
        Button nextButton = findViewById(R.id.second_activity_button);
        Button employeeButton = findViewById(R.id.staff_login);
        Button logoutButton = findViewById(R.id.logout_btn); // Initialize the Logout button

        // Show password dialog for admin login
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdminPasswordDialog();
            }
        });

        // Employee login button logic
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        // Logout button logic
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out from Firebase Authentication
                FirebaseAuth.getInstance().signOut();

                // Redirect the user to the SignupActivity
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    // Method to show the admin password dialog
    private void showAdminPasswordDialog() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_password, null);
        builder.setView(dialogView);

        // Get the EditText and Button from the dialog layout
        final EditText adminPasswordInput = dialogView.findViewById(R.id.admin_password_input);
        Button submitButton = dialogView.findViewById(R.id.submit_button);

        // Create the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Handle the submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = adminPasswordInput.getText().toString();

                // Check if the entered password matches the predefined admin password
                if (enteredPassword.equals(ADMIN_PASSWORD)) {
                    alertDialog.dismiss();  // Close the dialog
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    // Show an error message
                    Toast.makeText(MainActivity.this, "Invalid Admin Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
