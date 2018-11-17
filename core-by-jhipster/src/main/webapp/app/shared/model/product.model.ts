import { IUserX } from 'app/shared/model//user-x.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  status?: boolean;
  age?: number;
  uploadBy?: IUserX;
}

export const defaultValue: Readonly<IProduct> = {
  status: false
};
