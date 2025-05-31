package com.dancesys.dancesys.Job;

import com.dancesys.dancesys.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DividendoMensalidadeJobMensal {

    @Autowired
    UsuarioService usuarioService;

    @Scheduled(cron = "0 0 1 1 * *", zone = "America/Sao_Paulo")
    public void gerarBoletosMensalidades() throws Exception{
        usuarioService.gerarBoletosMensalJob();
    }
}
