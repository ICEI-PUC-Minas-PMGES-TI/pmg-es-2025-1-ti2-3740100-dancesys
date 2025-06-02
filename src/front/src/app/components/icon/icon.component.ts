import { Component, Input } from "@angular/core";

enum Icons {
	home = "home_icon.svg",
	calendar = "calendar_icon.svg",
	money = "money_icon.svg",
	events = "events_icon.svg",
	admin = "admin_icon.svg",
	account = "account_icon.svg",
	edit = "edit_icon.svg",
	warning = "warning_icon.svg",
	add_circle = "addcircle_icon.svg",
	search = "search_icon.svg",
	reload = "reload_icon.svg",
	delete = "delete_icon.svg",
	menu = "menu_icon.svg",
	check = "check_icon.svg",
	view = "view_icon.svg",
	indicadores = "indicadores_icon.svg"
}

enum Sizes {
	extraSmall = "size-[1.2rem]",
	small = "size-[1.6rem]",
	medium = "size-[2.2rem]",
	large = "size-[2.6rem]",
	extraLarge = "size-[2.85rem]",
}

@Component({
	selector: "app-icon",
	imports: [],
	templateUrl: "./icon.component.html",
	styleUrl: "./icon.component.css",
})
export class IconComponent {
	@Input({ required: true }) name!: string;
	@Input("size") size: string = "1/1";
	@Input("fill") fill: boolean = false;

	public get iconSrc() {
		return Icons[this.name as keyof typeof Icons];
	}

	public get styling() {
		const styles = [Sizes[this.size as keyof typeof Sizes], "p-1"];
		if (this.fill) {
			styles.push("bg-main-500");
			styles.push("rounded-full");
		}
		return styles;
	}
}
