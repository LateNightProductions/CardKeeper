package com.awscherb.cardkeeper.compose_common.icons
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

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

// changed : removed `Icons.Filled`
val FileOpen: ImageVector
    get() {
        if (_fileOpen != null) {
            return _fileOpen!!
        }
        _fileOpen = materialIcon(name = "Filled.FileOpen") {
            materialPath {
                moveTo(14.0f, 2.0f)
                horizontalLineTo(6.0f)
                curveTo(4.9f, 2.0f, 4.0f, 2.9f, 4.0f, 4.0f)
                verticalLineToRelative(16.0f)
                curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 1.99f, 2.0f)
                horizontalLineTo(15.0f)
                verticalLineToRelative(-8.0f)
                horizontalLineToRelative(5.0f)
                verticalLineTo(8.0f)
                lineTo(14.0f, 2.0f)
                close()
                moveTo(13.0f, 9.0f)
                verticalLineTo(3.5f)
                lineTo(18.5f, 9.0f)
                horizontalLineTo(13.0f)
                close()
                moveTo(17.0f, 21.66f)
                verticalLineTo(16.0f)
                horizontalLineToRelative(5.66f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.24f)
                lineToRelative(2.95f, 2.95f)
                lineToRelative(-1.41f, 1.41f)
                lineTo(19.0f, 19.41f)
                lineToRelative(0.0f, 2.24f)
                horizontalLineTo(17.0f)
                close()
            }
        }
        return _fileOpen!!
    }

private var _fileOpen: ImageVector? = null
