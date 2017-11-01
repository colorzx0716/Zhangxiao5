package com.bawie.zhangxiao5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //图片路径
        File file = new File("data/data/ac.jpg");
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceApi service=retrofit.create(InterfaceApi.class);
        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<ResponseBody> call=service.uploadImage("146",body);
        call.enqueue(new Callback<ResponseBody>() {
            private String string;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response!=null&&response.isSuccessful())
                {
                    try {
                        string = response.body().string();
                        System.out.println("success"+string);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("onFailure");
            }
        });

    }
}
