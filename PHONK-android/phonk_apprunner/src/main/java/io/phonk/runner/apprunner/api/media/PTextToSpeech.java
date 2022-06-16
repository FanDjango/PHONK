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

package io.phonk.runner.apprunner.api.media;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.phonk.runner.apidoc.annotation.PhonkClass;
import io.phonk.runner.apprunner.AppRunner;
import io.phonk.runner.base.utils.MLog;

@PhonkClass
public class PTextToSpeech {
    private static final String TAG = PTextToSpeech.class.getSimpleName();

    TextToSpeech mTts;

    public PTextToSpeech(AppRunner appRunner) throws InterruptedException {

        mTts = new TextToSpeech(appRunner.getAppContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTts.setLanguage(Locale.getDefault());
            } else {
                MLog.d(TAG, "Could not initialize TextToSpeech.");
            }
        });
    }

    public PTextToSpeech speak(String text) {
        mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        return this;
    }

    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }

    public PTextToSpeech locale(String lang) {
        Locale locale = new Locale(lang);
        int result = mTts.setLanguage(locale);

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Lanuage data is missing or the language is not supported.
            Log.e(TAG, "Language is not available.");
        }

        return this;
    }

    public PTextToSpeech pitch(float pitch) {
        mTts.setPitch(pitch);
        return this;
    }

    public PTextToSpeech rate(float rate) {
        mTts.setSpeechRate(rate);

        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PTextToSpeech voice(int num) {
        Set<Voice> voices = mTts.getVoices();

        List<Voice> list = new ArrayList<Voice>(voices);
        Voice voice = list.get(num);

        mTts.setVoice(voice);

        return this;
    }

    public void stop() {
        mTts.stop();
    }
}