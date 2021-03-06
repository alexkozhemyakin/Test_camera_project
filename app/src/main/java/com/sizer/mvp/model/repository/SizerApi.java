package com.sizer.mvp.model.repository;


import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.model.entity.SizerUser;

import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.MultipartBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SizerApi {

    @GET("bpp/user/checkVersionEx?version=0.1&platform=android&locale=us&language=he")
    Call<ApiResponse<Version>> checkVersion();

    @POST("bpp/user/save")
    Call<ApiResponse<SizerUser>> saveUser(@Query("name") String name,
        @Query("email") String email,
        @Query("password") String password,
        @Query("gender") String gender);

    @POST("bpp/user/save")
    Observable<ApiResponse<SizerUser>> saveUserRx(@Query("name") String name,
        @Query("email") String email,
        @Query("password") String password,
        @Query("gender") String gender,
        @Query("height") String height,
        @Query("manualFolder") String manualFolder);


    @POST("bpp/user/save")
    Observable<ApiResponse<SizerUser>> saveFullUserRx(@QueryMap Map<String, String> mappedUser);


    @POST("bpp/user/save")
    Call<ApiResponse<SizerUser>> saveFullUser(@QueryMap Map<String, String> mappedUser);


    @POST("bpp/user/save")
    Observable<SizerUser> saveUserByEntity(
        @Field("email") String email,
        @Field("password") String password,
        @Field("name") String name,
        @Field("gender") String gender,
        @Field("measurementUnit") String measurementUnit,
        @Field("promotionCode") String promotionCode,
        @Field("measurmentsJson") String measurmentsJson);


    @FormUrlEncoded
    @POST("bpp/user/save")
    Observable<SizerUser> saveUserByMap(@FieldMap Map<String, String> user);

    @POST("bpp/user/save?{userEntity}")
    Observable<SizerUser> saveUserByString(@Path("userEntity") String userFullUrl);


    @POST("bpp/user/save")
    Observable<SizerUser> saveUserByEntity(@Body SizerUser user);


    @Multipart
    @POST("bp/images/postscan")
    Completable uploadScan(@Part MultipartBody.Part image,
                           @Query("imageId") String imageId,
                           @Query("userId") String userId, @Query("scanId") String scanId);
}
