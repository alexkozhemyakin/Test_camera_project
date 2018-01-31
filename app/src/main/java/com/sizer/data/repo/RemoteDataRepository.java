package com.sizer.data.repo;

import com.sizer.data.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.entity.SizerUser;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class RemoteDataRepository implements IRemoteRepository {

    private SizerApi sizerApi;

    public RemoteDataRepository(Retrofit retrofit) {
        sizerApi = retrofit.create(SizerApi.class);
    }

    @Override
    public Call<Void> getVersion() {
        Call<Void> voidCall = sizerApi.checkVersionEmpty();
        return voidCall;
    }

    @Override
    public Observable<SizerUser> saveUser(SizerUser user) {
        return sizerApi.saveUser(user);
    }

    @Override
    public Observable<ApiResponse<JSONObject>> uploadScan(MultipartBody.Part image, String imageId, String userId, String scanId) {
        return sizerApi.uploadScan(image, imageId, userId, scanId);
    }
}
