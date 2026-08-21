package com.android.settings.display.darkmode

import android.content.Context
import android.content.om.IOverlayManager
import android.os.ServiceManager
import android.os.UserHandle
import com.android.settings.R
import com.android.settingslib.datastore.AbstractKeyedDataObservable
import com.android.settingslib.datastore.KeyValueStore
import com.android.settingslib.metadata.BooleanValuePreference
import com.android.settingslib.metadata.ReadWritePermit
import com.android.settingslib.metadata.SensitivityLevel

class TrueDarkPreference : BooleanValuePreference {

    override val key = KEY
    override val title = R.string.accessibility_true_dark_theme_title
    override val summary = R.string.accessibility_true_dark_theme_summary

    override fun storage(context: Context): KeyValueStore = TrueDarkStore(context)

    override fun getReadPermit(context: Context, myUid: Int, callingUid: Int) =
        ReadWritePermit.ALLOW

    override fun getWritePermit(context: Context, value: Boolean?, myUid: Int, callingUid: Int) =
        ReadWritePermit.ALLOW

    override val sensitivityLevel = SensitivityLevel.NO_SENSITIVITY

    companion object {
        const val KEY = "true_dark_enabled"
        private const val OVERLAY_PKG = "com.android.theme.truedark"
    }

    private class TrueDarkStore(private val context: Context) : AbstractKeyedDataObservable() {
        private val overlayManager: IOverlayManager = IOverlayManager.Stub.asInterface(
            ServiceManager.getService(Context.OVERLAY_SERVICE))

        override fun getBoolean(key: String, defValue: Boolean): Boolean {
            return try {
                val info = overlayManager.getOverlayInfo(OVERLAY_PKG, UserHandle.myUserId())
                info?.isEnabled == true
            } catch (e: Exception) {
                defValue
            }
        }

        override fun setBoolean(key: String, value: Boolean) {
            try {
                overlayManager.setEnabled(OVERLAY_PKG, value, UserHandle.myUserId())
            } catch (e: Exception) {
                // ignore
            }
        }
    }
}
