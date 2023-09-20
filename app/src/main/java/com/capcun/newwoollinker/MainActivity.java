package com.capcun.newwoollinker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class MainActivity extends AppCompatActivity {

    EditText number,pass;
    final String sharedPreferencesFileTitle = "wool_linker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);

        if (sharedPreferences.contains("number") & sharedPreferences.contains("password")) {
            String mobile = sharedPreferences.getString("number", "");
            String pass = sharedPreferences.getString("password", "");
            String role = sharedPreferences.getString("role", "");

            if (!mobile.equals("") && !pass.equals("")) {


                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                intent.putExtra("role", role);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }


        number = findViewById(R.id.userNumber);
        pass = findViewById(R.id.userPassword);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(number.getText().toString().isEmpty())
                     number.setError("Enter user number!");
                if(pass.getText().toString().isEmpty())
                    pass.setError("Enter user password!");

                userValidation(number.getText().toString(),pass.getText().toString());


            }
        });

        TextView signup = findViewById(R.id.sign_up_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent1);
            }
        });



//        eye.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (passwordVisible) {
//                    // Hide the password
//                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    eye.setImageResource(R.drawable.visible); // Change the eye symbol to closed state
//                } else {
//                    // Show the password
//                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    eye.setImageResource(R.drawable.hide); // Change the eye symbol to open state
//                }
//                passwordVisible = !passwordVisible; // Toggle the visibility flag
//            }
//        });
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Login.this,New_User.class);
//                startActivity(intent);
//
//            }
//        });




    }

    private void userValidation(String userlogins, String userPasswords) {

        String empid = "1";
        String emp_contact = userlogins, emp_pass = userPasswords;
        Toast.makeText(this, ""+emp_contact+" "+emp_pass, Toast.LENGTH_SHORT).show();
        Call<String> call = ApiControlller
                .getInstance()
                .getAddNewUserApiSet()
                .userLogin(emp_contact,emp_pass);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = response.body();



                if(data!=null) {
                    if (data.equals("success")) {
                        Call<List<UserProfileModelClass>> call2 = ApiControlller
                                .getInstance()
                                .getAddNewUserApiSet()
                                .getUserdata(emp_contact);
                        
                        call2.enqueue(new Callback<List<UserProfileModelClass>>() {
                            @Override
                            public void onResponse(Call<List<UserProfileModelClass>> call, Response<List<UserProfileModelClass>> response) {
                                List<UserProfileModelClass> userProfileModelClass = response.body();

                                if(userProfileModelClass!=null)
                                {
                                    for(UserProfileModelClass data : userProfileModelClass)
                                    {
                                        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("number", emp_contact);
                                        editor.putString("password", emp_pass);
                                        editor.putString("role",data.getUser_role());
                                        editor.putString("name",data.getUser_name());
                                        editor.putString("mail",data.getUser_mail());

                                        editor.apply();

                                        //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        intent.putExtra("role", data.getUser_role());
                                        startActivity(intent);

                                    }
                                }

                                
                            }

                            @Override
                            public void onFailure(Call<List<UserProfileModelClass>>call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        
                        
                    } else if (data.equals("passwordNot")) {
                        Toast.makeText(getApplicationContext(), "Oops! Admin not found...", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "Oops! Password is not valid", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong please try again later!", Toast.LENGTH_LONG).show();
            }
        });


    }

}