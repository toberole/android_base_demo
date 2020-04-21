#ifndef JNI_XXXX_DEMO_LOOPER_H
#define JNI_XXXX_DEMO_LOOPER_H

#include <pthread.h>
#include <semaphore.h>

struct looper_message;

class looper {
public:
    looper();
    looper& operator=(const looper& ) = delete;
    looper(looper&) = delete;
    virtual ~looper();

    void post(int what, void *data, bool flush = false);
    void quit();

    virtual void handle(int what, void *data);

private:
    void addMsg(looper_message *msg, bool flush);
    static void* trampoline(void* p);
    void loop();
    looper_message *head;
    pthread_t worker;
    sem_t headwriteprotect;
    sem_t headdataavailable;
    bool running;
};


#endif //JNI_XXXX_DEMO_LOOPER_H
