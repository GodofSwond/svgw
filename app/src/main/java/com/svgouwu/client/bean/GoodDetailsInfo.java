package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by whq on 2017/9/26.
 */

public class GoodDetailsInfo implements Serializable {

    /**
     * code : 0000
     * msg : 成功
     * data : {"goodsId":8063,"qrcode":"<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIQAAACEAQMAAABrihHkAAAABlBMVEX///8AAABVwtN+AAABZUlEQVRIie3Vva2DMBAH8EMu3NkLWPIa7lgpLJDAArCSO69hyQuYzgXK/x2RUPKax/HqWCnIr0D35YPoe04O0LabLqNWQBVLpuAMuaGVkeRSnkEtaXu0srRLst0C3bW6KNk2Z64Jx1zWlh+/sjgVrk+Zw/77qNip7K8yGlPMH+04lWx6fokHPGIVi6/aWZQpuQEklu2+l/QVQJKLG5Kfe6zRj7qKJXeNbFNrLEsksXCCZSYscMMFUROnGXmeM/VyyYbIBM/1MSQXjH3hebZQs5YL/0XlkoI6yAVj8As4Zn/0QiKK67M0ekQcvZAI15N7lztwL+TiwdMVOc331AmEZ6zU3tmUuyQXcElNj9eoVLGoFTxjzjYcd0ciewCkGfnqkVh4JwDYLO+6JBfeP+rJMadsUxUL7zrMQa17zHRF1MQBa94nl8TPe7L8IJc95hqcjdtxTyWyf5vu/TYA83sb/0O+54/zA3ZkstgKMBfzAAAAAElFTkSuQmCC\">","avgGrade":3.4615384615385,"goodsName":"逸佳空气净化凝露","price":{"price":"298.00","spelPrice":"","isSpel":0},"description":"逸佳空气净化凝露<br />\r\n<br />\r\n商品品牌：逸佳<br />\r\n商品规格：185g/盒<br />\r\n商品重量：1.6kg<br />\r\n<p>\r\n\t商品产地：天津\r\n<\/p>\r\n<p>\r\n\t<img src=\"http://www.svgouwu.com/data/files/store_1582/goods_59/201704241430599930.jpg\" alt=\"1.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_59/201704241430592080.jpg\" alt=\"2.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_60/201704241431005104.jpg\" alt=\"3.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_61/201704241431011882.jpg\" alt=\"4.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_61/201704241431011456.jpg\" alt=\"5.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_62/201704241431025098.jpg\" alt=\"6.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_62/201704241431026561.jpg\" alt=\"7.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_63/201704241431036995.jpg\" alt=\"8.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_65/201704241431054589.jpg\" alt=\"9.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_65/201704241431056676.jpg\" alt=\"10.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_90/201707191451301092.jpg\" alt=\"201704241431066955.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_67/201704241431071768.jpg\" alt=\"12.jpg\" /><img src=\"http://www.svgouwu.com/data/files/store_1582/goods_67/201704241431074423.jpg\" alt=\"13.jpg\" /> \r\n<\/p>","imgs":[{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_43/201704241430431877.jpg","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_43/small_201704241430431877.jpg"},{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_46/201704241430465175.JPG","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_46/small_201704241430465175.JPG"},{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_48/201704241430489871.JPG","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_48/small_201704241430489871.JPG"}],"stock":997,"sku":"1100014644","regionName":"中国\t北京市\t顺义区","address":"天津市南开区鼓楼尚佳新苑12-1-1003","sales":100,"comment":9}
     */

    /**
     * goodsId : 8063
     * qrcode : <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIQAAACEAQMAAABrihHkAAAABlBMVEX///8AAABVwtN+AAABZUlEQVRIie3Vva2DMBAH8EMu3NkLWPIa7lgpLJDAArCSO69hyQuYzgXK/x2RUPKax/HqWCnIr0D35YPoe04O0LabLqNWQBVLpuAMuaGVkeRSnkEtaXu0srRLst0C3bW6KNk2Z64Jx1zWlh+/sjgVrk+Zw/77qNip7K8yGlPMH+04lWx6fokHPGIVi6/aWZQpuQEklu2+l/QVQJKLG5Kfe6zRj7qKJXeNbFNrLEsksXCCZSYscMMFUROnGXmeM/VyyYbIBM/1MSQXjH3hebZQs5YL/0XlkoI6yAVj8As4Zn/0QiKK67M0ekQcvZAI15N7lztwL+TiwdMVOc331AmEZ6zU3tmUuyQXcElNj9eoVLGoFTxjzjYcd0ciewCkGfnqkVh4JwDYLO+6JBfeP+rJMadsUxUL7zrMQa17zHRF1MQBa94nl8TPe7L8IJc95hqcjdtxTyWyf5vu/TYA83sb/0O+54/zA3ZkstgKMBfzAAAAAElFTkSuQmCC">
     * avgGrade : 3.4615384615385
     * goodsName : 逸佳空气净化凝露
     * price : {"price":"298.00","spelPrice":"","isSpel":0}
     * description : 逸佳空气净化凝露<br />
     * <br />
     * 商品品牌：逸佳<br />
     * 商品规格：185g/盒<br />
     * 商品重量：1.6kg<br />
     * <p>
     * 商品产地：天津
     * </p>
     * <p>
     * <img src="http://www.svgouwu.com/data/files/store_1582/goods_59/201704241430599930.jpg" alt="1.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_59/201704241430592080.jpg" alt="2.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_60/201704241431005104.jpg" alt="3.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_61/201704241431011882.jpg" alt="4.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_61/201704241431011456.jpg" alt="5.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_62/201704241431025098.jpg" alt="6.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_62/201704241431026561.jpg" alt="7.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_63/201704241431036995.jpg" alt="8.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_65/201704241431054589.jpg" alt="9.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_65/201704241431056676.jpg" alt="10.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_90/201707191451301092.jpg" alt="201704241431066955.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_67/201704241431071768.jpg" alt="12.jpg" /><img src="http://www.svgouwu.com/data/files/store_1582/goods_67/201704241431074423.jpg" alt="13.jpg" />
     * </p>
     * imgs : [{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_43/201704241430431877.jpg","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_43/small_201704241430431877.jpg"},{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_46/201704241430465175.JPG","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_46/small_201704241430465175.JPG"},{"imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_48/201704241430489871.JPG","mini_imageUrl":"http://www.svgouwu.com/data/files/store_1582/goods_48/small_201704241430489871.JPG"}]
     * stock : 997
     * sku : 1100014644
     * regionName : 中国	北京市	顺义区
     * address : 天津市南开区鼓楼尚佳新苑12-1-1003
     * sales : 100
     * comment : 9
     */

    private int goodsId;
    private String qrcode;
    private double avgGrade;
    private String goodsName;
    private PriceBean price;
    private String description;
    private int stock;
    private String sku;
    private String regionName;
    private String address;
    private int sales;
    private String shareRebate;
    private int comment;
    private List<ImgsBean> imgs;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public PriceBean getPrice() {
        return price;
    }

    public void setPrice(PriceBean price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getShareRebate() {
        return shareRebate;
    }

    public void setShareRebate(String shareRebate) {
        this.shareRebate = shareRebate;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "goodsId=" + goodsId +
                ", qrcode='" + qrcode + '\'' +
                ", avgGrade=" + avgGrade +
                ", goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", sku='" + sku + '\'' +
                ", regionName='" + regionName + '\'' +
                ", address='" + address + '\'' +
                ", sales=" + sales +
                ", comment=" + comment +
                ", imgs=" + imgs +
                '}';
    }

    public static class PriceBean {
        /**
         * price : 298.00
         * spelPrice :
         * isSpel : 0
         */

        private String price;
        private String spelPrice;
        private int isSpel;
        private String activePrice;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpelPrice() {
            return spelPrice;
        }

        public void setSpelPrice(String spelPrice) {
            this.spelPrice = spelPrice;
        }

        public int getIsSpel() {
            return isSpel;
        }

        public void setIsSpel(int isSpel) {
            this.isSpel = isSpel;
        }

        public String getActivePrice() {
            return activePrice;
        }

        public void setActivePrice(String activePrice) {
            this.activePrice = activePrice;
        }

        @Override
        public String toString() {
            return "PriceBean{" +
                    "price='" + price + '\'' +
                    ", spelPrice='" + spelPrice + '\'' +
                    ", isSpel=" + isSpel +
                    '}';
        }
    }

    public static class ImgsBean {
        /**
         * imageUrl : http://www.svgouwu.com/data/files/store_1582/goods_43/201704241430431877.jpg
         * mini_imageUrl : http://www.svgouwu.com/data/files/store_1582/goods_43/small_201704241430431877.jpg
         */

        private String imageUrl;
        private String mini_imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMini_imageUrl() {
            return mini_imageUrl;
        }

        public void setMini_imageUrl(String mini_imageUrl) {
            this.mini_imageUrl = mini_imageUrl;
        }

        @Override
        public String toString() {
            return "ImgsBean{" +
                    "imageUrl='" + imageUrl + '\'' +
                    ", mini_imageUrl='" + mini_imageUrl + '\'' +
                    '}';
        }
    }
}
