package android.liujs.com.testretrofitandrxjava;

import android.liujs.com.bean.LatestStories;
import android.liujs.com.bean.Stories;
import android.liujs.com.bean.StoryContent;
import android.liujs.com.http.RetrofitManager;
import android.liujs.com.http.TestService;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //requestProjectDataFromZhihu();
      //  requestDataUseObserver();
        testFlatMap();
    }

    /**
     * 不用RXJava时的用法
     */
    private void requestProjectDataFromZhihu(){
        TestService mTestService =  RetrofitManager.getRetrofitInstance().create(TestService.class);
        Call<LatestStories> call = mTestService.callLatestStories();
        call.enqueue(new Callback<LatestStories>() {
            @Override
            public void onResponse(Call<LatestStories> call, Response<LatestStories> response) {
                mTextMessage .setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<LatestStories> call, Throwable t) {
                mTextMessage .setText("错误信息："+ t.getMessage());
            }
        });
    }

    /**
     *    RXJava map 的用法
     */

    private void requestDataUseObserver(){
        //请求成功打印顺序  onSubscribe --  onNext -- onComplete
        TestService mTestService =  RetrofitManager.getRetrofitInstance().create(TestService.class);
         mTestService.getLatestStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .map(new Function<LatestStories,List<Stories>>(){
             @Override
             public List<Stories> apply(@io.reactivex.annotations.NonNull LatestStories latestStories) throws Exception {
                 return latestStories.getStories();
             }
          }).map(new Function<List<Stories>, String>() {
             @Override
             public String apply(@io.reactivex.annotations.NonNull List<Stories> stories) throws Exception {
                 return stories.get(0).getTitle();
             }
          }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Observer","onSubscribe");
                    }

                    @Override
                    public void onNext(String latestStories) {
                        mTextMessage .setText(latestStories);
                        Log.d("Observer","onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Observer","onError");
                    }

                    @Override
                    public void onComplete() {
                      Log.d("Observer","onComplete");
                    }
                });
    }

    /**
     *  RXJava fliMap的用法 和 嵌套请求的实现
     *  线程切换：
     *  observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，
     *  只要在每个想要切换线程的位置调用一次 observeOn() 即可,不同于 observeOn() ，
     *  subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的
     *  例如：
     *   Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
         .subscribeOn(Schedulers.io())
         .observeOn(Schedulers.newThread())
         .map(mapOperator) // 新线程，由 observeOn() 指定
         .observeOn(Schedulers.io())
         .map(mapOperator2) // IO 线程，由 observeOn() 指定
         .observeOn(AndroidSchedulers.mainThread)
         .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定
     */
    public void testFlatMap(){
       final TestService testService =  RetrofitManager.getRetrofitInstance().create(TestService.class);
        testService.getLatestStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //从LatestStories - Stories[] - Stories ，进行返回对象的平铺
                .flatMap(new Function<LatestStories, Observable<Stories>>() {
                    @Override
                    public Observable<Stories> apply(@io.reactivex.annotations.NonNull LatestStories latestStories) throws Exception {
                       Log.d("Observable","Thread Name=" + Thread.currentThread().getName()+"Thread id="+Thread.currentThread().getId());
                        return Observable.fromArray(latestStories.getStories().toArray(new Stories[latestStories.getStories().size()]));
                    }
                })//map 专门进行返回类型的变换
                .map(new Function<Stories, Integer>() {
                    @Override
                    public Integer apply(@io.reactivex.annotations.NonNull Stories stories) throws Exception {
                        Log.d("Observable","Thread Name=" + Thread.currentThread().getName()+"Thread id="+Thread.currentThread().getId());
                        return stories.getId();
                    }
                })//flatMap还可以进行嵌套请求，嵌套的请求要指定IO线程，否则报android.os.NetworkOnMainThreadException
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<StoryContent>>() {
                    @Override
                    public Observable<StoryContent> apply(@io.reactivex.annotations.NonNull Integer storyId) throws Exception {
                        return testService.getStoryContent(storyId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryContent>() {
                    StringBuilder stringBuilder = new StringBuilder();
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.d("Observer","onSubscribe");
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull StoryContent s) {
                        stringBuilder.append( s.getBody()+ "\n\n");
                        Log.d("Observer","onNext ,resultData:"+s.getBody());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d("Observer","onError"+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Observer","onComplete");
                        mTextMessage .setText(stringBuilder);
                    }
                });
    }


}
