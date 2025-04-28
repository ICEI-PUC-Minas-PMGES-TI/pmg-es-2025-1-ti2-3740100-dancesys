import { Pipe, PipeTransform } from "@angular/core";
import { TipoAluno } from "../models/aluno.model";

@Pipe({
	name: "tipoAluno",
})
export class TipoAlunoPipe implements PipeTransform {
	transform(value: TipoAluno): string {
		switch (value) {
			case TipoAluno.FIXO:
				return "Fixo";
			case TipoAluno.FLEXIVEL:
				return "Flexível";
			default:
				return "Unknown";
		}
	}
}
