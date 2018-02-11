package work.yusukesugawara.blectric.function.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

abstract public class ValueObserver<T> implements Observer<T> {
    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    abstract public void onSubscribe(Disposable d);

    abstract public void onNext(T t);
}
