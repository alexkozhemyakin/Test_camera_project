package com.sizer.mvp.model.repository;

import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.model.entity.SizerUser;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.MultipartBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;


public class RemoteDataRepository implements IRemoteRepository {

    private SizerApi sizerApi;

    public RemoteDataRepository(Retrofit retrofit) {
        sizerApi = retrofit.create(SizerApi.class);
    }

    @Override
    public Call<ApiResponse<Version>> getVersion() {
        Call<ApiResponse<Version>> voidCall = sizerApi.checkVersion();
        return voidCall;
    }

    @Override
    public Call<ApiResponse<SizerUser>> saveUser(SizerUser user) {
        return sizerApi
            .saveUser(user.getName(), user.getEmail(), user.getPassword(), user.getGender());
    }

    @Override
    public Observable<ApiResponse<SizerUser>> saveUserRx(String name, String email, String password,
        String gender, String height, String manualFolder) {
        return sizerApi
                .saveUserRx(name, email,password,gender,height, manualFolder);
    }

    @Override
    public Call<ApiResponse<SizerUser>> saveFullUser(Map<String, String> mappedUser) {
        return sizerApi.saveFullUser(mappedUser);
    }


    @Override
    public Observable<ApiResponse<JSONObject>> uploadScan(MultipartBody.Part image, String imageId,
        String userId, String scanId) {
        return sizerApi.uploadScan(image, imageId, userId, scanId);
    }

}
