# NotchFit
    易用刘海屏适配库，使用简单，一键式刘海参数回调，支持市面所有主流机型，适配O版本和P版本
    
# 引入方式
    Gradle
    repositories {
        jcenter()
    }
    compile 'com.wcl.notchfit:notchfit:1.0'
    
# 使用方式
    1.沉浸式适配
     ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_translucent.jpg)
      
      NotchFit.fit(this, NotchScreenType.TRANSLUCENT, new OnNotchCallBack() {
            @Override
            public void onNotchReady(NotchProperty notchProperty) {
                if(notchProperty.isNotchEnable()){
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();
                    marginLayoutParams.topMargin = notchProperty.getNotchHeight();
                    listParent.requestLayout();
                }
            }
       });
       
    2.全屏适配   
     ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_fullscreen.jpg)
      
      NotchFit.fit(this, NotchScreenType.FULL_SCREEN, new OnNotchCallBack() {
                  @Override
                  public void onNotchReady(NotchProperty notchProperty) {
                      if(notchProperty.isNotchEnable()){
                          ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();
                          marginLayoutParams.topMargin = notchProperty.getNotchHeight();
                          listParent.requestLayout();
                      }
                  }
              });
              
     3.黑条填充
     ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_black.jpg)
     
     NotchFit.fitUnUse(this);
     
    更多使用方式请查看demo