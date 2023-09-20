package com.capcun.newwoollinker;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiControlller {

    // give url of your api folder
    static final String url = "https://capcun.com/wool_linker/";

    private static ApiControlller clientObject;

    // Create object of retrofit
    private static Retrofit retrofit;

    ApiControlller()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiControlller getInstance()
    {
        if(clientObject == null)
            clientObject = new ApiControlller();
        return clientObject;
    }

    // for verifying employee's credentials
//    VerifyLoginDataApi getVerifyLoginDataApi() {return retrofit.create(VerifyLoginDataApi.class);}


    AddNewUserApi getAddNewUserApiSet() {return retrofit.create(AddNewUserApi.class);}






}
