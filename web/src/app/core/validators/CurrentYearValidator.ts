
import { AbstractControl, ValidatorFn } from '@angular/forms';

export function CurrentYearValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    if (value) {
      const date = new Date(value);
      const currentYear = new Date().getFullYear();
      return date.getFullYear() === currentYear ? null : { 'currentYear': { value: control.value } };
    }
    return null;
  };
}
