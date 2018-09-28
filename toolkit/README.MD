## Toolkit
   该工具主要是提供Android快速开发模板，
   包含常用的UI、网络请求、图片加载、数据缓存、apk更新等功能
 
 - v1.0.0 已完成了网络请求的封装
 
 ## 在model的build.gradle中配置依赖
		
	
	implementation 'com.github.tunshicanyue:Tookit:v1.0'
	
	compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8

    }
	
## 在项目的配build.gradle 中配置
			
			allprojects {
			repositories {
				google()
				jcenter()
				maven { url 'https://jitpack.io' }
			}
		}
