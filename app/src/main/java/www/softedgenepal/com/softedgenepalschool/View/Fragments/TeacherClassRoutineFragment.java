package www.softedgenepal.com.softedgenepalschool.View.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel2;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.TeacherClassRoutineDaysAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherClassRoutineFragment extends CustomFragment {
    private RecyclerView recyclerView;

    private String title = null;
    private TeacherClassRoutineModel2 model;

    public TeacherClassRoutineFragment() {
        // Required empty public constructor
    }

    public void setTitle(String examNameEng, TeacherClassRoutineModel2 teacherClassRoutineModel2) {
        this.title = examNameEng;
        this.model = teacherClassRoutineModel2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_class_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        casting(view);
        populateView();
    }

    private void populateView(){
        TeacherClassRoutineDaysAdapter adapter = new TeacherClassRoutineDaysAdapter(getActivity(), model);
        recyclerView.setAdapter(adapter);
    }

    private void casting(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);
    }
}
