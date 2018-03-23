package com.example.rofor.NeventProj;
        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by User on 3/14/2017.
 */

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView vTitle;
        TextView vLocation;
        TextView vDate;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ListItemAdapter(Context context, int resource, ArrayList<ListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String Title = getItem(position).getTitle();
        String Location = getItem(position).getLocation();
        String Date = getItem(position).getDate();
        ListItem tempitem = new ListItem(Title,Location,Date);

        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.vTitle = (TextView) convertView.findViewById(R.id.textView1);
            holder.vLocation = (TextView) convertView.findViewById(R.id.textView3);
            holder.vDate = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vTitle.setText(tempitem.getTitle());
        holder.vLocation.setText(tempitem.getLocation());
        holder.vDate.setText(tempitem.getDate());


        return convertView;
    }
}



