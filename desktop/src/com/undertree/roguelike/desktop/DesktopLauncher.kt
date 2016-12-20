package com.undertree.roguelike.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.undertree.roguelike.RoguelikeGdxGame

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Roguelike GDX"
        config.width = 800
        config.height = 600
        LwjglApplication(RoguelikeGdxGame(), config)
    }
}
