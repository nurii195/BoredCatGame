package estructuras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import control.Random;
import materiales.Cuadrado;
import materiales.TablaHorizontal;
import utiles.Constants;

/**
 * Created by usuario on 08/03/2016.
 */
public class PuertaGatuna extends EstructuraAbstracta {
    private Cuadrado cuadro1, cuadro2, cuadro3, cuadro4;
    private TablaHorizontal tabla;

    public PuertaGatuna() {

        cuadro1 = new Cuadrado();
        cuadro2 = new Cuadrado();
        cuadro3 = new Cuadrado();
        cuadro4 = new Cuadrado();
        tabla = new TablaHorizontal(true);
        materials.add(cuadro1);
        materials.add(cuadro2);
        materials.add(cuadro3);
        materials.add(cuadro4);
        materials.add(tabla);

    }

    @Override
    public void init(World world) {
        super.init(world);
        float alto = cuadro1.getAlto();
        float ancho = cuadro1.getAncho();
        cuadro1.move(0,0);
        cuadro2.move(0, alto);
        cuadro3.move(ancho * 2, 0);
        cuadro4.move(ancho * 2, alto);
        tabla.move(ancho/2, alto*2);
    }

    public float[] posiciones() {
        float[][] posiciones = {
                {(cuadro1.getX()-cuadro1.getAncho()*2)*Constants.METERS_TO_PIXELS,Constants.GROUND_START*Constants.METERS_TO_PIXELS},
                {(cuadro1.getX())* Constants.METERS_TO_PIXELS,(cuadro1.getAlto()*2+tabla.getAlto()+Constants.GROUND_START)* Constants.METERS_TO_PIXELS},
                {(cuadro1.getX())* Constants.METERS_TO_PIXELS,Constants.GROUND_START* Constants.METERS_TO_PIXELS},
                {cuadro3.getX()*Constants.METERS_TO_PIXELS,Constants.GROUND_START*Constants.METERS_TO_PIXELS}};
        return posicionesGato(posiciones);
    }
}
