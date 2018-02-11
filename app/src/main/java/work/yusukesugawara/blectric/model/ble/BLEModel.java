package work.yusukesugawara.blectric.model.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import work.yusukesugawara.blectric.function.ble.BLEFunction;
import work.yusukesugawara.blectric.function.ble.connection.Connector;
import work.yusukesugawara.blectric.function.ble.connection.GattStarter;
import work.yusukesugawara.blectric.function.misc.Logg;

public class BLEModel {
    private static final String TAG = "BLEModel";
    @Nullable
    private static BLEModel object = null;
    @NonNull
    public static BLEModel get(@NonNull Context context) {
        if (object == null) {
            object = new BLEModel(context.getApplicationContext());
        }
        return object;
    }

    @NonNull
    private final BLEFunction bleFunction;

    private BLEModel(@NonNull Context context) {
        bleFunction = BLEFunction.get(context);

        bleFunction.getReceivedAdvertisingStream().subscribe(receivedAdvertisingList);
    }

    public void startScan() {
        receivedAdvertisingList.clear();

        bleFunction.startScan();
    }

    public void stopScan() {
        bleFunction.stopScan();
    }

    @NonNull
    private AdvertisingList receivedAdvertisingList = new AdvertisingList();

    @NonNull
    public AdvertisingList getReceivedAdvertisingList() {
        return receivedAdvertisingList;
    }

    @NonNull
    public Observable<AdvertisingList> getReceivedAdvertisingListUpdateStream() {
        return receivedAdvertisingList.getUpdateStream();
    }

    @NonNull
    public Connector connectTo(@NonNull BluetoothDevice bluetoothDevice) {
        return bleFunction.connectTo(bluetoothDevice, new Connector.Delegate() {
            @Override
            public void connectorDidDisconnect(@NonNull Connector connector) {
                Logg.e(TAG, "connectorDidDisconnect: connector=%s", connector.toString());
            }
        }, new GattStarter());
    }
}
