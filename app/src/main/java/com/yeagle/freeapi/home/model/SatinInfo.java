package com.yeagle.freeapi.home.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeagle on 2018/4/17.
 * 改网页的作者将这个名为 Satin，一查意思好想笑，意思是：缎子，通假字嘛
 * 我也用这个好了，实在翻译不了这个段子
 */
public class SatinInfo {
    /**
     * "type": "41",
     "text": "专治各种不服！哈哈哈",
     "user_id": "17932228",
     "name": "不交作业别想睡了",
     "screen_name": "不交作业别想睡了",
     "profile_image": "http://wimg.spriteapp.cn/profile/large/2017/11/02/59fada75627d6_mini.jpg",
     "created_at": "2018-04-17 09:38:57",
     "create_time": null,
     "passtime": "2018-04-17 09:38:57",
     "love": "178",
     "hate": "6",
     "comment": "22",
     "repost": "16",
     "bookmark": "3",
     "bimageuri": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "voiceuri": null,
     "voicetime": null,
     "voicelength": null,
     "status": "4",
     "theme_id": "124",
     "theme_name": "萌宠",
     "theme_type": "1",
     "videouri": "http://wvideo.spriteapp.cn/video/2018/0416/5ad3f40003ff8_wpd.mp4",
     "videotime": 19,
     "original_pid": "0",
     "cache_version": 2,
     "playcount": "8933",
     "playfcount": "1294",
     "cai": "6",
     "weixin_url": null,
     "image1": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "image2": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "is_gif": false,
     "image0": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "image_small": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "cdn_img": "http://wimg.spriteapp.cn/picture/2018/0416/5ad3f40003ff8_wpd.jpg",
     "width": "540",
     "height": "960",
     "tag": "",
     "t": 1523929137,
     "ding": "178",
     "favourite": "3",
     "top_cmt": null,
     "themes": null
     */
    public int type;
    public String text;
    public int user_id;

    public String name;
    public String screen_name;
    public String profile_image;

    public String created_at;
    public String create_time;
    public String passtime;

    @SerializedName("love")
    public int like; // 这个地方按道理是like才对，哈哈 love 其实就是顶，下面是踩嘛
    public int hate; //

    public int comment;
    public int repost;
    public int bookmark;

    public String bimageuri;
    public String voiceuri;
    public int voicetime;
    public int voicelength;

    public int status;

    public int theme_id;
    public String theme_name;
    public int theme_type;

    public String videouri;
    public int videotime;
    public int original_pid;

    public int cache_version;
    public int playcount;
    public int playfcount;

    public int cai;
    public String weixin_url;

    public String image1;
    public String image2;

    public boolean is_gif;
    public String image0;
    public String image_small;
    public String cdn_img;

    public int width;
    public int height;

    public String tag;
    public int t;
    public int ding;

    public String top_cmt;
    public String themes;
}
