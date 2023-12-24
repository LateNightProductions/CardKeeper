package com.awscherb.cardkeeper.ui.common.icons

/*
 * Copyright 2023 The Android Open Source Project
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

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Camera: ImageVector
    get() {
        if (_camera != null) {
            return _camera!!
        }
        _camera = materialIcon(name = "Filled.Camera") {
            materialPath {
                moveTo(9.4f, 10.5f)
                lineToRelative(4.77f, -8.26f)
                curveTo(13.47f, 2.09f, 12.75f, 2.0f, 12.0f, 2.0f)
                curveToRelative(-2.4f, 0.0f, -4.6f, 0.85f, -6.32f, 2.25f)
                lineToRelative(3.66f, 6.35f)
                lineToRelative(0.06f, -0.1f)
                close()
                moveTo(21.54f, 9.0f)
                curveToRelative(-0.92f, -2.92f, -3.15f, -5.26f, -6.0f, -6.34f)
                lineTo(11.88f, 9.0f)
                horizontalLineToRelative(9.66f)
                close()
                moveTo(21.8f, 10.0f)
                horizontalLineToRelative(-7.49f)
                lineToRelative(0.29f, 0.5f)
                lineToRelative(4.76f, 8.25f)
                curveTo(21.0f, 16.97f, 22.0f, 14.61f, 22.0f, 12.0f)
                curveToRelative(0.0f, -0.69f, -0.07f, -1.35f, -0.2f, -2.0f)
                close()
                moveTo(8.54f, 12.0f)
                lineToRelative(-3.9f, -6.75f)
                curveTo(3.01f, 7.03f, 2.0f, 9.39f, 2.0f, 12.0f)
                curveToRelative(0.0f, 0.69f, 0.07f, 1.35f, 0.2f, 2.0f)
                horizontalLineToRelative(7.49f)
                lineToRelative(-1.15f, -2.0f)
                close()
                moveTo(2.46f, 15.0f)
                curveToRelative(0.92f, 2.92f, 3.15f, 5.26f, 6.0f, 6.34f)
                lineTo(12.12f, 15.0f)
                lineTo(2.46f, 15.0f)
                close()
                moveTo(13.73f, 15.0f)
                lineToRelative(-3.9f, 6.76f)
                curveToRelative(0.7f, 0.15f, 1.42f, 0.24f, 2.17f, 0.24f)
                curveToRelative(2.4f, 0.0f, 4.6f, -0.85f, 6.32f, -2.25f)
                lineToRelative(-3.66f, -6.35f)
                lineToRelative(-0.93f, 1.6f)
                close()
            }
        }
        return _camera!!
    }

private var _camera: ImageVector? = null
