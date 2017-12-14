package com.example.huynhxuankhanh.minialbum.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dunarctic on 11/25/2017.
 */

public class SecurityConfigDialog extends Dialog {
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.confirm_password_et)
    EditText confirm_password_et;
    @BindView(R.id.security_delete)
    Switch security_delete;
    //@BindView(R.id.security_open)
    //Switch security_open;
    @BindView(R.id.apply)
    Button apply;
    @BindView(R.id.cancel)
    Button cancel;

    public SecurityConfigDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_password);
        ButterKnife.bind(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        security_delete.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext())
                .getBoolean("security_del", false));
//        security_open.setChecked(PreferenceManager.getDefaultSharedPreferences(getContext())
//                .getBoolean("security_open", false));
        password_et.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Utility.PASSWORD_KEY, ""));
        confirm_password_et.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Utility.PASSWORD_KEY, ""));

        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    apply.setEnabled(false);
                }
                if (password_et.getText().toString().equals
                        (confirm_password_et.getText().toString())) {
                    apply.setEnabled(true);
                } else {
                    apply.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        confirm_password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    apply.setEnabled(false);
                }
                if (password_et.getText().toString().equals
                        (confirm_password_et.getText().toString())) {
                    apply.setEnabled(true);
                } else {
                    apply.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @OnClick(R.id.cancel)
    public void close() {
        dismiss();
    }

    @OnClick(R.id.apply)
    public void apply() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString(Utility.PASSWORD_KEY, password_et.getText().toString());
        if (password_et.getText().toString().isEmpty()) {
            editor.putBoolean("security_del", false);
//            editor.putBoolean("security_open", false);
        } else {
            editor.putBoolean("security_del", security_delete.isChecked());
//            editor.putBoolean("security_open", security_open.isChecked());
        }
        editor.apply();
        dismiss();
    }
}
