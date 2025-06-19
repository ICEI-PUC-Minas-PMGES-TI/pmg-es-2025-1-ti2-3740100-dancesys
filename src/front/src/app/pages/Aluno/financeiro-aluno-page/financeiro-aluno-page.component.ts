import { Component, OnInit, inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BoletoCardComponent } from "../../../components/boleto-card/boleto.component";
import { AdminService } from "../../../services/admin.service";
import { UsuarioService } from "../../../services/usuario.service";
import { Dividendo, DividendoFilter } from "../../../models/Dividendo.model";
import { DividendoService } from "../../../services/dividendo.service";
import { prettyLog } from "../../../prettylog";
import { AlertService } from "../../../services/Alert.service";

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
	alertService = inject(AlertService)

	boletos: Dividendo[] = [];

	ngOnInit(): void {
		this.buscar();
	}

	buscar() {
		const filtro: DividendoFilter = {
			criadoEm: "",
			pagoEm: "",
			alunos: [this.usuarioService.usuario()!.id],
			status: [1,3],
			tipos: [],
			tamanho: 0,
			pagina: 0,
			orderBy: "",
			order: "",
		};

		this.adminService.filterDividendos(filtro).subscribe({
			next: (response: any) => {
				this.boletos = response.conteudo;
			},
		});
	}

	pagar(id: number) {
		prettyLog(`Pagando o boleto de id: ${id}`);
		this.dividendoService.pagarBoleto(id).subscribe({
			next: () => {
				this.alertService.sucesso("Boleto pago com sucesso")
				this.buscar()
			},
			error: (err: any) => {
			},
		});
	}
}
