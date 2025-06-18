import { Component, EventEmitter, Input, Output } from "@angular/core";
import { BotaoComponent } from "../botao/botao.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; 
import { CommonModule } from "@angular/common";


@Component({
	selector: "app-modal",
	imports: [BotaoComponent, ReactiveFormsModule, CommonModule],
	templateUrl: "./modal.component.html",
	styleUrl: "./modal.component.css",
})
export class ModalComponent {
	@Input() confirmMode: boolean = false;
	@Input() message: string = "";
	@Input() zindex: string = "z-3"
	@Output("onClose") close = new EventEmitter<void | boolean>();
	zindexBlackBlock: string = "z-2"

	ngOnInit(){
		const array: string[] = this.zindex.split("-")
		this.zindexBlackBlock = `z-${Number(array[1]) - 1}`
	}

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
