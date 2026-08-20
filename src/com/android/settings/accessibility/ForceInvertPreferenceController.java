/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.accessibility;

import android.content.Context;
import android.provider.Settings;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.display.darkmode.DarkThemeModeStorage;

public class ForceInvertPreferenceController extends BasePreferenceController {

    public ForceInvertPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return Flags.forceInvertColor() ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    public static int getDarkThemeMode(Context context) {
        return Settings.Secure.getInt(
                context.getContentResolver(),
                DarkThemeModeStorage.KEY,
                DarkThemeModeStorage.MODE_STANDARD);
    }

    public static void setDarkThemeMode(Context context, int mode) {
        Settings.Secure.putInt(
                context.getContentResolver(),
                DarkThemeModeStorage.KEY,
                mode);
        Settings.Secure.putBoolean(
                context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED,
                mode == DarkThemeModeStorage.MODE_EXPANDED);
    }
}
