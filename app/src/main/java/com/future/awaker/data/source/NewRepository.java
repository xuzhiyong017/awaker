package com.future.awaker.data.source;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.HttpResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public final class NewRepository {

    private static NewRepository INSTANCE;

    private RemoteNewDataSource remoteDataSource = new RemoteNewDataSource();
    private LocalNewDataSource localNewDataSource = new LocalNewDataSource();

    private NewRepository() {
    }

    public static NewRepository get() {
        if (INSTANCE == null) {
            synchronized (NewRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    ///////////////////////
    // remote
    ///////////////////////


    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return remoteDataSource.getBanner(token, advType);
    }

    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }

    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return remoteDataSource.getUpNewsComments(token, newId);
    }

    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                               int page) {
        return remoteDataSource.getNewsComments(token, newId, page);
    }

    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotviewNewsAll(token, page, id);
    }

    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotNewsAll(token, page, id);
    }

    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return remoteDataSource.getHotComment(token);
    }

    ///////////////////////
    // local
    ///////////////////////


    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return localNewDataSource.getLocalRealm(clazz, map);
    }

    public void updateLocalRealm(RealmModel realmModel) {
        localNewDataSource.updateLocalRealm(realmModel);
    }

    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        localNewDataSource.deleteLocalRealm(clazz, map);
    }

    public void close() {
        localNewDataSource.close();
    }
}
