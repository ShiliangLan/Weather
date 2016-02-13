package com.example.lanshiliang.myweather.model;

import java.util.List;

/**
 * Created by lanshiliang on 2016/2/13.
 */
public class WeatherInfo {

    /**
     * icon : http://static.etouch.cn/apps/weather/alarm_icon-1/warning_cold_blue-1.png
     * pub_time : 2016-02-12 16:30:00
     * desc : 北京市气象台12日16时30分发布寒潮蓝色预警, 受强冷空气影响，预计12日夜间有雨夹雪，山区有小到中雪、局地大雪，会出现道路结冰现象。13至14日有5、6级偏北风，阵风可达8级左右；平原地区最低气温将下降至-6℃左右，山区最低气温达-10℃左右，请注意防范。
     * degree : 蓝色
     * standard : 暂无
     * details : 北京市气象台发布寒潮蓝色预警
     * location : 北京市
     * suggestion : 暂无
     * type : 寒潮
     * city_range : 10101
     */

    private AlarmEntity alarm;
    /**
     * shidu : 28%
     * wp : 4级
     * temp : 2
     * wd : 西北风
     */

    private ObserveEntity observe;
    /**
     * time : 15:00:00
     * co : 0
     * mp :
     * so2 : 3
     * o3 : 70
     * no2 : 12
     * quality : 优
     * aqi : 32
     * pm10 : 11
     * suggest : 各类人群可自由活动
     * pm25 : 7
     */

    private EvnEntity evn;
    /**
     * html_url : http://yun.rili.cn/tianqi/1o4zw4.html
     * status : 1000
     * post_count : 2378
     * up_time : 15:50
     * citykey : 101010100
     * post_id : 501391
     * city : 北京
     */

    private MetaEntity meta;
    /**
     * sunset : 17:45
     * night : {"wthr":"雨夹雪","bgPic":"http://static.etouch.cn/suishen/weather/nighticyrain.jpg","wp":"3-4级","type":6,"wd":"北风"}
     * sunrise : 07:14
     * high : 10
     * day : {"wthr":"小雨","bgPic":"http://static.etouch.cn/suishen/weather/rain.jpg","wp":"微风","type":8,"wd":"无持续风向"}
     * date : 20160212
     * low : 2
     */

    private List<ForecastEntity> forecast;

    public void setAlarm(AlarmEntity alarm) {
        this.alarm = alarm;
    }

    public void setObserve(ObserveEntity observe) {
        this.observe = observe;
    }

    public void setEvn(EvnEntity evn) {
        this.evn = evn;
    }

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }

    public void setForecast(List<ForecastEntity> forecast) {
        this.forecast = forecast;
    }

    public AlarmEntity getAlarm() {
        return alarm;
    }

    public ObserveEntity getObserve() {
        return observe;
    }

    public EvnEntity getEvn() {
        return evn;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public List<ForecastEntity> getForecast() {
        return forecast;
    }

    public static class AlarmEntity {
        private String icon;
        private String degree;
        private String type;

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public String getDegree() {
            return degree;
        }

        public String getType() {
            return type;
        }
    }

    public static class ObserveEntity {
        private String shidu;
        private String wp;
        private int temp;
        private String wd;

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }

        public String getShidu() {
            return shidu;
        }

        public String getWp() {
            return wp;
        }

        public int getTemp() {
            return temp;
        }

        public String getWd() {
            return wd;
        }
    }

    public static class EvnEntity {
        private String quality;
        private int aqi;
        private int pm10;
        private String suggest;
        private int pm25;

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getQuality() {
            return quality;
        }

        public int getAqi() {
            return aqi;
        }

        public int getPm10() {
            return pm10;
        }

        public String getSuggest() {
            return suggest;
        }

        public int getPm25() {
            return pm25;
        }
    }

    public static class MetaEntity {
        private String up_time;
        private String city;

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUp_time() {
            return up_time;
        }

        public String getCity() {
            return city;
        }
    }

    public static class ForecastEntity {
        private String sunset;
        /**
         * wthr : 雨夹雪
         * bgPic : http://static.etouch.cn/suishen/weather/nighticyrain.jpg
         * wp : 3-4级
         * type : 6
         * wd : 北风
         */

        private NightEntity night;
        private String sunrise;
        private int high;
        /**
         * wthr : 小雨
         * bgPic : http://static.etouch.cn/suishen/weather/rain.jpg
         * wp : 微风
         * type : 8
         * wd : 无持续风向
         */

        private DayEntity day;
        private String date;
        private int low;

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public void setNight(NightEntity night) {
            this.night = night;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public void setHigh(int high) {
            this.high = high;
        }

        public void setDay(DayEntity day) {
            this.day = day;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public String getSunset() {
            return sunset;
        }

        public NightEntity getNight() {
            return night;
        }

        public String getSunrise() {
            return sunrise;
        }

        public int getHigh() {
            return high;
        }

        public DayEntity getDay() {
            return day;
        }

        public String getDate() {
            return date;
        }

        public int getLow() {
            return low;
        }

        public static class NightEntity {
            private String wthr;
            private String bgPic;
            private String wp;
            private int type;
            private String wd;

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getWthr() {
                return wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public String getWp() {
                return wp;
            }

            public int getType() {
                return type;
            }

            public String getWd() {
                return wd;
            }
        }

        public static class DayEntity {
            private String wthr;
            private String bgPic;
            private String wp;
            private int type;
            private String wd;

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getWthr() {
                return wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public String getWp() {
                return wp;
            }

            public int getType() {
                return type;
            }

            public String getWd() {
                return wd;
            }
        }
    }
}
