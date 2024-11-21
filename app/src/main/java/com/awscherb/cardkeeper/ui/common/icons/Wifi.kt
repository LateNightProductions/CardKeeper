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

// From https://androidx.tech/artifacts/compose.material/material-icons-extended-android/

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val NetworkWifi: ImageVector
    get() {
        if (_networkWifi != null) {
            return _networkWifi!!
        }
        _networkWifi = materialIcon(name = "Filled.NetworkWifi") {
            materialPath {
                moveTo(24.0f, 8.98f)
                curveTo(20.93f, 5.9f, 16.69f, 4.0f, 12.0f, 4.0f)
                curveTo(7.31f, 4.0f, 3.07f, 5.9f, 0.0f, 8.98f)
                lineTo(12.0f, 21.0f)
                verticalLineToRelative(0.0f)
                lineToRelative(0.0f, 0.0f)
                lineTo(24.0f, 8.98f)
                close()
                moveTo(2.92f, 9.07f)
                curveTo(5.51f, 7.08f, 8.67f, 6.0f, 12.0f, 6.0f)
                reflectiveCurveToRelative(6.49f, 1.08f, 9.08f, 3.07f)
                lineToRelative(-1.43f, 1.43f)
                curveTo(17.5f, 8.94f, 14.86f, 8.0f, 12.0f, 8.0f)
                reflectiveCurveToRelative(-5.5f, 0.94f, -7.65f, 2.51f)
                lineTo(2.92f, 9.07f)
                close()
            }
        }
        return _networkWifi!!
    }

private var _networkWifi: ImageVector? = null
