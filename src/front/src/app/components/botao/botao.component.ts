import { Component, Input } from "@angular/core";
import { IconComponent } from "../icon/icon.component";

enum ButtonColors {
	light = "bg-main-300 text-main-500",
	medium = "bg-main-400 text-white",
	dark = "bg-main-500 text-white",
	danger = "bg-danger text-white",
	blue = "bg-blue-500 text-white",
	success = "bg-green-500 text-white",
}

enum IconSlots {
	LEFT = "left",
	RIGHT = "right",
	ICON_ONLY = "icon-only",
}

@Component({
	selector: "button[appButton]",
	imports: [IconComponent],
	templateUrl: "./botao.component.html",
	styleUrl: "./botao.component.css",
})
export class BotaoComponent {
	@Input("icon") iconName: string = "";
	@Input("icon-slot") iconSlot: string = IconSlots.LEFT;
	@Input("icon-size") iconSize: string = "medium";
	@Input("color") color: string = "dark";
	@Input("moreStyles") moreStyles: string = "";

	get styling() {
		let styles = [
			"rounded-md",
			"p-1",
			"px-3",
			"font-bold",
			"cursor-pointer",
			"flex",
			"justify-center",
			"items-center",
		];
		if (this.iconName && this.iconSlot === IconSlots.ICON_ONLY) {
			styles[0] = "rounded-4xl";
			styles[1] = "p-1";
			styles.splice(2, 1);
		}
		return [
			...styles,
			...ButtonColors[this.color as keyof typeof ButtonColors].split(" "),
			...this.moreStyles.split(" "),
		];
	}
}
