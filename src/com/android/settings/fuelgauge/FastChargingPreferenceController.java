package com.android.settings.fuelgauge;

import android.content.Context;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import com.android.settings.core.BasePreferenceController;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FastChargingPreferenceController extends BasePreferenceController {

    private static final String PREF_KEY = "fast_charging_enabled";
    private static final String FAST_CHARGE_PATH = "/sys/class/qcom-battery/restrict_chg";

    public FastChargingPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return new java.io.File(FAST_CHARGE_PATH).exists() ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public void updateState(Preference preference) {
        if (preference instanceof SwitchPreferenceCompat) {
            ((SwitchPreferenceCompat) preference).setChecked(isFastChargingEnabled());
        }
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!PREF_KEY.equals(preference.getKey())) return false;
        if (preference instanceof SwitchPreferenceCompat) {
            boolean enabled = ((SwitchPreferenceCompat) preference).isChecked();
            setFastCharging(enabled);
        }
        return true;
    }

    private boolean isFastChargingEnabled() {
        try (FileReader fr = new FileReader(FAST_CHARGE_PATH)) {
            return fr.read() == '0';
        } catch (IOException e) {
            return true;
        }
    }

    private void setFastCharging(boolean enable) {
        try (FileWriter fw = new FileWriter(FAST_CHARGE_PATH)) {
            fw.write(enable ? "0" : "1");
        } catch (IOException e) {
            // ignore
        }
    }
}
