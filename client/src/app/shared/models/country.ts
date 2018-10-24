export class Country {
    name: String;
    countryCode: Number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}