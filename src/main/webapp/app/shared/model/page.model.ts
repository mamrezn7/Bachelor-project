export interface IPage {
  id?: number;
  title?: string;
  price?: number;
  details?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IPage> = {};
