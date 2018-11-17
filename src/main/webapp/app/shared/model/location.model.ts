import { IProduct } from 'app/shared/model//product.model';

export interface ILocation {
  id?: number;
  streetAddress?: string;
  city?: string;
  district?: string;
  ward?: string;
  locationOf?: IProduct;
}

export const defaultValue: Readonly<ILocation> = {};
