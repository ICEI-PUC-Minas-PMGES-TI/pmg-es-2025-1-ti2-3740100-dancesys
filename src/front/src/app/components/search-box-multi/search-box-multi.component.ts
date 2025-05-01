import { Component, Input, Output, EventEmitter, ElementRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-search-box-multi',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-box-multi.component.html',
  styleUrl: './search-box-multi.component.css'
})
export class SearchBoxMultiComponent {
  @Input() label: string = '';
  @Input() options: any[] = [];
  @Input() optionLabel: string = '';
  @Input() optionValue: string = '';
  @Output() selectionChange = new EventEmitter<any[]>();
  @Input() nullLabel: string = '';
  @Output() searchChange = new EventEmitter<string>();


  selectedValues: any[] = [];
  showDropdown = false;
  searchText: string = '';
  searchTimeout: any;


  constructor(private elementRef: ElementRef) {}

  
  getPropByPath(obj: any, path: string): any {
    return path.split('.').reduce((acc, part) => acc?.[part], obj);
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  isChecked(value: any): boolean {
    return this.selectedValues.includes(value);
  }

  onCheckboxChange(option: any) {
    const value = this.getPropByPath(option, this.optionValue); 
    const index = this.selectedValues.indexOf(value);
  
    if (index > -1) {
      this.selectedValues.splice(index, 1);
    } else {
      this.selectedValues.push(value);
    }
  
    this.selectionChange.emit(this.selectedValues);
  }
  
  get selectedLabels(): string[] {
    return this.options
      .filter(option => this.selectedValues.includes(this.getPropByPath(option, this.optionValue))) 
      .map(option => this.getPropByPath(option, this.optionLabel));
  }
  

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.showDropdown = false;
    }
  }

  onSearchChange(value: string) {
    this.searchText = value;
  
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }
  
    this.searchTimeout = setTimeout(() => {
      this.searchChange.emit(this.searchText.trim());
    }, 1000); // 1 segundo
  }
  

}
