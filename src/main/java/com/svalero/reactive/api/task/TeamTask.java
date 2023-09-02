package com.svalero.reactive.api.task;

import com.svalero.reactive.api.model.DataTeam;
import com.svalero.reactive.api.service.NBAService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;


public class TeamTask extends Task<Integer> {

    public Consumer<DataTeam> teamUser;
    public ProgressBar pbProgress;

    public TeamTask(Consumer<DataTeam> teamUser, ProgressBar pbProgress) {
        this.teamUser = teamUser;
        this.pbProgress = pbProgress;
    }

    @Override
    protected Integer call() throws Exception {
        this.pbProgress.setVisible(true);
        NBAService nbaService = new NBAService();
        nbaService.getTeams().subscribe(teamUser);
        return null;
    }
}
