import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent }  from './app.component';
import { routing }        from './app.routing';
import { CountryService } from './shared/services';
import { HomeComponent } from './home';
import { HttpModule } from '@angular/http';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        routing
    ],
    declarations: [
        AppComponent,
        HomeComponent,
    ],
    providers: [
        CountryService
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }