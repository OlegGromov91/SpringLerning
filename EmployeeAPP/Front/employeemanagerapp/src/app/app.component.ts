import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Employee } from './employee';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  //title = 'employeemanagerapp';
 public employees: Employee[];

 constructor(private employeeService: EmployeeService)
 {}
 ngOnInit()
 {
   this.getEmployees();
 }


 public getEmployees(): void {
   this.employeeService.getEmployees().subscribe(
     (responce: Employee[]) =>
     { this.employees = responce;},
     (error : HttpErrorResponse) =>
     {alert(error.message)}
   );
 }


}
