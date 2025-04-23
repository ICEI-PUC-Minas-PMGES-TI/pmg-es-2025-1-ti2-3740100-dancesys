import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
	name: 'statusUsuario'
})
export class StatusUsuarioPipe implements PipeTransform {

	transform(value: boolean): string {
		if (value) {
			return "Ativo";
		}
		return "Inativo";
	}

}
