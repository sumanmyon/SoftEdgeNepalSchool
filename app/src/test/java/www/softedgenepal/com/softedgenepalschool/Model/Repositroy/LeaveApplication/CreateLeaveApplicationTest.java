package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateLeaveApplicationTest {

    @Test
    public void createLeaveApp(){
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("Suman-Sick Leave");
        data.add("I want bed rest.");
        data.add("6/12/2017");
        data.add("6/12/2017");

        CreateLeaveApplication leaveApplication = new CreateLeaveApplication();
        String s = leaveApplication.data(data);

        assertEquals(s,"Successfully applied.");
    }
}