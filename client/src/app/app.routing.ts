import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);