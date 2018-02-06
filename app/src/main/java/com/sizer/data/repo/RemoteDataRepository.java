package com.sizer.data.repo;

import com.sizer.data.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.model.entity.SizerUser;
import io.reactivex.Observable;
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
        return sizerApi.saveUser(user.getName(), user.getEmail(), user.getPassword(), user.getGender().name());
    }

    @Override
    public Observable<ApiResponse<JSONObject>> uploadScan(MultipartBody.Part image, String imageId,
        String userId, String scanId) {
        return sizerApi.uploadScan(image, imageId, userId, scanId);
    }

    @Override
    public Observable<SizerUser> saveUserByMap(SizerUser user) {
        return sizerApi.saveUserByMap(user.getMappedUser());
    }
}
