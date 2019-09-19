package www.softedgenepal.com.softedgenepalschool.Services;

import android.app.Activity;
import android.content.Intent;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.NetworkHandler.NetworkConnection;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Constants;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAlertDialogs;

public class YouTubeVideoCall extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private Activity activity;
    private int youTubePlayer_FragmentId;
    private YouTubePlayerFragment fragment;

    private String url;
    private YouTubePlayer.Provider pro;
    private YouTubePlayer youTubeP;
    private boolean bb;
    private boolean isPlaying = false;

    public YouTubeVideoCall(Activity activity, int youTubePlayer_FragmentId) {
        this.activity = activity;
        this.youTubePlayer_FragmentId = youTubePlayer_FragmentId;
        casting();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b)
        {
            pro = provider;
            youTubeP = youTubePlayer;
            bb = b;
            youTubePlayer.loadVideo(url);
        }
        youTubePlayer.play();
        isPlaying = true;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            getYouTubePlayerProvider().initialize(Constants.Youtube_APIkey,this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(youTubePlayer_FragmentId);
    }

    public void play(String videoId) {
        this.url = videoId;
        if(new NetworkConnection(activity).isConnectionSuccess()) {
            if (isPlayingVideo()) {
                onInitializationSuccess(pro, youTubeP, false);
            } else {
                fragment.initialize(Constants.Youtube_APIkey, this);
            }
        }else {
            CustomAlertDialogs.NetworkError(activity);
        }
    }

    private boolean isPlayingVideo(){
        return isPlaying;
    }

    private void casting(){
        fragment = (YouTubePlayerFragment) activity.getFragmentManager().findFragmentById(youTubePlayer_FragmentId);
    }
}
