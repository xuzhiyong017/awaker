package com.future.awaker.video;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.Video;
import com.future.awaker.data.source.NewRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class VideoViewModel extends BaseListViewModel {

    public ObservableList<Video> videos = new ObservableArrayList<>();

    private NewRepository newRepository;

    private String token;
    private int cat;

    public VideoViewModel(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    public void setToken(String token, int cat) {
        this.token = token;
        this.cat = cat;
    }

    @Override
    public void fetchData(boolean isRefresh, int page) {
        if (TextUtils.isEmpty(token) || isRunning.get()) {
            return;
        }
        disposable.add(newRepository.getSpecialList(token, page, cat)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<Video> newList = httpResult.getData();
                    notifyEmpty(newList);

                    if (isRefresh) {
                        videos.clear();
                    }
                    videos.addAll(httpResult.getData());
                })
                .subscribe());
    }


}
