package com.android.settings.display;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;

import com.android.settings.core.TogglePreferenceController;

public class BlurPreferenceController extends TogglePreferenceController {

    public BlurPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return SystemProperties.getBoolean(
            "ro.surface_flinger.supports_background_blur", false)
            ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public boolean isChecked() {
        return Settings.Global.getInt(
            mContext.getContentResolver(),
            Settings.Global.DISABLE_WINDOW_BLURS, 0) == 0;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        Settings.Global.putInt(
            mContext.getContentResolver(),
            Settings.Global.DISABLE_WINDOW_BLURS, isChecked ? 0 : 1);
        return true;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return 0;
    }
}
