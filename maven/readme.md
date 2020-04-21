发布项目到Maven仓库

方式一：使用Nexus搭建Maven私有仓库
    第一步：官网下载最新安装包 nexus-3.7.1-02-mac，解压后配置环境变量；
    第二步：使用命令 nexus start 启动服务（nexus stop结束）；
    第三步：进入 nexus 界面：http://127.0.0.1:8081/nexus；
    第四步：使用管理员用户登录（用户名：admin，密码：admin123）；
    第五步：找到左侧 Browser ，右侧会展示不同形式的 maven 仓库地址。

发布到Maven私有仓库
第一步：编写 maven upload 脚本 nexus-push.gradle 置于lib gradle文件同级目录；
第二步：在 library 目录下的 build.gradle 文件末尾添加上述maven脚本引用（Tips：添加到顶部会因为 compileSakVersion无法编译
apply from: './nexus-push.gradle'
第三步：命令行键入命令 gradle uploadArchives，开始打包上传，build successful 后，到 nexus 页面，找到脚本中 repository 定义的 url http://127.0.0.1:8081/nexus/repository/maven-releases/，点击 browser -> maven-release，可以看到已发布的项目；
第四步：内部项目引入配置；
    // 1. 项目根gradle文件添加 maven 仓库地址
    allprojects {
        repositories {
            jcenter()
    
            // 1. 发布到本地nexus私服
            maven {
                url "http://电脑IP:8081/nexus/repository/maven-releases/"
            }
        }
    }
    
    // 2. app 或者其他需要引入的项目 gradle 文件中添加依赖：
    compile 'com.coral.banner:bannerwrapperview:0.0.1'


方式二：发布到JCenter仓库
第一步：注册 bintray 账户并在用户中心获取用户名和Api key
（Tips：必须使用 gmail 账户注册，另外在注册时注意注册个人账号，organization账户需付费免费使用30天，若不小心注册成组织账户，重新开gmail账户注册）；

第二步：到 https://bintray.com 网站 Add New Repository 创建新仓库，一般选择 type 为 Maven；

第三步： 选择新建的 Maven 仓库，Add New Package，
输入稍后要 upload 的项目 git 地址和描述信息；

第四步：New Version 添加版本号；

第五步：在需要发布的 module 下添加 jcenter-push.gradle 文件配置发布信息；

第六步：在根目录 gradle 文件中添加 bintray 发布项目插件配置：
dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.3'
}

第七步：在 library 目录下的 build.gradle 文件末尾添加上述maven脚本引用；
apply from: './jcenter-push.gradle'

第八步：终端依次执行以下命令将项目发布到 bintray 下 maven 仓库；
./gradlew install
./gradlew bintrayUpload

第九步：将 bintray 下的 maven 仓库 include 到 JCenter 仓库，点击右侧 Add to JCenter 按钮，填上 message 信息，最后send。之后就是等待人工审核结果，审核通过会收到站内消息。

通过后根据配置信息，可在项目中进行引用。编译时，gradle会自动从 JCenter 仓库下载对应的依赖包到本地项目中。引用形式如下：

compile 'com.coral.banner:bannerwrapperview:1.0.0'






