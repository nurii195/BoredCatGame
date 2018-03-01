package control;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import actores.Estructura;
import estructuras.Cattillo;
import estructuras.DobleCaja;
import estructuras.EstructuraAbstracta;
import estructuras.Piruleta;
import estructuras.PuertaGatuna;

/**
 * Created by Mary on 08/03/2016.
 */
public abstract class Sorteo {

	public int sorteo(int maximo) {
		return (int) (Math.random() * maximo);
	}

	public String sorteoGato() {
		String[] gatos = { "pusheen-ball.png", "pusheen-cut.png", "pusheen-viking.png" };
		return gatos[sorteo(gatos.length)];
	}

	public Estructura descubrirGanador() {
		switch (sorteo(4)) {
		case 0:
			DobleCaja dobleCaja = new DobleCaja();
			return dobleCaja;
		case 1:
			Piruleta piruleta = new Piruleta();
			return piruleta;
		case 2:
			PuertaGatuna puertaGatuna = new PuertaGatuna();
			return puertaGatuna;
		case 3:
			Cattillo cattillo = new Cattillo();
			return cattillo;
		}
		return null;
	}

	public abstract void inicializarEstructura(World world, Stage stage, EstructuraAbstracta estructura);

	public abstract void inicializarGato(World world, float[] posicion, Stage stage);

}
