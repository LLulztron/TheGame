package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;		
    private OrthographicCamera camera;
    private Music introMusic;
    private Texture buttonTexture;
    private Sprite buttonSprite;
    private Texture designButtonTexture;
    private Sprite designButtonSprite; // Botón para el apartado de diseños
    private Texture logoTexture;
    private Sprite logoSprite;
    private Texture cloudTexture;
    private Sprite cloudSprite;

    public MainMenuScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar la música
        introMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
        introMusic.setLooping(true);

        // Cargar la textura del botón principal
        buttonTexture = new Texture(Gdx.files.internal("button.png"));
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(225, 170);

        // Cargar la textura del botón "Diseños Disponibles"
        designButtonTexture = new Texture(Gdx.files.internal("temas.jpg"));
        designButtonSprite = new Sprite(designButtonTexture);
        designButtonSprite.setPosition(0, 0); // Posición del nuevo botón

        // Cargar la textura del logo
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoSprite = new Sprite(logoTexture);
        logoSprite.setPosition(200, 240);

        // Cargar la textura de nubes
        cloudTexture = new Texture(Gdx.files.internal("clouds.jpg"));
        cloudSprite = new Sprite(cloudTexture);
        cloudSprite.setSize(800, 480);
        cloudSprite.setPosition(0, 0);
    }

    @Override
    public void show() {
        introMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibuja el fondo de nubes
        cloudSprite.draw(batch);

        // Dibuja el logo
        logoSprite.draw(batch);

        // Dibuja el botón de inicio
        buttonSprite.draw(batch);

        // Dibuja el botón "Diseños Disponibles"
        designButtonSprite.draw(batch);

        batch.end();

        // Detecta si se toca dentro del botón de inicio
        if (Gdx.input.isTouched()) {
            // Obtiene las coordenadas de toque y las convierte a coordenadas del juego
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convierte a coordenadas del mundo de la cámara

            // Verifica si el toque está dentro del botón de inicio
            if (buttonSprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }

            // Verifica si el toque está dentro del botón "Diseños Disponibles"
            if (designButtonSprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new DesignScreen(game)); // Cambia a la nueva pantalla
                dispose();
            }
        }
    }

    @Override
    public void hide() {
        introMusic.stop();
    }

    @Override
    public void dispose() {
        introMusic.dispose();
        buttonTexture.dispose();
        designButtonTexture.dispose(); // Liberar la textura del nuevo botón
        logoTexture.dispose();
        cloudTexture.dispose();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}
