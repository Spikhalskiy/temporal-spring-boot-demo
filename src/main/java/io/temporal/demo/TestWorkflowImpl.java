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

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

@WorkflowImpl(taskQueues = "UnitTest")
public class TestWorkflowImpl implements App.TestWorkflow {
  private final Logger log = Workflow.getLogger(TestWorkflowImpl.class);
  private final App.TestActivity activityStub = Workflow.newActivityStub(
      App.TestActivity.class,
          ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(1))
          .validateAndBuildWithDefaults());
  @Override
  public String execute(String input) {
    log.debug("Execute workflow with input '{}'", input);
    return activityStub.execute("activityInput");
  }
}
