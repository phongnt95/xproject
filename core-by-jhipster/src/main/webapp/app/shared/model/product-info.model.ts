import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';

export interface IProductInfo {
  id?: number;
  weight?: number;
  high?: number;
  measurement1?: string;
  measurement2?: string;
  measurement3?: string;
  description?: string;
  phoneNumber?: string;
  joinTime?: Moment;
  comeFrom?: string;
  xInfo?: string;
  productInfo?: IProduct;
}

export const defaultValue: Readonly<IProductInfo> = {};
