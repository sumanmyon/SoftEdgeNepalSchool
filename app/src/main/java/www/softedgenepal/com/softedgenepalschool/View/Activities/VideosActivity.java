package www.softedgenepal.com.softedgenepalschool.View.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.DefaultDataStore;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.YouTubeModel;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.Services.ApiCall;
import www.softedgenepal.com.softedgenepalschool.Services.YouTubeVideoCall;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.YouTubeAdapter;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAppCompatActivity;

public class VideosActivity extends CustomAppCompatActivity implements ApiCall.ResultListener {
    private List<YouTubeModel> modelList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        casting();
        toolBarTitle.setText(getString(R.string.Videos));

        loadVideos();
    }

    private void loadVideos() {
        String parameters = "?part=snippet&playlistId=" + Constants.YouTube_playlistId;
        parameters = parameters + "&maxResults=" + Constants.YouTube_maxResults;
        parameters = parameters + "&key=" + Constants.Youtube_APIkey;
        ApiCall.getInstance().connect(this, Constants.YouTube_PlayList, Request.Method.GET, parameters, null, this, "");
        pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, boolean isSuccess, JSONObject jsonObject, VolleyError volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;
        try {
            online(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, String message) {
        offline(message);
    }

    private void online(JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        modelList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<YouTubeModel>>() {
        }.getType());

        DefaultDataStore.YoutubePlayLists.store(this, modelList);

        populateInView();
    }

    private void offline(String message) {
        modelList = (List<YouTubeModel>) DefaultDataStore.YoutubePlayLists.get(this, new TypeToken<List<YouTubeModel>>() {
        }.getType());

        if(modelList.size() > 0) {
            populateInView();
        }else {
            showErrorPopUp("", message);
        }
    }

    private void populateInView() {
        YouTubeAdapter adapter = new YouTubeAdapter(this, R.id.youtube, modelList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void casting() {
        super.casting();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(false);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutFrozen(true);
    }
}
