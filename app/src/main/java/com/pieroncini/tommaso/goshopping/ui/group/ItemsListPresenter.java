package com.pieroncini.tommaso.goshopping.ui.group;

import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.ui.base.BasePresenter;
import com.pieroncini.tommaso.goshopping.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Tommaso on 5/28/2017.
 */

public class ItemsListPresenter<V extends IItemsListView> extends BasePresenter<V>
        implements IItemsListPresenter<V> {

    @Inject
    public ItemsListPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
