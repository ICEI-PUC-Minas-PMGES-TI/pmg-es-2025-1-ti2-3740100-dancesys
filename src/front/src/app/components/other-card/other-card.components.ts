import { Component, Input } from "@angular/core";
import { IconComponent } from "../icon/icon.component";

@Component({
	selector: "app-other-card",

	imports: [IconComponent],
	templateUrl: "./other-card.components.html",
	styleUrl: "./other-card.components.css",

})
export class OtherCardComponent {

	@Input() checked: boolean = false;
	@Input() title!: string;
	@Input() subtitle: string = "";
	@Input() icon: string = "";
}
