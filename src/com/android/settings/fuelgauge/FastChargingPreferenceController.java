package com.android.settings.fuelgauge;

import android.content.Context;
import com.android.settings.core.TogglePreferenceController;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FastChargingPreferenceController extends TogglePreferenceController {

    private static final String FAST_CHARGE_PATH = "/sys/class/qcom-battery/restrict_chg";

    public FastChargingPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return new java.io.File(FAST_CHARGE_PATH).exists() ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public boolean isChecked() {
        try (FileReader fr = new FileReader(FAST_CHARGE_PATH)) {
            return fr.read() == '0';
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        try (FileWriter fw = new FileWriter(FAST_CHARGE_PATH)) {
            fw.write(isChecked ? "0" : "1");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return 0;
    }
}
