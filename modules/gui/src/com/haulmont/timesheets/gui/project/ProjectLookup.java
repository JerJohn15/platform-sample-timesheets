/*
 * Copyright (c) 2015 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haulmont.timesheets.gui.project;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TreeTable;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.haulmont.timesheets.entity.Project;
import com.haulmont.timesheets.gui.util.ComponentsHelper;
import com.haulmont.timesheets.service.ProjectsService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author gorelov
 */
public class ProjectLookup extends AbstractLookup {

    @Inject
    protected HierarchicalDatasource<Project, UUID> projectsDs;
    @Inject
    protected ProjectsService projectsService;
    @Inject
    protected TreeTable projectsTable;

    @Override
    public void init(Map<String, Object> params) {
        getDialogParams().setWidth(800);
        getDialogParams().setHeight(500);
        getDialogParams().setResizable(true);

        Project project = (Project) params.get("parentProject");
        if (project != null) {
            projectsDs.excludeItem(project);
            List<Project> childrenProjects = projectsService.getProjectChildren(project);
            for (Project child : childrenProjects) {
                projectsDs.excludeItem(child);
            }
        }

        project = (Project) params.get("exclude");
        if (project != null) {
            projectsDs.excludeItem(project);
        }

        projectsTable.setStyleProvider(new Table.StyleProvider() {
            @Nullable
            @Override
            public String getStyleName(Entity entity, String property) {
                if ("status".equals(property)) {
                    Project project = (Project) entity;
                    return ComponentsHelper.getProjectStatusStyle(project);
                }
                return null;
            }
        });
    }

    @Override
    public void ready() {
        projectsTable.expandAll();
    }
}