import { CommonModule, DatePipe } from "@angular/common";
import { Component, Input } from "@angular/core";
import { UsuarioTipos } from "../../models/usuario.model";
import { TipoAluno } from "../../models/aluno.model";
import { BotaoComponent } from "../botao/botao.component";

export type ItemDeCalendario = {
	tipoUsuario: UsuarioTipos;
	tipoAluno?: TipoAluno;
	dataHorario: Date;
	title: string;
	subtitle: string;
	button?: { label: string; click: Function; cor: string };
};

@Component({
	selector: "app-calendario-item",
	imports: [CommonModule, BotaoComponent, DatePipe],
	standalone: true,
	templateUrl: "./calendario-item.component.html",
	styleUrl: "./calendario-item.component.css",
})
export class CalendarioItemComponent {
	@Input({ required: true }) item!: ItemDeCalendario;
	tiposUsuario = UsuarioTipos;
	tiposAluno = TipoAluno;

	diasDaSemana: string[] = [
		"Domingo",
		"Segunda-Feira",
		"Terça-Feira",
		"Quarta-Feira",
		"Quinta-Feira",
		"Sexta-Feira",
		"Sábado",
	];

	getHoras(date: Date) {
		const horas: string = date.getHours().toString().padStart(2, "0");
		const minutos: string = date.getMinutes().toString().padStart(2, "0");
		return `${horas}:${minutos}`;
	}

	getButtonCor(): string {
		if (this.item.button) {
			return this.item.button.cor;
		}
		return "default";
	}
}
