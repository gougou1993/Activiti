/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.runtime.api.event.internal;

import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.runtime.api.event.TaskCandidateGroupRemoved;
import org.activiti.runtime.api.event.impl.ToTaskCandidateGroupRemovedConverter;
import org.activiti.runtime.api.event.listener.TaskRuntimeEventListener;

public class TaskCandidateGroupRemovedListenerDelegate implements ActivitiEventListener {

    private final List<TaskRuntimeEventListener<TaskCandidateGroupRemoved>> listeners;

    private final ToTaskCandidateGroupRemovedConverter converter;

    public TaskCandidateGroupRemovedListenerDelegate(List<TaskRuntimeEventListener<TaskCandidateGroupRemoved>> listeners,
                                                     ToTaskCandidateGroupRemovedConverter converter) {
        this.listeners = listeners;
        this.converter = converter;
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        if (event instanceof ActivitiEntityEvent) {
            converter.from((ActivitiEntityEvent) event)
                    .ifPresent(convertedEvent -> {
                        for (TaskRuntimeEventListener<TaskCandidateGroupRemoved> listener : listeners) {
                            listener.onEvent(convertedEvent);
                        }
                    });
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
