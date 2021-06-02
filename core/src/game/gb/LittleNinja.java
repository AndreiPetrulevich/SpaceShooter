package game.gb;

import com.badlogic.gdx.Game;
import game.gb.screen.MenuScreen;

public class LittleNinja extends Game {
	
	@Override
	public void create () {
		setScreen(new MenuScreen());
	}
}
