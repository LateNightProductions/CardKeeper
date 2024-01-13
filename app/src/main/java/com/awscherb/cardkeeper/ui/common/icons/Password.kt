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

public val Password: ImageVector
    get() {
        if (_password != null) {
            return _password!!
        }
        _password = materialIcon(name = "Filled.Password") {
            materialPath {
                moveTo(2.0f, 17.0f)
                horizontalLineToRelative(20.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(17.0f)
                close()
                moveTo(3.15f, 12.95f)
                lineTo(4.0f, 11.47f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(1.3f, -0.75f)
                lineTo(5.3f, 10.72f)
                horizontalLineTo(7.0f)
                verticalLineToRelative(-1.5f)
                horizontalLineTo(5.3f)
                lineToRelative(0.85f, -1.47f)
                lineTo(4.85f, 7.0f)
                lineTo(4.0f, 8.47f)
                lineTo(3.15f, 7.0f)
                lineToRelative(-1.3f, 0.75f)
                lineTo(2.7f, 9.22f)
                horizontalLineTo(1.0f)
                verticalLineToRelative(1.5f)
                horizontalLineToRelative(1.7f)
                lineTo(1.85f, 12.2f)
                lineTo(3.15f, 12.95f)
                close()
                moveTo(9.85f, 12.2f)
                lineToRelative(1.3f, 0.75f)
                lineTo(12.0f, 11.47f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(1.3f, -0.75f)
                lineToRelative(-0.85f, -1.48f)
                horizontalLineTo(15.0f)
                verticalLineToRelative(-1.5f)
                horizontalLineToRelative(-1.7f)
                lineToRelative(0.85f, -1.47f)
                lineTo(12.85f, 7.0f)
                lineTo(12.0f, 8.47f)
                lineTo(11.15f, 7.0f)
                lineToRelative(-1.3f, 0.75f)
                lineToRelative(0.85f, 1.47f)
                horizontalLineTo(9.0f)
                verticalLineToRelative(1.5f)
                horizontalLineToRelative(1.7f)
                lineTo(9.85f, 12.2f)
                close()
                moveTo(23.0f, 9.22f)
                horizontalLineToRelative(-1.7f)
                lineToRelative(0.85f, -1.47f)
                lineTo(20.85f, 7.0f)
                lineTo(20.0f, 8.47f)
                lineTo(19.15f, 7.0f)
                lineToRelative(-1.3f, 0.75f)
                lineToRelative(0.85f, 1.47f)
                horizontalLineTo(17.0f)
                verticalLineToRelative(1.5f)
                horizontalLineToRelative(1.7f)
                lineToRelative(-0.85f, 1.48f)
                lineToRelative(1.3f, 0.75f)
                lineTo(20.0f, 11.47f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(1.3f, -0.75f)
                lineToRelative(-0.85f, -1.48f)
                horizontalLineTo(23.0f)
                verticalLineTo(9.22f)
                close()
            }
        }
        return _password!!
    }

private var _password: ImageVector? = null
