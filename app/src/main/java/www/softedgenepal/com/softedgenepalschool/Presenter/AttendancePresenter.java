package www.softedgenepal.com.softedgenepalschool.Presenter;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.AttendanceModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.IContractor;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Student.AttendanceActivity;

public class AttendancePresenter implements IContractor.Presenter {
    private AttendanceActivity attendanceActivity;
    private AttendanceModel model;

    public AttendancePresenter(AttendanceActivity attendanceActivity) {
        this.attendanceActivity = attendanceActivity;
        model = new AttendanceModel(this);
    }

    @Override
    public void setMessage(String message) {
        attendanceActivity.setMessage(message);
    }

    @Override
    public void setLog(String topic, String body) {
        attendanceActivity.setLog(topic, body);
    }

    @Override
    public Context getCalContext() {
        return attendanceActivity.getCalContext();
    }

    @Override
    public void getJsonData(String studentId) {
        model.getJsonData(studentId);
    }

    @Override
    public Map<String, String> getParams() {
        return attendanceActivity.getParams();
    }

    @Override
    public void setJsonData(JSONObject response) {
        attendanceActivity.setJsonData(response);
    }
}
