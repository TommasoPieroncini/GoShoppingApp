package com.pieroncini.tommaso.goshopping.data;

import android.content.Context;

import com.pieroncini.tommaso.goshopping.di.ApplicationContext;
import com.pieroncini.tommaso.goshopping.di.DatabaseInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Tommaso on 5/27/2017.
 */
@Singleton
public class DataManager implements IDataManager    {

    @Inject
    public DataManager(@ApplicationContext Context context,
                    @DatabaseInfo String dbName,
                    @DatabaseInfo Integer version) {
//        super(context, dbName, null, version);
    }
}
