import { Component, Input } from '@angular/core';

enum ButtonColors {
	light = "bg-main-300 text-main-500",
	medium = "bg-main-400 text-white",
	dark = "bg-main-500 text-white",
	danger = "bg-danger text-white",
}

@Component({
	selector: 'button[appButton]',
	imports: [],
	templateUrl: './botao.component.html',
	styleUrl: './botao.component.css'
})
export class BotaoComponent {

	@Input("icon") iconName?: string;
	@Input("color") color?: string = "dark";
	@Input("moreStyles") moreStyles: string = "";

	get styling() {
		let styles = ["rounded-md", "p-1", "px-3", "font-bold", "cursor-pointer"]
		return [...styles, ...ButtonColors[this.color as keyof typeof ButtonColors].split(" "), ...this.moreStyles.split(" ")];

	}

}
