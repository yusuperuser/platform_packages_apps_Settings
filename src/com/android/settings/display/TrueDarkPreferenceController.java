package com.android.settings.display;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.android.settings.core.TogglePreferenceController;

public class TrueDarkPreferenceController extends TogglePreferenceController {

    private static final String OVERLAY_PKG = "com.android.theme.truedark";
    private final IOverlayManager mOverlayManager;

    public TrueDarkPreferenceController(Context context, String key) {
        super(context, key);
        mOverlayManager = IOverlayManager.Stub.asInterface(
                ServiceManager.getService(Context.OVERLAY_SERVICE));
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        try {
            var info = mOverlayManager.getOverlayInfo(OVERLAY_PKG, android.os.UserHandle.myUserId());
            return info != null && info.isEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        try {
            mOverlayManager.setEnabled(OVERLAY_PKG, isChecked, android.os.UserHandle.myUserId());
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return 0;
    }
}
