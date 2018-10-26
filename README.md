# NotchFit
    易用刘海屏适配库，使用简单，一键式刘海参数回调，支持市面所有主流机型，适配O版本和P版本
    
# 引入方式
    Gradle
    repositories {
        jcenter()
    }
    compile 'com.wcl.notchfit:notchfit:1.0'
    
# 使用方式
    NotchFit.fit(activity, NotchScreenType.TRANSLUCENT, new OnNotchCallBack() {
                @Override
                public void onNotchReady(NotchProperty notchProperty) {
                    //刘海参数回调
                }
            });
            
    更多使用方式请查看demo