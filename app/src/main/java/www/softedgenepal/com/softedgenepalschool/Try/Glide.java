package www.softedgenepal.com.softedgenepalschool.Try;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import www.softedgenepal.com.softedgenepalschool.R;

public class Glide {
    public void glide(Activity activity){
        ImageView imageView = activity.findViewById(R.id.imageview);

        //look document for more for glide
        //https://bumptech.github.io/glide/doc/getting-started.html
        String url = "https://picsum.photos/id/237/200/300";
        String gifUrl = "http://www.gifbay.com/gif/hey_dummy-119767";
        try {
            //Loading images with Glide
            com.bumptech.glide.Glide
                    .with(activity)
                    .asGif()
                    .load(gifUrl)
                    .centerCrop()
                    .override(400,400)
                    .placeholder(R.drawable.giphy)                  //it will show upto when real image is not loaded
                    .error(R.drawable.a)
                    .fallback(R.drawable.a)                        //it will call if you pass null in url
                    .transition(DrawableTransitionOptions.withCrossFade(10000))  //it will load image after 2 sec
                    .into(imageView);
            //Thread.sleep(10000);

            //Cancelling loads you no longer need
//            Glide
//                    .with(getApplication())
//                    .clear(imageView);
//            imageView.setImageDrawable(getResources().getDrawable(R.drawable.giphy));
            //Although it’s good practice to clear loads you no longer need, you’re not required to do so.
            // In fact, Glide will automatically clear the load and recycle any resources
            // used by the load when the Activity or Fragment you pass in to Glide.with() is destroyed.


//            FutureTarget<Bitmap> futureTarget =
//                    Glide.with(getApplication())
//                            .asBitmap()
//                            .load(url)
//                            .submit(400, 300);
//
//            Bitmap bitmap = futureTarget.get();
//
//            // Do something with the Bitmap and then when you're done with it:
//            Glide.with(getApplication()).clear(futureTarget);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
