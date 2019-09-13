export interface IGroup {
  id?: number;
  title?: string;
  details?: string;
  userId?: number;
}

export const defaultValue: Readonly<IGroup> = {};
