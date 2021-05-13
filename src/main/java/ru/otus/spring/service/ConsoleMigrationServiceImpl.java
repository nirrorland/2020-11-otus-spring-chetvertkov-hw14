package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsoleMigrationServiceImpl implements ConsoleMigrationService {

    private final Job job;
    private final JobOperator jobOperator;
    private final JobLauncher jobLauncher;
    private final ConsoleIOService ioService;
    private final MongoTemplate mongoTemplate;
    private JobExecution jobExecution;

    @Override
    public void startMigration() {
        try {
            mongoTemplate.getDb().drop();
            jobExecution = jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
            ioService.out("Data migration successful");
        } catch (Exception e) {
            ioService.out(e.getMessage());
        }
    }

    @Override
    public void restartMigration() {
        try {
            jobOperator.restart(jobExecution.getJobId());
            ioService.out("Data migration successful (restarted)");
        } catch (Exception e) {
            ioService.out(e.getMessage());
        }
    }

}
