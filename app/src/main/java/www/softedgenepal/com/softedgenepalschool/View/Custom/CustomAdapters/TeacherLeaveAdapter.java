package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveApplicationModel;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.LeaveTypeModel;
import www.softedgenepal.com.softedgenepalschool.Presenter.Contractor.AssignmentContractor;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication.EditTeacherLeaveAppActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication.ShowTeacherLeaveAppActivity;
import www.softedgenepal.com.softedgenepalschool.View.Activities.Teacher.LeaveApplication.ShowTeacherLeaveAppDetailActivity;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialogs;

import static www.softedgenepal.com.softedgenepalschool.View.Activities.MainActivity.user;

public class TeacherLeaveAdapter extends RecyclerView.Adapter<TeacherLeaveAdapter.ViewHolder> implements ApiCall.ResultListener {
    private ShowTeacherLeaveAppActivity context;
    private List<LeaveTypeModel> leaveTypeModelList;
    private List<LeaveApplicationModel> leaveApplicationModelList;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    public TeacherLeaveAdapter(ShowTeacherLeaveAppActivity context, List<LeaveTypeModel> leaveTypeModelList, List<LeaveApplicationModel> leaveApplicationModelList, BottomSheetBehavior mBehavior, BottomSheetDialog mBottomSheetDialog) {
        this.context = context;
        this.leaveTypeModelList = leaveTypeModelList;
        this.leaveApplicationModelList = leaveApplicationModelList;
        this.mBehavior = mBehavior;
        this.mBottomSheetDialog = mBottomSheetDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.showteacher_leaveapp_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        LeaveApplicationModel model = leaveApplicationModelList.get(position);

        for (int i = 0; i < leaveTypeModelList.size(); i++) {
            if (leaveTypeModelList.get(i).LeaveTypeCode.equals(model.LeaveType)) {
                setText(viewHolder.leaveTypeTextView, leaveTypeModelList.get(i).LeaveTypeName);
            }
        }
        forDate(viewHolder.createDateTextView, model.LeaveAppliedDate);

        String isApproved = model.IsApproved;
        if(isApproved.equals("0")){
            viewHolder.isAppraovedTextView.setVisibility(View.GONE);
        }else if(isApproved.equals("1")){
            setText(viewHolder.isAppraovedTextView, "Approved");
            viewHolder.isAppraovedTextView.setTextColor(context.getResources().getColor(R.color.green_500));
        }else if(isApproved.equals("2")){
            setText(viewHolder.isAppraovedTextView, "Canceled");
            viewHolder.isAppraovedTextView.setTextColor(context.getResources().getColor(R.color.red_500));
        }

        viewHolder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowTeacherLeaveAppDetailActivity.class);
                intent.putExtra("leaveModel", model);
                context.startActivity(intent);
            }
        });


        viewHolder.dots3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(model, viewHolder.dots3);
            }
        });

    }

    private void dialog(final LeaveApplicationModel model, View dots3){
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, dots3);
        //inflating menu from xml resource
        popup.inflate(R.menu.options_menu);

        if(model.IsApproved.equals("2")){
            popup.getMenu().setGroupVisible(R.id.editGrp, false);
        }

        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);

                switch (item.getItemId()) {
                    case R.id.edit:
                        Intent intent = new Intent(context, EditTeacherLeaveAppActivity.class);
                        intent.putExtra("leaveModel", model);
                        context.startActivity(intent);
                        return true;

                    case R.id.cancel:
                        showBottomSheetDialog(model, context.getResources().getString(R.string.LeaveApplication_Dialoge_Title), "Cancel");
                        return true;

                    case R.id.delete:
                        showBottomSheetDialog(model, "Delete your leave application", "Delete");
                        return true;

                    default:
                        return false;
                }
            }
        });
        //displaying the popup
        popup.show();
    }

    private void showBottomSheetDialog(final LeaveApplicationModel obj, String title, String topic) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        final View view = context.getLayoutInflater().inflate(R.layout.alert_dialog, null);
        ((TextView) view.findViewById(R.id.alertDialogTitle)).setText(title);
        Button deleteButton = (view.findViewById(R.id.alertDialogExitButton));
        ViewGroup.LayoutParams params = deleteButton.getLayoutParams();
        params.width = 0;
        params.height = 0;

        deleteButton.setLayoutParams(params);

        Button cancelButton = (view.findViewById(R.id.alertDialogCancelButton));
        cancelButton.setText(context.getResources().getString(R.string.yes));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                process(obj, topic);
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }

    private void process(LeaveApplicationModel obj, String topic) {
        if(topic.equals("Cancel")){
            String parameters = "?SystemCode="+obj.SystemCode+"&Role="+user.Role;
            ApiCall.getInstance().connect(context, Constants.CancelStaffLeaveApplication, Request.Method.POST, parameters, null, this, "Cancelling your leave application...");

        }else if(topic.equals("Delete")){
            String parameters = "?SystemCode="+obj.SystemCode+"&Role="+user.Role;
            ApiCall.getInstance().connect(context, Constants.DeleteStaffLeaveApplication, Request.Method.POST, parameters, null, this, "Deleting your leave application...");

        }
    }

    private void displayMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void forDate(TextView textView, String date) {
        setText(textView, DateTime.DateConvertToNepali(date, "date"));
    }

    private void setText(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setText(Objects.toString(s, ""));
        }
    }

    @Override
    public int getItemCount() {
        return leaveApplicationModelList.size();
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        if(isSuccess){
            try {
                displayMessage(jsonObject.getString("Response"));
                context.onRestart();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        CustomAlertDialogs.NetworkError(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveTypeTextView, createDateTextView, isAppraovedTextView;
        public View materialRippleLayout, dots3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leaveTypeTextView = itemView.findViewById(R.id.leaveTypeTextView);
            createDateTextView = itemView.findViewById(R.id.createDateTextView);
            isAppraovedTextView = itemView.findViewById(R.id.isAppraovedTextView);
            materialRippleLayout = itemView.findViewById(R.id.materialRippleLayout);
            dots3 = itemView.findViewById(R.id.materialRippleLayout3dots);
        }
    }



}
