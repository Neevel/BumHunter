package com.krustenkaese.bumhunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.krustenkaese.bumhunter.BumHunter;
import com.krustenkaese.bumhunter.Scenes.Hud;

/**
 * Created by Krustenkäse on 19.08.2018
 */
public class PlayScreen implements Screen {
    private BumHunter game;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(BumHunter game){
        this.game = game;

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(BumHunter.V_WIDTH, BumHunter.V_HEIGHT, gamecam);
        hud = new Hud(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("BumHunter.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        b2dr.SHAPE_STATIC.set(1,0,0,1);

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/ 2 , rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); 

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/ 2 , rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecam.position.x += 100 * dt;
    }
    public void update(float dt){
        handleInput(dt);

        gamecam.update();
        renderer.setView(gamecam);
        b2dr.render(world, gamecam.combined);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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

    }
}
