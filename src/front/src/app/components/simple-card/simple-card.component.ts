import { Component, Input } from "@angular/core";
import { IconComponent } from "../icon/icon.component";

@Component({
	selector: "app-simple-card",
	imports: [IconComponent],
	templateUrl: "./simple-card.component.html",
	styleUrl: "./simple-card.component.css",
})
export class SimpleCardComponent {
	@Input() checked: boolean = false;
	@Input() imageUrl: string = "";
	@Input({ required: true }) title!: string;
	@Input() subtitle: string = "";
}
