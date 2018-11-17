import { Moment } from 'moment';

export const enum XRole {
  CHECKER = 'CHECKER',
  BROKER = 'BROKER'
}

export interface IUserX {
  id?: number;
  username?: string;
  password?: string;
  fullname?: string;
  phoneNumber?: string;
  xInfo?: string;
  status?: boolean;
  datetime?: Moment;
  role?: XRole;
}

export const defaultValue: Readonly<IUserX> = {
  status: false
};
