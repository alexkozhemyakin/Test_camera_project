package com.sizer.data.repo;


import com.sizer.model.ApiResponse;
import com.sizer.model.Version;


import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SizerApi {

    @GET("bpp/user/checkVersionEx")
    Call<Void> checkVersionEmpty();
    @GET("bpp/user/checkVersionEx")
    Observable<ApiResponse<Version>> checkVersion();

    @POST("bpp/user/save")
    Observable<ApiResponse<JSONObject>> saveUser(@Query("email") String email, @Query("password") String password,
                                                 @Query("name") String name, @Query("gender") String gender,
                                                 @Query("device")String device, @Query("measurementUnit") String measurementUnit,
                                                 @Query("promotionCode") String promotionCode, @Query("manualFolder") String manualFolder,
                                                 @Query("measurmentsJson") String measurmentsJson);
    @POST("bpp/user/save")
    Observable<ApiResponse<JSONObject>> saveUSer(@QueryMap Map<String, String> params);

    @Multipart
    @POST("bp/images/postscan")
    Observable<ApiResponse<JSONObject>> uploadScan(@Part MultipartBody.Part image, @Query("imageId") String imageId,
                                                   @Query("userId") String userId, @Query("scanId") String scanId);
}
