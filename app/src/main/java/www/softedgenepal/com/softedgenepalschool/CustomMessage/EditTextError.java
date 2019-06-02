package www.softedgenepal.com.softedgenepalschool.CustomMessage;

import android.graphics.drawable.Drawable;
import android.widget.EditText;

import www.softedgenepal.com.softedgenepalschool.R;

public class EditTextError {
    public void setErrorForEditText(EditText editText, String error){
        editText.setError(error);
    }

    public void setErrorForEditText(EditText editText, String error, Drawable drawable) {
        editText.setError(error, drawable);
    }
}
