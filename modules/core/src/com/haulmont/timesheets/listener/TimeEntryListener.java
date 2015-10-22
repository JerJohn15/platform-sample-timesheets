/*
 * Copyright (c) 2015 com.haulmont.timesheets.listener
 */
package com.haulmont.timesheets.listener;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.timesheets.config.WorkTimeConfig;
import com.haulmont.timesheets.entity.TimeEntry;
import com.haulmont.timesheets.entity.TimeEntryStatus;
import com.haulmont.timesheets.exception.ClosedPeriodException;
import com.haulmont.timesheets.global.EntityDeletionException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author degtyarjov
 */
@ManagedBean("ts_TimeEntryListener")
public class TimeEntryListener implements BeforeInsertEntityListener<TimeEntry>,
        BeforeUpdateEntityListener<TimeEntry>,
        BeforeDeleteEntityListener<TimeEntry> {

    protected static final BigDecimal MINUTES_IN_HOUR = BigDecimal.valueOf(60);

    @Inject
    protected Persistence persistence;

    @Inject
    protected WorkTimeConfig workTimeConfig;

    @Override
    public void onBeforeInsert(TimeEntry entity) {
        if (entity.getTask() != null) {
            entity.setTaskName(entity.getTask().getName());
        }

        if (entity.getTimeInMinutes() != null) {
            setTimeInHours(entity);
        }

        checkClosedPeriods(entity.getDate());
    }

    @Override
    public void onBeforeUpdate(TimeEntry entity) {
        if (entity.getTask() != null) {
            entity.setTaskName(entity.getTask().getName());
        }

        if (entity.getTimeInMinutes() != null) {
            setTimeInHours(entity);
        }

        checkClosedPeriods(entity.getDate());
    }

    @Override
    public void onBeforeDelete(TimeEntry entity) {
        if (entity.getStatus() != null && TimeEntryStatus.CLOSED.equals(entity.getStatus())) {
            throw new EntityDeletionException("Deletion of closed TimeEntry");
        }

        checkClosedPeriods(entity.getDate());
    }

    protected void setTimeInHours(TimeEntry entity) {
        BigDecimal minutes = BigDecimal.valueOf(entity.getTimeInMinutes()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        entity.setTimeInHours(minutes.divide(MINUTES_IN_HOUR, BigDecimal.ROUND_HALF_DOWN));
    }

    protected void checkClosedPeriods(Date date) {
        Date openPeriodStart = workTimeConfig.getOpenPeriodStart();

        if (openPeriodStart != null && date.before(openPeriodStart)) {
            throw new ClosedPeriodException("You can not modify time entries in closed periods");
        }
    }
}