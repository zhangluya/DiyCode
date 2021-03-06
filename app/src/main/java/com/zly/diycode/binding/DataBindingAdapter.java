package com.zly.diycode.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.zly.diycode.common.HtmlUtils;
import com.zly.diycode.common.Navigation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 * Created by zhangly on 2017/3/18.
 */

public class DataBindingAdapter {

    @BindingAdapter({"android:drawableLeft"})
    public static void setDrawableLeft(final TextView textView, String url) {
        final Context context = textView.getContext();
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                final Drawable[] compoundDrawables = textView.getCompoundDrawables();
                final BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), resource);
                bitmapDrawable.setBounds(0, 0, 70, 70);
                textView.setCompoundDrawables(bitmapDrawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
            }
        });
    }

    @BindingAdapter({"adapter", "layoutManager"})
    public static void setAdapterAndLayoutManager(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(final ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter({"imageUrl", "isCircular"})
    public static void setImageUrl(final ImageView imageView, String url, final boolean isCircular) {
        Glide.with(imageView.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (isCircular) {
                    RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                    roundedDrawable.setCircular(true);
                    imageView.setImageDrawable(roundedDrawable);
                } else {
                    imageView.setImageBitmap(resource);
                }
            }
        });
    }

    @BindingAdapter({"html"})
    public static void setHtmlWithImage(final TextView textView, final String html) {
        Spannable spanned = (Spannable) Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                final UrlDrawable urlDrawable = new UrlDrawable();
                Glide.with(textView.getContext()).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        GlideBitmapDrawable d = new GlideBitmapDrawable(textView.getContext().getResources(), resource);

                        d.setBounds(0, 0, resource.getWidth(), resource.getHeight());

                        urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                        urlDrawable.setDrawable(d);
                        textView.setText(textView.getText());
                        textView.invalidate();

                    }
                });
                return urlDrawable;
            }
        }, null);
        final URLSpan[] urlSpans = spanned.getSpans(0, spanned.length(), URLSpan.class);
        for (final URLSpan urlSpan : urlSpans) {
            spanned.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Navigation.getInstance().openWebBrowser(widget.getContext(), urlSpan.getURL());
                }
            }, spanned.getSpanStart(urlSpan), spanned.getSpanEnd(urlSpan), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanned.removeSpan(urlSpan);
        }

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanned);
    }

    static final class UrlDrawable extends Drawable implements Drawable.Callback {

        private GlideDrawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            if (mDrawable != null) {
                mDrawable.setAlpha(alpha);
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            if (mDrawable != null) {
                mDrawable.setColorFilter(cf);
            }
        }

        @Override
        public int getOpacity() {
            if (mDrawable != null) {
                return mDrawable.getOpacity();
            }
            return 0;
        }

        public void setDrawable(GlideDrawable drawable) {
            if (this.mDrawable != null) {
                this.mDrawable.setCallback(null);
            }
            drawable.setCallback(this);
            this.mDrawable = drawable;
        }

        @Override
        public void invalidateDrawable(Drawable who) {
            if (getCallback() != null) {
                getCallback().invalidateDrawable(who);
            }
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            if (getCallback() != null) {
                getCallback().scheduleDrawable(who, what, when);
            }
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            if (getCallback() != null) {
                getCallback().unscheduleDrawable(who, what);
            }
        }
    }
}
