package dev.idw0309.counterstrike.data.game;

public class GameData {

    private int playercount; //Gets the playercount of the map
    private int minplayercount; //Gets the minimum amount of players to start the game
    private int maxplayercount; //Gets the maximum amount of players allowed to join the game
    private Enum state; //Gets the game state (Lobby/Started/Ended)
    private String map; //Gets the game state (Lobby/Started/Ended)
    private int round; //Gets the round witch is being played

    private int winsterrorist; //Gets the amount of wins the terrorist got this game
    private int winsdefenders; //Gets the amount of wins the defenders got this game

    private int amountterrorist; //Keeps track of the amount of players in te terrorist team in the game
    private int amountcounterterrorist; //Keeps track of the amount of players in te Counter-terrorist team in the game


    public GameData(int playercount, int minplayercount, int maxplayercount, String map, Enum state, int round,int amountcounterterrorist, int amountterrorist, int winsterrorist, int winsdefenders) {
        this.playercount = playercount;
        this.minplayercount = minplayercount;
        this.maxplayercount = maxplayercount;
        this.state = state;
        this.map = map;
        this.round = round;
        this.winsterrorist = winsterrorist;
        this.winsdefenders = winsdefenders;
        this.amountterrorist = amountterrorist;
        this.amountcounterterrorist = amountcounterterrorist;
    }

    //GET PLAYER COUNT DATA
    public int getPlayerCount() { return playercount; }

    public void addPlayerCount() {
        playercount++;
    }

    public void setPlayercount(int playercount) { this.playercount = playercount; }

    public void removePlayerCount() {
        playercount--;
    }


    //GET MINIMUM PLAYER COUNT DATA
    public int getMinPlayerCount() { return minplayercount; }

    public void setMinPlayerCount(int minplayercount) {
        this.minplayercount = minplayercount;
    }


    //GET MAXIMUM PLAYER COUNT DATA
    public int getMaxPlayerCount() { return maxplayercount; }

    public void setMaxPlayerCount(int maxplayercount) {
        this.maxplayercount = maxplayercount;
    }


    //GET STATE
    public Enum getGameState() { return state; }

    public void setGameState(Enum state) {
        this.state = state;
    }


    //GET MAP
    public String getMap() { return map; }

    public void setMap(String map) {
        this.map = map;
    }


    //GET ROUND DATA
    public int getGameRound() { return round; }

    public void addGameRound() {
        round++;
    }

    public void setGameRound(int round) {
        this.round = round;
    }


    //GET WINS TERROR DATA
    public int getWinsTerrorist() { return winsterrorist; }

    public void addWinsTerrorist() {
        winsterrorist++;
    }

    public void setWinsTerrorist(int winsterrorist) {
        this.winsterrorist = winsterrorist;
    }


    //GET WINS DEFENDERS DATA
    public int getWinsDefenders() { return winsdefenders; }

    public void addWinsDefenders() {
        winsdefenders++;
    }

    public void setWinsDefenders(int winsdefenders) {
        this.winsdefenders = winsdefenders;
    }


    public int getAmountTerrorist() { return amountterrorist; }

    public void addAmountTerrorist() {
        amountterrorist++;
    }

    public void removeAmountTerrorist() {
        amountterrorist--;
    }

    public void setAmountTerrorist(int amountterrorist) {
        this.amountterrorist = amountterrorist;
    }

    //GET PLAYER COUNT DATA
    public int getAmountCounterTerrorist() { return amountcounterterrorist; }

    public void addAmountCounterTerrorist() {
        amountcounterterrorist++;
    }

    public void removeAmountCounterTerrorist() {
        amountcounterterrorist--;
    }

    public void setAmountCounterTerrorist(int amountcounterterrorist) {
        this.amountcounterterrorist = amountcounterterrorist;
    }

}
