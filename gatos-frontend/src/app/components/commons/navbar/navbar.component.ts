import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { AuthenticationService } from '../../../services/authentication.service'; 


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  employeeLogged = false;
  isAdmin = false;
  employeeName = '';
  openMenu = false;

  constructor(private authenticationService: AuthenticationService, private router: Router){}

  ngOnInit(): void {
      this.employeeLogged = this.authenticationService.isLogged();
      this.isAdmin = this.authenticationService.isAdmin();

      if(this.employeeLogged){
        const employee = this.authenticationService.getCurrentEmployee();
        this.employeeName = employee?.nombre || '';
      }
  }

  toggleMenu(){
    this.openMenu = !this.openMenu;
  }

  logout(){
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
