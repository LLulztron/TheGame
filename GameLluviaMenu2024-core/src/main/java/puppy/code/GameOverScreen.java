/*package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	private final GameLluviaMenu game;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private OrthographicCamera camera;

	public GameOverScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw(batch, "GAME OVER ", 100, 200);
		font.draw(batch, "Toca en cualquier lado para reiniciar.", 100, 100);
		
		batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
*/
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture backgroundTexture; // Textura para el fondo
    private Sprite backgroundSprite;   // Sprite para manejar el fondo
    private Sound gameOverSound;       // Sonido para el Game Over

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar la textura del fondo
        backgroundTexture = new Texture(Gdx.files.internal("gameOver.jpeg")); // Cambia el nombre al archivo de fondo
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(800, 480); // Ajusta el tamaño del fondo para que cubra la pantalla

        // Cargar el sonido de Game Over
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover_sound.mp3")); // Cambia el nombre al archivo de sonido
    }

    @Override
    public void show() {
        // Reproducir el sonido de Game Over
        gameOverSound.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibuja el fondo
        backgroundSprite.draw(batch);
        
        // Mensaje de reinicio
        font.draw(batch, "Toca en cualquier lado para reiniciar.", 100, 100);

        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        gameOverSound.dispose(); // Liberar el sonido de Game Over
    }
}
