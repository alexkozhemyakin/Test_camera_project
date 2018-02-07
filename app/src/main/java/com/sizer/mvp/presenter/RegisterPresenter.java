package com.sizer.mvp.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sizer.App;
import com.sizer.model.ApiResponse;
import com.sizer.model.entity.SizerUser;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.mvp.view.RegisterView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

}
