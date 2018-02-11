package work.yusukesugawara.blectric.function.ble.scan;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

public class Advertising {
    @NonNull
    private BluetoothDevice device;
    private int rssi;
    @NonNull
    private byte[] data;

    public Advertising(@NonNull BluetoothDevice device, int rssi, @NonNull byte[] data) {
        this.device = device;
        this.rssi = rssi;
        this.data = data;
    }

    @NonNull
    public static Advertising create(@NonNull BluetoothDevice device, int rssi, @NonNull byte[] bytes) {
        return new Advertising(device, rssi, bytes);
    }

    @NonNull
    public String getAdvertiserName() {
        return device.getAddress();
    }

    @NonNull
    public BluetoothDevice getDevice() {
        return device;
    }

    public int getRssi() {
        return rssi;
    }

    @NonNull
    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Advertising) {
            Advertising o = (Advertising) obj;
            return device.equals(o.device);
        }
        else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return device.hashCode();
    }

    public void update(@NonNull Advertising other) {
        device = other.device;
        rssi = other.rssi;
        data = other.data;
    }
}
