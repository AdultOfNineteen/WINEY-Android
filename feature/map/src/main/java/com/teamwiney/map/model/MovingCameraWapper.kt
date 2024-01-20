package com.teamwiney.map.model

import android.location.Location


sealed class MovingCameraWrapper {
    object DEFAULT : MovingCameraWrapper()
    class MOVING(val location: Location) : MovingCameraWrapper()

    val isMoving: Boolean
        get() = this is MOVING
}

