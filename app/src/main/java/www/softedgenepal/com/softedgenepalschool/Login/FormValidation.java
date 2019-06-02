package www.softedgenepal.com.softedgenepalschool.Login;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage.EditTextError;

public class FormValidation implements View.OnClickListener, Validate{
    EditText editTextUserName;
    EditText editTextPassword;
    Drawable drawable;

    boolean user=false;
    boolean password=false;

    public FormValidation(EditText editTextUserName, EditText editTextPassword) {
        this.editTextUserName=editTextUserName;
        this.editTextPassword=editTextPassword;
    }

    public FormValidation(EditText editTextUserName, EditText editTextPassword, Drawable drawable) {
        this.editTextUserName=editTextUserName;
        this.editTextPassword=editTextPassword;
        this.drawable=drawable;
    }

    @Override
    public void onClick(View v) {
        //todo validating form
        if(TextUtils.isEmpty(editTextUserName.getText())){
            if(drawable!=null){
                new EditTextError().setErrorForEditText(editTextUserName,"Please type username", drawable);
            }else {
                new EditTextError().setErrorForEditText(editTextUserName,"Please type username");
            }
            user = false;
        }else {
            user = true;
        }

        if(TextUtils.isEmpty(editTextPassword.getText())){
            if(drawable!=null){
                new EditTextError().setErrorForEditText(editTextPassword,"Please type password",drawable);
            }else {
                new EditTextError().setErrorForEditText(editTextPassword,"Please type password");
            }
            password = false;
        }else {
            password = true;
        }

        if (user && password){
            //todo request to server and, validate and get/store token
            validateWithDataBase(editTextUserName.getText().toString(), editTextPassword.getText().toString());
        }
    }


    private void storeUserCredentials(){

    }

    @Override
    public void validateWithDataBase(String userName, String password) {

    }
}
