package com.pzy.chopin.schoolsecondmarket.Bean;

import java.util.List;

/**
 * Created by Chopin on 2017/9/12.
 */

public class SearchBean {

    /**
     * code : 601
     * message : get pros success
     * data : [{"type":"日用品","data":{"id":"10","cId":"6","uId":"aaa","pName":"洗衣粉","iPrice":"10.00","pDesc":"还有大半袋，保质期时间久","pubTime":"1504783059","proImg":[{"albumPath":"3619d42d6743d5325b7a60eabd5ff227.jpg","pid":"10"}],"cName":"aaa@qq.com"}},{"type":"电器","data":{"id":"11","cId":"5","uId":"bbb","pName":"洗衣机","iPrice":"250.00","pDesc":"买了不到一年，方便，洗涤脱水效果都很棒","pubTime":"1504783363","proImg":[{"albumPath":"d5ad3feeff8dc45fee2072c0b8dbead3.jpg","pid":"11"}],"cName":"bbbbb"}}]
     */

    private int code;
    private String message;
    private List<CateBean> data;

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

    public List<CateBean> getData() {
        return data;
    }

    public void setData(List<CateBean> data) {
        this.data = data;
    }

    public static class CateBean {
        /**
         * type : 日用品
         * data : {"id":"10","cId":"6","uId":"aaa","pName":"洗衣粉","iPrice":"10.00","pDesc":"还有大半袋，保质期时间久","pubTime":"1504783059","proImg":[{"albumPath":"3619d42d6743d5325b7a60eabd5ff227.jpg","pid":"10"}],"cName":"aaa@qq.com"}
         */

        private String type;
        private GoodsBean data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public GoodsBean getData() {
            return data;
        }

        public void setData(GoodsBean data) {
            this.data = data;
        }

        public static class GoodsBean {
            /**
             * id : 10
             * cId : 6
             * uId : aaa
             * pName : 洗衣粉
             * iPrice : 10.00
             * pDesc : 还有大半袋，保质期时间久
             * pubTime : 1504783059
             * proImg : [{"albumPath":"3619d42d6743d5325b7a60eabd5ff227.jpg","pid":"10"}]
             * cName : aaa@qq.com
             */

            private String id;
            private String cId;
            private String uId;
            private String pName;
            private String iPrice;
            private String pDesc;
            private String pubTime;
            private String cName;
            private List<ProImgBean> proImg;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public List<ProImgBean> getProImg() {
                return proImg;
            }

            public void setProImg(List<ProImgBean> proImg) {
                this.proImg = proImg;
            }

            public static class ProImgBean {
                /**
                 * albumPath : 3619d42d6743d5325b7a60eabd5ff227.jpg
                 * pid : 10
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
