package www.softedgenepal.com.softedgenepalschool.Model.URLs;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.FakeApi.StudentApi;

public class URL {
    Context context;
    private String studentUrl;

    public URL(Context context) {
        this.context = context;
        studentUrl = new StudentApi(context).getS();
    }

    public String getStudentUrl() {
        return studentUrl;
    }
}
