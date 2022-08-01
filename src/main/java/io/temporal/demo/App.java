/*
 * Copyright (C) 2022 Temporal Technologies, Inc. All Rights Reserved.
 *
 * Copyright (C) 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this material except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.temporal.demo;

import io.temporal.activity.ActivityInterface;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    private final Logger log = LoggerFactory.getLogger(App.class);
    @Autowired WorkflowClient workflowClient;

    private void run() {
        TestWorkflow testWorkflow =
            workflowClient.newWorkflowStub(
                TestWorkflow.class, WorkflowOptions.newBuilder().setTaskQueue("UnitTest").build());
        log.info("Workflow result: {}", testWorkflow.execute("workflowInput"));
    }

    @ActivityInterface
    public interface TestActivity {
        String execute(String input);
    }

    @WorkflowInterface
    public interface TestWorkflow {
        @WorkflowMethod
        String execute(String input);
    }


    // Spring boot boilerplate
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.info("\n\n\n\n\n");
            run();
            log.info("\n\n\n\n\n");
            System.exit(0);
        };
    }
}


