import { IProduct } from 'app/shared/model//product.model';

export interface IImage {
  id?: number;
  url?: string;
  isWallpaper?: boolean;
  images?: IProduct;
}

export const defaultValue: Readonly<IImage> = {
  isWallpaper: false
};
