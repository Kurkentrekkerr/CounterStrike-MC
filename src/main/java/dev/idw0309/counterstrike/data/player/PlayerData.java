package dev.idw0309.counterstrike.data.player;

public class PlayerData {

    private int totalkills;
    private int totaldeaths;
    private int totalmoney;
    private int wins;
    private int loses;
    private String rank;
    private int level;

    public PlayerData(int totalkills, int totaldeaths, int totalmoney, int wins, int loses, String rank, int level) {
        this.totalkills = totalkills;
        this.totaldeaths = totaldeaths;
        this.totalmoney = totalmoney;
        this.wins = wins;
        this.loses = loses;
        this.rank = rank;
        this.level = level;
    }


    //GET TOTALKILLS DATA
    public int getTotalKills() { return totalkills; }

    public void setTotalKills(int totalkills) {
        this.totalkills = totalkills;
    }

    public void addTotalKills(int totalkills) {
        this.totalkills = this.totalkills+totalkills;
    }


    //GET TOTALDEATHS DATA
    public int getTotalDeaths() { return totaldeaths; }

    public void setTotalDeaths(int totaldeaths) {
        this.totaldeaths = totaldeaths;
    }

    public void addTotalDeaths(int deaths) {
        this.totaldeaths = this.totaldeaths+deaths;
    }


    //GET TOTALMONEY DATA
    public int getTotalMoney() { return totalmoney; }

    public void setTotalMoney(int totalmoney) {
        this.totalmoney = totalkills;
    }

    public void addTotalMoney(int money) {
        this.totalmoney = totalmoney + money;
    }

    public void removeTotalMoney(int money) {
        this.totalmoney = totalmoney + money;
    }


    //GET WINS DATA
    public int getWins() { return wins; }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWins() {
        wins++;
    }


    //GET LOSES DATA
    public int getLoses() { return loses; }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public void addLoses() {
        loses++;
    }


    //GET RANK DATA
    public String getRank() { return rank; }

    public void setRank(String rank) {
        this.rank = rank;
    }


    //GET LEVEL DATA !!!CURRENTLY NOT USED!!!
    public int getLevel() { return level; }

    public void setRank(int level) {
        this.level = level;
    }

    public void addLevel() {
        level++;
    }

}
