import {
	Component,
	Input,
	Output,
	EventEmitter,
	ElementRef,
	HostListener,
	forwardRef,
  } from '@angular/core';
  import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
  import { CommonModule } from '@angular/common';
  import { FormsModule } from '@angular/forms';
  
  @Component({
	selector: 'app-search-box-multi',
	standalone: true,
	imports: [CommonModule, FormsModule],
	templateUrl: './search-box-multi.component.html',
	styleUrls: ['./search-box-multi.component.css'],
	providers: [
	  {
		provide: NG_VALUE_ACCESSOR,
		useExisting: forwardRef(() => SearchBoxMultiComponent),
		multi: true,
	  },
	],
  })
  export class SearchBoxMultiComponent implements ControlValueAccessor {
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
  
	writeValue(value: any): void {
		if (value && Array.isArray(value)) {
		  const validIds = this.options.map(option => this.getPropByPath(option, this.optionValue));
		  this.selectedValues = value.filter((id: any) => validIds.includes(id));
		} else {
		  this.selectedValues = [];
		}
	  }
	  
  
	registerOnChange(fn: any): void {
	  this.onChange = fn;
	}
  
	registerOnTouched(fn: any): void {
	  this.onTouched = fn;
	}
  
	private onChange: any = () => {};
	private onTouched: any = () => {};
  
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


		const newSelectedValues = [...this.selectedValues];
		this.selectionChange.emit(newSelectedValues);
		this.onChange(newSelectedValues);
	}

  
	get selectedLabels(): string[] {
	  return this.options
		.filter((option) =>
		  this.selectedValues.includes(
			this.getPropByPath(option, this.optionValue),
		  ),
		)
		.map((option) => this.getPropByPath(option, this.optionLabel));
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
  
	filteredOptions(): any[] {
	  if (!this.searchText?.trim()) {
		return this.options.filter(
		  (option) =>
			this.selectedValues.includes(this.getPropByPath(option, this.optionValue)),
		);
	  }
  
	  const lowerSearch = this.searchText.toLowerCase();
  
	  return this.options.filter((option) =>
		this.getPropByPath(option, this.optionLabel).toLowerCase().includes(lowerSearch),
	  );
	}
  }
  