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

public class AccountAdapter extends ArrayAdapter<Account> {

    private ArrayList<Account> dataSet;
    Context mContext;
    DecimalFormat format;

    private static class ViewHolder {
        TextView name;
        TextView balance;
    }


    public AccountAdapter(ArrayList<Account> data, Context context) {
        super(context, R.layout.row_view_acc, data);
        this.dataSet = data;
        this.mContext = context;
        format = new DecimalFormat("###,###,###,###", new DecimalFormatSymbols(Locale.US));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Account dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_view_acc, parent, false);
            viewHolder.name = convertView.findViewById(R.id.accrow_name);
            viewHolder.balance = convertView.findViewById(R.id.accrow_bal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(dataModel.getName());
        viewHolder.balance.setText("موجودی: " + format.format(dataModel.getBalance()));
        // Return the completed view to render on screen
        return convertView;
    }
}
