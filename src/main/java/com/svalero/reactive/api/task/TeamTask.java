package com.svalero.reactive.api.task;

import com.svalero.reactive.api.model.DataTeam;
import com.svalero.reactive.api.service.NBAService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;


public class TeamTask extends Task<Integer> {

    public Consumer<DataTeam> teamUser;

    public TeamTask(Consumer<DataTeam> teamUser) {
        this.teamUser = teamUser;
    }

    @Override
    protected Integer call() throws Exception {
        NBAService nbaService = new NBAService();
        nbaService.getTeams().subscribe(teamUser);
        return null;
    }
}
