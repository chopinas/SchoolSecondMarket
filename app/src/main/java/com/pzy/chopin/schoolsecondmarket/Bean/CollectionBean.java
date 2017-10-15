package com.pzy.chopin.schoolsecondmarket.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chopin on 2017/9/18.
 */

public class CollectionBean {

    /**
     * code : 701
     * message : return collection
     * data : [{"id":"12","pName":"二手单反","iPrice":"1000.00","pDesc":"七八成新，拍出来效果也不错，需要的可以联系","pubTime":"1504783544","uId":"2","proImg":[{"albumPath":"792b4308db9952b303144b52d0a08b33.jpg","pid":"12"}],"username":"bbb","email":"bbbbb"},{"id":"14","pName":"毛概","iPrice":"15.00","pDesc":"上学期新买的书，还很新，可以省不少钱.","pubTime":"1504956454","uId":"6","proImg":[{"albumPath":"f1cae0621d0ef2ff8c58a18aec15958a.jpg","pid":"14"}],"username":"ddd","email":"ddd"},{"id":"13","pName":"标准日本语","iPrice":"40.00","pDesc":"还是新买的书，比较干净，重点也有些画出来了，附带笔记","pubTime":"1504952798","uId":"6","proImg":[{"albumPath":"166a0e5da46da5c0b7b225ff699fc90b.jpg","pid":"13"}],"username":"ddd","email":"ddd"}]
     */

    private int code;
    private String message;
    @SerializedName("data")
    private List<GoodsBean> goodsbean;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GoodsBean> getGoodsbean() {
        return goodsbean;
    }

    public void setGoodsbean(List<GoodsBean> goodsbean) {
        this.goodsbean = goodsbean;
    }

    public static class GoodsBean {
        /**
         * id : 12
         * pName : 二手单反
         * iPrice : 1000.00
         * pDesc : 七八成新，拍出来效果也不错，需要的可以联系
         * pubTime : 1504783544
         * uId : 2
         * proImg : [{"albumPath":"792b4308db9952b303144b52d0a08b33.jpg","pid":"12"}]
         * username : bbb
         * email : bbbbb
         */

        private String id;
        private String pName;
        private String iPrice;
        private String pDesc;
        private String pubTime;
        private String uId;
        private String username;
        private String email;
        private List<ProImgBean> proImg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getIPrice() {
            return iPrice;
        }

        public void setIPrice(String iPrice) {
            this.iPrice = iPrice;
        }

        public String getPDesc() {
            return pDesc;
        }

        public void setPDesc(String pDesc) {
            this.pDesc = pDesc;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<ProImgBean> getProImg() {
            return proImg;
        }

        public void setProImg(List<ProImgBean> proImg) {
            this.proImg = proImg;
        }

        public static class ProImgBean {
            /**
             * albumPath : 792b4308db9952b303144b52d0a08b33.jpg
             * pid : 12
             */

            private String albumPath;
            private String pid;

            public String getAlbumPath() {
                return albumPath;
            }

            public void setAlbumPath(String albumPath) {
                this.albumPath = albumPath;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }
        }
    }
}
