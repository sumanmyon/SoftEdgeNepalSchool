package www.softedgenepal.com.softedgenepalschool.CustomImage;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class ShowInGlide {
    Activity activity;

    //todo Customizing requests of Glide
    RequestManager requestManager;
    RequestBuilder requestBuilder;
    RequestOptions requestOptions;

    //todo glide cache types
    public DiskCacheStrategy diskCache_all = DiskCacheStrategy.ALL;
    public DiskCacheStrategy diskCache_none = DiskCacheStrategy.NONE;
    public DiskCacheStrategy diskCache_automatic = DiskCacheStrategy.AUTOMATIC;
    public DiskCacheStrategy diskCache_data = DiskCacheStrategy.DATA;
    public DiskCacheStrategy diskCache_resource = DiskCacheStrategy.RESOURCE;

    RequestOptions options = new RequestOptions();
    public RequestOptions[] imageSizeDisplay = {
            null,
            options.fitCenter(),
            options.centerCrop(),
            options.centerInside(),
            options.circleCrop(),
            options.optionalFitCenter(),
            options.optionalCenterInside(),
            options.optionalCenterCrop()
    };


    public ShowInGlide(Activity activity) {
        this.activity = activity;
        requestManager = Glide.with(activity);
    }

    public void loadURL(String url){
        requestBuilder = requestManager.load(url);
    }

    public void loadFile(File file){
        requestBuilder = requestManager.load(file);
    }

    /*
        path for uri image it in /data/data/MYFOLDER/myimage.png
        ImageView.setImageURI(Uri.fromFile(new File("/sdcard/cats.jpg")));
        Or with:
        ImageView.setImageURI(Uri.parse(new File("/sdcard/cats.jpg").toString()));
     */
    public void loadUri(Uri uri){
        requestBuilder = requestManager.load(uri);
    }

    public void loadDrawable(int resourceImage){
        requestBuilder = requestManager.load(resourceImage);
    }

    public void loadFailed(int drawImage){
        requestOptions = new RequestOptions();
        requestOptions.error(drawImage);
        apply();
    }

    public void setSpecificSize(int width, int height){
        requestBuilder.override(width,height);
    }

    public void thumbnail(){
        requestBuilder.thumbnail();
    }

    public void show(ImageView imageView){
        requestBuilder.into(imageView);
    }

    public void clear(ImageView imageView, Drawable resDrawImag){
        requestManager.clear(imageView);
        imageView.setImageDrawable(resDrawImag);
    }

    //todo customizing glide
    //if you don't want to use any parameter set null
    //but disk cache is notnullable ,so use diskcache_none to set null
    public void setCustomParameters(int placeHolder, int errorPlaceHolder, int fallback,
                                    DiskCacheStrategy diskCacheStrategy, RequestOptions imageOption){

        requestOptions = new RequestOptions();

        requestOptions.placeholder(placeHolder);
        requestOptions.error(errorPlaceHolder);
        requestOptions.fallback(fallback);

        //todo first cache will affect future url, request will not work for second time
        requestOptions.diskCacheStrategy(diskCacheStrategy);         //use glide cache to store loaded image.
        if(imageOption!=null)
            requestOptions = imageOption;
        apply();
    }

    private void apply(){
        requestBuilder.apply(requestOptions);
    }

    //todo it will load image after .. sec
    public void transitionForSomeMilliSecond(int millisec){
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade(millisec));
    }

    //todo for gif image
    public void forGif(){
        requestManager.asGif();
    }
    //todo get Bitmap from url, file, uri and drawable
    //https://www.dev2qa.com/android-glide-example/





    //todo example1
//        String url = "https://picsum.photos/200/300?grayscale";
//        url="https://cdn-images-1.medium.com/max/1600/0*akL0KXb54mViVajR.";
//        ShowInGlide showInGlide = new ShowInGlide(this);
//        showInGlide.loadURL(url);
//        showInGlide.forGif();
//        showInGlide.show(imageView);

}
