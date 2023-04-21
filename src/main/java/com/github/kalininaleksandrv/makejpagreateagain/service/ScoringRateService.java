package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.ScoringRate;

import java.util.List;

public interface ScoringRateService {

    ScoringRate createOrUpdateScoringRate(Client client);
    List<Integer> getByRateLessThen(Integer rate);
}
