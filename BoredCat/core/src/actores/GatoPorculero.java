package actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utiles.Constants;

/**
 * Created by Kikeli on 29/02/2016.
 */
public class GatoPorculero extends Actor {

    private Body body;

    private Sprite sprite;

    private void definirPropiedades(World world, float xIni, float yIni) {
        sprite.setPosition(xIni,yIni);
        //Creo el bodyDef para aplicarle propiedades al gatete
        BodyDef bodyDef = new BodyDef();
        //Las tablas son dinamicas
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //Posicionarlas donde queramos por parametros, los valores vendran muy grandes por lo que se dividen por la constante
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) * Constants.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight() / 2) * Constants.PIXELS_TO_METERS);
        //creo el body en el world y le paso por parametro sus propiedades dadas en el bodyDef
        body = world.createBody(bodyDef);
//        //el gatete es circular porque esta gordito
//        CircleShape circleShape = new CircleShape();
//        //esto le da la forma circular
//        circleShape.setRadius((sprite.getWidth()/2) * Constants.PIXELS_TO_METERS);

        // Ahora le damos forma en el mundo
        PolygonShape shape = new PolygonShape();
        // El punto central del shape es el 0,0
        shape.setAsBox((sprite.getWidth() / 2 )* Constants.PIXELS_TO_METERS, (sprite.getHeight()/2)*Constants.PIXELS_TO_METERS);

        //Le doy propiedades fisicas a la tabla
        FixtureDef fixtureDef = new FixtureDef();
        //redimensiono la fixture
        fixtureDef.shape = shape;
        //Kilos/metro cuadrado
        fixtureDef.density = 0.5f;
        //elasticidad, entre 0 y 1
        fixtureDef.restitution = 0f;
        //friccion, entre 0 y 1
        fixtureDef.friction = 1f;

        //aplico las fixturas al body

        body.createFixture(fixtureDef).setUserData("gato");
        shape.dispose();
    }
    public GatoPorculero(World world, float xIni, float yIni, String gatete) {
        Texture texture = new Texture(gatete);
        sprite = new Sprite(texture);

        definirPropiedades(world,xIni, yIni );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    //Recoge los cambios en el body y actualiza la posicion
    @Override
    public void act(float delta) {
        sprite.setPosition((body.getPosition().x * Constants.METERS_TO_PIXELS) - sprite.getWidth() / 2,
                (body.getPosition().y * Constants.METERS_TO_PIXELS) - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public Body getBody() {
        return body;
    }
}
