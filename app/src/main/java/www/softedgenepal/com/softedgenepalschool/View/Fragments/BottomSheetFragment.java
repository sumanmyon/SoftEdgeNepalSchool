package www.softedgenepal.com.softedgenepalschool.View.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.FileDownloader;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSetting;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Settings.LanguageSettingv2;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ImageSplitter;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Tools;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.Model.URLs.URL;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile, homeWorkLinearLayout, homeWorkLinearLayoutDown, homeWorkFileLinearLayout;

    private TextView subjectTextView, createDateTextView, deadlineTextView;
    private WebView homeWorkTextView;
    private ImageButton homeWorkDownload;
    private RecyclerView recyclerView;
    private final int GRID_SPAN = 4;        //3

    private AssignmentCache cache;
    private LanguageSettingv2 languageSetting;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        languageSetting = new LanguageSettingv2(getActivity());
        languageSetting.loadLanguage();
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //casting
        casting(view);

        setHomeWorkInVisibilty();

        //setData to view
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

        (view.findViewById(R.id.assignment_bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    private void casting(View view) {
        app_bar_layout = view.findViewById(R.id.app_bar_layout);
        lyt_profile = view.findViewById(R.id.lyt_profile);
        subjectTextView = view.findViewById(R.id.assignment_subject);
        createDateTextView = view.findViewById(R.id.assignment_createdDate);
        deadlineTextView = view.findViewById(R.id.assignment_deadline);
        homeWorkTextView = view.findViewById(R.id.assignment_homeWorkWebView);
        homeWorkLinearLayout = view.findViewById(R.id.assignment_homwWork_lyl);
        homeWorkLinearLayoutDown = view.findViewById(R.id.assignment_homwWork_lyl_down);
        homeWorkFileLinearLayout = view.findViewById(R.id.assignment_homwWork_file_lyl);
        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(Tools.getScreenHeight() / 2);
        homeWorkDownload = view.findViewById(R.id.assignment_download);

        //todo for file download recycler view
        recyclerView = view.findViewById(R.id.bottom_sheet_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),GRID_SPAN));
    }

    private void setHomeWorkInVisibilty(){
        homeWorkLinearLayout.setVisibility(View.GONE);
        homeWorkLinearLayoutDown.setVisibility(View.GONE);
    }
    private void setHomeWorkVisibilty(){
        homeWorkLinearLayout.setVisibility(View.VISIBLE);
        homeWorkLinearLayoutDown.setVisibility(View.VISIBLE);
    }

    public void setAssignment(final AssignmentCache cache) {
        this.cache=cache;
    }

    public void setData(){
        subjectTextView.setText(cache.SubjectNameEng);
        createDateTextView.setText(cache.CreateDate);
        deadlineTextView.setText(cache.Deadline);

        String homeWork = cache.Homework;

        if(!homeWork.equals("") || !homeWork.equals(null) || !homeWork.equals("<p><br></p>")){
            setHomeWorkVisibilty();

            String[] strings = new String[]{"\b","\t","\n","\f","\r"};//,"\\\'","\\\"", "\\\\"};
            for (String s:strings) {
                homeWork = homeWork.replaceAll(s, "");//Matcher.quoteReplacement(""));
                Log.d("TAG_WebView",homeWork);
            }

            homeWorkTextView.getSettings().setDomStorageEnabled(true);
            homeWorkTextView.getSettings().setLoadsImagesAutomatically(true);
            homeWorkTextView.getSettings().setAppCacheEnabled(true);
            homeWorkTextView.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                homeWorkTextView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            homeWorkTextView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            homeWorkTextView.requestFocus();
            homeWorkTextView.loadData(homeWork, "text/html", "utf-8");

            boolean isHTML = homeWork.matches(".*\\<[^>]+>.*");
           Log.d("TAG_HTML",cache.Homework+"\t"+isHTML);
        }else {
            setHomeWorkInVisibilty();
        }

        String downloadLink = cache.ImageUrl;
        Log.d("TAG_downloadLink",downloadLink);
        if(downloadLink.equals("")){
            homeWorkDownload.setVisibility(View.GONE);
            homeWorkFileLinearLayout.setVisibility(View.GONE);
        }else {
            //todo download file
            //showMessage(downloadLink);
            homeWorkDownload.setVisibility(View.GONE);
            downloadLink = ImageSplitter.convertSplit(downloadLink,",~","~");
            String[] s = ImageSplitter.imageSplit(downloadLink, "~");
            List<String> a = new ArrayList<>();
            showMessage(String.valueOf(s.length));
            for(int i=0; i<s.length; i++){
                if(i < s.length-1) {
                    a.add(s[i + 1]);
                    showMessage(a.get(i));
                }
            }

            ShowImage showImage = new ShowImage(a);
            showImage.showInView();
        }
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

    private class ShowImage{
        private List<String> images;

        ShowImage(List<String> images) {
            this.images=images;
        }

        void showInView(){
            RecyclerAdapter adapter = new RecyclerAdapter(getContext(),images.size()) {
                ImageView homeworkImage;
                View bck;
                @Override
                public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    View view = inflater.inflate(R.layout.bottom_recycler_list, null);
                    return new ViewHolder(view);
                }

                @Override
                public void inflateUIFields(View itemView) {
                    homeworkImage=itemView.findViewById(R.id.bottom_sheet_recycler_view_image_view);
                    bck = itemView.findViewById(R.id.bck);

                    WindowManager wm = (WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int height = (size.y)/4;
                    int width = (size.x)/5;     //3

                    ViewGroup.LayoutParams params = homeworkImage.getLayoutParams();
                    params.height = width;
                    params.width = width;

                    homeworkImage.setLayoutParams(params);
                    homeworkImage.setAdjustViewBounds(true);
                    homeworkImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

                @Override
                public void onBind(ViewHolder viewHolder, int position) {
                    String url = new URL().url;
                    String fileName = cache.SubjectNameEng +"_"+cache.CreateDate+"_"+position;

                    ShowInGlide glide = new ShowInGlide(getActivity());
                    glide.loadURL( url + images.get(position));
                    if (images.get(position).matches(".*\\.(pdf)")) {     // https://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/
                        glide.loadFailed(R.drawable.ic_pdf3);
                        bck.setOnClickListener(new DownloadFile(url + images.get(position), fileName+".pdf",  FileDownloader.PDF));
                    } else {
                        glide.loadFailed(R.drawable.null_img2);
                        if(images.get(position).matches(".*\\.(png|PNG)"))
                            bck.setOnClickListener(new DownloadFile(url + images.get(position), fileName+".png",  FileDownloader.Photo));
                        else if(images.get(position).matches(".*\\.(jpg|JPG)"))
                            bck.setOnClickListener(new DownloadFile(url + images.get(position), fileName+".jpg",  FileDownloader.Photo));
                        else if(images.get(position).matches(".*\\.(jpeg|JPEG)"))
                            bck.setOnClickListener(new DownloadFile(url + images.get(position), fileName+".jpeg",  FileDownloader.Photo));

                       // ".*\\.(png|jpg|gif|bmp)"
                    }
                    glide.show(homeworkImage);

                    showMessage(new URL().url + images.get(position));
                }
            };

            recyclerView.setAdapter(adapter);
        }
    }

    private class DownloadFile implements View.OnClickListener {
        String imgUrl;
        String fileName;
        String formate;
        public DownloadFile(String imgUrl, String fileName, String formate) {
            this.imgUrl = imgUrl;
            this.fileName = fileName;
            this.formate = formate;
        }

        @Override
        public void onClick(View v) {
            if(new NetworkConnection(getContext()).isConnectionSuccess()) {
                //String url= "http://appeteria.com/video.mp4";
                //String fileName = "video.mp4";
                String title = getResources().getString(R.string.Download);
                String description = getResources().getString(R.string.Downloading)+" " + formate + " ...";
                FileDownloader downloader = new FileDownloader(getActivity());
                downloader.downloadFile(imgUrl, fileName, formate, title, description);

                downloader.registerReciver();
                showToast(fileName+" "+getResources().getString(R.string.Download_started));
            }else{
                showMessage(getResources().getString(R.string.Network_error));
            }
        }
    }

    private void showMessage(String message){
       // Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
        Log.d("ImagesURL", message);
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
