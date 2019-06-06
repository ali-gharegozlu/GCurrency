package ir.ali_gharegozlu.gcurrency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


import ir.ali_gharegozlu.gcurrency.model.CurrencyObject;

public class CoinItemAdapter extends RecyclerView.Adapter<CoinItemAdapter.ViewHolder> {

    private Context mContext;
     List<CurrencyObject> mCoinObjects;

    CoinItemAdapter(Context context, List<CurrencyObject> coinItems){
        this.mContext = context;
        this.mCoinObjects = coinItems;
    }

    @NonNull
    @Override
    public CoinItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.coin_list_item,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.nameTV.setText(mCoinObjects.get(i).getName());
        viewHolder.priceTV.setText(mCoinObjects.get(i).getPrice());
        switch (mCoinObjects.get(i).getAbbr()){
            case "gold_gerami": viewHolder.coinImage.setImageResource(R.drawable.bahar_coin); break;
            case "gold_rob": viewHolder.coinImage.setImageResource(R.drawable.emami_coin); break;
            case "gold_nim": viewHolder.coinImage.setImageResource(R.drawable.emami_coin); break;
            case "gold_sekee_emami": viewHolder.coinImage.setImageResource(R.drawable.emami_coin); break;
            case "gold_seke_bahar": viewHolder.coinImage.setImageResource(R.drawable.bahar_coin); break;
            case "gold_geram24": viewHolder.coinImage.setImageResource(R.drawable.gold_bar); break;
            case "gold_geram18": viewHolder.coinImage.setImageResource(R.drawable.gold_bar); break;
            case "gold_mesghal": viewHolder.coinImage.setImageResource(R.drawable.gold_bar); break;
            case "gold_ons": viewHolder.coinImage.setImageResource(R.drawable.gold_bar); break;
            default: viewHolder.coinImage.setImageResource(R.drawable.emami_coin); break;
        }
    }

    static String thousandSeparator(String number){
        int intPrice = Integer.valueOf(number);
        String newPrice = "";

        for(byte i=0;i<=intPrice;i++){
            newPrice = (intPrice % 1000)+"," + newPrice;
            intPrice /= 1000;
        }

        return newPrice.substring(0,newPrice.length()-1);
    }

    @Override
    public int getItemCount() {
        return mCoinObjects.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV, priceTV;
        ImageView coinImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.coinNameTV);
            priceTV = itemView.findViewById(R.id.coinPriceTV);
            coinImage = itemView.findViewById(R.id.coinImageView);
        }
    }
}
