package com.android.settings.display;

import static android.view.CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public final class EnableBlursPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String ENABLE_BLURS_ON_WINDOWS = "enable_blurs_on_windows";

    public EnableBlursPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return ENABLE_BLURS_ON_WINDOWS;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean enabled = (Boolean) newValue;
        Settings.Global.putInt(mContext.getContentResolver(),
                Settings.Global.DISABLE_WINDOW_BLURS, enabled ? 0 : 1);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return CROSS_WINDOW_BLUR_SUPPORTED;
    }

    @Override
    public void updateState(Preference preference) {
        boolean isEnabled = Settings.Global.getInt(mContext.getContentResolver(),
                Settings.Global.DISABLE_WINDOW_BLURS, 0) == 0;
        ((TwoStatePreference) preference).setChecked(isEnabled);
    }
}
