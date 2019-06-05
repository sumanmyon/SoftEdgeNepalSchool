package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.RequestDataForStudent;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;

public class StudentHomePagePresenter implements Contractor.Presenter {
    StudentHomePage studentHomePage;
    RequestDataForStudent requestDataForStudent;

    public StudentHomePagePresenter(StudentHomePage studentHomePage) {
        this.studentHomePage=studentHomePage;
        requestDataForStudent = new RequestDataForStudent(this);
    }

    public void getData() {
        requestDataForStudent.requestData();
    }

    @Override
    public void handleMessage(String message) {
        studentHomePage.setMessage(message);
    }

    @Override
    public Context getContext() {
        return studentHomePage.getContext();
    }
}
