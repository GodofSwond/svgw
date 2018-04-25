package com.svgouwu.client.utils;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by topwolf on 2016/9/12.
 */
public class GlideConfiguration implements GlideModule {
    int diskSize = 1024 * 1024 * 100;
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); //sd卡中

        // 默认内存和图片池大小
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
//        builder.setMemoryCache(new LruResourc、eCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
//        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 、默认
    }

    @Override
    public void registerComponents(Context context, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    //    @Override
//    public void registerComponents(Context context, Glide glide) {
////        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiWrapper.getInstance().getOkHttpClient()));
//        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
//    }
}
