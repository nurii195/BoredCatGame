package estructuras;

import com.badlogic.gdx.physics.box2d.World;

import materiales.Cuadrado;
import utiles.Constants;

/**
 * Created by Mary on 04/03/2016.
 */
public class DobleCaja extends EstructuraAbstracta {
    private final Cuadrado cajaUno;
    private final Cuadrado cajaDos;

    public DobleCaja() {
        cajaUno = new Cuadrado();
        cajaDos = new Cuadrado();
        materials.add(cajaUno);
        materials.add(cajaDos);
    }

    @Override
    public void init(World world) {
        super.init(world);
        cajaUno.move(0, 0);
        cajaDos.move(0, cajaUno.getAlto());
    }

    public float[] posiciones() {
        float[][] posiciones = {
                {(cajaUno.getX()-cajaUno.getAncho())* Constants.METERS_TO_PIXELS, cajaDos.getY()*Constants.METERS_TO_PIXELS},
                {cajaUno.getX()*Constants.METERS_TO_PIXELS, Constants.GROUND_START*Constants.METERS_TO_PIXELS},
                {(cajaUno.getX()-cajaUno.getAncho()*2)*Constants.METERS_TO_PIXELS,Constants.GROUND_START*Constants.METERS_TO_PIXELS}};
        return posicionesGato(posiciones);
    }
}
