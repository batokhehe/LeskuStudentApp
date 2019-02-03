package id.co.lesku.data.remote.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;

import id.co.lesku.manager.ConfigManager;
import id.co.lesku.manager.PrefManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {
    // TODO: define your own base url
    private static final String               BASE_URL   = ConfigManager.BASE_URL;
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static final Gson gson       = new GsonBuilder()
            .registerTypeAdapterFactory(new DataTypeAdapterFactory()).create();
    private static final Retrofit.Builder     sBuilder   = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static Retrofit sRetrofit;

    public static <S> S createService (Class<S> serviceClass, Context context)
    {
        final String authHeader;
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getAppUserToken() != null)
        {
            authHeader = "Bearer " + prefManager.getAppUserToken();
        }
        else
        {
            authHeader = "";
        }

        httpClient.addInterceptor(new ResponseInterceptor());
        httpClient.addInterceptor(new ChuckInterceptor(context));
        //add authorization header
        httpClient.addInterceptor(new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request lOriginalRequest = chain.request();
                Request lRequest = lOriginalRequest.newBuilder().header("Authorization", authHeader)
                        .method(lOriginalRequest.method(), lOriginalRequest.body()).build();

                return chain.proceed(lRequest);
            }
        });

        OkHttpClient lClient = httpClient.build();
        sRetrofit = sBuilder.client(lClient).build();
        return sRetrofit.create(serviceClass);
    }
}
