import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoletoCardComponent } from '../../../components/boleto-card/boleto.component';
import { AdminService } from '../../../services/admin.service';
import { UsuarioService } from '../../../services/usuario.service';
import { Dividendo } from '../../../models/Dividendo.model';

@Component({
  selector: 'app-financeiro-aluno-page',
  standalone: true,
  imports: [CommonModule, BoletoCardComponent],
  templateUrl: './financeiro-aluno-page.component.html',
  styleUrls: ['./financeiro-aluno-page.component.css']
})
export class FinanceiroAlunoPageComponent implements OnInit {
  adminService = inject(AdminService);
  usuarioService = inject(UsuarioService);

  boletos: Dividendo[] = [];

  ngOnInit(): void {
  /*  const alunoId = this.usuarioService.usuario()?.id;
    if (alunoId) {
      this.adminService.getBoletosByAluno(alunoId).subscribe(response => {
        this.boletos = response.conteudo;
      });
    }*/
  }
}