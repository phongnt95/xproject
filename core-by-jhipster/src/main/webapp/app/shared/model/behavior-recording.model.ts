import { Moment } from 'moment';

export const enum Action {
  VIEW = 'VIEW',
  GETCODE = 'GETCODE',
  COMMENT = 'COMMENT'
}

export interface IBehaviorRecording {
  id?: number;
  action?: Action;
  datetime?: Moment;
}

export const defaultValue: Readonly<IBehaviorRecording> = {};
