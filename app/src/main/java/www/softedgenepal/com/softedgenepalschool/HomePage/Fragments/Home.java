package www.softedgenepal.com.softedgenepalschool.HomePage.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomMessage.ShowMessageInSnackBar;
import www.softedgenepal.com.softedgenepalschool.HomePage.TypeOfHomPage.SchoolHomePage;
import www.softedgenepal.com.softedgenepalschool.R;

import static www.softedgenepal.com.softedgenepalschool.MainActivity.userType;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


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
            view = inflater.inflate(R.layout.fragment_home, container, false);
            showMessage("Student");
        }
        return view;
    }


    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

}
