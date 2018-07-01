package com.congwiny.gradle
/**
 * 与自定义PLugin进行参数传递
 */
class ReleaseInfoExt{

    String versionCode
    String versionName
    String versionInfo
    String fileName


    @Override
    String toString() {
        """| versionCode = ${versionCode}
           | versionName = ${versionName}
           | versionInfo = ${versionInfo}
           | fileName = ${fileName}
        """.stripMargin()
    }
}