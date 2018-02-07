package com.sizer.mvp.model;


import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.model.entity.SizerUser;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.MultipartBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;


public interface IRemoteRepository {

    /**
     * Fetch remote version
     */
    Call<ApiResponse<Version>> getVersion();

    /**
     * Create/save user
     */
    Call<ApiResponse<SizerUser>> saveUser(SizerUser user);

    /**
     * Create/save user
     */
    Observable<ApiResponse<SizerUser>> saveUserRx(String name, String email, String password, String gender, String height);

    /**
     * Upload scan image
     */
    Observable<ApiResponse<JSONObject>> uploadScan(MultipartBody.Part image, String imageId,
        String userId, String scanId);

    /**
     * Create/save full user information
     */
    Call<ApiResponse<SizerUser>> saveFullUser(Map<String, String> mappedUser);

}
