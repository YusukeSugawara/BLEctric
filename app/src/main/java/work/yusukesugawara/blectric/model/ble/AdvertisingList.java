package work.yusukesugawara.blectric.model.ble;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import work.yusukesugawara.blectric.function.ble.scan.Advertising;

public class AdvertisingList implements Observer<Advertising> {
    @NonNull
    private List<Advertising> inner = new ArrayList<>();

    @NonNull
    public List<Advertising> getInner() {
        return inner;
    }

    @NonNull
    private PublishSubject<AdvertisingList> updateStream = PublishSubject.create();

    @NonNull
    public Observable<AdvertisingList> getUpdateStream() {
        return updateStream;
    }

    public void clear() {
        inner.clear();
        updateStream.onNext(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Advertising advertising) {
        for (Advertising managed : inner) {
            if (managed.equals(advertising)) {
                managed.update(advertising);
                updateStream.onNext(this);
                return;
            }
        }

        inner.add(advertising);
        updateStream.onNext(this);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
