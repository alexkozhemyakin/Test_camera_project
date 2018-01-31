package com.sizer.data.repo;

import com.sizer.data.IRemoteRepository;
import com.sizer.model.ApiResponse;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class RemoteDataRepository implements IRemoteRepository {

    private Retrofit retrofit;

    public RemoteDataRepository(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Call<Void> getVersion() {
        return retrofit.create(SizerApi.class).checkVersionEmpty();
    }

    @Override
    public Observable<ApiResponse<JSONObject>> saveUser(Map<String, String> params) {
        return retrofit.create(SizerApi.class).saveUSer(params);
    }

    @Override
    public Observable<ApiResponse<JSONObject>> uploadScan(MultipartBody.Part image, String imageId, String userId, String scanId) {
        return retrofit.create(SizerApi.class).uploadScan(image, imageId, userId, scanId);
    }
}
