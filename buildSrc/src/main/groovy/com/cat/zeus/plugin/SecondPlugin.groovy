package com.cat.zeus.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SecondPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        System.out.println("***** SecondPlugin *****")
    }
}