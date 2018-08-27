package com.krustenkaese.bumhunter.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.krustenkaese.bumhunter.BumHunter;
import com.krustenkaese.bumhunter.Screens.PlayScreen;

public class Hunter extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Animation<TextureRegion> hunterStand;
    private Animation<TextureRegion> hunterRun;
    private Animation<TextureRegion> hunterJump;
    private float stateTimer;
    private boolean runningRight;

    public Hunter(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Hero1"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 103, 284, 103, 140));
        hunterRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i= 0; i < 5; i++)
            frames.add(new TextureRegion(getTexture(), i * 0, 140, 103, 140));
        hunterJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();


        for(int i= 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 104, 280, 103, 140));
        hunterStand = new Animation<TextureRegion>(1f, frames);
        setBounds(0, 0, 43 / BumHunter.PPM, 60 / BumHunter.PPM );   // die letzten beiden werte bestimmen die Bildgröße



        defineHunter();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = hunterRun.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = hunterRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = hunterStand.getKeyFrame(stateTimer);
                    break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
    region.flip(true, false);
    runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer +dt: 0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0 || b2body.getLinearVelocity().y != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineHunter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100/ BumHunter.PPM + 2, 290 / BumHunter.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / BumHunter.PPM, 15 / BumHunter.PPM); // bestimmt die Body Box größe
        fdef.filter.categoryBits = BumHunter.HUNTER_BIT;
        fdef.filter.maskBits = BumHunter.DEFAULT_BIT | BumHunter.COIN_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        PolygonShape coinContact = new PolygonShape();
        coinContact.setAsBox(05 / BumHunter.PPM, 20 / BumHunter.PPM);
        fdef.shape = coinContact;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("headAndSight");



    }
}
