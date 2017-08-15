package android.liujs.com.http;

import android.liujs.com.bean.LatestStories;
import android.liujs.com.bean.StoryContent;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujs on 2017/3/21.
 * 邮箱：725459481@qq.com
 */

public interface TestService {

    @GET("api/4/news/latest")
    Observable<LatestStories> getLatestStories();

    @GET("api/4/news/latest")
    Call<LatestStories> callLatestStories();

    @GET("/api/4/news/{id}")
    Observable<StoryContent> getStoryContent(@Path("id") int id);
}
