package www.softedgenepal.com.softedgenepalschool.View.Fragments.HomePage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.softedgenepal.com.softedgenepalschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification extends Fragment {


    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

}
