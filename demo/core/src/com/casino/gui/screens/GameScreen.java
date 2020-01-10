package com.casino.gui.screens;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.casino.deck.Card;
import com.casino.gui.MyGame;
import com.casino.main.Casino;
import com.casino.util.CPN;
//import com.badlogic.gdx.utils.Align;

public class GameScreen implements Screen {
	private MyGame game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private AssetManager asset;
	private TextureAtlas atlas;
	private Sprite background;
	private Stage stage;
	private ShapeRenderer shapeRenderer; // for drawing debug lines
	private Skin skin;
	private Table debugLine;
	
	// start screen widgets
	private Table startScreenPanel, createNewPlayerPanel;
	private Label playerNameLabel, playerAgeLabel, playerAgeText;
	private TextField playerNameTextField;
	private Slider playerAgeSlider;
	private TextButton createPlayerButton; 
	
	// game screen widgets
	private Table mainPlayerPanel;
	private Table buttonTable;
	private TextButton foldButton;
	private TextButton raiseButton;
	private TextButton allInButton;
	private TextButton callButton;
	private TextButton restartButton;
	private TextButton quitButton;
	private TextButton okRaiseButton;
	private TextButton cancelRaiseButton;
	private Slider slider;
	private Label sliderValue;
	private final int PADDING_SIZE = 5;
	private Image flop_1;
	private Image flop_2;
	private Image flop_3;
	private Image turn;
	private Image river;
	
	// main player
	private Stack mainPlayerAvatarStack;
	private Label mainPlayerName;
	private Label mainPlayerChips;
	private Image mainPlayerAvatarBorder;
	private Image mainPlayerInfoPanel;
	private Image mainPlayerAvatar;
	private Image mainPlayerCard_1;
	private Image mainPlayerCard_2;
	
	// player 1
	private Image player1Avatar;
	private Label player1Name;
	private Label player1Chips;
	
	// player 2
	private Image player2Avatar;
	private Label player2Name;
	private Label player2Chips;
	
	// player 3
	private Image player3Avatar;
	private Label player3Name;
	private Label player3Chips;
	
	// player 4
	private Image player4Avatar;
	private Label player4Name;
	private Label player4Chips;
	
	// player 5
	private Image player5Avatar;
	private Label player5Name;
	private Label player5Chips;
	
	// message
	private Label message;
	
	// casino game info
	private Casino casino;
	private String playerName;
	private int betValue = 50;
	private int playerAge = 18; // minimum age is 18
	private final int DEFAULT_CHIPS = 1000;
	private int minBet = 50, maxBet = 1000;
	
	public GameScreen(MyGame g) {
		this.game = g;

		// basic setup
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);

		this.batch = new SpriteBatch();
		
		this.asset = new AssetManager();
		// Scene2D - create new stage
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		this.shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		
		// create casino class
		this.casino = new Casino();
		
		createStartScreen();
		stage.addActor(startScreenPanel);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// set camera projection
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		batch.end();
		
		// draw stage
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		// draw debug lines
		shapeRenderer.begin();
		debugLine.drawDebug(shapeRenderer); 
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		game.dispose();
		asset.dispose();
		atlas.dispose();
		stage.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	public void createStartScreen() {
		setAtlast("atlas/create_player_screen.pack");
		
		background = new Sprite(atlas.findRegion("cp_background"));
		background.setPosition(0,0);
		
		// create a new stage and ShapeRenderer
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		
		// create text input and button
		skin = new Skin(Gdx.files.internal("jsons/create_player_screen.json"), atlas);
		
		boolean DEBUG_ENABLE = false;
		final int MIN_AGE = 18;
		final int MAX_AGE = 100;
		
		startScreenPanel = new Table();
		//startScreenPanel.setFillParent(true);
		startScreenPanel.setDebug(DEBUG_ENABLE);
		startScreenPanel.setPosition(350,400);
		
		createNewPlayerPanel = new Table();
		createNewPlayerPanel.setDebug(DEBUG_ENABLE);
		
		Label titleLabel = new Label("Create new player", skin);
		playerNameLabel = new Label("Name: ", skin);
		playerAgeLabel = new Label("Age: ", skin);
		
		playerNameTextField = new TextField("player_00", skin);
		playerNameTextField.setMaxLength(10);
		
		playerAgeSlider = new Slider(MIN_AGE, MAX_AGE, 1, false, skin);
		playerAgeText = new Label("18", skin);
		
		createPlayerButton = new TextButton("Create", skin);
		quitButton = new TextButton("Quit", skin);
		
		// add widgets to table
		createNewPlayerPanel.add(titleLabel).colspan(2).align(Align.left);
		createNewPlayerPanel.row();
		createNewPlayerPanel.add(playerNameLabel).align(Align.right).padTop(10);
		createNewPlayerPanel.add(playerNameTextField).padTop(10);
		createNewPlayerPanel.row();
		createNewPlayerPanel.add(playerAgeLabel).align(Align.right).padTop(10);
		createNewPlayerPanel.add(playerAgeText);
		createNewPlayerPanel.row();
		createNewPlayerPanel.add(playerAgeSlider).colspan(2).align(Align.right);
		createNewPlayerPanel.row();
		createNewPlayerPanel.add(createPlayerButton).padTop(10);
		createNewPlayerPanel.add(quitButton).padTop(10);
		
		startScreenPanel.add(createNewPlayerPanel);
		
		debugLine = startScreenPanel;
		
		playerAgeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerAge = Math.round(playerAgeSlider.getValue());
				playerAgeText.setText(Integer.toString(playerAge));
			}
		});	
		
		// add widgets listener
		createPlayerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playerName = playerNameTextField.getText();
				
				/* 	add player[0] (new player) to casino
				 * 	initialize player, slot, and table.
				 * 	print its log
				 */
				casino.createNewPlayer(playerName, playerAge, DEFAULT_CHIPS, "default_2");
				casino.initialize();
				casino.activeTurn(CPN.PLAYER00);
				casino.log();
				
				/* clear the stage and change to next stage(game stage) */
				stage.clear();
				createGameScreen();

			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.exit(0);
			}
		});
	}
	
	public void createGameScreen() {
		// load Background
		setAtlast("atlas/game_screen.pack");

		background = new Sprite(atlas.findRegion("table"));
		background.setPosition(0, 0);

		// Create skin
		skin = new Skin(Gdx.files.internal("jsons/game_screen.json"), atlas);
		
		
		// Create main player
		// Main Player Panel - create four button Fold, Check, Call, Raise
		mainPlayerPanel = new Table();
		mainPlayerPanel.setFillParent(true);
		mainPlayerPanel.setPosition(0, -308);
		stage.addActor(mainPlayerPanel);

		final boolean ENABLE_DEBUG = false;
		buttonTable = new Table();
		buttonTable.setDebug(ENABLE_DEBUG);
		buttonTable.padTop(15);

		foldButton = new TextButton("Fold", skin);
		raiseButton = new TextButton("Raise", skin);
		allInButton = new TextButton("All in", skin);
		callButton = new TextButton("Call", skin);
		restartButton = new TextButton("Restart", skin);
		quitButton = new TextButton("Quit", skin);

		// add slider for raise
		maxBet = casino.getPlayer(CPN.PLAYER00).getChips();
		slider = new Slider(minBet, maxBet, 5, false, skin);
		slider.setSize(300, 10);
		Label sliderLabel = new Label("Raise to: ", skin);
		sliderValue = new Label("$50", skin);		
		Table sliderLabelAndValueTable = new Table();
		sliderLabelAndValueTable.setDebug(ENABLE_DEBUG);
		sliderLabelAndValueTable.add(sliderLabel);
		sliderLabelAndValueTable.add(sliderValue);

		// create confirm raise and cancel raise button
		okRaiseButton = new TextButton("OK", skin);
		cancelRaiseButton = new TextButton("Cancel", skin);

		// create slider table and add it to the stage
		final Table sliderTable = new Table();
		sliderTable.setFillParent(true);
		sliderTable.setPosition(0, -308);
		sliderTable.setVisible(false);
		sliderTable.add(sliderLabelAndValueTable).colspan(2).padBottom(5);
		sliderTable.row();
		sliderTable.add(slider).colspan(2).padBottom(5);
		sliderTable.row();
		sliderTable.add(okRaiseButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);
		sliderTable.add(cancelRaiseButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);
		stage.addActor(sliderTable);

		// create profile picture
		String avatar = casino.getPlayer(CPN.PLAYER00).getAvatar();
		String name = casino.getPlayer(CPN.PLAYER00).getName();
		String chips = Integer.toString(casino.getPlayer(CPN.PLAYER00).getChips());
		String card_1 = casino.getSlot(CPN.PLAYER00).getCard(CPN.PLAYER_FIRST_CARD).getFace()+"_"+
				casino.getSlot(CPN.PLAYER00).getCard(CPN.PLAYER_FIRST_CARD).getSuit();
		String card_2 = casino.getSlot(CPN.PLAYER00).getCard(CPN.PLAYER_SECOND_CARD).getFace()+"_"+
				casino.getSlot(CPN.PLAYER00).getCard(CPN.PLAYER_SECOND_CARD).getSuit();
		
		mainPlayerAvatarStack = new Stack();
		mainPlayerAvatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		mainPlayerAvatar.scaleBy(-.07f);
		mainPlayerAvatar.setOrigin(65,65);
		mainPlayerAvatarBorder = new Image(atlas.findRegion("avatar_border"));
		mainPlayerAvatarStack.add(mainPlayerAvatar);
		mainPlayerAvatarStack.add(mainPlayerAvatarBorder);
		
		setAtlast("atlas/card.pack");
		mainPlayerCard_1 = new Image(atlas.findRegion(card_1));
		mainPlayerCard_1.setPosition(408, 173.5f);
		mainPlayerCard_2 = new Image(atlas.findRegion(card_2));
		mainPlayerCard_2.setPosition(511, 173.5f);
		
		// create user info
		mainPlayerName = new Label(name, new Skin(Gdx.files.internal("jsons/player_info.json")));
		mainPlayerName.setPosition(340, 80);
		mainPlayerChips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/player_info.json")));
		mainPlayerChips.setPosition(340, 30);
		setAtlast("atlas/game_screen.pack");
		mainPlayerInfoPanel = new Image(atlas.findRegion("main_player_panel"));

		stage.addActor(mainPlayerName);
		stage.addActor(mainPlayerChips);
		stage.addActor(mainPlayerCard_1);
		stage.addActor(mainPlayerCard_2);
		
		// Add widgets to table
		Table leftSide = new Table();
		leftSide.setDebug(ENABLE_DEBUG);
		leftSide.add(mainPlayerAvatarStack).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);
		leftSide.add(mainPlayerInfoPanel).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);

		Table rightSide = new Table();
		rightSide.setDebug(ENABLE_DEBUG);
		rightSide.add(foldButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE).padBottom(5);
		rightSide.add(raiseButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE).padBottom(5);
		rightSide.add(restartButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE).padBottom(5);
		rightSide.row();
		rightSide.add(allInButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);
		rightSide.add(callButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);
		rightSide.add(quitButton).padLeft(PADDING_SIZE).padRight(PADDING_SIZE);

		buttonTable.add(leftSide);
		buttonTable.add(rightSide);
		mainPlayerPanel.add(buttonTable);
		setBetInfo(0, 420, 330);
		setStatus(0, 420, 318);


		// create other players
		// player 1
		Table player1Table = new Table();
		player1Table.setFillParent(true);
		player1Table.setPosition(-380, -140);
		player1Table.setDebug(ENABLE_DEBUG);
		Image player1Profile = new Image(atlas.findRegion("players_panel"));
		player1Table.add(player1Profile);

		avatar = casino.getPlayer(CPN.PLAYER01).getAvatar();
		name = casino.getPlayer(CPN.PLAYER01).getName();
		chips = Integer.toString(casino.getPlayer(CPN.PLAYER01).getChips());
		player1Avatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		player1Avatar.setSize(60, 60);
		player1Avatar.setPosition(53, 238);
		
		player1Name = new Label(name, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player1Name.setPosition(67, 216);
		player1Chips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player1Chips.setPosition(67, 195);
		
		stage.addActor(player1Table);
		stage.addActor(player1Avatar);
		stage.addActor(player1Name);
		stage.addActor(player1Chips);
		setStatus(1, 40, 310);
		setBetInfo(1, 40, 322);
		
		// player 2
		Table player2Table = new Table();
		player2Table.setFillParent(true);
		player2Table.setPosition(-380, 280);
		player2Table.setDebug(ENABLE_DEBUG);
		Image player2Profile = new Image(atlas.findRegion("players_panel"));
		player2Table.add(player2Profile);
		
		avatar = casino.getPlayer(CPN.PLAYER02).getAvatar();
		name = casino.getPlayer(CPN.PLAYER02).getName();
		chips = Integer.toString(casino.getPlayer(CPN.PLAYER02).getChips());
		player2Avatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		player2Avatar.setSize(60, 60);
		player2Avatar.setPosition(53, 658);
		
		player2Name = new Label(name, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player2Name.setPosition(67, 636);
		player2Chips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player2Chips.setPosition(67, 615);
		
		stage.addActor(player2Table);
		stage.addActor(player2Avatar);
		stage.addActor(player2Name);
		stage.addActor(player2Chips);
		setStatus(2, 40, 588);
		setBetInfo(2, 40, 576);

		// player 3
		Table player3Table = new Table();
		player3Table.setFillParent(true);
		player3Table.setPosition(0, 280);
		player3Table.setDebug(ENABLE_DEBUG);
		Image player3Profile = new Image(atlas.findRegion("players_panel"));
		player3Table.add(player3Profile);

		avatar = casino.getPlayer(CPN.PLAYER03).getAvatar();
		name = casino.getPlayer(CPN.PLAYER03).getName();
		chips = Integer.toString(casino.getPlayer(CPN.PLAYER03).getChips());
		player3Avatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		player3Avatar.setSize(60, 60);
		player3Avatar.setPosition(433, 658);
		
		player3Name = new Label(name, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player3Name.setPosition(447, 636);
		player3Chips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player3Chips.setPosition(447, 615);
		
		stage.addActor(player3Table);
		stage.addActor(player3Avatar);
		stage.addActor(player3Name);
		stage.addActor(player3Chips);
		setStatus(3, 420, 588);
		setBetInfo(3, 420, 576);

		// player 4
		Table player4Table = new Table();
		player4Table.setFillParent(true);
		player4Table.setPosition(380, 280);
		player4Table.setDebug(ENABLE_DEBUG);
		Image player4Profile = new Image(atlas.findRegion("players_panel"));
		player4Table.add(player4Profile);

		avatar = casino.getPlayer(CPN.PLAYER04).getAvatar();
		name = casino.getPlayer(CPN.PLAYER04).getName();
		chips = Integer.toString(casino.getPlayer(CPN.PLAYER04).getChips());
		player4Avatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		player4Avatar.setSize(60, 60);
		player4Avatar.setPosition(813, 658);
		
		player4Name = new Label(name, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player4Name.setPosition(827, 636);
		player4Chips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player4Chips.setPosition(827, 615);
		
		stage.addActor(player4Table);
		stage.addActor(player4Avatar);
		stage.addActor(player4Name);
		stage.addActor(player4Chips);
		setStatus(4, 800, 588);
		setBetInfo(4, 800, 576);
		
		// player 5
		Table player5Table = new Table();
		player5Table.setFillParent(true);
		player5Table.setPosition(380, -140);
		player5Table.setDebug(ENABLE_DEBUG);
		Image player5Profile = new Image(atlas.findRegion("players_panel"));
		player5Table.add(player5Profile);
		
		avatar = casino.getPlayer(CPN.PLAYER05).getAvatar();
		name = casino.getPlayer(CPN.PLAYER05).getName();
		chips = Integer.toString(casino.getPlayer(CPN.PLAYER05).getChips());
		player5Avatar = new Image(new Texture(Gdx.files.internal("images/"+avatar+".png")));
		player5Avatar.setSize(60, 60);
		player5Avatar.setPosition(813, 238);
		
		player5Name = new Label(name, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player5Name.setPosition(827, 216);
		player5Chips = new Label("$"+chips, new Skin(Gdx.files.internal("jsons/other_player.json")));
		player5Chips.setPosition(827, 195);
		
		stage.addActor(player5Table);
		stage.addActor(player5Avatar);
		stage.addActor(player5Name);
		stage.addActor(player5Chips);
		setStatus(5, 800, 310);
		setBetInfo(5, 800, 322);
		
		// Message
		message = new Label("", skin);
		message.setPosition(450,50);
		
		stage.addActor(message);
		
		// Add Flop to the table
		if(casino.isFlopShowed()) {
			System.out.println("Setting flop...");
			Card flop = casino.getPoker().getFlop(CPN.Flop.FIRST_FLOP);
			String suit = flop.getSuit();
			String face = flop.getFace();
			setAtlast("atlas/card.pack");
			flop_1 = new Image(atlas.findRegion(face+"_"+suit));
			flop_1.setPosition(261, 381);
			stage.addActor(flop_1);
			
			flop = casino.getPoker().getFlop(CPN.Flop.SECOND_FLOP);
			suit = flop.getSuit();
			face = flop.getFace();
			setAtlast("atlas/card.pack");
			flop_2 = new Image(atlas.findRegion(face+"_"+suit));
			flop_2.setPosition(363, 381);
			stage.addActor(flop_2);
			
			flop = casino.getPoker().getFlop(CPN.Flop.THIRD_FLOP);
			suit = flop.getSuit();
			face = flop.getFace();
			setAtlast("atlas/card.pack");
			flop_3 = new Image(atlas.findRegion(face+"_"+suit));
			flop_3.setPosition(465, 381);
			stage.addActor(flop_3);
			
			System.out.println("Called displayFlop() successfull!");
		}
		
		// Add turn
		if(casino.isTurnShowed()) {
			Card t = casino.getPoker().getTurnCard();
			String suit = t.getSuit();
			String face = t.getFace();
			setAtlast("atlas/card.pack");
			turn = new Image(atlas.findRegion(face+"_"+suit));
			turn.setPosition(567, 381);
			stage.addActor(turn);
		}
		
		// Add river
		if(casino.isRiverShowed()) {
			Card t = casino.getPoker().getRiverCard();
			String suit = t.getSuit();
			String face = t.getFace();
			setAtlast("atlas/card.pack");
			river = new Image(atlas.findRegion(face+"_"+suit));
			river.setPosition(669, 381);
			stage.addActor(river);
		}
		
		
		
		// Add listeners to button
		foldButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		raiseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(//casino.getSlot(CPN.PLAYER00).isPlayerTurn() &&
						casino.getPlayer(CPN.PLAYER00).getChips() > minBet) {
					sliderTable.setVisible(true);
					buttonTable.setVisible(false);
					mainPlayerName.setVisible(false);
					mainPlayerChips.setVisible(false);
				} else {
					sliderTable.setVisible(false);
					buttonTable.setVisible(true);
					mainPlayerName.setVisible(true);
					mainPlayerChips.setVisible(true);
				}
			}
		});
		
		okRaiseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//casino.playerBet(CPN.PLAYER00, betValue);
				casino.playerRaise(CPN.PLAYER00, betValue);
				String temp = Integer.toString(casino.getPlayer(CPN.PLAYER00).getChips());
				mainPlayerChips.setText("$"+temp);
				if(casino.getPlayer(CPN.PLAYER00).getChips() >= minBet) {
					maxBet = casino.getPlayer(CPN.PLAYER00).getChips();
				} else {
					minBet = 0;
					maxBet = 0;
				}
				sliderTable.setVisible(false);
				buttonTable.setVisible(true);
				mainPlayerName.setVisible(true);
				mainPlayerChips.setVisible(true);
				stage.clear();
				createGameScreen();
					
			}
		});
		
		cancelRaiseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sliderTable.setVisible(false);
				buttonTable.setVisible(true);
				mainPlayerName.setVisible(true);
				mainPlayerChips.setVisible(true);
			}
		});
		
		callButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				casino.playerCall(CPN.PLAYER00);
				if(casino.getPlayer(CPN.PLAYER00).getChips() >= minBet) {
					maxBet = casino.getPlayer(CPN.PLAYER00).getChips();
				}
				stage.clear();
				createGameScreen();
				casino.log();
			}
		});
		
		
		allInButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				casino.playerAllin(CPN.PLAYER00);
				minBet = 0; maxBet = 0;
				stage.clear();
				createGameScreen();
			}
		}); 
		
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
			}
		});
		
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.exit(0);
			}
		});
		
		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				betValue = Math.round(slider.getValue());
				sliderValue.setText("$"+Integer.toString(betValue));
			}
		});	
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(casino.update1GameStage || casino.update2GameStage || casino.update3GameStage
						|| casino.update4GameStage) {
					createGameScreen();
					casino.setUpdateGameStage();
				}
			}

		}, 0, 1, TimeUnit.MILLISECONDS);
	}
	
	private void setBetInfo(int i, int x, int y) {
		Label bet = new Label("", new Skin(Gdx.files.internal("jsons/status.json")));
		String s = Integer.toString(casino.getSlot(i).getBetAmount());
		bet.setText("Bet: "+s);
		bet.setPosition(x, y);
		if(casino.getSlot(i).getBetAmount() > 0) {
			stage.addActor(bet);
		}
	}
	
	private void setStatus(int i, int x, int y) {
		Label status = new Label("", new Skin(Gdx.files.internal("jsons/status.json")));
		String command = casino.getSlot(i).getCommand();
		if(command == CPN.CommandString.RAISE) {
			command = command+" to: $"+casino.getSlot(i).getBetAmount();
		}
		status.setText(command);
		status.setPosition(x, y);
		stage.addActor(status);
	}
	
	private void setAtlast(String pack) {
		asset.load(pack,TextureAtlas.class);
		asset.finishLoading();
		
		atlas = asset.get(pack);
	}
}
