import { Component, Input, Output, EventEmitter, ElementRef, HostListener, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-box-single',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-box-single.component.html',
  styleUrl: './search-box-single.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SearchBoxSingleComponent),
      multi: true,
    },
  ]
})
export class SearchBoxSingleComponent implements ControlValueAccessor {
  @Input() label: string = '';
  @Input() options: any[] = [];
  @Input() optionLabel: string = '';
  @Input() optionValue: string = '';
  @Input() nullLabel: string = 'Selecione...';
  @Output() searchChange = new EventEmitter<string>();
  @Output() selectionChange = new EventEmitter<any>();

  selectedValue: any = null;
  showDropdown = false;
  searchText: string = '';
  searchTimeout: any;

  constructor(private elementRef: ElementRef) {}

  writeValue(value: any): void {
    this.selectedValue = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  private onChange: any = () => {};
  private onTouched: any = () => {};

  get selectedLabel(): string {
    const selected = this.options.find(
      (option) => this.getPropByPath(option, this.optionValue) === this.selectedValue
    );
    return selected ? this.getPropByPath(selected, this.optionLabel) : '';
  }

  getPropByPath(obj: any, path: string): any {
    return path.split('.').reduce((acc, part) => acc?.[part], obj);
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  onOptionSelect(option: any) {
    const value = this.getPropByPath(option, this.optionValue);
    this.selectedValue = value;
    this.onChange(value);
    this.selectionChange.emit(value);
    this.showDropdown = false;
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
    }, 1000);
  }

  filteredOptions(): any[] {
    const search = this.searchText?.trim().toLowerCase();
    if (!search) return this.options;

    return this.options.filter((option) =>
      this.getPropByPath(option, this.optionLabel)?.toLowerCase().includes(search)
    );
  }
}
