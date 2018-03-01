package estructuras;

import com.badlogic.gdx.physics.box2d.World;
import materiales.Cuadrado;
import materiales.TablaHorizontal;
import materiales.TablaVertical;
import utiles.Constants;

/**
 * Created by usuario on 08/03/2016.
 */
public class Cattillo extends EstructuraAbstracta {
	
	TablaVertical tabla1;
	TablaVertical tabla2;
	TablaHorizontal tabla3;
	TablaHorizontal tabla4;
	Cuadrado cuadro1;
	Cuadrado cuadro2;

	public Cattillo() {
		tabla1 = new TablaVertical(true);
		tabla2 = new TablaVertical(true);
		tabla3 = new TablaHorizontal(true);
		tabla4 = new TablaHorizontal(true);
		cuadro1 = new Cuadrado();
		cuadro2 = new Cuadrado();
		
		materials.add(cuadro1);
		materials.add(cuadro2);
		materials.add(tabla1);
		materials.add(tabla2);
		materials.add(tabla3);
		materials.add(tabla4);
		
	}

	@Override
	public void init(World world) {
		super.init(world);
		tabla1.move(0, 0);
		cuadro1.move(tabla1.getAncho(), 0);
		cuadro2.move(tabla1.getAncho() + cuadro1.getAncho(), 0);
		tabla2.move(tabla1.getAncho() + cuadro1.getAncho() * 2, 0);
		tabla3.move((tabla1.getX()) - tabla3.getAncho() / 2, tabla1.getAlto());
		tabla4.move((tabla2.getX()) - tabla4.getAncho() / 2, tabla2.getAlto());
	}
	
	public float[] posiciones() {
		float[][] posiciones = {
				{ (tabla1.getX() - tabla1.getAncho() * 2) * Constants.METERS_TO_PIXELS,
						(tabla1.getAlto() + tabla3.getAlto() + Constants.GROUND_START) * Constants.METERS_TO_PIXELS },
				{ (tabla2.getX() - tabla2.getAncho() * 2) * Constants.METERS_TO_PIXELS,
						(tabla1.getAlto() + tabla3.getAlto() + Constants.GROUND_START) * Constants.METERS_TO_PIXELS },
				{ tabla2.getX() * Constants.METERS_TO_PIXELS, Constants.GROUND_START * Constants.METERS_TO_PIXELS },
				{ (tabla3.getX() - (cuadro1.getAncho() * 2)) * Constants.METERS_TO_PIXELS,
						Constants.GROUND_START * Constants.METERS_TO_PIXELS },
				{ (cuadro1.getX()) * Constants.METERS_TO_PIXELS,
						(Constants.GROUND_START + cuadro1.getAlto()) * Constants.METERS_TO_PIXELS } };
		return posicionesGato(posiciones);
	}

}
