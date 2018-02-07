package com.sizer.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.sizer.R;
import com.sizer.mvp.presenter.RegisterPresenter;
import com.sizer.mvp.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends MvpAppCompatActivity implements RegisterView {

    @BindView(R.id.layout_register)
    View layoutRegister;

    @BindView(R.id.layout_done)
    View layoutDone;

    @BindView(R.id.layout_uploading)
    View layoutUpload;

    @BindView(R.id.til_email)
    TextInputLayout til_email;

    @BindView(R.id.edit_email)
    TextInputEditText edit_email;

    @BindView(R.id.til_password)
    TextInputLayout til_password;

    @BindView(R.id.edit_password)
    TextInputEditText edit_password;

    @BindView(R.id.til_name)
    TextInputLayout til_name;

    @BindView(R.id.edit_name)
    TextInputEditText edit_name;

    @BindView(R.id.til_height)
    TextInputLayout til_height;

    @BindView(R.id.edit_height)
    TextInputEditText edit_height;

    @BindView(R.id.radio_male)
    RadioButton radio_male;

    @BindView(R.id.radio_female)
    RadioButton radio_female;

    @BindView(R.id.btn_create)
    Button btn_create;

    Snackbar loadingSnackbar;

    private boolean isLoading;

    @InjectPresenter(tag = RegisterPresenter.TAG)
    RegisterPresenter presenter;
    private int animTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        loadingSnackbar = Snackbar.make(findViewById(android.R.id.content),R.string.loading,Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) loadingSnackbar.getView();
        snack_view.addView(new ProgressBar(this));

        animTime = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        edit_height.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onFocusChange();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    @OnFocusChange({R.id.edit_email, R.id.edit_password, R.id.edit_name, R.id.edit_height})
    void onFocusChange() {
        if(edit_email.length()>0) {
            til_email.setErrorEnabled(false);
        }
        else { setFieldError(til_email,R.string.error_empty_field);}

        if(edit_password.length()>0) {
            if(edit_password.length()<7) {
                setFieldError(til_password,R.string.error_weak_password);
            }
            else {
                til_password.setErrorEnabled(false);
            }
        }
        else {
            setFieldError(til_password,R.string.error_empty_field);
        }

        if(edit_name.length()>0) til_name.setErrorEnabled(false);
        else {
            setFieldError(til_name,R.string.error_empty_field);
        }

        if(edit_height.length()>0) til_height.setErrorEnabled(false);
        else {
            setFieldError(til_height,R.string.error_empty_field);
        }

        btn_create.setEnabled(!checkFields()&&!isLoading);
    }

    @OnClick(R.id.btn_create)
    void onBtnCreate() {
        presenter.callRegister(edit_email.getText().toString(),edit_password.getText().toString(),
                edit_name.getText().toString(), edit_height.getText().toString(), getGender());
    }

    private void setFieldError(TextInputLayout til, int resId) {
        til.setErrorEnabled(true);
        til.setError(getString(resId));
    }

    private boolean checkFields() {
        return til_email.isErrorEnabled() || til_password.isErrorEnabled() || til_name.isErrorEnabled() || til_height.isErrorEnabled();
    }

    private String getGender() {
        return (radio_male.isChecked())?"male":"female";
    }


    @Override
    public void onSuccess() {
        layoutUpload.setVisibility(View.VISIBLE);
        presenter.callUpload();
    }

    @Override
    public void onUploaded() {
        layoutUpload.setVisibility(View.GONE);
        layoutDone.setAlpha(0f);
        layoutDone.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        layoutDone.animate()
                .alpha(1f)
                .setDuration(animTime)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        layoutRegister.animate()
                .alpha(0f)
                .setDuration(animTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutRegister.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content),msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(boolean state) {
        isLoading = state;
        if(state)
            loadingSnackbar.show();
        else loadingSnackbar.dismiss();
    }

    @OnClick(R.id.btn_done)
    void onClickDone() {
        onBackPressed();
    }
}
