package work.yusukesugawara.blectric.function.ble.connection;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import work.yusukesugawara.blectric.function.misc.Str;

public class Connector {
    private static final String TAG = "Connector";

    private static final boolean AUTO_CONNECT = false;

    private String tag;

    @NonNull
    private Context applicationContext;
    @NonNull
    private final BluetoothDevice bluetoothDevice;
    @Nullable
    private Delegate delegate;

    public interface Delegate {

        void connectorDidDisconnect(@NonNull Connector connector);
    }
    public Connector(@NonNull Context applicationContext, @NonNull BluetoothDevice bluetoothDevice, @NonNull Delegate delegate) {
        this.applicationContext = applicationContext;
        this.bluetoothDevice = bluetoothDevice;
        this.delegate = delegate;

        tag = Str.format("%s:%s", TAG, bluetoothDevice.getAddress());
    }

    @Override
    public String toString() {
        return Str.format("<%s>", tag);
    }

    @Nullable
    private BluetoothGatt gatt;

    public void cancel() {
        delegate = null;

        if (gatt != null) {
            gatt.close();
            gatt = null;
        }
    }

    public void connect(@NonNull GattStarter gattStarter) {
        gattStarter.setConnector(this);

        gatt = bluetoothDevice.connectGatt(applicationContext, AUTO_CONNECT, new BluetoothGattCallback() {
            @Override
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            }

            @Override
            public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyRead(gatt, txPhy, rxPhy, status);
            }

            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
            }

            @Override
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorRead(gatt, descriptor, status);
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                super.onReadRemoteRssi(gatt, rssi, status);
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                super.onMtuChanged(gatt, mtu, status);
            }
        });
    }
}
