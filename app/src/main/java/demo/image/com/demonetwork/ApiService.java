package demo.image.com.demonetwork;

import demo.image.com.demonetwork.entity.PublicEntity;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Home on 2018/8/9.
 */

public interface ApiService {

    @POST("V1/picture/get_turn_picture.shtml")
    Observable<PublicEntity> getMovieListData(@Body RequestBody body);
    @POST("area")
    Observable<PublicEntity> area(@Body RequestBody body);
}
