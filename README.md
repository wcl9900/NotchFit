# NotchFit 智能刘海屏适配库

**优点：使用简单，刘海参数智能判断，一键式刘海参数回调，无需考虑机型和系统版本差异，适配O版本和P及以上版本。**
    
   刘海屏存在于系统O版本、P版本及以上版本，由于google P版本刘海api推出较晚，导致O版本刘海屏的不同厂商的机型有各自不同的
 适配方式，再者不同手机系统刘海区域使用设置也有所不同，有的默认开启，有的默认关闭，导致刘海屏适配比较繁琐和复杂，此库
 处理了以上所有的问题，并智能判断刘海区域的使用方式和刘海参数的获取，给开发者更好的刘海适配体验！
    
# 引入方式
    Gradle
    repositories {
        jcenter()
    }
    compile 'com.wcl.notchfit:notchfit:1.4.2'
# 特点
   1.支持多种刘海区域参数获取
   
        private String manufacturer; //手机厂商
        private boolean notchEnable; //是否显示刘海屏
        private NotchPosition notchPosition; //刘海所在屏幕位置（上、左、右）
        private int notchWidth;           //刘海屏宽度px
        private int notchHeight;          //刘海屏高度px
    
   2.刘海区域位置检测适配
   
   1).顶部适配 
   
   ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_top.jpg)

   2).左部适配
   
   ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_left.jpg)

   3).右部适配
   
   ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_right.jpg)
   
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
        
              
   3.黑条填充
   <p>应用场景：不想对app中的具体全屏UI做针对性刘海适配，可通过此接口一键黑条填充适配！！！
   
   ![name](https://raw.githubusercontent.com/wcl9900/NotchFit/master/image_notch_fit_black.jpg)
     
     NotchFit.fitUnUse(this);
     
    更多使用方式请查看demo
