package com.svalero.reactive.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPlayer {

    private List<Player> data;
    private Meta meta;
}
