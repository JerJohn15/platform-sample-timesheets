
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

package com.haulmont.timesheets.global;

import com.haulmont.timesheets.entity.DayOfWeek;
import com.haulmont.timesheets.service.ProjectsService;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.Date;

/**
 * @author gorelov
 */
@ManagedBean(WorkdaysTools.NAME)
public class WorkdaysTools {

    public static final String NAME = "ts_WorkdaysTools";

    @Inject
    protected WorkTimeConfigBean workTimeConfigBean;
    @Inject
    protected ProjectsService projectsService;

    public boolean isHoliday(Date date) {
        return !projectsService.getHolidaysForPeriod(date, date).isEmpty();
    }

    public boolean isWeekend(Date date) {
        DayOfWeek day = DayOfWeek.fromCalendarDay(DateTimeUtils.getCalendarDayOfWeek(date));
        return workTimeConfigBean.getWeekends().contains(day);
    }

    public boolean isWorkday(Date date) {
        return !isWeekend(date) && !isHoliday(date);
    }
}
