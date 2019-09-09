package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.internal.Util;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.FacultyClassDetailModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.HomeWorkModel;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.Teacher.TeacherDataStore;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.EditTeacherHomework;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TeacherHomeworkAdapter extends RecyclerView.Adapter<TeacherHomeworkAdapter.ViewHolder> {
    private Context context;
    private List<FacultyClassDetailModel> facultyClassDetailModels;
    private List<HomeWorkModel> homeWorkModelList;

    public TeacherHomeworkAdapter(Context context, List<HomeWorkModel> homeWorkModelList) {
        this.context=context;
        this.homeWorkModelList = homeWorkModelList;

        offline();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_teacher_homework_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeWorkModel model = homeWorkModelList.get(i);
        String facultyCode = model.FacultyCode;
        String classCode = model.ClassCode;
        String sectionCode = model.SectionCode;
        String subjectCode = model.SubjectCode;

        //faculty
        for (int j = 0; j < facultyClassDetailModels.size() ; j++) {
            if(facultyClassDetailModels.get(j).FacultyCode.equals(facultyCode)){
                viewHolder.facultyTextView.setText(facultyClassDetailModels.get(j).FacultyName);

                //class
                List<FacultyClassDetailModel.ClassSectionSubDetail> sectionSubDetail =  facultyClassDetailModels.get(j).ClassSectionSubDetail;
                for (int k = 0; k <sectionSubDetail.size() ; k++) {
                    if(sectionSubDetail.get(k).ClassCode.equals(classCode)){

                        //section
                        List<FacultyClassDetailModel.SectionList> sectionLists = facultyClassDetailModels.get(j).ClassSectionSubDetail.get(k).SectionList;
                        for (int l = 0; l < sectionLists.size() ; l++) {
                            if(sectionLists.get(l).SectionCode.equals(sectionCode)){
                                viewHolder.classSectionTextView.setText(sectionSubDetail.get(k).ClassName+", "+ sectionLists.get(l).SectionName);
                            }
                        }

                        //subject
                        List<FacultyClassDetailModel.SubjectList> subjectLists = facultyClassDetailModels.get(j).ClassSectionSubDetail.get(k).SubjectList;
                        for (int l = 0; l < subjectLists.size() ; l++) {
                            if(subjectLists.get(l).SubjectCode.equals(subjectCode)){
                                viewHolder.subjectTextView.setText(subjectLists.get(k).SubjectName);
                            }
                        }
                    }
                }
            }
        }

        String createDate = model.CreatedDate;
        if(!createDate.equals("") || createDate != null || !createDate.equals("null")) {
            viewHolder.createDateTextView.setText(DateTime.DateConvertToNepali(createDate, "date"));
        }

        String deadline = model.Deadline;
        if(deadline != null) {
            viewHolder.deadlineTextView.setText(DateTime.DateConvertToNepali(deadline, "date"));
        }

        viewHolder.homeWorkTextView.setText(Html.fromHtml(model.Homework));

        viewHolder.collapseButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.isCollapse){
                    viewHolder.isCollapse = false;
                    viewHolder.collapseView.setVisibility(View.GONE);
                    viewHolder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
                    viewHolder.imageButton.setColorFilter(context.getResources().getColor(R.color.green_500));
                }else {
                    viewHolder.isCollapse = true;
                    viewHolder.collapseView.setVisibility(View.VISIBLE);
                    viewHolder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus));
                    viewHolder.imageButton.setColorFilter(context.getResources().getColor(R.color.red_500));
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, EditTeacherHomework.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("homeWork", model);
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeWorkModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected boolean isCollapse = false;
        protected View collapseButtom, collapseView;
        protected ImageButton imageButton;

        protected TextView subjectTextView, facultyTextView, classSectionTextView, createDateTextView, deadlineTextView, homeWorkTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collapseView = itemView.findViewById(R.id.thomeWorklyl);
            collapseButtom = itemView.findViewById(R.id.materialRippleLayout);
            imageButton = itemView.findViewById(R.id.clopaseButton);

            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            facultyTextView = itemView.findViewById(R.id.facultyTextView);
            classSectionTextView = itemView.findViewById(R.id.classSectionTextView);
            createDateTextView = itemView.findViewById(R.id.createDateTextView);
            deadlineTextView = itemView.findViewById(R.id.deadlineTextView);
            homeWorkTextView = itemView.findViewById(R.id.homeWorkTextView);
        }
    }

    private void offline() {
        Type type = new TypeToken<List<FacultyClassDetailModel>>() {
        }.getType();
        facultyClassDetailModels = (List<FacultyClassDetailModel>) TeacherDataStore.FacultyClassDetail.get(context, type);
    }

    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
