package com.sizer.data.repo;


import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.model.entity.SizerUser;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.MultipartBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<ApiResponse<SizerUser>> saveFullUser(@QueryMap Map<String, String> mappedUser);


    @Multipart
    @POST("bp/images/postscan")
    Observable<ApiResponse<JSONObject>> uploadScan(@Part MultipartBody.Part image,
        @Query("imageId") String imageId,
        @Query("userId") String userId, @Query("scanId") String scanId);
}
