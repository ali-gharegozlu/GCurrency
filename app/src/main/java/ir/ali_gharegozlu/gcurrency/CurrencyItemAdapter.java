package ir.ali_gharegozlu.gcurrency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.ali_gharegozlu.gcurrency.model.CurrencyObject;

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.ViewHolder> {

    List<CurrencyObject> mCurrencyItems;
    private Context mContext;

    public CurrencyItemAdapter(Context context, List<CurrencyObject> currencyItems){
        this.mContext = context;
        this.mCurrencyItems = currencyItems;
    }
    @NonNull
    @Override
    public CurrencyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyItemAdapter.ViewHolder viewHolder, int i) {

        viewHolder.nameTV.setText(mCurrencyItems.get(i).getName());
        viewHolder.abbrTV.setText(mCurrencyItems.get(i).getAbbr());
        viewHolder.priceTV.setText(mCurrencyItems.get(i).getPrice());
        //viewHolder.priceTV.setText(CoinItemAdapter.thousandSeparator(mCurrencyItems.get(i).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mCurrencyItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV, abbrTV, priceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.currencyNameTV);
            abbrTV = itemView.findViewById(R.id.currencyAbbr);
            priceTV = itemView.findViewById(R.id.currencyPriceTV);
        }
    }
}
