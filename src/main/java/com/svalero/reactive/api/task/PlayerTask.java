package com.svalero.reactive.api.task;

import com.svalero.reactive.api.model.DataPlayer;
import com.svalero.reactive.api.service.NBAService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public class PlayerTask extends Task<Integer> {

    public Consumer<DataPlayer> playerUser;
    public ProgressBar pbProgress;


    public PlayerTask(Consumer<DataPlayer> userPlayer, ProgressBar pbProgress) {
        this.playerUser = userPlayer;
        this.pbProgress = pbProgress;
    }

    @Override
    protected Integer call() throws Exception {
        this.pbProgress.setVisible(true);
        NBAService nbaService = new NBAService();
        nbaService.getPlayers().subscribe(playerUser);
        return null;
    }
}
