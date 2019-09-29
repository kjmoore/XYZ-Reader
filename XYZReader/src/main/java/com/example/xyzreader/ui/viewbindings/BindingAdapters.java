package com.example.xyzreader.ui.viewbindings;

import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    private static final String TAG = BindingAdapters.class.getSimpleName();

    private static GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    @BindingAdapter("imageUrl")
    public static void setImage(final ImageView poster, final String posterPath) {
        if (posterPath == null || posterPath.isEmpty()) {
            Log.w(TAG, "The image URL was empty, not setting the background");
            return;
        }

        final Picasso.Builder picassoBuilder = new Picasso.Builder(poster.getContext());

        picassoBuilder.listener((picasso, uri, exception) -> Log.e(TAG, uri.toString(), exception));

        picassoBuilder.build()
                .load(posterPath)
                .fit()
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(poster);
    }

    @BindingAdapter("date")
    public static void setText(final TextView text, final String toSet) {
        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.getDefault());

        try {
            final Date published = dateFormat.parse(toSet);
            final String dateString;

            if (!published.before(START_OF_EPOCH.getTime())) {
                dateString = DateUtils.getRelativeTimeSpanString(
                        published.getTime(),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString();
            } else {
                dateString = SimpleDateFormat.getDateInstance().format(published);
            }
            text.setText(dateString);

        } catch (ParseException e) {
            text.setText(text.getResources().getString(R.string.unknown_date));
        }
    }
}
