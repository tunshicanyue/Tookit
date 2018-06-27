package com.xb.canyue;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2018/5/30.
 */

public interface ApiTestService {

    /*刷新Token*/
    @FormUrlEncoded
    @POST("refreshToken")
    Observable<RefreshTokenBean> refreshToken(@Field("keys") String value);

}
