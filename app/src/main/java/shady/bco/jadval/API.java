package shady.bco.jadval;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {
    public static final String BASE_URL = "http://erfanghaderi.ir/";
    public static Retrofit myRetrofit = null;

    public static Retrofit getAPI() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).build();


        if (myRetrofit == null) {
            myRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return myRetrofit;

    }
}