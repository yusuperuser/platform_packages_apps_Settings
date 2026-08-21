package com.android.settings.display;

import android.content.Context;
import android.os.SystemProperties;

import com.android.settings.core.TogglePreferenceController;

public class BlurPreferenceController extends TogglePreferenceController {

    private static final String BLUR_DISABLE_PROP = "persist.sys.sf.disable_blurs";

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
        return !SystemProperties.getBoolean(BLUR_DISABLE_PROP, false);
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        SystemProperties.set(BLUR_DISABLE_PROP, isChecked ? "0" : "1");
        return true;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return 0;
    }
}
