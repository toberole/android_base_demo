package com.xiaoge.org.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobTestService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        Log.d("hyc"," start job "+jobId);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        Log.d("hyc"," stop job "+jobId);
        return false;
    }
}

