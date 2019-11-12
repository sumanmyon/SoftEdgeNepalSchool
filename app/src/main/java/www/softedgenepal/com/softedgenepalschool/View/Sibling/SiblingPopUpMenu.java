package www.softedgenepal.com.softedgenepalschool.View.Sibling;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ImageUrlFormater;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.Student.StudentDataCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.SiblingActivity;
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
            private View siblingLylView;
            @Override
            public void inflateUIFields(View itemView) {
                siblingImageView = itemView.findViewById(R.id.popupmenu_ImageView);
                siblingNameTextView = itemView.findViewById(R.id.popupmenu_TextView);
                siblingLylView = itemView.findViewById(R.id.siblingLylView);
            }

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                animation_type =  ItemAnimation.NONE;
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.popupmenu_profile,null);
                return new ViewHolder(view);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                StudentDataCache dataCache = siblingDataCaches.get(position);
                siblingNameTextView.setText(dataCache.StudentName);

                //todo for image
                ShowInGlide glide = new ShowInGlide((Activity) context);
                glide.loadURL(new ImageUrlFormater().conver(dataCache.ImageUrl));
                glide.loadFailed(R.drawable.userprofile4);
                glide.show(siblingImageView);

                siblingLylView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        siblingDataCaches(dataCache);
                    }
                });
            }
        };

        RecyclerView recyclerView = findViewById(R.id.sibling_popupmenu_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private void siblingDataCaches(StudentDataCache dataCache){
        Intent intent = new Intent(getContext().getApplicationContext(), SiblingActivity.class);
        intent.putExtra("SiblingData", dataCache);
        getContext().startActivity(intent);
    }
    private void setMessage(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}