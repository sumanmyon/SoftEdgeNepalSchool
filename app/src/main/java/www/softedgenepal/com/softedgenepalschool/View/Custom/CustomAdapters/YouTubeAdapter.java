package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ImageUrlFormater;
import www.softedgenepal.com.softedgenepalschool.CustomImage.ShowInGlide;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.YouTubeModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.Services.YouTubeVideoCall;
import www.softedgenepal.com.softedgenepalschool.View.Activities.VideosActivity;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.ViewHolder> {
    private VideosActivity videosActivity;
    private int youTubePlayer_FragmentId;
    private List<YouTubeModel> modelList;
    private YouTubeVideoCall youTubeView;

    public YouTubeAdapter(VideosActivity videosActivity, int youTubePlayer_FragmentId, List<YouTubeModel> modelList){
        this.videosActivity = videosActivity;
        this.youTubePlayer_FragmentId = youTubePlayer_FragmentId;
        this.modelList = modelList;
        youTubeView = new YouTubeVideoCall(videosActivity, youTubePlayer_FragmentId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_video_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        YouTubeModel model1 = modelList.get(i);
        String videoId =model1.snippet.resourceId.videoId;
        loadVideoDetailData(videoId,  viewHolder.youtubeTime);

        viewHolder.youtubeTitle.setText(model1.snippet.title);

        if(model1.snippet.thumbnails != null) {
            String url = model1.snippet.thumbnails.maxres.url;

            ShowInGlide glide = new ShowInGlide(videosActivity);
            glide.loadURL(url);
            glide.loadFailed(R.drawable.userprofile4);
            glide.show(viewHolder.imageView);
        }

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVideo(videoId);
            }
        });
    }

    synchronized private void loadVideoDetailData(String videoId, TextView youtubeTime){
        String parameters = "?part=contentDetails&id=" + videoId;
        parameters = parameters + "&key=" + Constants.Youtube_APIkey;
        videosActivity.pendingRequestsCount++;

        ApiCall.getInstance().connect(videosActivity, Constants.YouTube_Video, Request.Method.GET, parameters, null, new ApiCall.ResultListener() {
            @Override
            public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
                videosActivity.pendingRequestsCount--;
                if(isSuccess) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                        JSONObject contentDetailsObject = jsonArray.getJSONObject(0).getJSONObject("contentDetails");
                        YouTubeModel.contentDetails contentDetails = new Gson().fromJson(contentDetailsObject.toString(), new TypeToken<YouTubeModel.contentDetails>() {
                        }.getType());


                        youtubeTime.setText("Duration: " +  DateTime.DateTimeConverterForYoutube(contentDetails.duration));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (progressDialog != null && progressDialog.isShowing() && videosActivity.pendingRequestsCount == 0)
                        progressDialog.dismiss();
                }else {
                    youtubeTime.setText("");
                }
            }

            @Override
            public void onNetworkFailed(String url, String message) {
                youtubeTime.setText("");
            }
        }, "");
    }

    private void loadVideo(String videoId) {
        if(youTubeView != null) {
            youTubeView.play(videoId);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView youtubeTitle, youtubeTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            youtubeTitle = itemView.findViewById(R.id.youtubeTitle);
            youtubeTime = itemView.findViewById(R.id.youtubeTime);
        }
    }
}
