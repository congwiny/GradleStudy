package com.congwiny.gradle
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义插件
 */
class MyCustomPlugin implements Plugin<Project> {

    //每当引入一个Plugin时，Gradle都会执行其apply方法
    /**
     * 插件被引入时要执行的方法
     * @param project 引入当前插件的Project
     */
    @Override
    void apply(Project project) {
        println "Hello Plugin ${project.name}"
    }
}
