package com.sizer.ui.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.sizer.App;
import com.sizer.R;
import com.sizer.data.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.entity.Gender;
import com.sizer.model.entity.SizerUser;
import com.sizer.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAccountFragment extends BaseFragment {

    @BindView(R.id.account_name_tv)
    EditText accountName;
    @BindView(R.id.account_email_et)
    EditText accountEmail;
    @BindView(R.id.account_pass_et)
    EditText pass;
    @BindView(R.id.account_pass_confirm_et)
    EditText passConfirm;

    @BindView(R.id.radioSex)
    RadioGroup genderGroup;

    private IRemoteRepository remoteRepository = App.getAppComponent().remoteDataRepository();


    @OnClick(R.id.btn_save)
    void callGetStarted() {
        final BaseActivity activity = getBaseActivity();
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (!pass.getText().toString().equals(passConfirm.getText().toString())) {
            activity.showMessage("Password doesn't match confirm password.");
            return;
        }
        if (accountName.getText().toString().isEmpty()) {
            activity.showMessage("Empty name.");
            return;
        }
        if (accountEmail.getText().toString().isEmpty()) {
            activity.showMessage("Empty email.");
            return;
        }

        SizerUser sizerUser = new SizerUser();
        sizerUser.setName(accountName.getText().toString());
        sizerUser.setPassword(pass.getText().toString());
        sizerUser.setEmail(accountEmail.getText().toString());
        sizerUser.setGender(
                genderGroup.getCheckedRadioButtonId() == R.id.radioMale ?
                        Gender.MALE : Gender.FEMALE);

        remoteRepository.saveUser(sizerUser).enqueue(new Callback<ApiResponse<SizerUser>>() {
            @Override
            public void onResponse(Call<ApiResponse<SizerUser>> call, Response<ApiResponse<SizerUser>> response) {
                ApiResponse<SizerUser> body = response.body();
                if (body.getResultCode().equalsIgnoreCase("ERROR")) {
                    activity.showMessage(body.getMessage());
                    showDoneFragment();
                } else {
                    activity.showMessage("Saved Successfully.");
                    showDoneFragment();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<SizerUser>> call, Throwable t) {
                activity.showMessage("Saved Failed.");
                showDoneFragment();
            }
        });


    }

    @Override
    int getLayoutResource() {
        return R.layout.account_register;
    }

    private void showDoneFragment() {
        LinearLayout lyt = (LinearLayout) getActivity().findViewById(R.id.registerAccount_lt);
        lyt.removeAllViews();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.registerAccount_lt, new RegisterAccountSuccessFragment());
        ft.commit();

    }
}
