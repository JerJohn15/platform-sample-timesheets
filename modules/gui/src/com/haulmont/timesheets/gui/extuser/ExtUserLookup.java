
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

package com.haulmont.timesheets.gui.extuser;

import com.haulmont.cuba.gui.app.security.user.browse.UserLookup;

import java.util.Map;

/**
 * @author gorelov
 */
public class ExtUserLookup extends UserLookup {

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        getDialogParams().setWidth(800);
        getDialogParams().setHeight(500);
        getDialogParams().setResizable(true);
    }
}
