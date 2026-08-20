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

package com.android.settings.display.darkmode

import android.content.Context
import android.util.AttributeSet
import com.android.settings.R

sealed class DarkModeSelectorPreference(
    context: Context,
    attrs: AttributeSet?,
) : RadioButtonPreference(context, attrs) {

    init {
        // Ensure only one radio button is checked in the group
        isPersistent = false
    }

    override fun onClick() {
        super.onClick()
        // Notify storage or handle selection if needed
    }
}

class StandardDarkModeSelectorPreference(
    context: Context,
    attrs: AttributeSet?,
) : DarkModeSelectorPreference(context, attrs) {
    override val key
        get() = KEY

    override val title
        get() = R.string.dark_theme_ui_mode_standard

    override val summary
        get() = R.string.dark_theme_ui_mode_standard_summary

    override val keywords: Int
        get() = R.string.keywords_standard_dark_theme

    override fun getIndexableTitle(context: Context): CharSequence? =
        context.getText(R.string.dark_theme_ui_mode_standard)

    companion object {
        const val KEY = "standard_dark_theme"
    }
}

class ExpandedDarkModeSelectorPreference(
    context: Context,
    attrs: AttributeSet?,
) : DarkModeSelectorPreference(context, attrs) {
    override val key
        get() = KEY

    override val title
        get() = R.string.accessibility_expanded_dark_theme_title

    override val summary
        get() = R.string.accessibility_true_dark_theme_summary

    override val keywords: Int
        get() = R.string.keywords_expanded_dark_theme

    override fun getIndexableTitle(context: Context): CharSequence? =
        context.getText(R.string.accessibility_true_dark_theme_title_in_search)

    companion object {
        const val KEY = "expanded_dark_theme"
    }
}

class TrueDarkModeSelectorPreference(
    context: Context,
    attrs: AttributeSet?,
) : DarkModeSelectorPreference(context, attrs) {
    override val key
        get() = KEY

    override val title
        get() = R.string.accessibility_true_dark_theme_title

    override val summary
        get() = R.string.accessibility_true_dark_theme_summary

    override val keywords: Int
        get() = R.string.keywords_expanded_dark_theme

    override fun getIndexableTitle(context: Context): CharSequence? =
        context.getText(R.string.accessibility_true_dark_theme_title)

    companion object {
        const val KEY = "dark_theme_true_dark"
    }
}
