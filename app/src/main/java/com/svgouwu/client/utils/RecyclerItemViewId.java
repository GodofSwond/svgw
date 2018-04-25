package com.svgouwu.client.utils;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface RecyclerItemViewId {
    int value();
}
