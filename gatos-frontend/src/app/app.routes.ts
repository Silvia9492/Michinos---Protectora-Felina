import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { CatsListComponent } from './components/cats/cats-list/cats-list.component';
import { CatDetailComponent } from './components/cats/cat-detail/cat-detail.component';
import { CatFormComponent } from './components/cats/cat-form/cat-form.component';
import { EmployeesListComponent } from './components/employees/employees-list/employees-list.component';
import { EmployeeFormComponent } from './components/employees/employee-form/employee-form.component';
import { authenticationGuard } from './guards/authentication.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent, canActivate: [authenticationGuard] },
    { path: 'gatos', component: CatsListComponent, canActivate: [authenticationGuard] },
    { path: 'gatos/nuevo', component: CatFormComponent, canActivate: [authenticationGuard] },
    { path: 'gatos/editar/:id', component: CatFormComponent, canActivate: [authenticationGuard] },
    { path: 'gatos/:id', component: CatDetailComponent, canActivate: [authenticationGuard] },
    { path: 'empleados', component: EmployeesListComponent, canActivate: [authenticationGuard, adminGuard] },
    { path: 'empleados/nuevo', component: EmployeeFormComponent, canActivate: [authenticationGuard, adminGuard] },
    { path: 'empleados/editar/:id', component: EmployeeFormComponent, canActivate: [authenticationGuard, adminGuard] },
    { path: '**', redirectTo: '/login' }
];