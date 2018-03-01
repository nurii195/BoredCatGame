package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

import utiles.Constants;

/**
 * Created by Mary on 26/02/2016.
 * Clase para el actor ovillo
 */
public class Ovillo extends Actor implements InputProcessor {
    //Sprite ovillo
    private Sprite spriteOvillo;
    //Sprite corazon
    private final Sprite spriteCorazon;
    // El objeto en el mundo
    private Body body;
    private float x, y;
    private boolean disparado;
    private long tiempo;

    //Para detectar si se ha pulsado en el ovillo
    private boolean pulsado = false;

    //Variables usadas para pintar la trayectoria
    private Vector2 vector2 = new Vector2();
    private boolean pintaTrayectoria;

    //Para controlar en el listener con una tecla si se muestra/oculta el debugrenderer
    private boolean mostrarMatriz=false;
    //Para mutear o poner el sonido
    private boolean sonido=true;


    public Ovillo(World world) {
        // la parte visual
        //Ovillo
        spriteOvillo = new Sprite(new Texture("yarn.png"));
        //Corazon
        spriteCorazon = new Sprite(new Texture("corazoncitoMarco.png"));
        spriteOvillo.setPosition(Gdx.graphics.getWidth() / 6, Constants.GROUND_START * Constants.METERS_TO_PIXELS + 103);

        //Hay que definir el centro para que ruede el spriteOvillo cuando lo haga el body
        spriteOvillo.setOrigin(spriteOvillo.getWidth() / 2.0f, spriteOvillo.getHeight() / 2.0f);

        // Definimos el cuerpo
        BodyDef bodyDef = new BodyDef();
        // En type definimos como interactua el body con las caracterisitcas del
        // mundo. dynamic significa que el cuerpo esta sujeto a las leyes de la
        // fisica
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        // colocamos en el mundo el body donde hallamos colocado la imagen
        bodyDef.position.set((spriteOvillo.getX() + spriteOvillo.getWidth() / 2) * Constants.PIXELS_TO_METERS,
                (spriteOvillo.getY() + spriteOvillo.getHeight() / 2) * Constants.PIXELS_TO_METERS);

        // Con esta definicion creamos el body
        body = world.createBody(bodyDef);

        // Ahora le damos forma en el mundo
        CircleShape shape = new CircleShape();
        // El punto central del shape es el 0,0
        shape.setRadius((spriteOvillo.getWidth() / 2) * Constants.PIXELS_TO_METERS);

        // Para terminar definimos otras propiedades de comportamiento
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // en kg/m2
        fixtureDef.density = 1.3f;

        fixtureDef.restitution = .1f;

        fixtureDef.friction = 1;

        body.createFixture(fixtureDef);

        // aplicamos la definicion de fixture a un objeto fixture
        body.createFixture(fixtureDef);

        // borrar la forma
        shape.dispose();

    }

    public boolean isQuieto() {
        return body.isAwake();
    }

    @Override
    public void act(float delta) {
        spriteOvillo.setPosition((body.getPosition().x * Constants.METERS_TO_PIXELS) - spriteOvillo.getWidth() / 2,
                (body.getPosition().y * Constants.METERS_TO_PIXELS) - spriteOvillo.getHeight() / 2);
        //Para que gire el ovillo
        spriteOvillo.setRotation((float) Math.toDegrees(body.getAngle()));

        x = body.getPosition().x * Constants.METERS_TO_PIXELS;
        y = body.getPosition().y * Constants.METERS_TO_PIXELS;
    }

    /**
     * Obtiene la coordenada X de la trayectoria estimada
     *
     * @param t tiempo
     * @return la coordenada
     */
    public float trayX(float t) {
        return (vector2.x * Constants.METERS_TO_PIXELS) * t + spriteOvillo.getX();
    }

    /**
     * Obtiene la coordenada Y de la trayectoria estimada
     *
     * @param t tiempo
     * @return la coordenada
     */
    public float trayY(float t) {
        return 0.5f * body.getWorld().getGravity().y * t * t + (vector2.y * Constants.METERS_TO_PIXELS) * t + spriteOvillo.getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Se pinta la trayectoria solo si se esta pulsando en el ovillo y se esta haciendo drag
        if (pulsado && pintaTrayectoria) {
            for (float i = 1; i < 30; i += 0.3f) {
                float x = trayX(i);
                float y = trayY(i);
                spriteCorazon.setPosition(x, y);
                spriteCorazon.draw(batch);
            }
        }
        spriteOvillo.draw(batch);
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setDisparado(boolean disparado) {
        this.disparado = disparado;
    }

    public boolean isDisparada() {
        return disparado;
    }

    public boolean isMostrarMatriz() {
        return mostrarMatriz;
    }

    public boolean isSonido() {
        return sonido;
    }

    public Body getBody() {
        return body;
    }

    //Listener

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.M){
            if(mostrarMatriz)
                mostrarMatriz=false;
            else
                mostrarMatriz=true;
        }

       if(keycode== Input.Keys.S){
            if(sonido)
                sonido=false;
            else
                sonido=true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Comprueba la posicion pulsada para saber si esta tocando el ovillo o no, si se cumple se pone a true la bandera

        //Para trasladar la Y de la pantalla(0.0 arriba-izq) a la del spriteOvillo(0.0 abajo-izq)
        float tempY = Gdx.graphics.getHeight() - screenY;

        if (screenX >= spriteOvillo.getX() && screenX <= spriteOvillo.getX() + spriteOvillo.getWidth()) {
            if (tempY >= spriteOvillo.getY() && tempY <= spriteOvillo.getY() + spriteOvillo.getHeight()) {
                //Estamos tocando el ovillo
                pulsado = true;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Cuando se levanta el dedo
        if (pulsado) {
            //Para que solo pille las coordenadas hacia la izquierda
            if (screenX < spriteOvillo.getX()) {
                //Se trasladan las coordenadas
                float tempY = Gdx.graphics.getHeight() - screenY;
                //Captura la diferencia entre las coordenadas del ovillo y las de donde se levantó el dedo/ratón
                vector2.set(spriteOvillo.getX() - screenX, spriteOvillo.getY() - tempY);
                vector2 = vector2.scl(Constants.PIXELS_TO_METERS);
                //Con el vector ya calculado se aplica el impulso lineal
                body.applyLinearImpulse(vector2, body.getWorldCenter(), true);
                disparado = true;
                tiempo = System.currentTimeMillis() + 4000;
            }
        }
        //Para que se reinicie siempre
        pulsado = false;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Si la bandera esta a true es que se esta pulsando en el ovillo
        //Se calcula la trayectoria para qe se pinte
        if (pulsado) {
            if (screenX < spriteOvillo.getX()) {
                pintaTrayectoria = true;
                float tempY = Gdx.graphics.getHeight() - screenY;
                vector2.set(spriteOvillo.getX() - screenX, spriteOvillo.getY() - tempY);
                vector2 = vector2.scl(Constants.PIXELS_TO_METERS);
            } else {
                pintaTrayectoria = false;
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
