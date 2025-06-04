import { Component, Input, Output, EventEmitter, NgModule, HostListener, ElementRef, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms'; 

@Component({
  selector: 'app-multi-select-input',
  standalone: true,
  imports: [ CommonModule, FormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiSelectInputComponent),
      multi: true
    }
  ],
  templateUrl: './multi-select-input.component.html',
  styleUrl: './multi-select-input.component.css'
})
export class MultiSelectInputComponent {
  @Input() label: string = '';
  @Input() options: any[] = [];
  @Input() optionLabel: string = '';
  @Input() optionValue: string = '';
  @Output() selectionChange = new EventEmitter<any[]>();
  @Input() nullLabel: string = '';

  selectedValues: any[] = [];
  showDropdown = false;

  constructor(private elementRef: ElementRef) {}

  private onChange = (_: any) => {};
  private onTouched = () => {};

  writeValue(value: any[]): void {
    this.selectedValues = value || [];
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

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
  
    this.onChange(this.selectedValues);
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

}
