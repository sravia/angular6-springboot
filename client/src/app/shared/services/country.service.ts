import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { environment } from '../../../environments/environment';
import { Country } from '../models';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class CountryService {
    constructor(private http: Http) { }

    getByCountryCode(countryCode: Number) {
        return this.http.get(`${environment.apiUrl}/countries/${countryCode}`);
    }

    getAll() : Observable<Country[]> {
        return this.http.get(`${environment.apiUrl}/countries`).pipe(map(res => res.json()));
    }
}