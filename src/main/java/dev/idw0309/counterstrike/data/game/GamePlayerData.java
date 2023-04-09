package dev.idw0309.counterstrike.data.game;

public class GamePlayerData {

    private int ingamekills;
    private int ingamedeaths;
    private int ingamemoney;
    private int game; //saves in witch game they are (this will be a random created number on first join to allow multiple games)
    private Enum team; //saves in witch game they are
    private Enum state;

    private String handgun; //Gets current handgun
    private String automaticgun; //Gets current automaticgun
    private int armour; //Gets current armour | 0 = none, 1 = helmet, 2 = body + Helmet
    private String throable1; //Flashbang etc.
    private String throable2; //Flashbang etc.
    private String melee; //witch knife the player has etc. !!CURENTLY NOT USED!!

    public GamePlayerData(int ingamekills, int ingamedeaths, int ingamemoney, int game, Enum team, Enum state, String handgun, String automaticgun, int armour, String throable1, String throable2, String melee) {
        this.ingamekills = ingamekills;
        this.ingamedeaths = ingamedeaths;
        this.ingamemoney = ingamemoney;
        this.game = game;
        this.team = team;
        this.state = state;

        this.handgun = handgun;
        this.automaticgun = automaticgun;
        this.armour = armour;
        this.throable1 = throable1;
        this.throable2 = throable2;
        this.melee = melee;
    }

    public String getHandgun() { return handgun; }

    public void setHandgun(String handgun) { this.handgun = handgun; }


    public String getAutomaticgun() { return automaticgun; }

    public void setAutomaticgun(String automaticgun) { this.automaticgun = automaticgun; }


    public int getArmour() { return armour; }

    public void setArmour(int armour) { this.armour = armour; }


    public String getThroable1() { return throable1; }

    public void setThroable1(String throable1) { this.throable1 = throable1; }


    public String getThroable2() { return throable2; }

    public void setThroable2(String throable2) { this.throable2 = throable2; }


    public String getMelee() { return melee; }

    public void setMelee(String melee) { this.melee = melee; }


    //GET STATE
    public Enum getState() { return state; }

    public void setState(Enum state) {
        this.state = state;
    }

    //GET INGAME KILLS DATA
    public int getIngameKills() { return ingamekills; }

    public void setIngameKills(int ingamekills) {
        this.ingamekills = ingamekills;
    }

    public void addIngameKills() {
        ingamekills++;
    }


    //GET INGAME DEATHS DATA
    public int getIngameDeaths() { return ingamedeaths; }

    public void setIngameDeaths(int ingamedeaths) {
        this.ingamedeaths = ingamedeaths;
    }

    public void addIngameDeaths() {
        ingamedeaths++;
    }


    //GET INGAME MONEY DATA
    public int getIngameMoney() { return ingamemoney; }

    public void setIngameMoney(int ingamemoney) {
        this.ingamemoney = ingamemoney;
    }

    public void addIngameMoney(int money) {
        this.ingamemoney = ingamemoney + money;
    }

    public void removeIngameMoney(int money) { this.ingamemoney = ingamemoney - money; }


    //GET GAME DATA
    public int getGame() { return game; }

    public void setGame(int game) {
        this.game = game;
    }


    //GET TEAM DATA
    public Enum getTeam() { return team; }

    public void setTeam(Enum team) {
        this.team = team;
    }


}
