export interface IClasses {
  id?: number;
  title?: string;
  description?: string;
  techer_name?: string;
  price?: number;
  location?: string;
  duration?: number;
}

export const defaultValue: Readonly<IClasses> = {};
