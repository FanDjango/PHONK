/*
 * Part of Phonk http://www.phonk.io
 * A prototyping platform for Android devices
 *
 * Copyright (C) 2013 - 2017 Victor Diaz Barrales @victordiaz (Protocoder)
 * Copyright (C) 2017 - Victor Diaz Barrales @victordiaz (Phonk)
 *
 * Phonk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Phonk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Phonk. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.phonk.runner.apprunner.api.widgets;

import android.content.Context;
import android.widget.ProgressBar;

import io.phonk.runner.apidoc.annotation.PhonkClass;
import io.phonk.runner.apprunner.AppRunner;

@PhonkClass
public class PLoader extends ProgressBar {

    public PLoader(AppRunner appRunner) {
        super(appRunner.getAppContext());
        // setProgressDrawable(getResources().getDrawable(R.drawable.ui_seekbar_progress));
    }

    public PLoader(Context a, int style) {
        super(a, null, style);
        this.setProgress(0);
    }

    public PLoader progress(float val) {
        this.setProgress((int) val);
        return this;
    }

}
