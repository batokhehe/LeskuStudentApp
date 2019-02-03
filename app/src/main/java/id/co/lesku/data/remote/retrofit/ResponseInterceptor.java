package id.co.lesku.data.remote.retrofit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import id.co.lesku.LeskuApplication;
import okhttp3.Interceptor;
import okhttp3.Response;


public class ResponseInterceptor implements Interceptor
{
    private LeskuApplication leskuApplication;
    private Context       mContext;

    public ResponseInterceptor()
    {
        leskuApplication = LeskuApplication.getInstance();
        mContext = null;
    }

    public ResponseInterceptor(Context context)
    {
        leskuApplication = LeskuApplication.getInstance();
        mContext = context;
    }

    @Override
    public Response intercept (Chain chain) throws IOException
    {
        Response lResponse = chain.proceed(chain.request());
        // TODO: implement your intercept logic below
        if (lResponse.code() == 200)
        {
            // unauthorized
            Log.d("Success", "200");
        }
        else if (lResponse.code() == 401)
        {
            // unauthorized
            Log.d("Unauthorized", "401");
        }
        else if (lResponse.code() == 403)
        {
            // forbidden
            Log.d("Forbidden", "403");
        }
        else if (lResponse.code() == 404)
        {
            // endpoint not found
            Log.d("Endpoint Not Found", "404");
        }
        else if (lResponse.code() == 500)
        {
            // internal server error
            Log.d("Internal Server Error", "500");
        }
        else if (lResponse.code() == 502)
        {
            // bad gateway
            Log.d("Bad Gateway", "502");
        }
        else if (lResponse.code() == 503)
        {
            // service unavailable
            Log.d("Service Unavailable", "503");
        }
        else if (lResponse.code() == 504)
        {
            // gateway timeout
            Log.d("Gateway Timeout", "504");
        }

        return lResponse;
    }
}
