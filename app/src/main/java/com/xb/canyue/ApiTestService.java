package com.xb.canyue;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by admin on 2018/5/30.
 */

public interface ApiTestService {

    /*刷新Token*/
    @POST("refreshToken")
    Observable<RefreshTokenBean> refreshToken();

}
