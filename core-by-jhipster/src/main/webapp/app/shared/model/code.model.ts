import { IProduct } from 'app/shared/model//product.model';

export interface ICode {
  id?: number;
  code?: string;
  pwd?: IProduct;
}

export const defaultValue: Readonly<ICode> = {};
