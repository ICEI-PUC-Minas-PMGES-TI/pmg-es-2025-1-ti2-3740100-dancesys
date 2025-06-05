import {
	ApplicationConfig,
	LOCALE_ID,
	provideZoneChangeDetection,
} from "@angular/core";
import { provideRouter } from "@angular/router";
import { routes } from "./app.routes";
import { provideHttpClient } from "@angular/common/http";
import { provideEnvironmentNgxMask } from "ngx-mask";
import { registerLocaleData } from "@angular/common";
import localePt from "@angular/common/locales/pt";

registerLocaleData(localePt, "pt");

export const appConfig: ApplicationConfig = {
	providers: [
		provideZoneChangeDetection({ eventCoalescing: true }),
		{ provide: LOCALE_ID, useValue: "pt" },
		provideRouter(routes),
		provideHttpClient(),
		provideEnvironmentNgxMask(),
	],
};
