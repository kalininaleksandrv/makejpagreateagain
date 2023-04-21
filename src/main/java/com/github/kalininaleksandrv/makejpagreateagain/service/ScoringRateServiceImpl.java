package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.ScoringRate;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ScoringRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoringRateServiceImpl implements ScoringRateService{

    private final ScoringRateRepository scoringRateRepository;

    @Override
    public ScoringRate createOrUpdateScoringRate(Client client) {
        ScoringRate scoringRate = new ScoringRate(client, 1);
        return scoringRateRepository.save(scoringRate);
    }

    @Override
    public List<Integer> getByRateLessThen(Integer rate) {
        List<ScoringRate> rates = scoringRateRepository.findByRateLessThanEqual(rate);
        return rates
                .stream()
                .map(i -> i.getClient().getId())
                .collect(Collectors.toList());
    }
}
