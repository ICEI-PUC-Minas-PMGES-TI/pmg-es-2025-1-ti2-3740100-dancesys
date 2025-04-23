import { Component, Input } from "@angular/core";
import { NavbarButtonComponent } from "../navbar-button/navbar-button.component";
import { IconComponent } from "../icon/icon.component";
import { BotaoComponent } from "../botao/botao.component";

@Component({
	selector: "app-navbar",
	imports: [NavbarButtonComponent, IconComponent, BotaoComponent],
	templateUrl: "./navbar.component.html",
	styleUrl: "./navbar.component.css",
})
export class NavbarComponent {}
