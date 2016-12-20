package com.undertree.roguelike

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.MathUtils
import ktx.app.KotlinApplication

//
class RoguelikeGdxGame : KotlinApplication(), InputProcessor {

    lateinit var map: TiledMap
    lateinit var renderer: TiledMapRenderer
    lateinit var camera: OrthographicCamera
    lateinit var assetManager: AssetManager
    lateinit var tiles: Texture
    lateinit var texture: Texture
    lateinit var font: BitmapFont
    lateinit var batch: SpriteBatch
    lateinit var rogueGuy : RogueGuy

    override fun create() {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera()
        camera.setToOrtho(true, 30f, 20f) //(w / h) * 320, 320f)
        camera.update()

        Gdx.input.inputProcessor = this

        font = BitmapFont()
        batch = SpriteBatch()

        tiles = Texture(Gdx.files.internal("tileset.png"))
        val splitTiles = TextureRegion.split(tiles, 32, 32)

        map = TiledMap()

        (0..1).forEach { sublayer ->
            val layer = TiledMapTileLayer(150, 100, 32, 32)

            (0..149).forEach { x ->
                (0..99).forEach { y ->
                    val cell = TiledMapTileLayer.Cell()
                    // generate time from random grass
                    cell.tile = StaticTiledMapTile(splitTiles[13][MathUtils.random(6)])
                    layer.setCell(x, y, cell)
                }
            }

            map.layers.add(layer)
        }

        renderer = OrthogonalTiledMapRenderer(map, 1/32f)

        rogueGuy = RogueGuy(Sprite(splitTiles[31][1]))
        rogueGuy.sprite.setPosition(w/2 -rogueGuy.sprite.getWidth()/2,
                h/2 - rogueGuy.sprite.getHeight())
    }

    override fun render(delta: Float) {
        //Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        renderer.setView(camera)
        renderer.render()

        batch.begin()
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10f, 20f)
        rogueGuy.sprite.draw(batch)
        batch.end()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-1f, 0f)
        if(keycode == Input.Keys.RIGHT)
            camera.translate(1f, 0f)
        if(keycode == Input.Keys.UP)
            camera.translate(0f, -1f)
        if(keycode == Input.Keys.DOWN)
            camera.translate(0f, 1f)
        if(keycode == Input.Keys.NUM_1)
            map.layers.get(0).isVisible = !map.layers.get(0).isVisible
        if(keycode == Input.Keys.NUM_2)
            map.layers.get(1).isVisible = !map.layers.get(1).isVisible

        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }
}


data class RogueGuy constructor(val sprite: Sprite) {
}