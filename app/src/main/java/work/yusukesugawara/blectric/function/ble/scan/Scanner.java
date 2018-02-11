package work.yusukesugawara.blectric.function.ble.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.support.annotation.NonNull;

import io.reactivex.subjects.PublishSubject;

public class Scanner {
    @NonNull
    private BluetoothLeScanner bluetoothLeScanner;

    public Scanner() {
        bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
    }

    public void start() {
        bluetoothLeScanner.startScan(
                null,
                new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build(),
                scanCallback
        );
    }

    public void stop() {

    }

    @NonNull
    private PublishSubject<Advertising> receivedAdvertisingStream = PublishSubject.create();

    @NonNull
    public PublishSubject<Advertising> getReceivedAdvertisingStream() {
        return receivedAdvertisingStream;
    }

    @NonNull
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord == null) {
                return;
            }

            Advertising advertising = Advertising.create(result.getDevice(), result.getRssi(), scanRecord.getBytes());
            receivedAdvertisingStream.onNext(advertising);
        }
    };
}
