package com.krustenkaese.bumhunter.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.krustenkaese.bumhunter.BumHunter;

/**
 * Created by Krustenkäse on 20.08.2018
 */
public class Coin extends InteractiveTileObject{
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(BumHunter.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        setCategoryFilter(BumHunter.DESTROYED_BIT);
    }

    @Override
    public void onRightHit() {
        Gdx.app.log("Coin", "Collision");
        setCategoryFilter(BumHunter.DESTROYED_BIT);
    }
}
