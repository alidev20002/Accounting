package ir.alidev.hesabdar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class TransAdapter extends ArrayAdapter<Transaction> {

    private ArrayList<Transaction> dataSet;
    Context mContext;
    DecimalFormat format;

    private static class ViewHolder {
        TextView type;
        TextView amount;
        TextView date;
        TextView detail;
    }


    public TransAdapter(ArrayList<Transaction> data, Context context) {
        super(context, R.layout.row_view_trans, data);
        this.dataSet = data;
        this.mContext = context;
        format = new DecimalFormat("###,###,###,###", new DecimalFormatSymbols(Locale.US));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_view_trans, parent, false);
            viewHolder.type = convertView.findViewById(R.id.row_type);
            viewHolder.amount = convertView.findViewById(R.id.row_amount);
            viewHolder.date = convertView.findViewById(R.id.row_date);
            viewHolder.detail = convertView.findViewById(R.id.row_detail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.type.setText(dataModel.getType());
        viewHolder.amount.setText(format.format(dataModel.getAmount()));
        viewHolder.date.setText(dataModel.getDate());
        viewHolder.detail.setText(dataModel.getDetail());
        // Return the completed view to render on screen
        return convertView;
    }
}
