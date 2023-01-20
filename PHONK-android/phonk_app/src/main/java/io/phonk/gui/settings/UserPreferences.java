/*
 * Part of Phonk http://www.phonk.io
 * A prototyping platform for Android devices
 *
 * Copyright (C) 2013 - 2017 Victor Diaz Barrales @victordiaz (Protocoder)
 * Copyright (C) 2017 - Victor Diaz Barrales @victordiaz (Phonk)
 *
 * Phonk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Phonk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Phonk. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.phonk.gui.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.phonk.runner.base.utils.FileIO;

public class UserPreferences {

    private static UserPreferences instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Map<String, Object> pref = null;

    public static UserPreferences getInstance() {
        if (instance == null) instance = new UserPreferences();
        return instance;
    }

    public Object get(String key) {
        if (pref == null) load();
        return pref.get(key);
    }

    public void load() {
        String json = FileIO.loadStringFromFile(PhonkSettings.getPrefUrl());
        if (json != null) {
            Type stringStringMap = new TypeToken<Map<String, Object>>() {}.getType();
            pref = gson.fromJson(json, stringStringMap);
        } else {
            pref = new HashMap<>();
        }

        // AndroidUtils.prettyPrintMap(pref);

        // fill with default properties
        resetIfEmpty("device_id", "12345");
        resetIfEmpty("screen_always_on", false);
        resetIfEmpty("servers_enabled_on_start", true);
        resetIfEmpty("device_wakeup_on_play", false);
        resetIfEmpty("advertise_mdns", false);
        resetIfEmpty("servers_mask_ip", false);
        resetIfEmpty("notify_new_version", true);
        resetIfEmpty("send_usage_log", false);
        resetIfEmpty("webide_mode", false);
        resetIfEmpty("launch_on_device_boot", false);
        resetIfEmpty("launch_script_on_app_launch", "");
        resetIfEmpty("launch_script_on_boot", "");
        resetIfEmpty("apps_in_list_mode", true);

        resetIfEmpty("background_color", new String[]{"#0000bb", "#00bb00"});
        resetIfEmpty("background_image", null);


    }

    private void resetIfEmpty(String key, Object obj) {
        if (!pref.containsKey(key)) pref.put(key, obj);

    }

    public void save() {
        String p = gson.toJson(pref);
        FileIO.saveStringToFile(p, PhonkSettings.getPrefUrl());
    }

    public UserPreferences set(String key, Object value) {
        if (pref == null) load();
        pref.put(key, value);

        return this;
    }
}
