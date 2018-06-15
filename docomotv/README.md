# docomoTV

## 工程目录

```java
android-project
  src
    ├──main
       ├── AndroidManifest.xml
       ├── com.docomotv  
       │   ├── module
       │   │   ├── base
       │   │   ├── auth（可选）
       │   │   └── ……
       │   ├── util
       │   │   ├── FileUtils.java (可选)
       │   │   ├── JsonUtils.java (可选)
       │   │   ├── DateUtils.java (可选)
       │   │   ├── ImageUtils.java (可选)
       │   │   └── ……
       │   ├── network
       │   │   ├── base（可选）
       │   │   ├── ItemService.java （可选）
       │   │   └── ……
       │   ├── database（可选）
       │   ├── widget（可选）
       │   ├── constant
       │   └── model（可选）
       ├── assets
       ├── res
       │   ├── anim
       │   ├── drawable
       │   │   ├── shape_common_btn_dialog_cancel_normal.xml（可选）
       │   │   └── ……
       │   ├── layout
       │   │   ├── setting_act.xml（可选）
       │   │   └── ……
       │   ├── mipmap
       │   │   ├── common_ic_arrow_left_white.png（可选）
       │   │   ├── ic_user_info_edit.png（可选）
       │   │   ├── bg_startup_bottom.png（可选）
       │   │   └── ……
       │   └── values
       │       ├── colors.xml
       │       ├── dimens.xml
       │       ├── strings.xml
       │       ├── styles.xml
       │       ├── strings_array.xml
       │       └── attrs.xml
    ......
```
> 注：上图中的目录结构以 AndroidStudio 中的典型目录结构为准，而且并没有对一个Android工程中的所有目录进行一一例举，只选择我们关心的开发代码目录进行说明。

## 命名规范
### 文件命名
#### Java文件
- Java文件名采用大驼峰命名法。
- Activity、Fragment、Service、Receiver、Adapter、Listener 类型的类文件名末尾相应的需要以 Activity、Fragment、Service、Receiver、Adapter、Listener结尾。
- 类的命名用名词，当类名由多个单词组成时，同一类别业务的类使用同一前缀。

#### xml文件
- 文件名全部小写，多个单词用下划线分隔。
- 布局文件（layout 文件夹下）  
  - 文件命名以模块划分，activity 与 fragment 的布局文件后面加入 act、frag 后缀  
    >正例：setting_act.xml  
    >反例：activity_setting.xml 

    > 注：之所以不推荐 `activity_setting.xml` 这种命名方式，是因为一旦采用这种方式，相同模块的布局文件，在目录中的排列会因为文件命名而分散到各处，比如 `SettingActivity` 画面中用到了两个布局文件，画面整体的布局和页面中的列表条目的布局，推荐如下的命名方式：`setting_act.xml`、`setting_act_lvi.xml`，这样的命名方式在集成开发环境中这两个文件会被排列在一起。如果采用如下方式的命名：`activity_setting.xml`、`item_list_setting.xml`，则在集成开发环境中两个文件就会被排列在距离较远的位置，不利于查找。

   - 布局文件可以使用约定的缩写以减小文件名称长度  
        常用布局文件名称缩写约定：  
        `activity -> act`   
        `fragment -> frag`  
        `list_view_item -> lvi`  
        `grid_view_item -> gvi`  


- 绘画文件（drawable 文件夹下）   
  文件命名以功能为前缀命名，表示这个绘画文件表示一个什么样的资源（本资源是形状？选择器？图标？等等），功能前缀后面加入表示该资源对应的逻辑名称。  
  常用前缀：`shape`（形状）、`selector`（选择器）、`icon`（图标）  
  例：  
  `shape_common_btn_dialog_cancel_normal.xml` —— 本资源为形状  
  `selector_btn_round_normal.xml` —— 本资源为选择器  
  `icon_common_loading.xml` —— 本资源为图标  
 
  > 注：
  - 用 `shape`、`selector`、`icon` 等做前缀的好处是，我们可以通过文件名一眼看出这个 drawable 的作用是什么，有利于在面对一些画面UI问题时帮助我们快速分析问题（比如某按钮按下时没有样式变化一类的问题）。
  - 之所以不把业务名称写在文件名前面，是因为在工程中往往有很多 drawable 资源是可以复用的，这时如果用业务来命名，会导致资源文件泛滥，比如登录画面中的确定按钮与支付画面中的确定按钮都需要用到一个相同的 selector，由于按照业务名称命名，我们不得不写两个一模一样的 selector 来对应代码的逻辑，`login_btn_selector.xml`，`payment_btn_selector.xml`，其实我们只需要写一个 selector，并给这个 selector 起一个泛化的名称，比如：`selector_common_primary_btn.xml`，然后为两个画面的按钮都指定此 selector 即可。所以，泛化可复用的 drawable 的命名，并复用之。当然，对于程序中没有复用或者需要特殊处理的 drawable 除外。


#### 图片文件
  - 文件名全部小写，多个单词用下划线分隔。
  - 通用图片加入 `common` 前缀，图标类图片加入 `ic` 前缀，背景类图片加入 `bg` 前缀，前缀后面名称按照图片表示的业务逻辑命名。
  > 注：图片资源加入前缀的目的主要是方便图片的查找与对图片的理解，例如，工程中被很多地方共用的一些图片，加入 `common` 前缀可以有效的防止一个图片被以不同的名称放入资源文件夹多次的问题。加入 `ic` 与 `bg` 前缀可以使我们快速的知道这个图片是一个图标类的小型图片还是一个背景类的大型图片。  

  例：  
  `common_ic_arrow_left_white.png` —— 通用类图标，左向箭头图标  
  `ic_user_info_edit.png` —— 非通用类图标，用户信息编辑图标  
  `bg_startup_bottom.png` —— 非通用类图片，启动画面底部背景图片

#### 资源文件（二进制文件、html、js等等）
  - 文件名全部小写，多个单词用下划线分隔。
  - 按照业务逻辑进行命名。  
      例：  
    `warning.ogg`  

> 注：如果是 html、js 等 web 资源文件，则相应的遵循 web 资源的命名规则。  

## 编码规范
### Java代码编码规范
1. 【强制】可读性第一原则。书写代码时，代码的可读性大于代码的简洁性。可读性第一，不要盲目追求代码的简洁，在团队协作中可读性好的代码才有更好的可维护性，而且一般情况下也具有更好的健壮性。代码的语法运用与书写顺序应以可读性优先。

2. 【强制】逻辑复杂、难以理解的代码必须加入注释，比如：复杂的业务逻辑、算法、控制流程等。注释的内容需要写清楚代码的功能，如果有必要还要写明为什么这么做，以便于后续维护。

3. 类、方法、参数、变量的命名  

    【强制】类名使用 UpperCamelCase 风格，必须遵从驼峰形式  
    >正例:MarcoPolo / UserDO / XmlService / TcpUdpDeal / TaPromotion  
    >反例:macroPolo / UserDo / XMLService / TCPUDPDeal / TAPromotion

    【强制】方法名、参数名、成员变量、局部变量都统一使用 lowerCamelCase 风格，必须遵从 驼峰形式。  
    >正例: localValue / getHttpMessage() / inputUserId

    【强制】常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长。   
    > 正例:MAX_STOCK_COUNT  
    > 反例:MAX_COUNT

4. 【强制】包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用 单数形式，但是类名如果有复数含义，类名可以使用复数形式。  
`例：应用工具类包名为 com.open.util、类名为 MessageUtils`

5. 【强制】在 if/else/for/while/do 语句中必须使用大括号。即使只有一行代码，避免采用单行的编码方式：  
>正例：if (condition) { statements; }  
>反例：if (condition) statements;

6. 【推荐】单行字符数限制不超过 120 个，超出需要换行，换行时遵循如下原则：
  - 第二行相对第一行缩进 4 个空格，从第三行开始，不再继续缩进，参考示例。
  - 运算符与下文一起换行。
  - 方法调用的点符号与下文一起换行。
  - 方法调用时，多个参数，需要换行时，在逗号后进行。
  - 在括号前不要换行，见反例。  

  >正例

  ```java
    StringBuffer sb = new StringBuffer(); 
    // 超过 120 个字符的情况下,换行缩进 4 个空格,点号和方法名称一起换行 
    sb.append("zi").append("xin")... 
        .append("huang")...
        .append("huang")...
        .append("huang");

  ```
  >反例

  ```java
    StringBuffer sb = new StringBuffer(); 
    // 超过 120 个字符的情况下,不要在括号前换行 
    sb.append("zi").append("xin")...append
        ("huang"); 
    // 参数很多的方法调用可能超过 120 个字符,不要在逗号前换行 
    method(args1, args2, args3, ... 
        , argsX);

  ```

### xml 布局文件编码规范

#### 命名规范
##### id 命名规范  
控件缩写_业务规则  
例：`et_user_name`、`btn_confirm`、`btn_reload`

##### 控件缩写
  为了方便控件 id 的命名，我们约定一些常用控件的缩写，控件缩写采用控件类名驼峰标识的首字母组合。  
  常用控件缩写：  

|控件名称|缩写名称|
|-|-|  
|TextView|tv|
|EditText|et|
|Button|btn（特殊）|
|ImageView|iv|
|ProgressBar|pb|
|ListView|lv|
|GridView|gv|
|ScrollView|sv|
|LinearLayout|ll|
|RelativeLayout|rl|
|FrameLayout|fl|
|RecyclerView|rv|

#### 属性排列
规范布局控件的属性排列顺序，可以提高布局代码的可读性，维护性，提高团队协作的效率。排列顺序如下：  
**1\. id**   
    （`id`）  
**2\. 宽高**  
    （`layout_width`、`layout_height`）  
**3\. 内外边距**  
    （`layout_paddingTop`、`layout_paddingBottom`、`layout_paddingLeft`、`layout_paddingRight`、`layout_marginTop`、`layout_marginBottom`、`layout_marginLeft`、`layout_marginRight`）  
**4\. 位置**  
    （`layout_above`、`layout_below`、`layout_toLeftOf`、`layout_toRgithOf`、`layout_gravity`、`layout_centerVertical`、`layout_centerInParent`、`layout_centerHorizontal` 等）  
**5\. 其他**  
    例： 
  - 文字相关
      （`text`、`textColor`、`textSize`）
  - 背景、图片资源
      （`background`、`src`）
  - 图片的缩放策略、文字的单行显示、超字数省略等等
      （`scaleType`、`singleLine`、`ellipsize` 等）  

**6\. 可见性**（`visibility`）  
    例：  
  ```xml
  <Button
    android:id="@+id/btn_reload"
    android:layout_width="100dp"
    android:layout_height="30dp"
    android:layout_marginTop="@dimen/common_margin_top"
    android:layout_below="@id/tv_blank_sub_title"
    android:layout_centerHorizontal="true"
    android:layout_centerVertical="true"
    android:text="@string/blankpage_net_work_btn_txt"
    android:textSize="@dimen/common_font_size_15"
    android:textColor="@color/common_warn_assist_font_color"
    android:background="@drawable/selector_btn_common_reload"
    android:gravity="center"
    android:visibility="gone"/>
  ```