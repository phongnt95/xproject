import { IProduct } from 'app/shared/model//product.model';

export interface IServiceOpt {
  id?: number;
  name?: string;
  opts?: IProduct[];
}

export const defaultValue: Readonly<IServiceOpt> = {};
