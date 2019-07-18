package www.softedgenepal.com.softedgenepalschool.View.Fragments;


import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.RoutineCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private RoutineCache routineCache;

    private TextView titleTextView;
    private RecyclerView recyclerView;

    private final int GRID_SPAN = 1;

    public RoutineFragment() {
        // Required empty public constructor
    }
    public void setAssignment(final RoutineCache routineCache) {
        this.routineCache=routineCache;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_routine, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        Log.d("RoutineFrag","1");

        casting(view);

        titleTextView.setText(routineCache.ExamNameEng);
        setData();

        hideView(app_bar_layout);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    showView(app_bar_layout, getActionBarSize());
                    //hideView(lyt_profile);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hideView(app_bar_layout);
                    //showView(lyt_profile, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        (view.findViewById(R.id.routine_bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    private void setData() {
        Log.d("RoutineFrag","3");
        //todo setRecycler view
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), routineCache.routine.size()) {
            TextView subjectTextView, startTimeTextView, endTimeTextView;
            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routine_recycler_list, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                subjectTextView = itemView.findViewById(R.id.routine_listSubject);
                startTimeTextView = itemView.findViewById(R.id.routine_listStartTime);
                endTimeTextView = itemView.findViewById(R.id.routine_listEndTime);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                final RoutineCache.Routine routine = routineCache.routine.get(position);
                subjectTextView.setText(routine.SubjectNameEng);
                startTimeTextView.setText(routine.StartTime);
                endTimeTextView.setText(routine.EndTime);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void casting(View view){
        titleTextView = view.findViewById(R.id.routine2_textView_toolbar);
        app_bar_layout = view.findViewById(R.id.app_bar_layout);

        //todo for file download recycler view
        recyclerView = view.findViewById(R.id.routine_frag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        return (int) styledAttributes.getDimension(0, 0);
    }
}
