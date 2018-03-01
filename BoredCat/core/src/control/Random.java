package control;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import actores.Estructura;
import actores.GatoPorculero;
import estructuras.Cattillo;
import estructuras.DobleCaja;
import estructuras.EstructuraAbstracta;
import estructuras.Piruleta;
import estructuras.PuertaGatuna;
import utiles.Constants;

/**
 * Created by Mary on 03/03/2016.
 */
public class Random extends Sorteo {

	private Body bodyGato;
	private Estructura estructura;

	public Random() {
	}

	public Random(World world, Stage stage) {
		colocar(world, stage);
	}

	public void colocar(World world, Stage stage) {
		estructura = descubrirGanador();
		if (estructura == null) {
			colocar(world, stage);
		} else {
			inicializarEstructura(world, stage, (EstructuraAbstracta) estructura);
			inicializarGato(world, ((EstructuraAbstracta) estructura).posiciones(), stage);
		}
	}

	public Body getBodyGato() {
		return bodyGato;
	}

	@Override
	public void inicializarEstructura(World world, Stage stage, EstructuraAbstracta estructura) {
		estructura.init(world);
		estructura.mover(Constants.INIX, Constants.GROUND_START);
		stage.addActor(estructura);
	}

	@Override
	public void inicializarGato(World world, float[] posicion, Stage stage) {
		GatoPorculero gatoPorculero = new GatoPorculero(world, posicion[0], posicion[1], sorteoGato());
		Body bodyGato = gatoPorculero.getBody();
		stage.addActor(gatoPorculero);
	}

}
