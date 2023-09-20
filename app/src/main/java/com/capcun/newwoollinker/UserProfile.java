package com.capcun.newwoollinker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    final String sharedPreferencesFileTitle = "wool_linker";
    EditText ed1,ed2,ed3,ed4;

    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.phone);
        ed3 = findViewById(R.id.mail);
        ed4 = findViewById(R.id.role);

        ed1.setText(sharedPreferences.getString("name",""));
        ed2.setText(sharedPreferences.getString("number",""));
        ed3.setText(sharedPreferences.getString("mail",""));
        ed4.setText(sharedPreferences.getString("role",""));

        ed1.setEnabled(false);
        ed2.setEnabled(false);
        ed3.setEnabled(false);
        ed4.setEnabled(false);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                builder.setTitle("Confirm Logout");
                builder.setMessage("Are you sure you want to logout?");

                // Add buttons for positive (yes) and negative (no) responses
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Clear SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        // Redirect to MainActivity
                        Intent intent = new Intent(UserProfile.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the back stack
                        startActivity(intent);
                        finish(); // Close the current activity
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog (do nothing)
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });





    }
}