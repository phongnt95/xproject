import { Moment } from 'moment';
import { IComment } from 'app/shared/model//comment.model';
import { IProduct } from 'app/shared/model//product.model';

export interface IComment {
  id?: number;
  content?: string;
  datetime?: Moment;
  sub?: IComment;
  comments?: IProduct;
}

export const defaultValue: Readonly<IComment> = {};
