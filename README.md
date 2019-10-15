# SimpleDoubleRoundButton

No picture u say a j8!

少啰嗦，先看效果

![闭嘴看图](https://github.com/michael007js/SimpleDoubleRoundButton/blob/master/images/preview.gif "闭嘴看图")


## 项目介绍

一款纯自绘的双头按钮，按钮的功能能用代码实现的就用代码实现，不能用代码实现的突破天际也得用代码实现，从源头上杜绝各种xml样式文件的创建，保证项目的干净整洁

- [x] 支持圆角的动态调整
- [x] 支持动态调整边框大小/颜色
- [x] 支持按压变色
- [x] 支持按钮大小比例动态调整

重要的事情说三遍：
全程就一个文件！全程就一个文件！全程就一个文件！

[文件在此](https://github.com/michael007js/SimpleDoubleRoundButton/blob/master/app/src/main/java/com/sss/simpleDoubleRoundButton/SimpleDoubleRoundButton.java)


极大的降低耦合，想怎么折腾就怎么折腾

唯一的回调事件 ：

        setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
               //左侧按钮回调事件
               //view.setStrokeEnalbe(SimpleDoubleRoundButton.StrokeEnalbe.right);//如果你希望点击后启用右侧按钮的边框可以加上这行
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
               //右侧按钮回调事件
               //view.setStrokeEnalbe(SimpleDoubleRoundButton.StrokeEnalbe.left);//如果你希望点击后启用左侧按钮的边框可以加上这行
            }
        });


 ## 自定义属性介绍
 
        <!--左上角圆角-->
        <attr name="left_top_corners_radius" format="dimension" />
        <!--右上角圆角-->
        <attr name="right_top_corners_radius" format="dimension" />
        <!--左下角圆角-->
        <attr name="left_bottom_corners_radius" format="dimension" />
        <!--右下角圆角-->
        <attr name="right_bottom_corners_radius" format="dimension" />
        <!--左半部分背景颜色-->
        <attr name="left_background_color" format="color" />
        <!--右半部分背景颜色-->
        <attr name="right_background_color" format="color" />
        <!--左半部分触摸背景颜色-->
        <attr name="left_touch_background_color" format="color" />
        <!--右半部分触摸背景颜色-->
        <attr name="right_touch_background_color" format="color" />
        <!--边框宽度-->
        <attr name="stroke_width" format="dimension" />
        <!--边框颜色-->
        <attr name="stroke_rect_color" format="color" />
        <!--左右按钮绘制区域大小百分比-->
        <attr name="button_rect_percent" format="float" />
        <!--左边按钮文字-->
        <attr name="left_text" format="string" />
        <!--左边按钮文字颜色-->
        <attr name="left_text_color" format="color" />
        <!--左边按钮文字字体大小-->
        <attr name="left_text_size" format="dimension" />
        <!--右边按钮文字-->
        <attr name="right_text" format="string" />
        <!--右边按钮文字颜色-->
        <attr name="right_text_color" format="color" />
        <!--右边按钮文字字体大小-->
        <attr name="right_text_size" format="dimension" />
    
  
 over

 By SSS





