package edu.ipfw.parkview.indoornavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//see: https://www.raywenderlich.com/124438/android-listview-tutorial
public class DirectoryAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Directory> mDataSource;

    public DirectoryAdapter(Context context, ArrayList<Directory> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_events, parent, false);
        // Get title element
        TextView titleTextView =
                (TextView) rowView.findViewById(edu.ipfw.parkview.indoornavigation.R.id.event_list_title);

// Get subtitle element
        TextView subtitleTextView =
                (TextView) rowView.findViewById(edu.ipfw.parkview.indoornavigation.R.id.recipe_list_subtitle);

// Get detail element
        TextView detailTextView =
                (TextView) rowView.findViewById(edu.ipfw.parkview.indoornavigation.R.id.event_list_detail);

// Get thumbnail element
        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(edu.ipfw.parkview.indoornavigation.R.id.event_list_thumbnail);

        // 1
        Directory ue = (Directory) getItem(position);

// 2
        titleTextView.setText(ue.title);
        subtitleTextView.setText(ue.description);
        detailTextView.setText(ue.label);

// 3

        Picasso.with(mContext).load(ue.imageUrl).placeholder(R.drawable.dr_nophoto).into(thumbnailImageView);
        return rowView;
    }

}
