package actores;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import materiales.Material;

public class Estructura extends Actor{

	  protected List<Material> materials = new ArrayList<Material>();
	  
	    public void act(float delta) {
	        super.act(delta);
	        for (Material m : materials) {
	            m.act();
	        }
	    }

	    public void draw(Batch batch, float parentAlpha) {
	        for (Material m : materials) {
	            m.draw(batch);
	        }
	    }

}
