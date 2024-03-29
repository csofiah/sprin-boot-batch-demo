package com.example.demospringbatch.tasks;

import com.example.demospringbatch.listener.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobTask {

    private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(fixedRate = 10000  )
    public void executeJob() throws Exception{
        LOG.info("inicio "+ new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
