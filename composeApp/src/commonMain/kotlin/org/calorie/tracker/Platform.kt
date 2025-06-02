package org.calorie.tracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform