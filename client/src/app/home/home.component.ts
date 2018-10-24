import { Component } from '@angular/core';
import { CountryService } from '../shared/services';

@Component({templateUrl: 'home.component.html'})
export class HomeComponent{
    phoneNumber = "";
    countries = [];
    country;
    error;

    constructor(private countryService: CountryService) {
        this.getCountries();
    }

    private getCountries(){
        this.countryService.getAll().subscribe(
            response => { 
                this.countries = response;
            },
            error => {
                this.error = "Unable to communicate with server!";
            }
        );
    }

    private filter(){
        this.error = "";
        this.country = "";

        let formattedPhoneNumber = this.phoneNumber.replace(/[\s+\+\-]/gi,"");
        let countries = this.countries.filter(country => formattedPhoneNumber.startsWith(country.countryCode))
        countries.sort((a, b) => b.countryCode - a.countryCode);

        if(this.phoneNumber){
            if(countries.length == 0){
                this.error = "Invalid phone number!"
            }else{
                this.country = countries[0].name
            }
        }
    }
}