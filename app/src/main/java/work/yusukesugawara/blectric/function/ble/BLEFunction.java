package work.yusukesugawara.blectric.function.ble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.subjects.PublishSubject;
import work.yusukesugawara.blectric.function.ble.connection.Connector;
import work.yusukesugawara.blectric.function.ble.connection.GattStarter;
import work.yusukesugawara.blectric.function.ble.scan.Advertising;
import work.yusukesugawara.blectric.function.ble.scan.Scanner;
import work.yusukesugawara.blectric.function.misc.Logg;
import work.yusukesugawara.blectric.function.misc.Str;

public class BLEFunction {
    private static final String TAG = "BLEFunction";

    @SuppressLint("StaticFieldLeak")
    private static BLEFunction object = null;

    @NonNull
    private Context applicationContext;

    private BLEFunction(@NonNull Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    public static BLEFunction get(@NonNull Context context) {
        if (object == null) {
            object = new BLEFunction(context);
        }

        return object;
    }

    @NonNull
    public static String stateString(int state) {
        switch (state) {
            case BluetoothGatt.STATE_DISCONNECTED:
                return "DISCONNECTED";
            case BluetoothGatt.STATE_DISCONNECTING:
                return "DISCONNECTING";
            case BluetoothGatt.STATE_CONNECTING:
                return "CONNECTING";
            case BluetoothGatt.STATE_CONNECTED:
                return "CONNECTED";
            default:
                return Str.format("Unknown(%d)", state);
        }
    }

    public static void disposeGatt(@Nullable BluetoothGatt gatt) {
        Logg.e(TAG, "disposeGatt: gatt=%s", gatt);
        if (gatt != null) {
            gatt.close();
        }
    }


    @NonNull
    private Scanner scanner = new Scanner();

    public void startScan() {
        scanner.start();
    }

    @NonNull
    public PublishSubject<Advertising> getReceivedAdvertisingStream() {
        return scanner.getReceivedAdvertisingStream();
    }

    public void stopScan() {
        scanner.stop();
    }

    @NonNull
    public Connector connectTo(@NonNull BluetoothDevice bluetoothDevice, @NonNull final Connector.Delegate delegate, @NonNull GattStarter gattStarter) {
        Connector connector = new Connector(applicationContext, bluetoothDevice, delegate);

        connector.connect(gattStarter);

        return connector;
    }
}
