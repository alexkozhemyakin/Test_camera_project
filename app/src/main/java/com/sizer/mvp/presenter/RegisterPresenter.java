package com.sizer.mvp.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sizer.App;
import com.sizer.model.ApiResponse;
import com.sizer.model.entity.SizerUser;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.mvp.view.RegisterView;
import com.sizer.util.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@InjectViewState
public class RegisterPresenter extends MvpPresenter<RegisterView> {
    public static final String TAG = "REGISTER_PRESENTER";
    @Inject
    ILocalRepository localRepository;

    @Inject
    IRemoteRepository remoteRepository;

    public RegisterPresenter() {
        App.getAppComponent().inject(this);
    }

    public void callRegister(String email, String password,
                             String name, String height, String gender) {
        getViewState().showLoading(true);
        remoteRepository.saveUserRx(name, email, password, gender, height)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SizerUser>>() {
                    @Override
                    public void accept(ApiResponse<SizerUser> sizerUserApiResponse) throws Exception {
                        getViewState().showLoading(false);
                        if (sizerUserApiResponse.getResultCode().equals("Error")) {
                            getViewState().showLoading(false);
                            getViewState().showMessage(sizerUserApiResponse.getMessage());
                            return;
                        }
                        localRepository.setSizerUser(sizerUserApiResponse.getData());
                        getViewState().onSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getViewState().showLoading(false);
                        getViewState().showMessage(throwable.getMessage());
                    }
                });
    }

    public void callUpload() {
        Observable.zip(getUplodsList(), new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Exception {
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getViewState().onUploaded();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getViewState().showMessage(throwable.getMessage());
                        Log.d("RETROFIT", throwable.getMessage());
                    }
                });
    }

    private List<Observable<ApiResponse<JSONObject>>> getUplodsList() {
        List<Observable<ApiResponse<JSONObject>>> requests = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry: localRepository.getScanList().entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue());
            requests.add(remoteRepository.uploadScan(MultipartBody.Part.create(body),entry.getKey(),
                    localRepository.getSizerUser().getUserID(),localRepository.getScanData().getScanId()));
        }
        return requests;
    }

}
