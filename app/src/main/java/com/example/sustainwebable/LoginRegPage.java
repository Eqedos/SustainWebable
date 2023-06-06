package com.example.sustainwebable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegPage extends BaseActivity {
    private Button mRegister,mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg_page);
        Intent intent1 = getIntent();
        mRegister = (Button) findViewById(R.id.register);
        mLogin = (Button) findViewById(R.id.login);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegPage.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegPage.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}