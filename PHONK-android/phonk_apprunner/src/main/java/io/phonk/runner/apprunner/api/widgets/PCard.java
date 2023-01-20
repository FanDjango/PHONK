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
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.phonk.runner.R;
import io.phonk.runner.apidoc.annotation.PhonkClass;
import io.phonk.runner.apidoc.annotation.PhonkMethod;
import io.phonk.runner.apidoc.annotation.PhonkMethodParam;

@PhonkClass
public class PCard extends LinearLayout {

    final LinearLayout cardLl;
    final TextView title;
    private final Context c;

    public PCard(Context context) {
        super(context);
        c = context;

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pwidget_card, this, true);

        title = findViewById(R.id.cardTitle);
        cardLl = findViewById(R.id.cardWidgets);

    }

    @Override

    @PhonkMethod(description = "Adds a new view", example = "")
    @PhonkMethodParam(params = {"view"})
    public void addView(View v) {
        v.setAlpha(0);
        v.animate().alpha(1).setDuration(500).setStartDelay(100);

        // v.setPadding(0, 0, 0, AndroidUtils.pixelsToDp(c, 3));
        cardLl.addView(v);
    }


    @PhonkMethod(description = "Add a row of n columns", example = "")
    @PhonkMethodParam(params = {"columnNumber"})
    public PRow addRow(int n) {
        return new PRow(c, cardLl, n);
    }


    @PhonkMethod(description = "Set the title of the card", example = "")
    @PhonkMethodParam(params = {"text"})
    public void setTitle(String text) {
        if (!text.isEmpty()) {
            title.setVisibility(View.VISIBLE);
            title.setText(text);
        }
    }


    @PhonkMethod(description = "Changes the title color", example = "")
    @PhonkMethodParam(params = {"colorHex"})
    public void setTitleColor(String color) {
        title.setBackgroundColor(Color.parseColor(color));
    }


    @PhonkMethod(description = "Card with horizontal views", example = "")
    @PhonkMethodParam(params = {""})
    public void setHorizontal() {
        LinearLayout ll = findViewById(R.id.cardWidgets);
        ll.setOrientation(LinearLayout.HORIZONTAL);
    }


    @PhonkMethod(description = "Card with vertical views", example = "")
    @PhonkMethodParam(params = {""})
    public void setVertical() {
        LinearLayout ll = findViewById(R.id.cardWidgets);
        ll.setOrientation(LinearLayout.VERTICAL);
    }

}
