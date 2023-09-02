package com.svalero.reactive.api.controller;

import com.opencsv.CSVWriter;
import com.svalero.reactive.api.model.DataPlayer;
import com.svalero.reactive.api.model.DataTeam;
import com.svalero.reactive.api.model.Player;
import com.svalero.reactive.api.model.Team;
import com.svalero.reactive.api.task.PlayerTask;
import com.svalero.reactive.api.task.TeamTask;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppController {

    public Button btLoadAllPlayers;
    public Button btLoadAllTeams;
    public Button btDeletePlayer;
    public Button btExport;


    public TextField tfIdPlayer;
    public TextArea playersArea;
    public ListView teamsList;
    public ProgressBar pbProgress;
    public List<String> teams;
    public List<Player> players;
    public ObservableList<Object> teamsResults;
    private TeamTask teamTask;
    private PlayerTask playerTask;

    //Inicializamos
    @FXML
    public void initialize() {
        pbProgress.setProgress(0.0);
        teamsResults = FXCollections.observableArrayList();
        this.teamsList.setItems(this.teamsResults); //suscripcion
    }

    //Evento que se ejecuta al presionar el boton "players"
    @FXML
    public void loadAllPlayers(ActionEvent event) {
        this.players = new ArrayList<>();
        playersArea.setText("");


        Consumer<DataPlayer> userPlayer = (dataPlayer -> {

            Platform.runLater(() -> {
                pbProgress.progressProperty().unbind();
                pbProgress.progressProperty().bind(playerTask.progressProperty());
            });

            /*Platform.runLater(() -> {
                pbProgress.setProgress(0);
            });*/

            String text = playersArea.getText() + "\n";
            //double length = 1/dataPlayer.getData().toArray().length;
            for (Player player : dataPlayer.getData()) {
                Thread.sleep(100);
                playersArea.appendText("ID: " + player.getId() + " Name: " + player.getFirst_name() + " Surname: " + player.getLast_name() + " Position: " + player.getPosition() + "Team: " + player.getTeam() + "\n\n");
                this.players.add(player);
                /*Platform.runLater(() -> {
                    this.pbProgress.setProgress(length * 1/this.players.toArray().length);
                });*/
            }
            this.pbProgress.setVisible(false);

            /*Platform.runLater(() -> {
                this.pbProgress.setProgress(1.0);
            });*/
        });
        this.playerTask = new PlayerTask(userPlayer, pbProgress);
        new Thread(playerTask).start();
    }

    //Evento que se ejecuta al presionar el boton "teams"
    @FXML
    public void loadAllTeams(ActionEvent event) {
        this.teams = new ArrayList<String>();

        Consumer<DataTeam> userTeam = (dataTeam -> {

            Platform.runLater(() -> {
                pbProgress.progressProperty().unbind();
                pbProgress.progressProperty().bind(teamTask.progressProperty());
            });

            /*Platform.runLater(() -> {
                pbProgress.setProgress(0);
            });*/

            for (Team team : dataTeam.getData()) {
                Thread.sleep(100);
                this.teamsResults.add(" Name: " + team.getName() + " Abbrev: " + team.getAbbreviation() + " Conference: " + team.getConference() + " Division: " +
                        team.getDivision() + " Full name: " + team.getFull_name());
            }
            /*Platform.runLater(() -> {
                pbProgress.setProgress(1.0);
            });*/
            this.pbProgress.setVisible(false);
        });

        this.teamTask = new TeamTask(userTeam, pbProgress);
        new Thread(teamTask).start();
    }

    //Eliminar jugador por ID
    @FXML
    public void deletePlayer(ActionEvent event) {
        int playerId = Integer.parseInt(tfIdPlayer.getText());
        players.removeIf(player -> player.getId() == playerId);
        this.playersArea.setText("");
        for (Player player : this.players) {
            this.playersArea.appendText(player.getId() + " " + player.getFirst_name() + " " + player.getLast_name() + " " + player.getPosition() + " " + player.getTeam() + "\n\n");
        }
    }

    //Exportar a CSV los equipos
    @FXML
    public void exportCSV(ActionEvent event) {
        File outputFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
                + "equipos.csv");
        try {
            FileWriter writer = new FileWriter(outputFile);
            CSVWriter csvWriter = new CSVWriter(writer);
            List<String[]> file = new ArrayList<String[]>();

            for (Object team : this.teamsResults) {
                file.add(new String[]{team.toString()});
            }
            csvWriter.writeAll(file);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
