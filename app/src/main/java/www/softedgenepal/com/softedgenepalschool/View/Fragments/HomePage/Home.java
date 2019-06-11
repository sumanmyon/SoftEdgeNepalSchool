package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.SchoolHomePage;
import www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage.TypeOfHomPage.StudentHomePage;
import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.userType;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    StudentHomePage studentHomePage;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        if(userType.equals("school")){
            view = inflater.inflate(R.layout.school, container, false);
            new SchoolHomePage(getActivity(),view).setView();
            showMessage("School");
        }else if(userType.equals("teacher")){
            view = inflater.inflate(R.layout.fragment_home, container, false);
            showMessage("Teacher");
        }else if(userType.equals("student")){
            view = inflater.inflate(R.layout.user_profile, container, false);
            setHasOptionsMenu(true);
            studentHomePage = new StudentHomePage(getActivity(), view);
            studentHomePage.setView();
            showMessage("Student");
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        if(userType.equals("school")){
            menu.setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("parent")){
            menu.setGroupVisible(R.id.group_for_student, false);
        }else if(userType.equals("student")){
            menu.setGroupVisible(R.id.group_for_student, true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo for menu items
        if(userType.equals("student")){
            studentHomePage.siblingMenu(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
