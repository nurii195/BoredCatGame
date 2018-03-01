package estructuras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import actores.Estructura;
import control.Random;
import materiales.Material;
import utiles.Constants;

/**
 * Created by Mary on 04/03/2016.
 */
public abstract class EstructuraAbstracta extends Estructura {
	
      public void mover(float x, float y){
        for (Material m : materials) {
            m.move(x + m.getX(), y + m.getY());
        }
    }

    public void init(World world){
        for (Material m : materials) {
            m.init(world);
        }
    }
    
    public float[] posicionesGato(float[][] posiciones) {
    	Random random = new Random();
		int sorteo = random.sorteo(posiciones.length);
		Gdx.app.log("posicion: ", String.valueOf(sorteo));
		float[] resultado = { posiciones[sorteo][0], posiciones[sorteo][1] };
		return resultado;
    }

	public abstract float[] posiciones();
	
}
