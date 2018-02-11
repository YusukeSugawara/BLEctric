package work.yusukesugawara.blectric.function.ble.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import work.yusukesugawara.blectric.function.ble.BLEFunction;
import work.yusukesugawara.blectric.function.misc.Logg;

public class GattStarter extends BluetoothGattCallback {
    private static final String TAG = "GattStarter";

    public static final int DEFAULT_CONNECTION_TRYING_COUNT_LIMIT = 3;
    private static final int SUCCESS = BluetoothGatt.GATT_SUCCESS;

    private int connectionTryingCount = 0;
    private int connectionTryingCountLimit = DEFAULT_CONNECTION_TRYING_COUNT_LIMIT;
    public void setConnectionTryingCountLimit(int connectionTryingCountLimit) {
        this.connectionTryingCountLimit = connectionTryingCountLimit;
    }

    @Nullable
    private Connector connector;

    public void setConnector(@NonNull Connector connector) {
        this.connector = connector;
    }

    @NonNull
    private GattStarter self = this;

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);

        String newStateString = BLEFunction.stateString(newState);
        Logg.w(TAG, "onConnectionStateChange: gatt=%s, status=%d, newState=%d", gatt, status, newStateString);

        if (status != SUCCESS) {
            BLEFunction.disposeGatt(gatt);
            return;
        }

        switch (newState) {
            case BluetoothGatt.STATE_DISCONNECTED:
                if (connectionTryingCount > connectionTryingCountLimit) {
//                    delegate.connectorDidDisconnect(self);
                }
                else {
                    if (connector != null) {
                        connector.connect(self);
                    }
                }
                break;

            case BluetoothGatt.STATE_CONNECTED:
                gatt.discoverServices();
                break;
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        Logg.w(TAG, "onServicesDiscovered: gatt=%s, status=%d", gatt, status);


    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        Logg.w(TAG, "onDescriptorWrite: gatt=%s, descriptor=%s, status=%d", gatt, descriptor, status);
    }
}
