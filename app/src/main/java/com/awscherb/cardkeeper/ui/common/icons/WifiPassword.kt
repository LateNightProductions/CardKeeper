package com.awscherb.cardkeeper.ui.common.icons

/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val WifiPassword: ImageVector
    get() {
        if (_wifiPassword != null) {
            return _wifiPassword!!
        }
        _wifiPassword = materialIcon(name = "Filled.WifiPassword") {
            materialPath {
                moveTo(23.0f, 19.0f)
                verticalLineToRelative(-1.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(1.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, 0.45f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveTo(24.0f, 19.45f, 23.55f, 19.0f, 23.0f, 19.0f)
                close()
                moveTo(22.0f, 19.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-1.0f)
                curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
                reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
                verticalLineTo(19.0f)
                close()
                moveTo(24.0f, 8.98f)
                lineToRelative(-2.12f, 2.13f)
                curveTo(19.35f, 8.57f, 15.85f, 7.0f, 12.0f, 7.0f)
                reflectiveCurveToRelative(-7.35f, 1.57f, -9.88f, 4.11f)
                lineTo(0.0f, 8.98f)
                curveTo(3.07f, 5.9f, 7.31f, 4.0f, 12.0f, 4.0f)
                reflectiveCurveTo(20.93f, 5.9f, 24.0f, 8.98f)
                close()
                moveTo(12.0f, 10.0f)
                curveToRelative(3.03f, 0.0f, 5.78f, 1.23f, 7.76f, 3.22f)
                lineToRelative(-2.12f, 2.12f)
                curveTo(16.2f, 13.9f, 14.2f, 13.0f, 12.0f, 13.0f)
                curveToRelative(-2.2f, 0.0f, -4.2f, 0.9f, -5.64f, 2.35f)
                lineToRelative(-2.12f, -2.12f)
                curveTo(6.22f, 11.23f, 8.97f, 10.0f, 12.0f, 10.0f)
                close()
                moveTo(15.53f, 17.46f)
                lineTo(12.0f, 21.0f)
                lineToRelative(-3.53f, -3.54f)
                curveTo(9.37f, 16.56f, 10.62f, 16.0f, 12.0f, 16.0f)
                reflectiveCurveTo(14.63f, 16.56f, 15.53f, 17.46f)
                close()
            }
        }
        return _wifiPassword!!
    }

private var _wifiPassword: ImageVector? = null
