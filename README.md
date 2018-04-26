# FreeApi
这个项目将采用MVP + Dagger-Android + BindKnife + Glide + Okhttp + retrofit 

所有的接口来自https://www.apiopen.top/

图片来自阿里巴巴的http://iconfont.cn/

将library上传到binary.com，装逼成功，哈哈
使用library加上

    implementation 'com.yeagle:library:1.0.0'

还需要在project下面的build.gradle加上

    repositories {
        maven { url "https://dl.bintray.com/yeagle/maven" }
    }
    
这里是因为我在binary.com 注册的地址错了，导致不能添加到jCenter
