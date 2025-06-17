import { Component, Input } from "@angular/core";
import { IconComponent } from "../icon/icon.component";

enum ButtonColors {
	light = "bg-main-300 hover:bg-main-300/80 text-main-500",
	medium = "bg-main-400 hover:bg-main-400/80 text-white",
	dark = "bg-main-500 hover:bg-main-500/80 text-white",
	danger = "bg-danger text-white",
	blue = "bg-blue-500 text-white",
	success = "bg-green-500 text-white",
	yellow = "yellow text-white",
	green = "green text-white",
	red = "red text-white",
}

enum IconSlots {
	LEFT = "left",
	RIGHT = "right",
	ICON_ONLY = "icon-only",
}

enum Sizes {
	extraSmall = "text-xs",
	small = "text-sm",
	medium = "text-md",
	large = "text-lg",
	extraLarge = "text-xl",
	default = medium,
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
	@Input("size") size: string = Sizes.default;
	@Input("color") color: string = "dark";
	@Input("moreStyles") moreStyles: string = "";
	@Input("text") text: string = "";

	get styling() {
		let styles = [
			"rounded-md",
			"p-1",
			"px-3",
			"font-bold",
			"cursor-pointer",
			"flex",
			"justify-center",
			"transicao",
			"items-center",
			Sizes[this.size as keyof typeof Sizes],
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
