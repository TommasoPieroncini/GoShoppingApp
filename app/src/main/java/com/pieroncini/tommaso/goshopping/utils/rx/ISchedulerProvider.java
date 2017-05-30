package com.pieroncini.tommaso.goshopping.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Tommaso on 5/29/2017.
 */

public interface ISchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
