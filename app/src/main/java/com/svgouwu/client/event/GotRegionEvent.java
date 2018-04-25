package com.svgouwu.client.event;

/**
 * 有好货Selected-详情点赞，列表刷新。
 */
public class GotRegionEvent {
    public String regionId;
    public String regionName;

    public GotRegionEvent(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }
}
