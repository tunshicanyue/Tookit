package com.xb.canyue;

import java.util.List;

/**
 * Created by admin on 2018/7/24.
 */

public class WebTestBean  {


    /**
     * code : 200
     * message : 成功
     * data : {"introduction":["6666666","777777","888888"],"items":[{"name":"11","marketing_campaign_id":"3","from":"1","from_relate_id":"1","number":"101","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"11"},{"name":"22","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"1230","condition":"3","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"12"},{"name":"33","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"101","condition":"1","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"13"},{"name":"44","marketing_campaign_id":"3","from":"3","from_relate_id":"0","number":"500","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"14"},{"name":"55","marketing_campaign_id":"3","from":"1","from_relate_id":"1","number":"400","condition":"1","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:11","created_at":"2018-07-20 09:57:51","item_id":"15"},{"name":"66","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"600","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:11","created_at":"2018-07-20 09:57:51","item_id":"16"}],"chance":"0","reason":"1","history":[{"user":"132****5766","award":"33"},{"user":"136****6151","award":"33"},{"user":"156****7345","award":"22"},{"user":"136****3862","award":"66"},{"user":"182****8847","award":"33"},{"user":"134****6816","award":"33"},{"user":"183****7746","award":"33"},{"user":"187****1368","award":"55"},{"user":"151****1300","award":"11"},{"user":"183****7262","award":"66"},{"user":"132****9050","award":"55"},{"user":"131****3299","award":"22"},{"user":"132****2553","award":"66"},{"user":"180****1522","award":"22"},{"user":"186****655"},{"user":"150****9010","award":"33"},{"user":"132****9997","award":"66"},{"user":"150****1732","award":"22"},{"user":"135****2545","award":"55"},{"user":"134****9806","award":"33"},{"user":"138****2670","award":"55"},{"user":"138****5602","award":"66"},{"user":"131****8727","award":"55"},{"user":"153****4056","award":"22"},{"user":"183****5671","award":"33"},{"user":"135****1115","award":"66"},{"user":"189****9513","award":"55"},{"user":"159****3118","award":"55"},{"user":"153****2521","award":"66"},{"user":"151****1982","award":"11"},{"user":"137****8429","award":"33"},{"user":"136****6700","award":"11"},{"user":"155****6630","award":"33"},{"user":"186****6254","award":"22"},{"user":"151****1526","award":"66"},{"user":"150****3219","award":"33"},{"user":"137****4532","award":"11"},{"user":"182****4916","award":"33"},{"user":"186****5151","award":"11"},{"user":"159****1871","award":"66"},{"user":"186****8220","award":"33"},{"user":"156****7651","award":"55"},{"user":"150****8384","award":"11"},{"user":"137****8992","award":"22"},{"user":"158****7451","award":"55"},{"user":"182****8867","award":"55"},{"user":"131****8125","award":"66"},{"user":"185****7528","award":"66"},{"user":"132****5431","award":"33"},{"user":"139****1619","award":"33"},{"user":"137****5040","award":"33"},{"user":"139****3811","award":"22"},{"user":"135****6455","award":"55"},{"user":"183****1034","award":"55"},{"user":"130****5775","award":"33"},{"user":"182****3335","award":"55"},{"user":"156****7822","award":"22"},{"user":"186****4497","award":"22"},{"user":"130****4598","award":"55"},{"user":"155****2871","award":"55"},{"user":"133****9617","award":"11"},{"user":"181****8522","award":"55"},{"user":"187****2074","award":"22"},{"user":"152****9797","award":"22"},{"user":"158****8256","award":"66"},{"user":"189****2729","award":"33"},{"user":"159****7882","award":"11"},{"user":"132****3562","award":"55"},{"user":"186****4592","award":"33"},{"user":"134****9277","award":"33"},{"user":"152****4916","award":"55"},{"user":"133****4513","award":"66"}],"addition":{}}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * introduction : ["6666666","777777","888888"]
         * items : [{"name":"11","marketing_campaign_id":"3","from":"1","from_relate_id":"1","number":"101","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"11"},{"name":"22","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"1230","condition":"3","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"12"},{"name":"33","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"101","condition":"1","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"13"},{"name":"44","marketing_campaign_id":"3","from":"3","from_relate_id":"0","number":"500","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:10","created_at":"2018-07-20 09:57:51","item_id":"14"},{"name":"55","marketing_campaign_id":"3","from":"1","from_relate_id":"1","number":"400","condition":"1","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:11","created_at":"2018-07-20 09:57:51","item_id":"15"},{"name":"66","marketing_campaign_id":"3","from":"2","from_relate_id":"1","number":"600","condition":"2","image":"http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg","updated_at":"2018-07-23 09:55:11","created_at":"2018-07-20 09:57:51","item_id":"16"}]
         * chance : 0
         * reason : 1
         * history : [{"user":"132****5766","award":"33"},{"user":"136****6151","award":"33"},{"user":"156****7345","award":"22"},{"user":"136****3862","award":"66"},{"user":"182****8847","award":"33"},{"user":"134****6816","award":"33"},{"user":"183****7746","award":"33"},{"user":"187****1368","award":"55"},{"user":"151****1300","award":"11"},{"user":"183****7262","award":"66"},{"user":"132****9050","award":"55"},{"user":"131****3299","award":"22"},{"user":"132****2553","award":"66"},{"user":"180****1522","award":"22"},{"user":"186****655"},{"user":"150****9010","award":"33"},{"user":"132****9997","award":"66"},{"user":"150****1732","award":"22"},{"user":"135****2545","award":"55"},{"user":"134****9806","award":"33"},{"user":"138****2670","award":"55"},{"user":"138****5602","award":"66"},{"user":"131****8727","award":"55"},{"user":"153****4056","award":"22"},{"user":"183****5671","award":"33"},{"user":"135****1115","award":"66"},{"user":"189****9513","award":"55"},{"user":"159****3118","award":"55"},{"user":"153****2521","award":"66"},{"user":"151****1982","award":"11"},{"user":"137****8429","award":"33"},{"user":"136****6700","award":"11"},{"user":"155****6630","award":"33"},{"user":"186****6254","award":"22"},{"user":"151****1526","award":"66"},{"user":"150****3219","award":"33"},{"user":"137****4532","award":"11"},{"user":"182****4916","award":"33"},{"user":"186****5151","award":"11"},{"user":"159****1871","award":"66"},{"user":"186****8220","award":"33"},{"user":"156****7651","award":"55"},{"user":"150****8384","award":"11"},{"user":"137****8992","award":"22"},{"user":"158****7451","award":"55"},{"user":"182****8867","award":"55"},{"user":"131****8125","award":"66"},{"user":"185****7528","award":"66"},{"user":"132****5431","award":"33"},{"user":"139****1619","award":"33"},{"user":"137****5040","award":"33"},{"user":"139****3811","award":"22"},{"user":"135****6455","award":"55"},{"user":"183****1034","award":"55"},{"user":"130****5775","award":"33"},{"user":"182****3335","award":"55"},{"user":"156****7822","award":"22"},{"user":"186****4497","award":"22"},{"user":"130****4598","award":"55"},{"user":"155****2871","award":"55"},{"user":"133****9617","award":"11"},{"user":"181****8522","award":"55"},{"user":"187****2074","award":"22"},{"user":"152****9797","award":"22"},{"user":"158****8256","award":"66"},{"user":"189****2729","award":"33"},{"user":"159****7882","award":"11"},{"user":"132****3562","award":"55"},{"user":"186****4592","award":"33"},{"user":"134****9277","award":"33"},{"user":"152****4916","award":"55"},{"user":"133****4513","award":"66"}]
         * addition : {}
         */

        private String chance;
        private String reason;
        private AdditionBean addition;
        private List<String> introduction;
        private List<ItemsBean> items;
        private List<HistoryBean> history;

        public String getChance() {
            return chance;
        }

        public void setChance(String chance) {
            this.chance = chance;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public AdditionBean getAddition() {
            return addition;
        }

        public void setAddition(AdditionBean addition) {
            this.addition = addition;
        }

        public List<String> getIntroduction() {
            return introduction;
        }

        public void setIntroduction(List<String> introduction) {
            this.introduction = introduction;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public List<HistoryBean> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryBean> history) {
            this.history = history;
        }

        public static class AdditionBean {
        }

        public static class ItemsBean {
            /**
             * name : 11
             * marketing_campaign_id : 3
             * from : 1
             * from_relate_id : 1
             * number : 101
             * condition : 2
             * image : http://182.150.20.24:10012/zhph_commonServices/webservice/hdfs/showFile/zhph/zhh/php/test/201805//d043a106ed9d477c9ece3329e3055b3b.jpeg
             * updated_at : 2018-07-23 09:55:10
             * created_at : 2018-07-20 09:57:51
             * item_id : 11
             */

            private String name;
            private String marketing_campaign_id;
            private String from;
            private String from_relate_id;
            private String number;
            private String condition;
            private String image;
            private String updated_at;
            private String created_at;
            private String item_id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMarketing_campaign_id() {
                return marketing_campaign_id;
            }

            public void setMarketing_campaign_id(String marketing_campaign_id) {
                this.marketing_campaign_id = marketing_campaign_id;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getFrom_relate_id() {
                return from_relate_id;
            }

            public void setFrom_relate_id(String from_relate_id) {
                this.from_relate_id = from_relate_id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }
        }

        public static class HistoryBean {
            /**
             * user : 132****5766
             * award : 33
             */

            private String user;
            private String award;

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public String getAward() {
                return award;
            }

            public void setAward(String award) {
                this.award = award;
            }
        }
    }
}
