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

package com.awscherb.cardkeeper.ui.common.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val FlashlightOn: ImageVector
    get() {
        if (_flashlightOn != null) {
            return _flashlightOn!!
        }
        _flashlightOn = materialIcon(name = "Filled.FlashlightOn") {
            materialPath {
                moveTo(6.0f, 2.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(-12.0f)
                close()
            }
            materialPath {
                moveTo(6.0f, 7.0f)
                verticalLineToRelative(1.0f)
                lineToRelative(2.0f, 3.0f)
                verticalLineToRelative(11.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(11.0f)
                lineToRelative(2.0f, -3.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(6.0f)
                close()
                moveTo(12.0f, 15.5f)
                curveToRelative(-0.83f, 0.0f, -1.5f, -0.67f, -1.5f, -1.5f)
                reflectiveCurveToRelative(0.67f, -1.5f, 1.5f, -1.5f)
                reflectiveCurveToRelative(1.5f, 0.67f, 1.5f, 1.5f)
                reflectiveCurveTo(12.83f, 15.5f, 12.0f, 15.5f)
                close()
            }
        }
        return _flashlightOn!!
    }

private var _flashlightOn: ImageVector? = null
