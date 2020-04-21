package com.cat.zeus.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import org.gradle.api.Project

class MyTransform extends Transform{
    MyTransform(Project project) {

    }

    @Override
    String getName() {
        return MyTransform.simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return null
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return null
    }

    @Override
    boolean isIncremental() {
        return false
    }
}
