package com.dancesys.dancesys.Job;

import com.dancesys.dancesys.service.DividendoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DividendoJob {

    @Autowired
    private DividendoService dividendoService;

    @Scheduled(cron = "0 0 0 * * *", zone="America/Sao_Paulo")
    public void verificarBoletos(){
        dividendoService.jobDividendosDiario();
    }
}
