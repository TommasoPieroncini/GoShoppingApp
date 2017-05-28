package com.pieroncini.tommaso.goshopping.di;

/**
 * Created by Tommaso on 5/27/2017.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseInfo {
}
