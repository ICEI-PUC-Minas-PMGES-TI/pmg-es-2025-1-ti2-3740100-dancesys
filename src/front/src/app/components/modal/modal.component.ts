import { Component, EventEmitter, Input, Output } from "@angular/core";
import { BotaoComponent } from "../botao/botao.component";

@Component({
	selector: "app-modal",
	imports: [BotaoComponent],
	templateUrl: "./modal.component.html",
	styleUrl: "./modal.component.css",
})
export class ModalComponent {
	@Input() confirmMode: boolean = false;
	@Input() message: string = "";
	@Output("onClose") close = new EventEmitter<void | boolean>();

	onClose() {
		this.close.emit();
	}

	onConfirm() {
		this.close.emit(true);
	}

	onCancel() {
		this.close.emit(false);
	}
}
