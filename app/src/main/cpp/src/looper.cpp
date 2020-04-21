#include "looper.h"

#include <assert.h>
#include <jni.h>
#include <pthread.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <limits.h>
#include <semaphore.h>

#include <android/log.h>
#define TAG "NativeCodec-looper"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)


struct looper_message;

struct looper_message {
    int what;
    void *obj;
    looper_message *next;
    bool quit;
};

void* looper::trampoline(void* p) {
    ((looper*)p)->loop();
    return NULL;
}

looper::looper() {
    sem_init(&headdataavailable, 0, 0);
    sem_init(&headwriteprotect, 0, 1);
    pthread_attr_t attr;
    pthread_attr_init(&attr);

    pthread_create(&worker, &attr, trampoline, this);
    running = true;
}


looper::~looper() {
    if (running) {
        LOGV("Looper deleted while still running. Some messages will not be processed");
        quit();
    }
}

void looper::post(int what, void *data, bool flush) {
    looper_message *msg = new looper_message();
    msg->what = what;
    msg->obj = data;
    msg->next = NULL;
    msg->quit = false;
    addMsg(msg, flush);
}

void looper::addMsg(looper_message *msg, bool flush) {
    sem_wait(&headwriteprotect);
    looper_message *h = head;

    if (flush) {
        while(h) {
            looper_message *next = h->next;
            delete h;
            h = next;
        }
        h = NULL;
    }

    if (h) {
        while (h->next) {
            h = h->next;
        }
        h->next = msg;
    } else {
        head = msg;
    }
    LOGV("post msg %d", msg->what);
    sem_post(&headwriteprotect);
    sem_post(&headdataavailable);
}

void looper::loop() {
    while(true) {
        // wait for available message
        sem_wait(&headdataavailable);

        // get next available message
        sem_wait(&headwriteprotect);
        looper_message *msg = head;
        if (msg == NULL) {
            LOGV("no msg");
            sem_post(&headwriteprotect);
            continue;
        }
        head = msg->next;
        sem_post(&headwriteprotect);

        if (msg->quit) {
            LOGV("quitting");
            delete msg;
            return;
        }

        LOGV("processing msg %d", msg->what);

        handle(msg->what, msg->obj);
        delete msg;
    }
}

void looper::quit() {
    LOGV("quit");
    looper_message *msg = new looper_message();
    msg->what = 0;
    msg->obj = NULL;
    msg->next = NULL;
    msg->quit = true;
    addMsg(msg, false);
    void *retval;
    pthread_join(worker, &retval);
    sem_destroy(&headdataavailable);
    sem_destroy(&headwriteprotect);
    running = false;
}

void looper::handle(int what, void* obj) {
    LOGV("dropping msg %d %p", what, obj);
}
