package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage;

import android.graphics.drawable.Drawable;
import android.widget.EditText;

public class EditTextError {
    public void setErrorForEditText(EditText editText, String error){
        editText.setError(error);
    }

    public void setErrorForEditText(EditText editText, String error, Drawable drawable) {
        editText.setError(error, drawable);
    }
}
