package com.android.settings.display;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;

import com.android.settings.core.TogglePreferenceController;

public class TrueDarkPreferenceController extends TogglePreferenceController {

    private static final String OVERLAY_PKG = "com.android.theme.truedark";
    private final IOverlayManager mOverlayManager;

    public TrueDarkPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
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
            OverlayInfo info = mOverlayManager.getOverlayInfo(
                    OVERLAY_PKG, UserHandle.myUserId());
            return info != null && info.isEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        try {
            mOverlayManager.setEnabled(OVERLAY_PKG, isChecked, UserHandle.myUserId());
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
