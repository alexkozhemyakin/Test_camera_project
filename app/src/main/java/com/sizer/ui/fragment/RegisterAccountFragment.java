package com.sizer.ui.fragment;

import android.widget.EditText;
import android.widget.RadioGroup;

import com.sizer.R;
import com.sizer.model.entity.Gender;
import com.sizer.model.entity.SizerUser;
import com.sizer.ui.activity.BaseActivity;
import com.wonderkiln.camerakit.CameraView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterAccountFragment extends BaseFragment {

    @OnClick(R.id.btn_save)
    void callGetStarted() {
        BaseActivity activity = (BaseActivity) getActivity();
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

        activity.showMessage("Not implemented yet.");
    }

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

    @Override
    int getLayoutResource() {
        return R.layout.account_register;
    }
}
