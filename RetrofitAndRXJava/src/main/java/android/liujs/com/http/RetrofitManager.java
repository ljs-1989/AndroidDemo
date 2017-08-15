package android.liujs.com.http;

import android.app.DownloadManager;
import android.liujs.com.testretrofitandrxjava.BuildConfig;
import android.os.Build;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.okhttp.FormEncodingBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujs on 2017/3/21.
 * 邮箱：725459481@qq.com
 */

public class RetrofitManager {

     private static  OkHttpClient initOkHttp(){
        final int DEFAULT_TIMEOUT = 10;
         OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
         //设置超时
         mOkHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
         mOkHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
         mOkHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

         //设置请求头
         mOkHttpClientBuilder.addInterceptor(new Interceptor() {
             @Override
             public Response intercept(Chain chain) throws IOException {
             Request request = chain.request().newBuilder()
                         .addHeader("PLAFORM","Android")
                         .addHeader("DEVICENAME", Build.MANUFACTURER + " " + Build.MODEL)
                         .addHeader("Accept", "application/json; q=0.5")
                         .addHeader("Accept", "application/vnd.github.v3+json")
                         .build();
                 return chain.proceed(request);
             }
         });
       /*  //设置API通用参数,以及效验参数
         mOkHttpClientBuilder.addInterceptor(new Interceptor() {
             @Override
             public Response intercept(Chain chain) throws IOException {
                 Request request = chain.request();
                 Request.Builder requestBuilder = request.newBuilder();
                 FormBody.Builder formBodyBuilder = new FormBody.Builder();
                 FormBody formBody = formBodyBuilder
                         .add(entry.getKey(), entry.getValue())
                         .add()
                         .build();

//                postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  RetrofitUtils.bodyToString(formBody);
               String  postBodyString = RetrofitUtils.bodyToString(formBody);
                 request = requestBuilder
                         .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
                         .build();
                 return chain.proceed(request);
             }
         });*/

         //调试模式下设置Log日志
         if(BuildConfig.DEBUG){
             HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
             interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
             mOkHttpClientBuilder.addInterceptor(interceptor);
         }
         return  mOkHttpClientBuilder.build();
     }

   private static Retrofit mRetrofit;
    public static Retrofit getRetrofitInstance(){
        if(mRetrofit==null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://news-at.zhihu.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(initOkHttp())
                    .build();
        }

        return  mRetrofit;
    }
}
