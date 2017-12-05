package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tobias on 09/11/2017.
 */

public class StartScreen extends InputAdapter implements Screen{
    private final TetrisGame game;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private TextButton newGameButton;
    private TextButton HighscoreButton;
    private TextButton QuiteButton;

    public StartScreen(TetrisGame game) {
        this.game = game;
    }
    
    /**
    * Create the main menu.
    */
    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.ROYAL);
        textButtonStyle.down = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.over = skin.newDrawable("background", Color.BLUE);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }
    
    /**
    * Display the main menu.
    */
    @Override
    public void show() {
        stage = new Stage();
        background = new Texture(Gdx.files.internal("background.png"));
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        createBasicSkin();
        newGameButton = new TextButton("New game", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , 4*Gdx.graphics.getHeight()/6);
        newGameButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(newGameButton);
        HighscoreButton = new TextButton("Highscore", skin); // Use the initialized skin
        HighscoreButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , 3*Gdx.graphics.getHeight()/6);
        HighscoreButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                HighScore highScore=new HighScore();
                game.setScreen(new HighscoreScreen(game,highScore.getNames(),highScore.getPoints()));
            }
        });
        stage.addActor(HighscoreButton);
        stage.addActor(newGameButton);
        QuiteButton = new TextButton("Quite", skin); // Use the initialized skin
        QuiteButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , 2*Gdx.graphics.getHeight()/6);
        QuiteButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(QuiteButton);
    }
    
    /**
    * Refresh the user interface
    */
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }
    
   
    @Override
    public void resize(int width, int height) {

    }
    
   
    @Override
    public void pause() {

    }

    
    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        skin.dispose();
        stage.dispose();
    }
}
