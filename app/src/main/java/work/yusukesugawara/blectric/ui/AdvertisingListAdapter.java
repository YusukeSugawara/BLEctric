package work.yusukesugawara.blectric.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.yusukesugawara.blectric.R;
import work.yusukesugawara.blectric.function.ble.scan.Advertising;
import work.yusukesugawara.blectric.function.misc.Str;
import work.yusukesugawara.blectric.model.ble.BLEModel;

public class AdvertisingListAdapter extends BaseAdapter {
    @NonNull
    private final List<Advertising> receivedAdvertisingList;

    @NonNull
    private final LayoutInflater layoutInflater;

    public AdvertisingListAdapter(@NonNull Context context) {
        layoutInflater = LayoutInflater.from(context);
        receivedAdvertisingList = BLEModel.get(context).getReceivedAdvertisingList().getInner();
    }

    @Override
    public int getCount() {
        return receivedAdvertisingList.size();
    }

    @Override
    public Advertising getItem(int position) {
        return receivedAdvertisingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cell_advertising, parent);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Advertising advertising = getItem(position);
        viewHolder.updateSubviews(advertising);

        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.textViewAdvertiserName)
        TextView textViewAdvertiserName;

        @BindView(R.id.textViewAdvertiserRssi)
        TextView textViewAdvertiserRssi;

        @BindView(R.id.textViewAdvertisingData)
        TextView textViewAdvertisingData;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        public void updateSubviews(@NonNull Advertising advertising) {
            textViewAdvertiserName.setText(advertising.getAdvertiserName());
            textViewAdvertiserRssi.setText(Str.format("RSSI: %d", advertising.getRssi()));
            textViewAdvertisingData.setText(Str.hexString(advertising.getData()));
        }
    }
}
