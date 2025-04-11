import { Pipe, PipeTransform } from '@angular/core';
import { UsuarioTipos } from '../models/usuario.model';

@Pipe({
	name: 'tipoUsuario'
})
export class TipoUsuarioPipe implements PipeTransform {

	transform(value: UsuarioTipos): string {
		switch (value) {
			case UsuarioTipos.ADMIN: return "Admin";
			case UsuarioTipos.FUNCIONARIO: return "Funcionário";
			case UsuarioTipos.ALUNO: return "Aluno";
			default: return "Unknown";
		}
	}

}
