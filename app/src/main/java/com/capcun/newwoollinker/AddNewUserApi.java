package com.capcun.newwoollinker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddNewUserApi {

    @FormUrlEncoded

    // register new user
    @POST("add_new_user_api.php")
    Call<String> addNewUser(
            @Field("name") String name_dt,
            @Field("mail") String mail_dt,
            @Field("contact") String contact_dt,
            @Field("role") String designation_dt,
            @Field("password") String password_dt

    );

    @FormUrlEncoded
    @POST("store_msg.php")
    Call<String> sendMsg(
            @Field("number") String number,
            @Field("msg") String msg
    );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<String> userLogin(
            @Field("number") String number,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("fetch_msg.php")
    Call<List<msgModelclass>>fetchChat(
            @Field("number") String number
    );

    @FormUrlEncoded
    @POST("user_profile.php")
    Call<List<UserProfileModelClass>>getUserdata(
            @Field("number") String number
    );
}
