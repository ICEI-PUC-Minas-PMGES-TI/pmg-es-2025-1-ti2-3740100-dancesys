import { Component, Input } from '@angular/core';

enum Icons {
	home = "home_icon.svg",
	calendar = "calendar_icon.svg",
	money = "money_icon.svg",
	events = "events_icon.svg",
	admin = "admin_icon.svg",
	account = "account_icon.svg"
}

@Component({
	selector: 'app-icon',
	imports: [],
	templateUrl: './icon.component.html',
	styleUrl: './icon.component.css'
})
export class IconComponent {

	@Input({ required: true }) name!: string;

	public get iconSrc() {
		return Icons[this.name as keyof typeof Icons];
	}

}
