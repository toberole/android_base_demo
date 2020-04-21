package com.cat.zeus.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("******************************")
        System.out.println("******** hello plugin ********")
        System.out.println("******************************")
    }
}