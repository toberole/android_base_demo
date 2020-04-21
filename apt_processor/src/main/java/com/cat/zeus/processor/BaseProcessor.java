package com.cat.zeus.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;

public abstract class BaseProcessor extends AbstractProcessor {
    protected Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        log("Processor#init");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        log("Processor#getSupportedSourceVersion");
        return SourceVersion.latestSupported();
    }

    protected void log(String msg) {
        log(null, msg);
    }

    protected void log(String tag, String msg) {
        if (null == tag) {
            System.out.println(msg);
        } else {
            System.out.println(tag + ": " + msg);
        }
    }
}
