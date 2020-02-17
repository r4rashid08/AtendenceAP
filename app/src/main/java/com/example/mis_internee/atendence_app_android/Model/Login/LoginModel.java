
package com.example.mis_internee.atendence_app_android.Model.Login;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel implements Serializable {

    @SerializedName("Result")
    @Expose
    private List<LoginResult> loginResult = null;

    public List<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(List<LoginResult> loginResult) {
        this.loginResult = loginResult;
    }

}
