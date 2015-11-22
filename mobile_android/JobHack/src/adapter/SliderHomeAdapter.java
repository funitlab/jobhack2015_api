package adapter;

import java.util.List;

import widget.CanaroTextView;

import models.BrowseItem;
import models.MenuItem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.funitlab.jobhack.R;

public class SliderHomeAdapter extends BaseAdapter {  
	private static final String TAG = "SliderHomeAdapter";

    private Context context;
    private LayoutInflater inflater;
    private List<MenuItem> menuItems;
 
    public SliderHomeAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }
 
    @Override
    public int getCount() {
        return menuItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return menuItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.cell_slider_menu, null);
 
        CanaroTextView tvTitle = (CanaroTextView) convertView.findViewById(R.id.tvTitle);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        
        MenuItem item = menuItems.get(position);
        
        // Set title
        tvTitle.setText(item.getTitle());
        
		Log.d(TAG, "isBooL: " + item.isSelected());

        // Set icon
        if (item.isSelected()) {
            imgIcon.setImageResource(item.getSelectedIcon());        
            tvTitle.setTextColor(context.getResources().getColor(R.color.selected_item_color));
		}else{
	        imgIcon.setImageResource(item.getDefaultIcon());        
            tvTitle.setTextColor(context.getResources().getColor(R.color.default_item_color));
		}

        return convertView;
    }
 
}
