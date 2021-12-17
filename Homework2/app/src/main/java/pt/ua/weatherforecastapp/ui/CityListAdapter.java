package pt.ua.weatherforecastapp.ui;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import pt.ua.weatherforecastapp.R;
import pt.ua.weatherforecastapp.datamodel.City;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private HashMap<String, City> cityList;
    private List<String> cities = new ArrayList<>();
    private LayoutInflater inflater;
    private OnNoteListener listener;
    private Context context;


    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView cityItemView;
        final CityListAdapter adapter;

        // Creates a new custom view holder to hold the view to display in the RecyclerView.
        public CityViewHolder (View itemView, CityListAdapter adapter){
            super(itemView);
            cityItemView = itemView.findViewById(R.id.city);
            itemView.setOnClickListener(this);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View view){
           /** int pos = getAdapterPosition();
            for(String key : cityList.keySet()){
                cities.add(key);
            }
            Log.d("City clicked", cities.get(pos)); **/
           int pos = getAdapterPosition();
           Log.d("City position", String.valueOf(pos));
           listener.onNoteClick(view, pos);
        }
    }

    public CityListAdapter(Context context, HashMap<String, City> cityList){
        inflater = LayoutInflater.from(context);
        this.cityList = cityList;
        this.context = context;
    }


    @Override
    public CityListAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Inflate an item view.
        View itemView = inflater.inflate(R.layout.citylist_item, parent, false);

        return new CityViewHolder(itemView, this);
    }

    // Called by RecyclerView to display the data at the specified position.
    // This method should update the contents of the ViewHolder.itemView to
    // reflect the item at the given position.
    @Override
    public void onBindViewHolder(CityListAdapter.CityViewHolder holder, int position){
        // Retrieve the data for that position.
        String current = "";
        int pos = 0;

        for (String key : cityList.keySet()){
            if (pos == position){
                current = Objects.requireNonNull(cityList.get(key)).getLocal();
                break;
            }
            pos++;
        }
        // Add the data to the view holder.
       holder.cityItemView.setText(current);

    }

    // Returns the total number of items in the daa set held by the adapter.
    @Override
    public int getItemCount(){
        return cityList == null ? 0 : cityList.size();
    }

    public void setCityList(HashMap<String, City> cityList){
        this.cityList = cityList;
    }

    public void setListener(OnNoteListener listener){
        this.listener = listener;
    }

    public interface OnNoteListener{
        void onNoteClick(View view, int position);
    }
}
