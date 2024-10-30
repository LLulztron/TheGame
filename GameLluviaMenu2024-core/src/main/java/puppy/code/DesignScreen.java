package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DesignScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture designTexture;
    private Sprite designSprite;

    public DesignScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar una textura de ejemplo para mostrar un diseño
        designTexture = new Texture(Gdx.files.internal("design_example.jpg"));
        designSprite = new Sprite(designTexture);
        designSprite.setPosition(100, 100);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        designSprite.draw(batch); // Dibuja el diseño
        batch.end();

        // Aquí podrías detectar un clic para regresar al menú principal
        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        designTexture.dispose();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}
