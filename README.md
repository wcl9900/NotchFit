# NotchFit
   ##智能刘海屏适配库
   ##优点：使用简单，智能刘海判断，一键式刘海参数回调，无需考虑机型差异，适配O版本和P版本。
 
         刘海屏存在于系统O版本、P版本及以上版本，由于google P版本刘海api推出较晚，导致O版本刘海屏的不同厂商的机型有各自不同的
     适配方式，再者不同手机系统刘海区域使用设置也有所不同，有的默认开启，有的默认关闭，导致刘海屏适配比较繁琐和复杂，此库
     处理了以上所有的问题，并智能判断刘海区域的使用方式和刘海参数的获取，给开发者更好的刘海适配体验！
      
   
# 引入方式
    Gradle
    repositories {
        jcenter()
    }
    compile 'com.wcl.notchfit:notchfit:1.0'
    
# 使用方式

   1.沉浸适配
   
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
        
              
   3.黑条填充。
   应用场景：如果不想对刘海布局进行具体适配，可统一通过此方法进行刘海区域黑条填充
   
   ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_black.jpg)
     
     NotchFit.fitUnUse(this);
     
    更多使用方式请查看demo