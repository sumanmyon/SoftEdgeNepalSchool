package www.softedgenepal.com.softedgenepalschool.View.Fragments.ReportCard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.ReportCardCache;
import www.softedgenepal.com.softedgenepalschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Grade extends Fragment {
    public Grade() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_percentage, container, false);
    }
}
