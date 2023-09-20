package com.capcun.newwoollinker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    Spinner bld_spinner;
    EditText user_name, user_mail, user_contact, user_pass, confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        List<String> yourDataList = new ArrayList<>();
        yourDataList.add("Select Use Case");
        yourDataList.add("Wool Producer");
        yourDataList.add("Wool Purchaser");
        yourDataList.add("Service Provider");

        bld_spinner = findViewById(R.id.blood);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yourDataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bld_spinner.setAdapter(adapter);


        user_mail = findViewById(R.id.mail);
        user_name = findViewById(R.id.name);
        user_contact = findViewById(R.id.phone);
        user_pass = findViewById(R.id.pass);
        confirm_pass = findViewById(R.id.con_pass);
        //sign_up_btn = findViewById(R.id.add_rm);


        Button sign_up_btn = findViewById(R.id.sign_up);
         sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //retriving text from edittext's
                String name_dt = user_name.getText().toString().trim();
                String mail_dt = user_mail.getText().toString().trim();
                String contact_dt = user_contact.getText().toString().trim();
                String password = user_pass.getText().toString().trim();
                String password2 = confirm_pass.getText().toString().trim();
                String bld_grp_dt = bld_spinner.getSelectedItem().toString();



                if (mail_dt.isEmpty()) {
                    user_mail.setError("Please enter mail..!");
                    return;
                }

                if (name_dt.isEmpty()) {
                    user_name.setError("Please enter name..!");
                    return;
                }

                if (contact_dt.isEmpty()) {
                    user_contact.setError("Please enter contact details..!");
                    return;
                }

                if (bld_grp_dt.equals("Select Use Case")) {
                    Toast.makeText(SignUp.this, "Please select user role..!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    confirm_pass.setError("Please enter password..!");
                    return;
                }

                if (password2.isEmpty()) {
                    confirm_pass.setError("Please enter password..!");
                    return;
                }

                if (!password.equals(password2)) {
                    confirm_pass.setError("Please enter the same password..!");
                    return;
                }

                addNewUser(name_dt, mail_dt, contact_dt, password, bld_grp_dt);

            }
        });
    }

    private void addNewUser(String name_dt, String mail_dt, String contact_dt, String password, String bld_grp_dt) {

//        String desig = "RM";
//        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);
//        String manager = sharedPreferences.getString("mobile", "");

        Call<String> call = ApiControlller
                .getInstance()
                .getAddNewUserApiSet()
                .addNewUser(name_dt, mail_dt, contact_dt, bld_grp_dt,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res_msg = response.body();


                if (res_msg.equals("already_present")) {
                    Toast.makeText(SignUp.this, "User already present do sign in!!", Toast.LENGTH_SHORT).show();
                    user_contact.setText("");
                    user_contact.findFocus();
                } else if (res_msg.equals("success")) {

                    Toast.makeText(SignUp.this, "User " + name_dt + " is added successfully..!!", Toast.LENGTH_SHORT).show();
                    sendRegistrationSuccessNotification(name_dt);

                    Intent home_intent = new Intent(SignUp.this, MainActivity.class);
                    home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home_intent);
                } else {
                    Toast.makeText(SignUp.this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                    Intent home_intent = new Intent(SignUp.this, MainActivity.class);
                    home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home_intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SignUp.this, "Something went wrong please try again later !", Toast.LENGTH_SHORT).show();
                Intent home_intent = new Intent(SignUp.this, MainActivity.class);
                home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
            }
        });
    }


    private void sendRegistrationSuccessNotification(String name_dt) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle("Registration Successful")
                .setContentText("User " + name_dt + " is added successfully.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Create a unique notification ID
        int notificationId = (int) System.currentTimeMillis();

        // Show the notification
        notificationManager.notify(notificationId, builder.build());
    }


}

