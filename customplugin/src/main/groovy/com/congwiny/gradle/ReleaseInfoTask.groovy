package com.congwiny.gradle

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 自定义Task，实现维护版本信息功能
 */
class ReleaseInfoTask extends DefaultTask{

    ReleaseInfoTask() {
        group = 'congwiny'
        description = 'update the release info'
    }

    /**
     * 被@TaskAction注解标注的方法，执行于gradle执行阶段
     */
    @TaskAction
    void doAction() {
        updateInfo()
    }

    //真正的将Extension类中的信息呢，写入指定文件中
    private void updateInfo() {
        //获取将要写入的信息
        String versionCodeMsg = project.extensions.
                customReleaseInfo.versionCode
        String versionNameMsg = project.extensions.
                customReleaseInfo.versionName
        String versionInfoMsg = project.extensions.
                customReleaseInfo.versionInfo
        String fileName = project.extensions.
                customReleaseInfo.fileName
        def file = project.file(fileName)
        if (!file.exists()){
            file.createNewFile()
        }
        //将实体对象写入到xml文件中
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text.size() <= 0) {
            //没有内容
            xmlBuilder.releases {
                release {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            //直接写入
            file.withWriter { writer -> writer.append(sw.toString())
            }
        } else {
            //已有其它版本内容
            xmlBuilder.release {
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
            }
            //插入到最后一行前面
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { line, index ->
                    if (index != lengths) {
                        writer.append(line + '\r\n')
                    } else if (index == lengths) {
                        writer.append('\r\r\n' + sw.toString() + '\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }
    }
}