package work.yusukesugawara.blectric.ui;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import work.yusukesugawara.blectric.function.ble.connection.Connector;
import work.yusukesugawara.blectric.function.misc.Logg;
import work.yusukesugawara.blectric.model.ble.BLEModel;

public class ConnectionActivity extends AppCompatActivity {
    private static final String TAG = "ConnectionActivity";

    @Nullable
    private Connector connector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BluetoothDevice bluetoothDevice = getIntent().getParcelableExtra(BluetoothDevice.class.getName());
        if (bluetoothDevice == null) {
            Logg.e(TAG, "bluetoothDevice == null");
            finish();
            return;
        }

        connector = BLEModel.get(this).connectTo(bluetoothDevice);
    }

    @Override
    protected void onDestroy() {
        if (connector != null) {
            connector.cancel();
            connector = null;
        }

        super.onDestroy();
    }
}
