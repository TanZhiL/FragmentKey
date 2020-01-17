### FragmentKey一款解决使用newInstance创建fragment定义key传值问题的apt框架

[![](https://jitpack.io/v/TanZhiL/FragmentKey.svg)](https://jitpack.io/#TanZhiL/FragmentKey)
### 更新日志：
###### v1.0.0 2020.1.17
* 第一次发布
#### 使用前:
```
   public TFragment newInstance(String username, String password, int age) {
        TFragment tFragment = new TFragment();
//传值
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putInt("age", age);
        tFragment.setArguments(bundle);
        return tFragment;
    }
	
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
		//使用
        Bundle arguments = getArguments();
        mUsername = arguments.getString("username");
        mPassword = arguments.getString("password");
        age = arguments.getInt("age");
    }
```
#### 使用后:
```  
	//定义
    @Inject
    public String mUsername;
    @Inject(name = "password1")
    public String mPassword;
    @Inject
    public int age;
	//传值
     TFragment2 tFragment = new TFragment2Key().get("姓名", "密码", 10);
	  
	@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		//使用
        Log.d(TAG, mUsername);
        Log.d(TAG, mPassword);
        Log.d(TAG, String.valueOf(age));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
```
可以看出此框架简化了传值过程,避免了使用key来传递数据带来的麻烦.
## Installation：
1.project.gradle
```java
    buildscript {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.1'
    }
}
```
2.app.gradle 添加
```java
dependencies {
   implementation 'com.github.TanZhiL.FragmentKey:fragmentkey:1.0.0'
    annotationProcessor 'com.github.TanZhiL.FragmentKey:fragmentkey-compiler:1.0.0'
}
```
## Usage：
1.在需要外部传递的字段上加上Inject注解
```java
   @Inject
    public String mUsername;
	//name为自定义key值,默认为字段名
    @Inject(name = "password1")
    public String mPassword;
    @Inject
    public int age;
```
2.创建fragment实例时使用xxxKey.get(...)方法;
```java
      TFragment2 tFragment = new TFragment2Key().get("姓名", "密码", 10);
```
##注意:
目前以支持bundle能传递的常见类型字段
```
   @Inject
    protected String s;
    @Inject
    protected Integer i;
    @Inject
    protected boolean b;
    @Inject
    protected float f;
    @Inject
    protected double d;
    @Inject
    protected long l;
    @Inject
    protected ArrayList<String> ls;
    @Inject
    protected ArrayList<Integer> li;
    @Inject
    protected ArrayList<Parcelable> lp;
    @Inject
    protected Serializable se;
    @Inject
    protected Parcelable p;
```

### 致谢
* 感谢所有开源库的大佬
* 借鉴大佬 https://github.com/JakeWharton/butterknife
### 问题反馈
欢迎加星，打call https://github.com/TanZhiL/FragmentKey
* email：1071931588@qq.com
### 关于作者
谭志龙
### 开源项目
* 快速切面编程开源库 https://github.com/TanZhiL/OkAspectj
* 一款解决使用newInstance创建fragment定义key传值问题的apt框架 https://github.com/TanZhiL/FragmentKey
* 高仿喜马拉雅听Android客户端 https://github.com/TanZhiL/Zhumulangma
* 基于面向对象设计的快速持久化框架 https://github.com/TanZhiL/RxPersistence
* 骨架屏弹性块 https://github.com/TanZhiL/SkeletonBlock
### License
```
Copyright (C)  tanzhilong FragmentKey Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
