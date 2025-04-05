import { Component } from '@angular/core';
import { NavbarButtonComponent } from '../navbar-button/navbar-button.component';
import { IconComponent } from '../icon/icon.component';

@Component({
	selector: 'app-navbar',
	imports: [NavbarButtonComponent, IconComponent],
	templateUrl: './navbar.component.html',
	styleUrl: './navbar.component.css'
})
export class NavbarComponent {

}
