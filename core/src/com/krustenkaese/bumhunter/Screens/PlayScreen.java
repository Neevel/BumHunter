package com.krustenkaese.bumhunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.krustenkaese.bumhunter.BumHunter;
import com.krustenkaese.bumhunter.Scenes.Hud;
import com.krustenkaese.bumhunter.Sprites.Hunter;
import com.krustenkaese.bumhunter.Sprites.TiledObjectUtil;
import com.krustenkaese.bumhunter.Tools.B2WorldCreator;
import com.krustenkaese.bumhunter.Tools.WorldContactListener;

/**
 * Created by Krustenkäse on 19.08.2018
 */
public class PlayScreen implements Screen {

    private BumHunter game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Texture tex;

    private Hunter player;

    private Music music;


    public PlayScreen(BumHunter game){
        atlas = new TextureAtlas("hero_and_enemy.pack");

        this.game = game;

        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(BumHunter.V_WIDTH / BumHunter.PPM, BumHunter.V_HEIGHT / BumHunter.PPM, gamecam);
        hud = new Hud(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / BumHunter.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2 , gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        player = new Hunter(world, this);

        world.setContactListener(new WorldContactListener());

        tex = new Texture(Gdx.files.internal("test.png"));

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("test").getObjects());

       // music = BumHunter.manager.get("audio/music/Suspense Loop.ogg", Music.class); // nimmt sich die richtige datei aus dem assetmanager
       // music.setVolume(1f/5);
       // music.setLooping(true); // wiederholungsschleife
       // music.play();

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
        //if(player.b2body.getPosition().y <= (0.7f)){

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            BumHunter.manager.get("audio/sounds/jump.wav", Sound.class).play();
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(),true);}
        if(Gdx.input.isKeyPressed(Input.Keys.D)&& player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.A)&& player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            player.b2body.applyLinearImpulse(new Vector2(0, 0f), player.b2body.getWorldCenter(),true);

        }
    }
    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;



        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //b2dr.render(world, gamecam.combined);

        game.getBatch().setProjectionMatrix(gamecam.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        game.getBatch().draw(tex, -180/ BumHunter.PPM + 2, 10 / BumHunter.PPM, BumHunter.V_WIDTH / BumHunter.PPM+ 7f, BumHunter.V_HEIGHT / BumHunter.PPM + 3);
        game.getBatch().end();

        // Hud zeichnen
        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
       b2dr.dispose();

    }
}
