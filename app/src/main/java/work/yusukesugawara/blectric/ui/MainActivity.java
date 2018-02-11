package work.yusukesugawara.blectric.ui;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import work.yusukesugawara.blectric.R;
import work.yusukesugawara.blectric.function.ble.scan.Advertising;
import work.yusukesugawara.blectric.function.rx.ValueObserver;
import work.yusukesugawara.blectric.model.ble.AdvertisingList;
import work.yusukesugawara.blectric.model.ble.BLEModel;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private BLEModel bleModel;

    @BindView(R.id.listViewReceivedAdvertising)
    ListView listViewReceivedAdvertising;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        listViewReceivedAdvertising.setAdapter(new AdvertisingListAdapter(this));
        listViewReceivedAdvertising.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapter adapter = listViewReceivedAdvertising.getAdapter();
                Advertising advertising = (Advertising) adapter.getItem(position);
                BluetoothDevice device = advertising.getDevice();

                Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
                intent.putExtra(BluetoothDevice.class.getName(), device);
                startActivity(intent);
            }
        });

        bleModel = BLEModel.get(this);
    }

    @Nullable
    private Disposable disposableAdvertiseList;

    @Override
    protected void onStart() {
        super.onStart();

        MainActivityPermissionsDispatcher.startScanningWithPermissionCheck(this);

        bleModel.getReceivedAdvertisingListUpdateStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ValueObserver<AdvertisingList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableAdvertiseList = d;
                    }

                    @Override
                    public void onNext(AdvertisingList advertisingList) {
                        ListAdapter adapter = listViewReceivedAdvertising.getAdapter();
                        ((BaseAdapter) adapter).notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void startScanning() {
        bleModel.startScan();
    }

    @Override
    protected void onStop() {
        bleModel.stopScan();

        if (disposableAdvertiseList != null) {
            disposableAdvertiseList.dispose();
        }

        super.onStop();
    }
}
