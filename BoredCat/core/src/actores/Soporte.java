package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utiles.Constants;

/**
 * Created by Mary on 26/02/2016.
 * Clase para el actor soporte, sobre él se posiciona el ovillo
 */
public class Soporte extends Actor {
    //Sprite
    private Sprite sprite;
    // El objeto en el mundo
    private Body body;

    public Soporte(World world) {
        // la parte visual
        Texture texture = new Texture("stick.png");
        sprite = new Sprite(texture);
        sprite.setPosition(Gdx.graphics.getWidth()/6, Constants.GROUND_START*Constants.METERS_TO_PIXELS);

        // Definimos el cuerpo
        BodyDef bodyDef = new BodyDef();
        // En type definimos como interactua el body con las caracterisitcas del
        // mundo
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // colocamos en el mundo el body donde hallamos colocado la imagen
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) * Constants.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight() / 2) * Constants.PIXELS_TO_METERS);

        // Con esta definicion creamos el body
        body = world.createBody(bodyDef);

        // Ahora le damos forma en el mundo
       PolygonShape shape = new PolygonShape();
        // El punto central del shape es el 0,0
        shape.setAsBox((sprite.getWidth() / 2 )* Constants.PIXELS_TO_METERS, (sprite.getHeight()/2)*Constants.PIXELS_TO_METERS);

        // Para terminar definimos otras propiedades de comportamiento
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // en kg/m2
        fixtureDef.density = (float) (1);

        fixtureDef.restitution = (float) .3;

        fixtureDef.friction = (float) (.9);

        body.createFixture(fixtureDef);

        // aplicamos la definicion de fixture a un objeto fixture
        body.createFixture(fixtureDef);

        // borrar la forma
        shape.dispose();
    }

    @Override
    public void act(float delta) {
        sprite.setPosition((body.getPosition().x * Constants.METERS_TO_PIXELS) - sprite.getWidth() / 2,
                (body.getPosition().y * Constants.METERS_TO_PIXELS) - sprite.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
