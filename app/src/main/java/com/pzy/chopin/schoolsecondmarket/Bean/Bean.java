package com.pzy.chopin.schoolsecondmarket.Bean;

import java.util.List;

/**
 * Created by Chopin on 2017/9/8.
 */

public class Bean  {

    /**
     * code : 601
     * message : get pros success
     * data : [{"type":"电器","data":[{"id":"8","pName":"台灯","iPrice":"10.00","pDesc":"质量好，充电一小时，能用一晚","pubTime":"1504782802","cName":"电器","cId":"5","uId":"1","proImg":[{"albumPath":"e8ecb5237b357b79c7269510af91d7b1.jpg","pid":"8"}]},{"id":"11","pName":"洗衣机","iPrice":"250.00","pDesc":"买了不到一年，方便，洗涤脱水效果都很棒","pubTime":"1504783363","cName":"电器","cId":"5","uId":"2","proImg":[{"albumPath":"d5ad3feeff8dc45fee2072c0b8dbead3.jpg","pid":"11"}]}]},{"type":"日用品","data":[{"id":"10","pName":"洗衣粉","iPrice":"10.00","pDesc":"还有大半袋，保质期时间久","pubTime":"1504783059","cName":"日用品","cId":"6","uId":"1","proImg":[{"albumPath":"3619d42d6743d5325b7a60eabd5ff227.jpg","pid":"10"}]}]},{"type":"数码产品","data":[{"id":"12","pName":"二手单反","iPrice":"1000.00","pDesc":"七八成新，拍出来效果也不错，需要的可以联系","pubTime":"1504783544","cName":"数码产品","cId":"8","uId":"2","proImg":[{"albumPath":"792b4308db9952b303144b52d0a08b33.jpg","pid":"12"}]}]},{"type":"书籍","data":[{"id":"9","pName":"c++面向对象","iPrice":"25.00","pDesc":"几乎全新，附赠笔记","pubTime":"1504782947","cName":"书籍","cId":"9","uId":"1","proImg":[{"albumPath":"64388cd50450716985daff6c3d4beab4.jpg","pid":"9"}]}]}]
     */

    private int code;
    private String message;
    private List<CateGoodBean> data;

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

    public List<CateGoodBean> getData() {
        return data;
    }

    public void setData(List<CateGoodBean> data) {
        this.data = data;
    }

    public static class CateGoodBean {
        /**
         * type : 电器
         * data : [{"id":"8","pName":"台灯","iPrice":"10.00","pDesc":"质量好，充电一小时，能用一晚","pubTime":"1504782802","cName":"电器","cId":"5","uId":"1","proImg":[{"albumPath":"e8ecb5237b357b79c7269510af91d7b1.jpg","pid":"8"}]},{"id":"11","pName":"洗衣机","iPrice":"250.00","pDesc":"买了不到一年，方便，洗涤脱水效果都很棒","pubTime":"1504783363","cName":"电器","cId":"5","uId":"2","proImg":[{"albumPath":"d5ad3feeff8dc45fee2072c0b8dbead3.jpg","pid":"11"}]}]
         */

        private String type;
        private List<GoodsBean> data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<GoodsBean> getData() {
            return data;
        }

        public void setData(List<GoodsBean> data) {
            this.data = data;
        }

        public static class GoodsBean {
            /**
             * id : 8
             * pName : 台灯
             * iPrice : 10.00
             * pDesc : 质量好，充电一小时，能用一晚
             * pubTime : 1504782802
             * cName : 电器
             * cId : 5
             * uId : 1
             * proImg : [{"albumPath":"e8ecb5237b357b79c7269510af91d7b1.jpg","pid":"8"}]
             */

            private String id;
            private String pName;
            private String iPrice;
            private String pDesc;
            private String pubTime;
            private String cName;
            private String cId;
            private String uId;
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

            public String getCName() {
                return cName;
            }

            public void setCName(String cName) {
                this.cName = cName;
            }

            public String getCId() {
                return cId;
            }

            public void setCId(String cId) {
                this.cId = cId;
            }

            public String getUId() {
                return uId;
            }

            public void setUId(String uId) {
                this.uId = uId;
            }

            public List<ProImgBean> getProImg() {
                return proImg;
            }

            public void setProImg(List<ProImgBean> proImg) {
                this.proImg = proImg;
            }

            public static class ProImgBean {
                /**
                 * albumPath : e8ecb5237b357b79c7269510af91d7b1.jpg
                 * pid : 8
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
}
