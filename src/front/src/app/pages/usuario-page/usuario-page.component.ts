import { Component, inject } from '@angular/core';
import { BotaoComponent } from '../../components/botao/botao.component';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-usuario-page',
  standalone : true,
  imports: [BotaoComponent],
  templateUrl: './usuario-page.component.html',
  styleUrl: './usuario-page.component.css'
})

export class UsuarioPageComponent {

  usuarioService = inject(UsuarioService);

  deslogar() {
    this.usuarioService.deslogar();
  }

}
