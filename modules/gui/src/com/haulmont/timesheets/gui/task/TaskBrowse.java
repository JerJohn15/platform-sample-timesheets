/*
 * Copyright (c) 2015 com.haulmont.ts.gui.task
 */
package com.haulmont.timesheets.gui.task;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractAction;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.timesheets.entity.Task;
import com.haulmont.timesheets.entity.TimeEntry;
import com.haulmont.timesheets.gui.ComponentsHelper;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

/**
 * @author gorelov
 */
public class TaskBrowse extends AbstractLookup {

    @Inject
    protected Table tasksTable;

    @Override
    public void init(Map<String, Object> params) {
        tasksTable.setStyleProvider(new Table.StyleProvider() {
            @Nullable
            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                if ("status".equals(property)) {
                    Task task = (Task) entity;
                    return ComponentsHelper.getTaskStatusStyle(task);
                }
                return null;
            }
        });

        tasksTable.addAction(new AbstractAction("createTimeEntry") {
            @Override
            public String getCaption() {
                return getMessage("caption.createTimeEntry");
            }

            @Override
            public void actionPerform(Component component) {
                Task selected = tasksTable.getSingleSelected();
                if (selected != null) {
                    TimeEntry timeEntry = new TimeEntry();
                    timeEntry.setTask(selected);
                    openEditor("ts$TimeEntry.edit", timeEntry, WindowManager.OpenType.DIALOG);
                }
            }
        });
    }
}