import {
	Component,
	EventEmitter,
	Input,
	Output,
	SimpleChanges,
	OnChanges,
} from "@angular/core";
import { BotaoComponent } from "../botao/botao.component";
import { IconComponent } from "../icon/icon.component";
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";

export type ItensPorPagina = 0 | 5 | 10 | 25 | 50 | 100;

@Component({
	selector: "app-simple-table",
	imports: [BotaoComponent, IconComponent, CommonModule, ReactiveFormsModule],
	templateUrl: "./simple-table.component.html",
	styleUrl: "./simple-table.component.css",
})
export class SimpleTableComponent implements OnChanges {
	@Input() colunas: {
		chave: string;
		titulo: string;
		width?: string;
		formatar?: (valor: any, item?: any) => string;
	}[] = [];
	@Input() dados: any[] = [];
	@Input() botoesAcoes: {
		icon: string;
		title: string | ((item: any) => string);
		cor: string;
		callback: (item: any) => void;
	}[] = [];
	@Input() totalItens: number = 0;
	@Input() paged: boolean = true;

	@Output() paginacaoChange = new EventEmitter<{
		paginaSelecionada: number;
		itensPage: ItensPorPagina;
	}>();

	itensForm: FormGroup;
	pgsLs: number[] = [];
	paginaSelecionada: number = 1;
	paginaIn: number = 1;
	totalPaginas!: number;
	itensPage: ItensPorPagina = 10;

	constructor(private fb: FormBuilder) {
		this.itensForm = this.fb.group({
			itensPage: [this.itensPage],
		});
	}

	ngOnChanges(changes: SimpleChanges) {
		if (
			changes["totalItens"] &&
			changes["totalItens"].currentValue !== undefined
		) {
			this.calcTotalPaginas();
			this.gerarPgsLs();
		}
	}

	ngOnInit(): void {
		this.itensForm.get("itensPage")?.valueChanges.subscribe((valor) => {
			this.onItensPorPaginaChange(valor);
		});
	}

	onItensPorPaginaChange(valor: ItensPorPagina): void {
		this.itensPage = valor;
		this.paginaSelecionada = 1;
		if (valor == 0) {
			this.totalPaginas = 1;
		} else {
			this.calcTotalPaginas();
		}
		this.gerarPgsLs();
		this.emitirPaginacao();
	}

	selecionarPagina(pagina: number): void {
		this.paginaSelecionada = pagina;
		this.emitirPaginacao();
	}

	primeiraPagina() {
		this.paginaSelecionada = this.paginaIn;
		this.gerarPgsLs();
		this.emitirPaginacao();
	}

	ultimaPagina() {
		this.paginaSelecionada = this.totalPaginas;
		this.gerarPgsLsUltimo();
		this.emitirPaginacao();
	}

	proximaPagina() {
		if (this.paginaSelecionada !== this.totalPaginas) {
			this.paginaSelecionada++;
			this.emitirPaginacao();
		}

		if (this.paginaSelecionada > this.pgsLs[4]) {
			this.gerarPgsLsProximo();
		}
	}

	anteriorPagina() {
		if (this.paginaSelecionada !== this.paginaIn) {
			this.paginaSelecionada--;
			this.emitirPaginacao();
		}

		if (this.paginaSelecionada < this.pgsLs[0]) {
			this.gerarPgsLsAnterios();
		}
	}

	calcTotalPaginas() {
		this.totalPaginas = Math.ceil(this.totalItens / this.itensPage);
	}

	gerarPgsLs() {
		this.pgsLs = [];
		for (let i = 0; i < 5; i++) {
			if (this.paginaSelecionada + i <= this.totalPaginas) {
				this.pgsLs.push(this.paginaSelecionada + i);
			}
		}
	}

	gerarPgsLsUltimo() {
		this.pgsLs = [];
		let count = this.totalPaginas % 5;
		if (count == 0) {
			for (let i = 4; i >= 0; i--) {
				this.pgsLs.push(this.paginaSelecionada - i);
			}
		} else {
			for (
				let i = this.totalPaginas - count;
				i < this.totalPaginas;
				i++
			) {
				this.pgsLs.push(i + 1);
			}
		}
	}

	gerarPgsLsProximo() {
		this.pgsLs = [];
		for (let i = 0; i < 5; i++) {
			if (this.paginaSelecionada + i <= this.totalPaginas) {
				this.pgsLs.push(this.paginaSelecionada + i);
			}
		}
	}

	gerarPgsLsAnterios() {
		this.pgsLs = [];
		for (let i = 4; i >= 0; i--) {
			if (this.paginaSelecionada - i >= this.paginaIn) {
				this.pgsLs.push(this.paginaSelecionada - i);
			}
		}
	}

	emitirPaginacao() {
		this.paginacaoChange.emit({
			paginaSelecionada: this.paginaSelecionada,
			itensPage: this.itensPage,
		});
	}

	getValor(item: any, chave: string): any {
		const valor = chave.split(".").reduce((obj, key) => {
			return obj?.[key];
		}, item);

		return valor;
	}

	getBotaoTitulo(value: string | ((item: any) => string), item: any): string {
		if (typeof value === "string") {
			return value;
		}
		return value(item);
	}
}
