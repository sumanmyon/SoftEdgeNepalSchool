package www.softedgenepal.com.softedgenepalschool.View.Sibling;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

public class SiblingPopUpMenu extends Dialog {
    private Context context;
    private List<StudentDataCache> siblingDataCaches;

    public SiblingPopUpMenu(Context context, List<StudentDataCache> siblingDataCaches) {
        super(context);
        this.context = context;
        this.siblingDataCaches = siblingDataCaches;
        onCreate();
    }


    private void onCreate() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sibling_pop_up);
        show();

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.END;
        wlp.y = 48;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        //get sibling size
        int size = siblingDataCaches.size();

        if(size<=0){
            setMessage("There is no sibling data."+size);
            return;
        }

        RecyclerAdapter adapter = new RecyclerAdapter(context,size) {
            private CircleImageView siblingImageView;
            private TextView siblingNameTextView;
            @Override
            public void inflateUIFields(View itemView) {
                siblingImageView = itemView.findViewById(R.id.popupmenu_ImageView);
                siblingNameTextView = itemView.findViewById(R.id.popupmenu_TextView);
            }

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.popupmenu_profile,null);
                return new ViewHolder(view);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                StudentDataCache dataCache = siblingDataCaches.get(position);
                siblingNameTextView.setText(dataCache.username);
                //todo for image
                ShowInGlide glide = new ShowInGlide((Activity) context);
                glide.loadURL(dataCache.imageUrl);
                glide.loadFailed(R.drawable.userprofile);
                glide.show(siblingImageView);
            }
        };

        RecyclerView recyclerView = findViewById(R.id.sibling_popupmenu_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private void setMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

//    private void test(){
//        //activity.startActivity(new Intent(activity, SiblingPopUp.class));
//        Dialog dialog_box = new Dialog(activity);
//        dialog_box.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_box.setContentView(R.layout.activity_sibling_pop_up);
//        dialog_box.show();
//
//        Window window = dialog_box.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.TOP | Gravity.END;
//        wlp.y = 48;
//        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
//
//        TextView siblingTextView = dialog_box.findViewById(R.id.sibling_pop_up_textView);
//        siblingTextView.setText("Suman Poudel");
//        //todo stuff here
//    }
}