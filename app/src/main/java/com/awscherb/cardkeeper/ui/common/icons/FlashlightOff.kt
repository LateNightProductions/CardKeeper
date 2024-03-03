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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val FlashlightOff: ImageVector
    get() {
        if (_flashlightOff != null) {
            return _flashlightOff!!
        }
        _flashlightOff = materialIcon(name = "Filled.FlashlightOff") {
            materialPath {
                moveTo(18.0f, 5.0f)
                lineToRelative(0.0f, -3.0f)
                lineToRelative(-12.0f, 0.0f)
                lineToRelative(0.0f, 1.17f)
                lineToRelative(1.83f, 1.83f)
                close()
            }
            materialPath {
                moveTo(16.0f, 11.0f)
                lineToRelative(2.0f, -3.0f)
                lineToRelative(0.0f, -1.0f)
                lineToRelative(-8.17f, 0.0f)
                lineToRelative(6.17f, 6.17f)
                close()
            }
            materialPath {
                moveTo(2.81f, 2.81f)
                lineTo(1.39f, 4.22f)
                lineTo(8.0f, 10.83f)
                verticalLineTo(22.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(-3.17f)
                lineToRelative(3.78f, 3.78f)
                lineToRelative(1.41f, -1.41f)
                lineTo(2.81f, 2.81f)
                close()
            }
        }
        return _flashlightOff!!
    }

private var _flashlightOff: ImageVector? = null
