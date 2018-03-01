package control;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import actores.Ovillo;
import actores.Soporte;
import comun.MakingACage;
import utiles.Constants;

public class BoredCat extends ApplicationAdapter {
    //Variables Jose
    private SpriteBatch batch;
    private Texture img;
    private MakingACage nicholasTheCage;
    private Sprite spriteFondo, sprite2;
    // La vista a través de una camara
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    // La simulacion visual de lo que ve Box2D
    private Box2DDebugRenderer debugRenderer;

    //Variables Mary
    private Ovillo ovillo;
    private Soporte soporte;
    private World world;
    private Stage stage;
    private Random random;


    //Sonido
    private boolean reproducir = true;
    private Music music;
    private Sound gatoEntallao;

    @Override
    public void create() {
        // La gravedad negativa es hacia abajo (9,8 como en el mundo real), la
        // positiva es hacia arriba
        world = new World(new Vector2(0, -9.8f), true);

        //Jose
        batch = new SpriteBatch();
        img = new Texture("fondo.png");
        spriteFondo = new Sprite(img);

        float h = Gdx.graphics.getHeight() + 100;
        float w = Gdx.graphics.getWidth();

        // making a cage
        nicholasTheCage = new MakingACage(world, Constants.GROUND_START, w, h, Constants.METERS_TO_PIXELS);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Sonido al golpear el gato
        gatoEntallao = Gdx.audio.newSound(Gdx.files.internal("gatoEntallao.mp3"));
        //Sonido de fondo (Activar/desactivar con la tecla 'S')
        if (reproducir) {
            //Sonido
            music = Gdx.audio.newMusic(Gdx.files.internal("catos.mp3"));
            //Sonido
            music.setLooping(true);
            music.play();
            reproducir = false;
        }

        //Mary
        stage = new Stage(new ScreenViewport(new OrthographicCamera()));
        ovillo = new Ovillo(world);
        soporte = new Soporte(world);
        stage.addActor(ovillo);
        stage.addActor(soporte);
        ovillo.setDisparado(false);
        random=new Random(world, stage);

                Gdx.input.setInputProcessor(ovillo);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if ((contact.getFixtureA().getBody() == ovillo.getBody()
                        && contact.getFixtureB().getBody() == random.getBodyGato() ||
                        (contact.getFixtureA().getBody() == random.getBodyGato() && contact.getFixtureB().getBody() == ovillo.getBody()))) {
                    gatoEntallao.play();
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void resize(int width, int height) {
        nicholasTheCage.destroi();
        // making a cage
        nicholasTheCage = new MakingACage(world, Constants.GROUND_START, width, height, Constants.METERS_TO_PIXELS);
    }

    @Override
    public void render() {
        //Mary
        // estos 6,2 son los valores recomendados
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        //Jose
        // actualiza lo que ve la camara
        camera.setToOrtho(false);

        camera.update();
        // poner el fondo
        spriteFondo.setPosition(0, 0);

        // se le dice al batch que tiene que mostrar la camara
        batch.setProjectionMatrix(camera.combined);
        // para pooder mostrar el debug renderer
        debugMatrix = batch.getProjectionMatrix().cpy().scale(Constants.METERS_TO_PIXELS, Constants.METERS_TO_PIXELS,
                0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        ((OrthographicCamera) stage.getCamera()).setToOrtho(false);

        batch.begin();

        spriteFondo.draw(batch);

        batch.end();

        //Reiniciar los actores si el ovillo se para o pasan 3 segundos desde su lanzamiento
        if (ovillo.isDisparada() && (!ovillo.isQuieto() || ovillo.getTiempo() <= System.currentTimeMillis())) {
            ovillo.setDisparado(false);
            stage.clear();
            create();
        }
        stage.draw();

        // dibujar en pantalla los contornos que ve Box2D (Activar/desactivar con la tecla 'M')
        if (ovillo.isMostrarMatriz()) {
            //mostrar la matriz de pruebas
            debugRenderer = new Box2DDebugRenderer();
            debugRenderer.render(world, debugMatrix);
        } else {
            debugRenderer = new Box2DDebugRenderer();
        }

        if(!ovillo.isSonido()){
            music.pause();
        }else{
            music.play();
        }

    }

    @Override
    public void dispose() {
        music.dispose();
        img.dispose();
        world.dispose();
        stage.dispose();
    }
}
