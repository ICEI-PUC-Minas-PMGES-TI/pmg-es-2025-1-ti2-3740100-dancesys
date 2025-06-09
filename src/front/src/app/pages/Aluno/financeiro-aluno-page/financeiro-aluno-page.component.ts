import { Component, OnInit, inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BoletoCardComponent } from "../../../components/boleto-card/boleto.component";
import { AdminService } from "../../../services/admin.service";
import { UsuarioService } from "../../../services/usuario.service";
import { Dividendo, DividendoFilter } from "../../../models/Dividendo.model";
import { DividendoService } from "../../../services/dividendo.service";
import { prettyLog } from "../../../prettylog";

@Component({
	selector: "app-financeiro-aluno-page",
	standalone: true,
	imports: [CommonModule, BoletoCardComponent],
	templateUrl: "./financeiro-aluno-page.component.html",
	styleUrls: ["./financeiro-aluno-page.component.css"],
})
export class FinanceiroAlunoPageComponent implements OnInit {
	adminService = inject(AdminService);
	usuarioService = inject(UsuarioService);
	dividendoService = inject(DividendoService);

	boletos: Dividendo[] = [];

	ngOnInit(): void {
		this.buscar();
	}

	buscar() {
		const filtro: DividendoFilter = {
			criadoEm: "",
			pagoEm: "",
			alunos: [this.usuarioService.usuario()!.id],
			status: [],
			tipos: [],
			tamanho: 0,
			pagina: 0,
			orderBy: "",
			order: "",
		};

		this.adminService.filterDividendos(filtro).subscribe({
			next: (response: any) => {
				this.boletos = response.conteudo;
				console.log(this.boletos);
			},
		});
	}

	pagar(id: number) {
		prettyLog(`Pagando o boleto de id: ${id}`);
		this.dividendoService.pagarBoleto(id).subscribe({
			next: () => {
				this.buscar();
			},
			error: (err: any) => {
				console.log(err);
			},
		});
	}
}
