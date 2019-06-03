package id.co.lesku.views.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.co.lesku.manager.ConfigManager;

public class BindingAdapter {
    @android.databinding.BindingAdapter("imageTeacherUrl")
    public static void setImageTeacherUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(ConfigManager.BASE_URL_IMAGE + "teacher_profile/"  + url).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    @android.databinding.BindingAdapter("imageProductUrl")
    public static void setImageProductUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(ConfigManager.BASE_URL_IMAGE + "products/" + url).into(imageView);
    }
}
