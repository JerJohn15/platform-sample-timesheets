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
package com.haulmont.timesheets.gui.rejection;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextArea;

import javax.inject.Inject;

/**
 * @author gorelov
 */
public class RejectionReason extends AbstractWindow {

    public static final String CONFIRM_ACTION_AD = "confirm";
    public static final String CANCEL_ACTION_AD = "cancel";

    @Inject
    protected TextArea rejectionReasonText;

    public void confirm() {
        close(CONFIRM_ACTION_AD);
    }

    public void cancel() {
        rejectionReasonText.setValue(null);
        close(CANCEL_ACTION_AD);
    }

    public String getRejectionReason() {
        return rejectionReasonText.getValue();
    }
}