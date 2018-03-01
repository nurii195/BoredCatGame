package materiales;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import utiles.Constants;

/**
 * Created by Mary on 04/03/2016.
 */
public class Cuadrado extends Material {
    private Sprite sprite;
    private PolygonShape polygonShape;

    public Cuadrado() {
        sprite = new Sprite(new Texture("cuadro.png"));
    }

    @Override
    public BodyDef bodyDef() {
        //Creo el bodyDef para aplicarle propiedades
        BodyDef bodyDef = new BodyDef();
        //Las tablas son dinamicas
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        return bodyDef;
    }

    @Override
    public Sprite sprite() {
        return sprite;
    }

    @Override
    public FixtureDef fixtureDef() {
        //el gatete es circular porque esta gordito
        polygonShape = new PolygonShape();
        //esto le da la forma circular
        polygonShape.setAsBox((sprite.getWidth() / 2) * Constants.PIXELS_TO_METERS, (sprite.getHeight() / 2) * Constants.PIXELS_TO_METERS);
        //Le doy propiedades fisicas a la tabla
        FixtureDef fixtureDef = new FixtureDef();
        //redimensiono la fixture
        fixtureDef.shape = polygonShape;
        //Kilos/metro cuadrado
        fixtureDef.density = .05f;
        //elasticidad, entre 0 y 1
        fixtureDef.restitution = 0f;
        //friccion, entre 0 y 1
        fixtureDef.friction = 0.5f;

        return fixtureDef;
    }

    @Override
    public void disposeShape() {
        polygonShape.dispose();
    }
}
