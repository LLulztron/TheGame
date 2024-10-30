package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;

    private Texture backgroundImage; // Fondo de juego

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        backgroundImage = new Texture(Gdx.files.internal("Fondo.jpg")); // Ajusta el nombre del archivo de imagen

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaBuff = new Texture(Gdx.files.internal("healthDrp.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(gota, gotaMala,gotaBuff ,dropSound, rainMusic);
        

        // Camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Tarro and lluvia creation
        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con un color específico
        ScreenUtils.clear(0, 0, 0.1f, 1);
        
        // Actualizar la cámara
        camera.update();
        
        // Configurar la matriz de proyección del batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar el fondo
        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Dibujar textos
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);
        
        if (!tarro.estaHerido()) {
            // Movimiento del tarro desde teclado
            tarro.actualizarMovimiento();
            // Caída de la lluvia 
            if (!lluvia.actualizarMovimiento(tarro)) {
                // Actualizar HighScore
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());  
                // Ir a la ventana de fin de juego y destruir la actual
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }
        
        // Dibujar el tarro y la lluvia
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() {
        // Continuar con el sonido de lluvia
        lluvia.continuar();
    }

    @Override
    public void hide() { }

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this)); 
    }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
        backgroundImage.dispose(); // Liberar la textura del fondo
    }
}
